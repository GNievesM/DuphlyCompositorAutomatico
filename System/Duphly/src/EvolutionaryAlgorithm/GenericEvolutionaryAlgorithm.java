package EvolutionaryAlgorithm;

import DataDefinition.Note;
import java.util.ArrayList;
import java.util.List;

public class GenericEvolutionaryAlgorithm {
    
    int fitnessThreshold;
    int maxPopulation;
    int quantityToReplaceByCrossOver;
    int mutationRate;
    int maxIterations;
    List<IEvolvableClass> population;
    List<Double> populationFitness;
    
    public GenericEvolutionaryAlgorithm(int fitnessThreshold, int maxPopulation, int quantityToReplaceByCrossOver, int mutationRate, List<IEvolvableClass> population, int maxIterations) {
        this.fitnessThreshold = fitnessThreshold;
        this.maxPopulation = maxPopulation;
        this.quantityToReplaceByCrossOver = quantityToReplaceByCrossOver;
        this.mutationRate = mutationRate;
        this.population = population;
        populationFitness = new ArrayList<Double>();
        this.maxIterations = maxIterations;
    }
    
    public void initializePopulation(List<IEvolvableClass> l) {
        population = l;
    }
    
    public void evaluatePopulation() {
        for (int i = 0; i < population.size(); i++) {
            if (i == populationFitness.size())
                populationFitness.add(population.get(i).evaluateFitness());
            else
                populationFitness.set(i, population.get(i).evaluateFitness());
        }
    }
    
    public List<Double> bestIndividualsOnlyProbability(int quantity) {
        List<Double> oldFitness = new ArrayList<>();
        List<Double> probability = new ArrayList<>();
        for (Double d : this.populationFitness) {
            oldFitness.add(d);
            probability.add(new Double(0));
        }
        for (int i = 0; i < quantity; i++) {
            int bestPosition = this.bestIndividualPosition();
            probability.set(bestPosition, new Double(1 / quantity));
            this.populationFitness.set(bestPosition, new Double(0));
        }
        for (int i = 0; i < oldFitness.size(); i++) {
            this.populationFitness.set(i, oldFitness.get(i));
        }
        return probability;
    }
    
    public void newEvolution() {
        List<Double> probability = this.createProbability();
        List<IEvolvableClass> intactSelected = this.Select(this.maxPopulation - this.quantityToReplaceByCrossOver, probability, true);
        
        this.population = new ArrayList<>();
        for (int i = 0; i < intactSelected.size(); i++)
            this.population.add(intactSelected.get(i));
        if (this.quantityToReplaceByCrossOver > 0) {
            probability = this.createProbability();
            List<IEvolvableClass> crossOverSelection = this.Select(this.quantityToReplaceByCrossOver, probability, true);
            crossOverSelection = this.crossOver(crossOverSelection);
            for (int i = 0; i < crossOverSelection.size(); i++)
                this.population.add(crossOverSelection.get(i));
        }
                
        mutatePopulation();
        evaluatePopulation();
    }
    
    public IEvolvableClass findBestSolution() {
        evaluatePopulation();
        int bestIndividualPosition = this.bestIndividualPosition();
        double bestFitness = this.populationFitness.get(bestIndividualPosition);

        IEvolvableClass bestSolution = this.population.get(bestIndividualPosition).clone();
        int generationsLimit = this.maxIterations; // Set a limit to avoid infinite loops if no happy solution is found.
        int processingTime = this.maxIterations / 100;
        while (this.populationFitness.get(bestIndividualPosition) < fitnessThreshold && generationsLimit > 0) {
            
            if (generationsLimit % processingTime == 0)
                System.out.print("I");
            this.newEvolution();            
            generationsLimit--;
            bestIndividualPosition = this.bestIndividualPosition();
                
            if (this.populationFitness.get(bestIndividualPosition) > bestFitness) {
                bestSolution = this.population.get(bestIndividualPosition).clone();
                bestFitness = this.populationFitness.get(bestIndividualPosition);
            }
            if (this.populationFitness.get(bestIndividualPosition) <= bestFitness - 25) {
                fitnessThreshold = 0; // If fitness is decreasing significantly, abandon.
            }
        }
        System.out.println("");
        return bestSolution;
    }

    protected void mutatePopulation() {
        List<IEvolvableClass> mutators = this.Select(mutationRate, this.createEqualProbabilityForAllPoblation(), false);
        for (int i = 0; i < this.mutationRate; i++) {
            mutators.get(i).mutate(mutators.get(i).generateFactorMutationList());
        }
    }
    
    protected List<IEvolvableClass> crossOver(List<IEvolvableClass> pairs) {
        for (int i = 0; i < pairs.size(); i += 2) {
            if (i < pairs.size() - 1)
                pairs.get(i).crossOver(pairs.get(i + 1), pairs.get(i).generateFactorCrossOverList());
            else
                pairs.get(i).crossOver(pairs.get(i - 1), pairs.get(i).generateFactorCrossOverList()); 
        }
        return pairs;
    }
    
    protected List<IEvolvableClass> Select(int quantity, List<Double> probabilityToBeSelected, boolean clone) {
        List<IEvolvableClass> selected = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            int individualPosition = this.calculateRandomUsingProbability(probabilityToBeSelected);
            if (clone)
                selected.add(this.population.get(individualPosition).clone());
            else
                selected.add((this.population.get(individualPosition)));
            probabilityToBeSelected.set(individualPosition, new Double(0));
        }
        return selected;
    }
    
    protected int bestIndividualPosition() {
        double best = this.populationFitness.get(0);
        int position = 0;
        for (int i = 1; i < this.population.size(); i++) {
            if (best < this.populationFitness.get(i)) {
                best = this.populationFitness.get(i);
                position = i;
            }
        }
        return position;
    }
    
    protected List<Double> createProbability() {
        List<Double> solution = new ArrayList<>();
        int totalFitness = this.totalFitness();
        if (this.totalFitness() == 0)
            return this.createEqualProbabilityForAllPoblation();
        else {
            for (int i = 0; i < this.population.size(); i++) {
                solution.add(this.populationFitness.get(i) / Double.valueOf(totalFitness));
            }
        }
        return solution;
    }
    
    protected int totalFitness() {
        int sum = 0;
        for (int i = 0; i < this.populationFitness.size(); i++)
            sum += populationFitness.get(i);
        return sum;
    }

    // Receives probability between 0 and 1.
    protected int calculateRandomUsingProbability(List<Double> probabilityToUse) {
        double random = Math.random() * this.probabilitySum(probabilityToUse);
        int elementPosition = 0;
        double rangeStart = 0;
        for (int i = 0; i < probabilityToUse.size(); i++) { 
            if (rangeStart <= random && random <= rangeStart + probabilityToUse.get(i))
                return elementPosition;
            elementPosition++;
            rangeStart += probabilityToUse.get(i);
        }
        return elementPosition - 1;
    }
    
    protected double probabilitySum(List<Double> probability) {
        double sum = 0;
        for (Double d : probability) {
            sum += d;
        }
        return sum;
    }
    
    protected List<Double> createEqualProbabilityForAllPoblation() {
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i < this.population.size(); i++) {
            list.add(Double.valueOf(1) / Double.valueOf(this.population.size()));
        }
        return list;
    }
}
