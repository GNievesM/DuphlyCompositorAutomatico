/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EvolutionaryAlgorithm;

import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 * @param <T>
 */
public interface IEvolvableClass {
    public String getId();
    public  double evaluateFitness();
    public  void crossOver(IEvolvableClass ec, List<Boolean> changes);
    public void mutate (List<Boolean> mutationPlaces);
    public List<Pair<Integer,Integer>> faultyPartition();
    public  List<Boolean> generateFactorCrossOverList();
    public  List<Boolean> generateFactorMutationList();
    public void PrintMelody();
    public IEvolvableClass clone();
    //public T getIndividual();
 //   protected abstract List<T> getCrossPart(List<Boolean> changes);
  //  protected abstract void setCrossPart(List<Boolean> changes, List<T> wholePart );

}
