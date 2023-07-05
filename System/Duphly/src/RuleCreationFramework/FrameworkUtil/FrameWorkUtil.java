package RuleCreationFramework.FrameworkUtil;

import DataDefinition.Note;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class FrameWorkUtil {

  public static List<Note> copyMelody(List<Note> l) {
    List<Note> newList = new ArrayList<Note>();
    for (Note n : l) {
      Note newNote = new Note(
        n.getDuration(),
        n.getNote(),
        n.getOctave(),
        n.getAccident()
      );
      newList.add(newNote);
    }

    return newList;
  }

  public static boolean applyByProbability(double probability) {
    double random = Math.random();
    if (random <= probability) return true;
    return false;
  }

  public static boolean isInsidePartition(
    List<Pair<Double, Double>> partitions,
    Double place
  ) {
    for (Pair<Double, Double> p : partitions) {
      if (place >= p.getKey() && place <= p.getValue()) return true;
    }
    return false;
  }
}
