/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EvolutionaryAlgorithm;

import DataDefinition.Chord;
import DataDefinition.Note;
import Style.AbstractStyle;
import java.util.List;

/**
 *
 * @author Gaston
 */
public interface SpecificModificationMutator {
      public abstract Note mutate(List<Chord> base, List<Note> melody, double start, double end, int specificNotePosition,AbstractStyle style);
}
