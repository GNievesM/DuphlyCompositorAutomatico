package RuleCreationFramework.ExcerptFilter.ExcerptSelectors;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ExcerptFilter.ExcerptConditionSelector;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class Position extends ExcerptConditionSelector {

  Integer relativePosition;

  public Position(Integer relativePosition) {
    this.relativePosition = relativePosition;
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
    if (startPosition + relativePosition < endPosition) {
      Double positionPlace = Util.calculateTimeSumByPosition(
        melody,
        startPosition + this.relativePosition
      );
      Pair<Double, Double> pair = new Pair<Double, Double>(
        positionPlace,
        positionPlace
      );
      solution.add(pair);
    }

    return solution;
  }
}
