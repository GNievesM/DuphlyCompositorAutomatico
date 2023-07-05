package RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteeSolver;

import DataDefinition.Chord;
import DataDefinition.Note;
import EvolutionaryAlgorithm.ConditionEvolvableClass;
import EvolutionaryAlgorithm.GenericEvolutionaryAlgorithm;
import EvolutionaryAlgorithm.IEvolvableClass;
import ImprovisationRules.Util;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedCondition;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import Style.AbstractStyle;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;


public class DivideAndConquerEvolutionarySolve extends GuaranteeConditionSolver {
    
    int threshold;
    int populationQuantity;
    int crossoverPercentage;
    int mutationPercentage;
    int mutationLimitPerSubject;
    int iterationLimit;

    public DivideAndConquerEvolutionarySolve(int threshold, int populationQuantity, int crossoverPercentage, int mutationPercentage, int mutationLimitPerSubject, int iterationLimit) {
        this.threshold = threshold;
        this.populationQuantity = populationQuantity;
        this.crossoverPercentage = crossoverPercentage;
        this.mutationPercentage = mutationPercentage;
        this.mutationLimitPerSubject = mutationLimitPerSubject;
        this.iterationLimit = iterationLimit;
    }
    
    @Override
    public List<Note> Solve(List<Chord> base, List<Note> melody, Double start, Double end, AbstractStyle style, GuaranteedCondition gc, SpecificModification sm) {
        List<IEvolvableClass> initialPopulation = new ArrayList<>();
        for (int i = 0; i < this.populationQuantity; i++) {
            ConditionEvolvableClass cec = new ConditionEvolvableClass("" + i, base, FrameWorkUtil.copyMelody(melody), start, end, gc, sm, this.mutationLimitPerSubject, style);
            cec.mutate(cec.generateFactorCrossOverList());
            initialPopulation.add(cec);
        }
        
        GenericEvolutionaryAlgorithm ea = new GenericEvolutionaryAlgorithm(this.threshold, populationQuantity, (this.populationQuantity * mutationPercentage) / 100, (this.populationQuantity * this.mutationPercentage / 100), initialPopulation, this.iterationLimit);
        IEvolvableClass solution = ea.findBestSolution();
        System.out.println("Fitness of solution 1: " + solution.evaluateFitness());
        
        List<Pair<Integer, Integer>> partitions = solution.faultyPartition();
        melody = ((ConditionEvolvableClass) solution).getMelody();
        ConditionEvolvableClass sol1 = (ConditionEvolvableClass) solution.clone();
        
        for (Pair<Integer, Integer> partition : partitions) {
            Integer partitionStart = partition.getKey();
            Integer partitionEnd = partition.getValue();
            GuaranteeConditionSolver gcs = new EvolutionarySolve(70, (partitionEnd - partitionStart) * 10, 0, 80, 1, 1000);
            melody = gcs.Solve(base, melody, Util.calculateTimeSumByPosition(melody, partitionStart), Util.calculateTimeSumByPosition(melody, partitionEnd), style, gc, sm);
        }
        
        ConditionEvolvableClass sol2 = new ConditionEvolvableClass("0", base, FrameWorkUtil.copyMelody(melody), start, end, gc, sm, this.mutationLimitPerSubject, style);
        System.out.println("Fitness of solution 2: " + sol2.evaluateFitness());
        
        GuaranteeConditionSolver gcs = new EvolutionarySolve(this.threshold, this.populationQuantity, this.crossoverPercentage, this.mutationPercentage, this.mutationLimitPerSubject, this.iterationLimit);
        melody = gcs.Solve(base, melody, start, end, style, gc, sm);
        ConditionEvolvableClass sol3 = new ConditionEvolvableClass("0", base, FrameWorkUtil.copyMelody(melody), start, end, gc, sm, this.mutationLimitPerSubject, style);
        System.out.println("Fitness of solution 3: " + sol3.evaluateFitness());
        
        ConditionEvolvableClass bestSolution = sol1;
        
        if (bestSolution.evaluateFitness() <= sol2.evaluateFitness()) {
            bestSolution = sol2;
        }
        if (bestSolution.evaluateFitness() <= sol3.evaluateFitness()) {
            bestSolution = sol3;
        }
        
        System.out.println("Fitness Result:");
        System.out.println(bestSolution.evaluateFitness());
        System.out.println("Solution:");
        bestSolution.PrintMelody();
        
        return bestSolution.getMelody();
    }
    
}
