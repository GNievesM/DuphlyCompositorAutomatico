package EvolutionaryAlgorithm;

import DataDefinition.Chord;
import DataDefinition.Note;
import Style.AbstractStyle;
import java.util.List;

public interface SpecificModificationMutator {
      public abstract Note mutate(List<Chord> base, List<Note> melody, double start, double end, int specificNotePosition,AbstractStyle style);
}
