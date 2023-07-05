package RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationConditionChecker;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationCondition;
import java.util.ArrayList;
import java.util.List;
 
public class CheckSequency extends ModificationCondition {

  boolean isASequency;
  boolean asc;
  Integer number;
  boolean allowRepeat;

  public CheckSequency(
    boolean isASequency,
    boolean asc,
    Integer number,
    boolean allowRepeat
  ) {
    this.isASequency = isASequency;
    this.asc = asc;
    this.number = number;
    this.allowRepeat = allowRepeat;
  }

  @Override
  public boolean checkCondition(
    List<Chord> base,
    List<Note> melody,
    Double start,
    Double end
  ) {
    int positionStartIndex = Util.calculateNotePositionInListByTimeSum(
      melody,
      start
    );
    int positionEndIndex = Util.calculateNotePositionInListByTimeSum(
      melody,
      end
    );
    int correctNotes = 0;
    for (int i = positionStartIndex; i < positionEndIndex - 1; i++) {
      Note n1 = melody.get(i);
      Note n2 = melody.get(i + 1);
      if (apliesConditions(n1, n2)) correctNotes += 1; else correctNotes = 0;
      if (correctNotes == this.number) return true && this.isASequency;
    }

    return false || !this.isASequency;
  }

  private boolean apliesConditions(Note n1, Note n2) {
    if (
      asc &&
      this.allowRepeat &&
      Util.IsHIgherOrEqualPitchThan(n2, n1) ||
      asc &&
      !this.allowRepeat &&
      Util.IsHIgherPitchThan(n2, n1) ||
      !asc &&
      this.allowRepeat &&
      Util.IsLowerOrEqualPitchThan(n2, n1) ||
      !asc &&
      !this.allowRepeat &&
      Util.IsHIgherPitchThan(n1, n2)
    ) return true;
    return false;
  }

  @Override
  public List<Double> listFaultyPositions(
    List<Chord> base,
    List<Note> melody,
    Double start,
    Double end
  ) {
    List<Double> faulty = new ArrayList<Double>();
    int positionStartIndex = Util.calculateNotePositionInListByTimeSum(
      melody,
      start
    );
    int positionEndIndex = Util.calculateNotePositionInListByTimeSum(
      melody,
      end
    );
    for (int i = positionStartIndex; i < positionEndIndex - 1; i++) {
      Note n1 = melody.get(i);
      Note n2 = melody.get(i + 1);
      if (apliesConditions(n1, n2)) faulty.add(
        Util.calculateTimeSumByPosition(melody, i)
      );
    }

    return faulty;
  }
}
