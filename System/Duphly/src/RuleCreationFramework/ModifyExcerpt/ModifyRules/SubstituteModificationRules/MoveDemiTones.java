package RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import EvolutionaryAlgorithm.Mutator;
import ImprovisationRules.Util;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import Style.AbstractStyle;
import java.util.List;

public class MoveDemiTones extends SpecificModification {

  boolean up;
  int quantity;
  AbstractStyle style;

  public MoveDemiTones(boolean up, int quantity) {
    this.up = up;
    this.quantity = quantity;
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
    Note particularNote = melody.get(position);
    melody.set(
      position,
      style.addDemiTones(particularNote, up ? quantity : -quantity)
    );
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
    return style.addDemiTones(
      melody.get(specificNotePosition),
      up ? quantity : -quantity
    );
  }
}
