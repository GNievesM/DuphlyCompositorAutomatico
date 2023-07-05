package RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationConditionChecker;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationCondition;
import java.util.ArrayList;
import java.util.List;

public class CheckPercentage extends ModificationCondition {

  Note note;
  boolean absolut;
  int percentage;
  Comparador cmp;
  Integer grade;

  public CheckPercentage(
    Note note,
    boolean absolut,
    int percentage,
    Comparador comp
  ) {
    this.note = note;
    this.absolut = absolut;
    this.percentage = percentage;
    cmp = comp;
    this.grade = null;
  }

  public CheckPercentage(Integer grade, int percentage, Comparador comp) {
    this.note = null;
    this.absolut = false;
    this.percentage = percentage;
    cmp = comp;
    this.grade = grade;
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
    int quantity = 0;
    for (int i = positionStartIndex; i < positionEndIndex; i++) {
      Note particularNote = melody.get(i);
      if (
        this.satisfiesCondition(
            particularNote,
            Util.LookForBaseChord(base, melody, i)
          )
      ) quantity++;
    }
    int totalPercentage =
      quantity * 100 / (positionStartIndex - positionEndIndex);
    return (
      totalPercentage > this.percentage &&
      cmp.esMayor() ||
      totalPercentage < this.percentage &&
      cmp.esMenor() ||
      totalPercentage == this.percentage &&
      cmp.esIgual()
    );
  }

  private boolean satisfiesCondition(Note particularNote, Chord c) {
    if (this.grade != null) {
      return Util.calculateNoteGradeForChord(c, particularNote) == this.grade;
    }

    if (
      this.absolut &&
      particularNote.getNote() == this.note.getNote() &&
      particularNote.getOctave() == this.note.getOctave() ||
      !this.absolut &&
      particularNote.getNote() == this.note.getNote()
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
    int positionStartIndex = Util.calculateNotePositionInListByTimeSum(
      melody,
      start
    );
    int positionEndIndex = Util.calculateNotePositionInListByTimeSum(
      melody,
      end
    );
    List<Double> faultyPosition = new ArrayList<>();
    for (int i = positionStartIndex; i < positionEndIndex; i++) {
      Note particularNote = melody.get(i);
      if (
        this.satisfiesCondition(
            particularNote,
            Util.LookForBaseChord(base, melody, i)
          )
      ) faultyPosition.add(Util.calculateTimeSumByPosition(melody, i)); //faultyPosition.add(i);//
    }

    return faultyPosition;
  }
}
