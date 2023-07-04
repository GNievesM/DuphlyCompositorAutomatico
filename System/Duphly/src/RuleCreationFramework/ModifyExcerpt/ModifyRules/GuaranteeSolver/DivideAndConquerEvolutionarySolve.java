/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Gaston
 */
public class DivideAndConquerEvolutionarySolve extends GuaranteeConditionSolver{
    
    int treshold;
    int poblationQuantity;
    int crossOverPercentage;
    int mutationPercentage;
    int mutationLimitPerSubject;
    int iterationLimit;

    public DivideAndConquerEvolutionarySolve(int treshold, int poblationQuantity, int crossOverPercentage, int mutationPercentage, int mutationLimitPerSubject, int iterationLimit) {
        this.treshold = treshold;
        this.poblationQuantity = poblationQuantity;
        this.crossOverPercentage = crossOverPercentage;
        this.mutationPercentage = mutationPercentage;
        this.mutationLimitPerSubject = mutationLimitPerSubject;
        this.iterationLimit = iterationLimit;
    }
    
    @Override
    public List<Note> Solve(List<Chord> base, List<Note> melody, Double start, Double end, AbstractStyle style, GuaranteedCondition gc, SpecificModification sm) {
        List<IEvolvableClass> init = new ArrayList<>();
        //buscar que esta chueco
        //crear un evolutivo para esa parte
        //juntar con el resto 
        //testear todo junto.
        System.out.println("Solving....");
        for (int i = 0; i < this.poblationQuantity; i++) {
            ConditionEvolvableClass cec= new ConditionEvolvableClass(""+i, base,FrameWorkUtil.copyMelody(melody) ,start,end,gc,sm,this.mutationLimitPerSubject,style);//new Double(0),new Double(10), guaranteedCondition, modification);
            cec.mutate(cec.generateFactorCrossOverList()); 
            init.add(cec);
        }    
       GenericEvolutionaryAlgorithm ea = new GenericEvolutionaryAlgorithm( this.treshold, poblationQuantity,(this.poblationQuantity*mutationPercentage)/100,(this.poblationQuantity*this.mutationPercentage/100), init,this.iterationLimit);
       IEvolvableClass solution = ea.findBestSolution();
       System.out.println("fitness solucion 1:" + solution.evaluateFitness());
       
       List<Pair<Integer,Integer>> partitions = solution.faultyPartition();
       melody = ((ConditionEvolvableClass)solution).getMelody();
       ConditionEvolvableClass sol1 = (ConditionEvolvableClass) solution.clone();
 //   ConditionEvolvableClass cec= new ConditionEvolvableClass("0", base,FrameWorkUtil.copyMelody(melody) ,start,end,gc,sm,this.mutationLimitPerSubject);//new Double(0),new Double(10), guaranteedCondition, modification);
  //   List<Pair<Integer,Integer>> partitions = cec.faultyPartition();
     //  for (int i = 0; i < 4; i++) {        
         for(Pair<Integer,Integer> partition : partitions){
            Integer parStart = partition.getKey();
            Integer parEnd = partition.getValue();
            //GuaranteeConditionSolver gcs = new EvolutionarySolve(70,(parEnd-parStart)*20, 0, 80,1,2000);
            GuaranteeConditionSolver gcs = new EvolutionarySolve(70,(parEnd-parStart)*10, 0, 80,1,1000);
            melody = gcs.Solve(base, melody, Util.calculateTimeSumByPosition(melody, parStart), Util.calculateTimeSumByPosition(melody, parEnd), style, gc, sm);
         }
         ConditionEvolvableClass sol2= new ConditionEvolvableClass("0", base,FrameWorkUtil.copyMelody(melody) ,start,end,gc,sm,this.mutationLimitPerSubject,style);//new Double(0),new Double(10), guaranteedCondition, modification);
         System.out.println("fitness solucion 2:" + sol2.evaluateFitness());
   //      partitions = cec.faultyPartition();
    //   }
    
       GuaranteeConditionSolver gcs = new EvolutionarySolve(this.treshold,this.poblationQuantity,this.crossOverPercentage,this.mutationPercentage,this.mutationLimitPerSubject,this.iterationLimit);
       melody = gcs.Solve(base,melody,start,end,style,gc,sm);
       ConditionEvolvableClass sol3 = new ConditionEvolvableClass("0", base,FrameWorkUtil.copyMelody(melody) ,start,end,gc,sm,this.mutationLimitPerSubject,style);//new Double(0),new Double(10), guaranteedCondition, modification,);
       System.out.println("fitness solucion 3:" + sol3.evaluateFitness());
       
       ConditionEvolvableClass bestSolution=sol1;
       
        if(bestSolution.evaluateFitness()<=sol2.evaluateFitness())
            bestSolution = sol2;
        if(bestSolution.evaluateFitness()<=sol3.evaluateFitness())
            bestSolution = sol3;
       
        System.out.println("Fitness resultante:");
        System.out.println(bestSolution.evaluateFitness());
        System.out.println("solucion:");
        bestSolution.PrintMelody();
       return bestSolution.getMelody();
    }
    
}
