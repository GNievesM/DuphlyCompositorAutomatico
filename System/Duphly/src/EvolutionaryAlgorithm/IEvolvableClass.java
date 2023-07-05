package EvolutionaryAlgorithm;

import java.util.List;
import javafx.util.Pair;

public interface IEvolvableClass {
    public String getId();
    public double evaluateFitness();
    public void crossOver(IEvolvableClass ec, List<Boolean> changes);
    public void mutate(List<Boolean> mutationPlaces);
    public List<Pair<Integer, Integer>> faultyPartition();
    public List<Boolean> generateFactorCrossOverList();
    public List<Boolean> generateFactorMutationList();
    public void PrintMelody();
    public IEvolvableClass clone();
}
