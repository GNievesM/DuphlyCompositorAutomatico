package RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import Style.AbstractStyle;
import java.util.List;

public class SubstituteForASequency extends SpecificModification {

  List<Note> sequence;
  boolean completeNote;

  public SubstituteForASequency(List<Note> sequence, boolean completeNote) {
    this.sequence = sequence;
    this.completeNote = completeNote;
  }

  @Override
  public List<Note> makeModification(
    List<Chord> base,
    List<Note> melody,
    double start,
    double end,
    double specificNote,
    AbstractStyle style
  ) {
    int position = Util.calculateNotePositionInListByTimeSum(
      melody,
      specificNote
    );
    int lastPosition = Util.calculateNotePositionInListByTimeSum(melody, end);
    for (
      int i = position, sequencePosition = 0;
      i < lastPosition && sequencePosition < sequence.size();
      i++, sequencePosition++
    ) {
      Note modifying = melody.get(i);
      modifying.setNote(this.sequence.get(sequencePosition).getNote());
      if (completeNote) modifying.setOctave(
        this.sequence.get(sequencePosition).getOctave()
      );
      melody.set(i, modifying);
    }
    return melody;
  }

  @Override
  public Note mutate(
    List<Chord> base,
    List<Note> melody,
    double start,
    double end,
    int specificNotePosition,
    AbstractStyle style
  ) {
    int position = specificNotePosition;
    int lastPosition = Util.calculateNotePositionInListByTimeSum(melody, end);
    Note firstNote = melody.get(specificNotePosition);
    for (
      int i = position, sequencePosition = 0;
      i < lastPosition && sequencePosition < sequence.size();
      i++, sequencePosition++
    ) {
      Note modifying = melody.get(i);
      modifying.setNote(this.sequence.get(sequencePosition).getNote());
      if (completeNote) modifying.setOctave(
        this.sequence.get(sequencePosition).getOctave()
      );
      melody.set(i, modifying);
    }
    return firstNote;
  }
}
