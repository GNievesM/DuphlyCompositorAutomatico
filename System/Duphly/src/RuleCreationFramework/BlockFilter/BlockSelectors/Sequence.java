package RuleCreationFramework.BlockFilter.BlockSelectors;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.BlockFilter.BlockConditionSelector;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class Sequence extends BlockConditionSelector {

  List<Integer> pitches;
  List<Note> notes;

  public Sequence(List<Integer> pitches, List<Note> notes) {
    this.pitches = pitches;
    this.notes = notes;
  }

  @Override
  public List<Pair<Integer, Integer>> applySelector(
    List<Note> melody,
    List<Chord> base,
    Integer start,
    Integer end,
    Integer blockQuantity
  ) {
    List<Pair<Integer, Integer>> solution = new ArrayList<>();
    for (int i = start; i < end;) {
      Double selectionStart = Util.calculateTimeSumByPosition(
        melody,
        Util.getBlockPositionByNumber(melody, i)
      );
      Double selectionEnd = Util.calculateTimeSumByPosition(
        melody,
        Util.getBlockPositionByNumber(melody, i + blockQuantity)
      );
      int indexStart = Util.calculateNotePositionInListByTimeSum(
        melody,
        selectionStart
      );
      int indexEnd = Util.calculateNotePositionInListByTimeSum(
        melody,
        selectionEnd
      );
      int correctSequence = 0;
      int sequenceIndex = 0;
      for (int j = indexStart; j < indexEnd; j++) {
        if (
          (
            pitches != null &&
            pitches.get(sequenceIndex) == melody.get(j).getNote()
          ) ||
          notes != null &&
          notes.get(sequenceIndex).getNote() == melody.get(j).getNote() &&
          notes.get(sequenceIndex).getOctave() == melody.get(j).getOctave()
        ) {
          correctSequence++;
          sequenceIndex++;
        } else {
          correctSequence = 0;
          sequenceIndex = 0;
        }

        if (
          (pitches != null && correctSequence == this.pitches.size()) ||
          notes != null &&
          correctSequence == this.notes.size()
        ) {
          j = indexEnd;
          Pair<Integer, Integer> workingInterval = new Pair<>(
            i,
            i + blockQuantity
          );
          solution.add(workingInterval);
          i += blockQuantity;
        }
      }

      if (
        (this.pitches != null && correctSequence != this.pitches.size()) ||
        this.notes != null &&
        correctSequence != this.notes.size()
      ) i++;
    }

    return solution;
  }
}
