package RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import Style.AbstractStyle;
import java.util.List;

public class SustituteRandomValidNote extends SpecificModification {

  Comparador comp;
  int distance;
  List<Double> prob;
  boolean constraint;

  public SustituteRandomValidNote(
    Comparador comp,
    int distance,
    List<Double> optionalProbability
  ) {
    this.comp = comp;
    this.distance = distance;
    this.prob = optionalProbability;
    constraint = true;
  }

  public SustituteRandomValidNote(boolean constraint) {
    this.comp = null;
    this.distance = 0;
    this.prob = null;
    this.constraint = false;
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
    if (
      position + distance < 0 ||
      position + distance > melody.size() ||
      comp.isEqual()
    ) return melody;

    Chord noteAssociatedChord = Util.LookForBaseChord(base, melody, position);
    Note particularNote = melody.get(position + distance);
    particularNote =
      new Note(
        melody.get(position).getDuration(),
        particularNote.getNote(),
        particularNote.getOctave(),
        particularNote.getAccident()
      );

    if (!constraint) particularNote =
      style.validRandomNote(
        noteAssociatedChord,
        particularNote.getDuration(),
        prob
      ); else {
      if (comp.isGreater()) {
        particularNote =
          style.biggerValidNote(noteAssociatedChord, particularNote, prob);
      }
      if (comp.isLess()) {
        particularNote =
          style.smallerValidNote(noteAssociatedChord, particularNote, prob);
      }
    }
    melody.set(position, particularNote);
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
    Note element = melody.get(specificNotePosition);
    int position = specificNotePosition;

    if (
      position + distance < 0 || position + distance > melody.size()
    ) return element;

    Chord noteAssociatedChord = Util.LookForBaseChord(base, melody, position);
    Note particularNote = melody.get(position + distance);
    if (!constraint) element =
      style.validRandomNote(
        noteAssociatedChord,
        particularNote.getDuration(),
        prob
      ); else {
      if (comp.isGreater()) {
        element =
          style.biggerValidNote(noteAssociatedChord, particularNote, prob);
      }
      if (comp.isLess()) {
        element =
          style.smallerProbabilityNote(
            noteAssociatedChord,
            particularNote,
            prob
          );
      }
    }
    return element;
  }
}
