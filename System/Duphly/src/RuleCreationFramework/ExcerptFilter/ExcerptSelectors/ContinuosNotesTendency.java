package RuleCreationFramework.ExcerptFilter.ExcerptSelectors;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ExcerptFilter.ExcerptConditionSelector;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class ContinuosNotesTendency extends ExcerptConditionSelector {

  Integer quantity;

  public ContinuosNotesTendency(Integer quantity) {
    this.quantity = quantity;
  }

  @Override
  public List<Pair<Double, Double>> applySelector(
    List<Note> melody,
    List<Chord> base,
    double start,
    double end,
    int length,
    List<Pair<Double, Double>> alreadySelected
  ) {
    int startPosition = Util.calculateNotePositionInListByTimeSum(
      melody,
      start
    );
    int endPosition = Util.calculateNotePositionInListByTimeSum(melody, end);
    List<Pair<Double, Double>> solution = new ArrayList<>();
    boolean tendencyUp =
      this.tendencyContinues(
          true,
          melody.get(startPosition),
          melody.get(startPosition + 1)
        );
    int intervalStart = startPosition;
    int count = 1;
    for (int i = startPosition + 1; i < endPosition; i++) {
      if (
        this.tendencyContinues(tendencyUp, melody.get(i), melody.get(i + 1))
      ) count++; else {
        Pair<Double, Double> interval = new Pair<Double, Double>(
          Util.calculateTimeSumByPosition(melody, intervalStart),
          Util.calculateTimeSumByPosition(melody, i)
        );
        if (
          this.sharesPartition(intervalStart, i, alreadySelected) &&
          count >= this.quantity
        ) solution.add(interval);
        tendencyUp = !tendencyUp;
        intervalStart = i;
        count = 1;
      }
    }
    return solution;
  }

  private boolean tendencyContinues(boolean up, Note present, Note next) {
    return (
      (
        up &&
        Util.getExpandedFormNote(present.getNote(), present.getOctave()) <
        Util.getExpandedFormNote(next.getNote(), next.getOctave())
      ) ||
      (
        !up &&
        Util.getExpandedFormNote(present.getNote(), present.getOctave()) >
        Util.getExpandedFormNote(next.getNote(), next.getOctave())
      )
    );
  }
}
