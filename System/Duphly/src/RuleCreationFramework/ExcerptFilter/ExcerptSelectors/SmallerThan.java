/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ExcerptFilter.ExcerptSelectors;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ExcerptFilter.ExcerptConditionSelector;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public class SmallerThan extends ExcerptConditionSelector {

  Integer note;
  Integer octave;
  Integer quantity;
  Integer grade;

  public SmallerThan(Integer note, Integer octave, Integer quantity) {
    this.note = note;
    this.octave = octave;
    this.quantity = quantity;
    this.grade = null;
  }

  public SmallerThan(Integer grade, Integer quantity) {
    this.note = null;
    this.octave = null;
    this.quantity = quantity;
    this.grade = grade;
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
    for (int i = startPosition; i < endPosition; i++) {
      int count = 0;
      for (int j = i; j < endPosition && j < i + this.quantity; j++) {
        if (
          satisfiesSmaller(
            melody.get(j),
            Util.LookForBaseChord(base, melody, j)
          )
        ) count++; else {
          i = j;
          j = endPosition;
          count = 0;
        }
        if (
          count <= quantity &&
          !this.sharesPartition(i, i + this.quantity, alreadySelected)
        ) {
          Pair<Double, Double> pair = new Pair<Double, Double>(
            Util.calculateTimeSumByPosition(melody, i),
            Util.calculateTimeSumByPosition(melody, i + this.quantity)
          );
          solution.add(pair);
        }
      }

      count = 0;
    }

    return solution;
  }

  private boolean satisfiesSmaller(Note n, Chord c) {
    boolean solution = false;
    if (this.grade == null) {
      if (this.octave == null) solution = n.getNote() < this.note;
      if (this.octave != null) solution |= n.getOctave() < this.octave;
    } else solution = Util.calculateNoteGradeForChord(c, n) < this.grade;
    return solution;
  }
}
