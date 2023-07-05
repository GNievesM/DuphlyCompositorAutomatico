package RuleCreationFramework.ExcerptFilter.ExcerptSelectors;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ExcerptFilter.ExcerptConditionSelector;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class Sequency extends ExcerptConditionSelector {

  List<Note> notes;
  List<Integer> pitches;
  List<Integer> grades;

  Boolean asc;
  Comparador comp;
  int quantity;
  boolean allowRepeat;

  public Sequency(
    List<Integer> grades,
    Comparador comp,
    List<Note> notes,
    List<Integer> pitches,
    Boolean asc,
    int quantity,
    boolean allowRepeat
  ) {
    this.notes = notes;
    this.pitches = pitches;
    this.asc = asc;
    this.quantity = quantity;
    this.allowRepeat = allowRepeat;
    this.grades = grades;
    this.comp = comp;
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
    if (
      this.notes != null || this.pitches != null || this.grades != null
    ) solution =
      this.explicitSequence(
          melody,
          base,
          startPosition,
          endPosition,
          alreadySelected
        ); else solution =
      this.lineTendency(
          melody,
          base,
          startPosition,
          endPosition,
          alreadySelected
        );
    return solution;
  }

  private List<Pair<Double, Double>> explicitSequence(
    List<Note> melody,
    List<Chord> base,
    int startPosition,
    int endPosition,
    List<Pair<Double, Double>> alreadySelected
  ) {
    List<Pair<Double, Double>> solution = new ArrayList<>();
    if (this.notes != null) for (
      int i = startPosition, notesIndex = 0, quantityOk = 0;
      i < endPosition - this.notes.size();
      i++
    ) {
      if (Util.IsEqualPitch(melody.get(i), this.notes.get(notesIndex))) {
        if (quantityOk == this.notes.size()) {
          this.addInterval(
              solution,
              i,
              i + notesIndex,
              melody,
              alreadySelected
            );
          quantityOk = 0;
          notesIndex = 0;
        } else {
          notesIndex++;
          quantityOk++;
        }
      } else {
        if (Util.IsEqualPitch(melody.get(i), this.notes.get(0))) {
          quantityOk = 1;
          notesIndex = 1;
        } else {
          quantityOk = 0;
          notesIndex = 0;
        }
      }
    } else if (this.pitches != null) {
      for (
        int i = startPosition, pitchesIndex = 0, quantityOk = 0;
        i < endPosition - this.pitches.size();
        i++
      ) {
        if (melody.get(i).getNote() == this.pitches.get(pitchesIndex)) {
          if (quantityOk == this.notes.size()) {
            this.addInterval(
                solution,
                i,
                i + pitchesIndex,
                melody,
                alreadySelected
              );
            quantityOk = 0;
            pitchesIndex = 0;
          } else {
            pitchesIndex++;
            quantityOk++;
          }
        } else {
          if (melody.get(i).getNote() == this.pitches.get(0)) {
            quantityOk = 1;
            pitchesIndex = 1;
          } else {
            quantityOk = 0;
            pitchesIndex = 0;
          }
        }
      }
    }
    if (this.grades != null) {
      for (
        int i = startPosition, pitchesIndex = 0, quantityOk = 0;
        i < endPosition - this.pitches.size();
        i++
      ) {
        if (
          Util.calculateNoteGradeForChord(
            Util.LookForBaseChord(base, melody, i),
            melody.get(i)
          ) ==
          this.grades.get(pitchesIndex)
        ) {
          if (quantityOk == this.notes.size()) {
            this.addInterval(
                solution,
                i,
                i + pitchesIndex,
                melody,
                alreadySelected
              );
            quantityOk = 0;
            pitchesIndex = 0;
          } else {
            pitchesIndex++;
            quantityOk++;
          }
        } else {
          if (
            Util.calculateNoteGradeForChord(
              Util.LookForBaseChord(base, melody, i),
              melody.get(i)
            ) ==
            this.grades.get(0)
          ) {
            quantityOk = 1;
            pitchesIndex = 1;
          } else {
            quantityOk = 0;
            pitchesIndex = 0;
          }
        }
      }
    }
    return solution;
  }

  private List<Pair<Double, Double>> lineTendency(
    List<Note> melody,
    List<Chord> base,
    int startPosition,
    int endPosition,
    List<Pair<Double, Double>> alreadySelected
  ) {
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
      ) if (
        !Util.IsEqualPitch(melody.get(i), melody.get(i + 1))
      ) count++; else {
        if (
          count > this.quantity &&
          this.comp.esMayor() ||
          count < this.quantity &&
          this.comp.esMenor() ||
          count == this.quantity &&
          this.comp.esIgual()
        ) this.addInterval(solution, intervalStart, i, melody, alreadySelected);
        tendencyUp = !tendencyUp;
        intervalStart = i;
        count = 1;
      }
    }
    return solution;
  }

  private boolean tendencyContinues(boolean up, Note present, Note next) {
    return (
      (up && Util.IsHIgherPitchThan(present, next)) ||
      (!up && Util.IsHIgherPitchThan(present, next)) ||
      this.allowRepeat &&
      Util.IsEqualPitch(present, next)
    );
  }

  private void addInterval(
    List<Pair<Double, Double>> solution,
    int indexStart,
    int indexEnd,
    List<Note> melody,
    List<Pair<Double, Double>> alreadySelected
  ) {
    Pair<Double, Double> interval = new Pair<Double, Double>(
      Util.calculateTimeSumByPosition(melody, indexStart),
      Util.calculateTimeSumByPosition(melody, indexEnd)
    );
    if (!this.sharesPartition(indexStart, indexEnd, alreadySelected)) {
      solution.add(interval);
    }
  }
}
