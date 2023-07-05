package RuleCreationFramework.BlockFilter;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.List;
import javafx.util.Pair;

public abstract class DependentBlockFilterElement {

  public abstract List<Pair<Double, Double>> filter(
    List<Chord> base,
    List<Note> melody,
    List<Pair<Double, Double>> partitions
  );
}
