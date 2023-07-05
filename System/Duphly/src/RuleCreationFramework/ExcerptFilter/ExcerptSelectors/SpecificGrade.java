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
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public class SpecificGrade extends ExcerptConditionSelector {

  Comparador gradeComp;
  Integer grade;
  Integer quantity;
  Comparador quantityComp;

  public SpecificGrade(
    Comparador gradeComp,
    Integer grade,
    Integer quantity,
    Comparador quantityComp
  ) {
    this.gradeComp = gradeComp;
    this.grade = grade;
    this.quantity = quantity;
    this.quantityComp = quantityComp;
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
    for (
      int i = startPosition, intervalStart = i, correct = 0;
      i < endPosition;
      i++
    ) {
      Note present = melody.get(i);

      int grade = Util.calculateNoteGradeForChord(
        Util.LookForBaseChord(base, melody, i),
        present
      );
      if (
        grade == this.grade &&
        this.gradeComp.esIgual() ||
        grade < this.grade &&
        this.gradeComp.esMenor() ||
        grade > this.grade &&
        this.gradeComp.esMayor()
      ) {
        // if(intervalStart !=startPosition){
        correct++;
        //}
      } else {
        if (
          correct > this.quantity &&
          this.quantityComp.esMayor() ||
          correct < this.quantity &&
          this.quantityComp.esMenor() ||
          correct == this.quantity &&
          this.quantityComp.esIgual()
        ) {
          addInterval(
            solution,
            intervalStart,
            intervalStart + correct,
            melody,
            alreadySelected
          );
        }
        intervalStart = i + 1;
        correct = 0;
      }
    }

    return solution;
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
