package EvolutionaryAlgorithm;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.List;
import javafx.util.Pair;

public interface IFitnessEvaluable {
        public abstract double evaluateFitness(List<Note> melody, List<Chord> base, double start, double end);
        public abstract List<Pair<Integer,Integer>> faultyPartition(List<Note> melody, List<Chord> base, double start, double end);
}
