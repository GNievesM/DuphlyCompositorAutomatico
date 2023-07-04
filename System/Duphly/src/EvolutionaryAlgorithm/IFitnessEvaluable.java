/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EvolutionaryAlgorithm;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public interface IFitnessEvaluable {
        public abstract double evaluateFitness(List<Note> melody, List<Chord> base, double start, double end);
        public abstract List<Pair<Integer,Integer>> faultyPartition(List<Note> melody, List<Chord> base, double start, double end);
}
