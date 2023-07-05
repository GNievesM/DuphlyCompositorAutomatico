package RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import Style.AbstractStyle;
import java.util.List;

public class CopyMelodyNote extends SpecificModification {

  boolean siguiente;

  public CopyMelodyNote(boolean siguiente) {
    this.siguiente = siguiente;
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
    melody.set(position, melody.get(position + (this.siguiente ? 1 : -1)));
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
    Note n = melody.get(position);
    if (specificNotePosition > 0 && specificNotePosition < melody.size()) n =
      melody.get(position + (this.siguiente ? 1 : -1));
    return n;
  }
}
