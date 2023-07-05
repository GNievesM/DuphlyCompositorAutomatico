package RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedConditionChecker;

import DataDefinition.Chord;
import DataDefinition.Note;
import EvolutionaryAlgorithm.IFitnessEvaluable;
import ImprovisationRules.Util;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedCondition;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationCondition;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationConditionChecker.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class GuaranteeOctave
  extends GuaranteedCondition
  implements IFitnessEvaluable {

  CheckOctave c;
  Comparador comp;
  int diffNumber;
  List<Note> melody;
  List<Chord> base;
  double start;
  double end;

  public GuaranteeOctave(Comparador comp, int diffNumber) {
    c = new CheckOctave(comp, diffNumber);
    this.comp = comp;
    this.diffNumber = diffNumber;
  }

  @Override
  public boolean checkCondition(
    List<Chord> base,
    List<Note> melody,
    Double start,
    Double end
  ) {
    this.melody = melody;
    this.base = base;
    this.start = start;
    this.end = end;
    return c.checkCondition(base, melody, start, end);
  }

  private double calculateDifferenceSum() {
    int notePositionStart = Util.calculateNotePositionInListByTimeSum(
      melody,
      start
    );
    int notePositionEnd = Util.calculateNotePositionInListByTimeSum(
      melody,
      end
    );

    double correctPercentaje =
      ((double) 100 / (notePositionEnd - notePositionStart));
    double differencePercentaje = 0;
    for (int i = notePositionStart; i < notePositionEnd - 1; i++) {
      int octave = melody.get(i).getOctave();
      if (
        comp.esIgual() &&
        octave == this.diffNumber ||
        comp.esMayor() &&
        octave > this.diffNumber ||
        comp.esMenor() &&
        octave < this.diffNumber
      ) differencePercentaje += correctPercentaje;
    }

    return differencePercentaje;
  }

  @Override
  public double evaluateFitness(
    List<Note> melody,
    List<Chord> base,
    double start,
    double end
  ) {
    this.melody = melody;
    this.base = base;
    this.start = start;
    this.end = end;
    double percentaje = calculateDifferenceSum();
    return percentaje;
  }

  protected List<Pair<Integer, Integer>> calculateFaultyPartition() {
    int notePositionStart = Util.calculateNotePositionInListByTimeSum(
      melody,
      start
    );
    int notePositionEnd = Util.calculateNotePositionInListByTimeSum(
      melody,
      end
    );
    List<Pair<Integer, Integer>> particiones = new ArrayList<>();
    Integer partitionStart = notePositionStart;
    boolean insidePartition = false;

    for (int i = notePositionStart; i < notePositionEnd - 1; i++) {
      int octave = melody.get(i).getOctave();

      if (
        comp.esIgual() &&
        octave == this.diffNumber ||
        comp.esMayor() &&
        octave > this.diffNumber ||
        comp.esMenor() &&
        octave < this.diffNumber
      ) {
        if (insidePartition) {
          Pair<Integer, Integer> faulty;
          faulty =
            new Pair<Integer, Integer>(
              partitionStart,
              (i == (notePositionEnd - 2) ? new Integer(i) : new Integer(i + 1))
            );
          particiones.add(faulty);
          insidePartition = false;
        }
      } else {
        if (!insidePartition) {
          partitionStart = i == notePositionStart ? notePositionStart : i - 1;
          insidePartition = true;
        }
      }
    }

    return particiones;
  }

  @Override
  public List<Pair<Integer, Integer>> faultyPartition(
    List<Note> melody,
    List<Chord> base,
    double start,
    double end
  ) {
    this.melody = melody;
    this.base = base;
    this.start = start;
    this.end = end;
    return this.calculateFaultyPartition();
  }
}
