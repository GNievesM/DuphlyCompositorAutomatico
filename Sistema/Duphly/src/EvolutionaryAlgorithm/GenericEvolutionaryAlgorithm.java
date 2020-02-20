/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EvolutionaryAlgorithm;

import DataDefinition.Note;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gaston
 */
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
        this.maxIterations=maxIterations;
    }
    
    public void initializePopulation( List<IEvolvableClass> l){ population = l;};
    //public abstract List <IEvolvableClass> initializePopulation( List<IEvolvableClass> l);
    
    public void evaluatePopulation(){
        for (int i =0;i<population.size();i++) {
            if(i == populationFitness.size())
                populationFitness.add(population.get(i).evaluateFitness());
            else
                populationFitness.set(i,population.get(i).evaluateFitness());
        }
    }
    
    public List<Double> bestIndividualsOnlyProbability(int quantity){
        List<Double> oldFitness = new ArrayList<>();
        List<Double> probability = new ArrayList<>();
        for(Double d : this.populationFitness){
            oldFitness.add(d);
            probability.add(new Double(0));
        }
        for(int i = 0; i<quantity ; i++){
            int bestPosition = this.bestIndividualPosition();
            probability.set(bestPosition, new Double(1/quantity));
            this.populationFitness.set(bestPosition, new Double(0));
        }
        for(int i = 0;i<oldFitness.size();i++){
            this.populationFitness.set(i,oldFitness.get(i));
        }
        return probability;
    }
    
    
    public void newEvolution(){
        List<Double> probability = this.createProbability();
        //List<IEvolvableClass> intactSelected = this.Select(this.maxPopulation-this.quantityToReplaceByCrossOver, this.bestIndividualsOnlyProbability(this.maxPopulation-this.quantityToReplaceByCrossOver));
        List<IEvolvableClass> intactSelected = this.Select(this.maxPopulation-this.quantityToReplaceByCrossOver,probability,true);
        
        this.population=new ArrayList<>();
        for (int i = 0; i < intactSelected.size(); i++)
            this.population.add(intactSelected.get(i));
        if(this.quantityToReplaceByCrossOver>0){
            probability = this.createProbability();
            List<IEvolvableClass> crossOverSelection = this.Select(this.quantityToReplaceByCrossOver, probability,true);
            crossOverSelection = this.crossOver(crossOverSelection);
            for(int i =0;i<crossOverSelection.size(); i++)
                this.population.add(crossOverSelection.get(i));
        }
        
  //      System.out.println("");
  //    System.out.println("Poblacion actual sin tocar:");
 //       for (int i = 0; i < this.maxPopulation; i++) 
   //        this.population.get(i).PrintMelody();
        
        
        
 //       System.out.println("poblacion intacta");
 //       for (int i = 0; i < intactSelected.size(); i++) {
 //           intactSelected.get(i).PrintMelody();
 ////       }
 //       System.out.println("Poblacion de cruza");
 //       for (int i = 0; i < crossOverSelection.size(); i++) {
 //           crossOverSelection.get(i).PrintMelody();
 //       }
       
     
       
      
  //      System.out.println("");
 //       System.out.println("Poblacion despues de cruzar:");
        
   //     for (int i = 0; i < this.maxPopulation; i++) 
   //         this.population.get(i).PrintMelody();
                
        mutatePopulation();
        evaluatePopulation();
        
  //      System.out.println("poblacion despues de mutar:");
  //      for (int i = 0; i < this.maxPopulation; i++) 
   //         this.population.get(i).PrintMelody();
        
    //    System.out.println("NUEVA GENERACION -----------------------------------------------------------------");
    }
    
    public IEvolvableClass findBestSolution(){
        //initializePopulation();
        evaluatePopulation();
        int  bestIndividualPosition = this.bestIndividualPosition();
        double bestFitness = this.populationFitness.get(bestIndividualPosition);
        
   //     System.out.println("Mejor fitness al empezar");
  //      System.out.println(bestFitness);
  //      System.out.println("Melodia de comienzo:");
  //      this.population.get(bestIndividualPosition).PrintMelody();
        IEvolvableClass bestSolution = this.population.get(bestIndividualPosition).clone();;
        int generationsLimit = this.maxIterations; // pongo un limite para que no se vaya al infiniuto si no encunetra una solucion feliz.
        int proccessingTime  = this.maxIterations/100;
        while(this.populationFitness.get(bestIndividualPosition)<fitnessThreshold &&  generationsLimit>0){
            
            if(generationsLimit%proccessingTime == 0)
                System.out.print("I");
            this.newEvolution();            
            generationsLimit--;
            bestIndividualPosition = this.bestIndividualPosition();
            //System.out.print("Mejor fitness hasta el momento");
    //        System.out.print(this.populationFitness.get(bestIndividualPosition) + "-" );
          //  System.out.println("Individuo:");
        //    this.population.get(bestIndividualPosition).PrintMelody();
       //     System.out.println("Fitness de la poblacion: ");
 //           for (Integer n: this.populationFitness) {
   //             System.out.print(n + " - ");
     //       }
    //        System.out.println("");
    //        System.out.println("Poblacion actual:");
    //        for (int i = 0; i < this.maxPopulation; i++) 
    //            this.population.get(i).PrintMelody();
                
            if(this.populationFitness.get(bestIndividualPosition) > bestFitness){
                bestSolution = this.population.get(bestIndividualPosition).clone();
                bestFitness = this.populationFitness.get(bestIndividualPosition);
            }
            if(this.populationFitness.get(bestIndividualPosition) <= bestFitness-25){
                fitnessThreshold= 0; // si va muy en bajada directamente abandona.
            }
        }
 //          System.out.println("Poblacion actual:");
  //          for (int i = 0; i < this.maxPopulation; i++) 
   //             this.population.get(i).PrintMelody();
       System.out.println("");
 //       System.out.println("solucion:");
 //       bestSolution.PrintMelody();
  //      System.out.println("bestFitness:");
  //      System.out.println(bestFitness);
  //      System.out.println("--------------------------------------------------------------------");
        return bestSolution;
        //return this.population.get(bestIndividualPosition());
    }
    /// cambiar las mutaciones para que se hagan sobre individuos aleatorios segun el numero de mutationFActor
    // checkear que las mutaciones no esten pasando sobre el mismo objeto mas de una vez
    protected void mutatePopulation(){
      List<IEvolvableClass> mutators = this.Select(mutationRate, this.createEqualProbabilityForAllPoblation(),false);
        for (int i = 0; i < this.mutationRate; i++) {
           mutators.get(i).mutate(mutators.get(i).generateFactorMutationList());

        }
    }
    protected List<IEvolvableClass> crossOver(List<IEvolvableClass> pairs){
        
        for (int i = 0; i < pairs.size(); i+=2) {
            if(i<pairs.size()-1)
                pairs.get(i).crossOver(pairs.get(i+1), pairs.get(i).generateFactorCrossOverList());
            else
                pairs.get(i).crossOver(pairs.get(i-1), pairs.get(i).generateFactorCrossOverList()); // si la poblacion es impar.
        }
        return pairs;
    }
    
    
    protected List<IEvolvableClass> Select(int quantity, List<Double> probabilityToBeSelected, boolean clone){ // selecciono todos aquellos indiviuos que no van a ser remplazados.
        List<IEvolvableClass> selected = new ArrayList<>();
       // System.out.println("selected individuals");
        for (int i = 0; i < quantity; i++) {
            int individualPosition = this.calculateRandomUsingProbability(probabilityToBeSelected) ;// el modulo es por que estaba fallando el caluclo de la posicion. % this.maxPopulation;
            if(clone)
                selected.add(this.population.get(individualPosition).clone());
            else
                selected.add((this.population.get(individualPosition)));
  //          System.out.print(this.population.get(individualPosition).getId() + " - ");
            probabilityToBeSelected.set(individualPosition,new Double(0)); // una vez que encuentro el individuo le bajo a 0 la probabilidad para que no caiga de nuevo.
        }
   //     System.out.println("");
        
        return selected;
    }

    
    
    protected int bestIndividualPosition(){
        double best = this.populationFitness.get(0);
        int position = 0;
        for (int i = 1; i < this.population.size(); i++) {
            if(best<this.populationFitness.get(i)){
                best = this.populationFitness.get(i);
                position=i;
            }
        }
        return position;
    }
    protected List<Double> createProbability(){
        List<Double> solution = new ArrayList<>();
        int totalFitness =this.totalFitness();
        if(this.totalFitness()==0)
            return this.createEqualProbabilityForAllPoblation();
        else
            for (int i = 0; i < this.population.size(); i++) {
            
              solution.add(this.populationFitness.get(i)/Double.valueOf(totalFitness));            
            }
        
        return solution;
    }
    
    protected int totalFitness(){
        int sum = 0;
        for(int i =0;i<this.populationFitness.size();i++)
            sum += populationFitness.get(i);
        return sum;
    }
    //recibe la probabilidad entre 0 y 1.
    protected int calculateRandomUsingProbability(List<Double>probabilityToUse){
        double random = Math.random()*this.probabilitySum(probabilityToUse); // si la probabilidad no suma 1, entonces lo calculo en base a el numero que sea
        int elementPosition = 0;
        double rangeStart=0;
        for (int i = 0; i < probabilityToUse.size(); i++) { // preguntar cuanto es la probabilidad del 1 y devolver uno para eso voy sumando las probabilidades y checkeo que sea mayor al random.
            if(rangeStart<=random&&random<=rangeStart+probabilityToUse.get(i))
                return elementPosition;
            elementPosition++;
            rangeStart += probabilityToUse.get(i);
        }
    //    System.out.println("estem, no funco bien esto: ");
   //     for (int i = 0; i < probabilityToUse.size(); i++) 
  //          System.out.println(probabilityToUse.get(i));
  //      System.out.println("numero randon :" + random);
        return elementPosition-1;
    }
    
    protected double probabilitySum (List<Double> probability){
        double sum=0;
        for (Double d: probability){
            sum += d;
        }
        return (sum);
    }
    protected List<Double> createEqualProbabilityForAllPoblation(){
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i < this.population.size(); i++) {
            list.add(Double.valueOf(1)/Double.valueOf(this.population.size()));
        }
        return list;
    }
}
