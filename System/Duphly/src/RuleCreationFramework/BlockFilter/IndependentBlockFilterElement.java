package RuleCreationFramework.BlockFilter;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.List;
import javafx.util.Pair;

public abstract class IndependentBlockFilterElement {

  public abstract List<Pair<Double, Double>> Filter(
    List<Chord> chords,
    List<Note> melody
  );
}
