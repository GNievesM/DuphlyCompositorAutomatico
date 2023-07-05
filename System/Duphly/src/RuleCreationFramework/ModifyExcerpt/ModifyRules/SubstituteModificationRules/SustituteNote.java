package RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import Style.AbstractStyle;
import java.util.List;

public class SustituteNote extends SpecificModification {

  int pitch;
  int octave;
  boolean noteOnly;
  boolean silence;

  public SustituteNote(int pitch, int octave, boolean noteOnly) {
    this.pitch = pitch;
    this.octave = octave;
    this.noteOnly = noteOnly;
  }

  public SustituteNote(boolean silence) {
    this.pitch = 0;
    this.octave = -1;
    this.noteOnly = false;
    this.silence = silence;
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
    if (this.silence) {
      particularNote.setNote(0);
      particularNote.setOctave(-1);
    } else {
      particularNote.setNote(pitch);
      if (!noteOnly) particularNote.setOctave(octave);
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
    int position = specificNotePosition;
    Note particularNote = melody.get(position);
    if (this.silence) {
      particularNote.setNote(0);
      particularNote.setOctave(-1);
    } else {
      particularNote.setNote(pitch);
      if (!noteOnly) particularNote.setOctave(octave);
    }

    return particularNote;
  }
}
