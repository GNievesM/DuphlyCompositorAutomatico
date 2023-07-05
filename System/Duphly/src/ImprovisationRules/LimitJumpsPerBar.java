package ImprovisationRules;

import ConstantDefinition.ConstantsDefinition;
import DataDefinition.Chord;
import DataDefinition.Note;
import DataDefinition.Rhythm;
import ImprovisationManagement.GenericImprovisationRule;
import RhythmCreator.RhythmFactory;
import RhythmRules.SimpleRhythmCreator;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author gasto_000
 */
public class LimitJumpsPerBar extends GenericImprovisationRule {

  private int jumpsLeft;
  private List<Note> improvisation;

  @Override
  public List<Note> GenerateImprovisation(List<Chord> base) {
    improvisation = new ArrayList<Note>();
    RhythmFactory rf = new RhythmFactory(base);
    Rhythm r = rf.CreateRhythmSequence(new SimpleRhythmCreator());

    jumpsLeft = 3;
    double totalTime = 0;
    double changeFirstTime = 0;
    boolean firstTime = true;
    double chordDuration = r
      .getRhythmSequence()
      .get(0)
      .getChord()
      .GetDuration();
    for (int i = 0; i < r.getRhythmSequence().size(); i++) {
      if (totalTime > ConstantsDefinition.getInstance().GetBlackFigure() * 4) {
        totalTime = 0;

        jumpsLeft = 3;
      }
      if (changeFirstTime >= chordDuration) {
        changeFirstTime = 0;
        chordDuration = r.getRhythmSequence().get(i).getChord().GetDuration();
      }
      firstTime = changeFirstTime == 0;

      this.NoteListFormationForChord(
          r.getRhythmSequence().get(i).getDuration(),
          r.getRhythmSequence().get(i).getChord(),
          jumpsLeft,
          firstTime
        );
      changeFirstTime += r.getRhythmSequence().get(i).getDuration();
      totalTime += r.getRhythmSequence().get(i).getDuration();
    }
    return improvisation;
  }

  @Override
  public Pair<List<Note>, List<Chord>> VerifyAndCorrectImprovisation(
    List<Note> improvisation,
    List<Chord> base
  ) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean VerifyImprovisationAppliesRule(
    List<Note> improvisation,
    List<Chord> base
  ) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<Note> ApplyRuleInAMoment(
    List<Note> improvisation,
    List<Chord> base,
    int momentToApply
  ) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  private void NoteListFormationForChord(
    double duration,
    Chord acord,
    int jumpsLeft,
    boolean firstTime
  ) {
    boolean noteSelected = false;
    while (!noteSelected) {
      int NoteToImprovisation = (int) Math.rint(Math.random() * 5);
      int noteOctave = (int) Math.rint((Math.random() * 2) + 5);
      int noteBehind = NoteToImprovisation;
      int octave = noteOctave;

      if (this.improvisation.size() > 0) {
        noteBehind =
          this.improvisation.get(this.improvisation.size() - 1).getNote();
        octave =
          this.improvisation.get(this.improvisation.size() - 1).getOctave();
      }

      int note = 0;
      switch (NoteToImprovisation) {
        case 0:
          note = Util.CalculateNoteHeightForChord(acord, 5);
          if (
            firstTime &&
            (Math.abs(noteBehind - note) < 4) &&
            octave == noteOctave
          ) {
            this.improvisation.add(new Note(duration, note, noteOctave, 'b'));
            noteSelected = true;
            break;
          }
          if (
            Math.abs(
              (note + (noteOctave * 12)) - (noteBehind + (12 * octave))
            ) <
            4 ||
            this.jumpsLeft > 0
          ) {
            this.improvisation.add(new Note(duration, note, noteOctave, 'b'));
            noteSelected = true;
            if (
              Math.abs(
                note + (noteOctave * 12) - (noteBehind + (12 * octave))
              ) >=
              4
            ) {
              this.jumpsLeft--;
            }
          } else {
            noteSelected = false;
          }
          break;
        case 1:
          note = Util.CalculateNoteHeightForChord(acord, 0); //acord.GetGrade();
          if (
            firstTime &&
            (Math.abs(noteBehind - note) < 4) &&
            octave == noteOctave
          ) {
            this.improvisation.add(new Note(duration, note, noteOctave, 'b'));
            noteSelected = true;
            break;
          }
          if (
            Math.abs(
              (note + (12 * noteOctave)) - (noteBehind + (12 * octave))
            ) <
            4 ||
            this.jumpsLeft > 0
          ) {
            noteSelected = true;
            this.improvisation.add(new Note(duration, note, noteOctave, 'n'));
            if (
              Math.abs(
                note + (12 * noteOctave) - (noteBehind + (12 * octave))
              ) >=
              4
            ) {
              this.jumpsLeft--;
            }
          } else {
            noteSelected = false;
          }
          break;
        case 2:
          note = Util.CalculateNoteHeightForChord(acord, 1);
          if (
            firstTime &&
            (Math.abs(noteBehind - note) < 4) &&
            octave == noteOctave
          ) {
            this.improvisation.add(new Note(duration, note, noteOctave, 'b'));
            noteSelected = true;
            break;
          }
          if (
            note + (12 * noteOctave) + 2 == noteBehind + (12 * octave) ||
            (note + (12 * noteOctave)) - 3 == noteBehind + (12 * octave) ||
            (note == noteBehind && noteOctave == octave) ||
            this.jumpsLeft > 0
          ) {
            this.improvisation.add(new Note(duration, note, noteOctave, 'b'));
            noteSelected = true;
            if (
              note + (12 * noteOctave) + 2 != noteBehind + (12 * octave) &&
              (note + (12 * noteOctave)) - 3 != noteBehind + (12 * octave) &&
              (note != noteBehind || noteOctave != octave)
            ) {
              this.jumpsLeft--;
            }
          } else {
            noteSelected = false;
          }
          break;
        case 3:
          note = Util.CalculateNoteHeightForChord(acord, 2);
          if (
            firstTime &&
            (Math.abs(noteBehind - note) < 4) &&
            octave == noteOctave
          ) {
            this.improvisation.add(new Note(duration, note, noteOctave, 'b'));
            noteSelected = true;
            break;
          }
          if (
            note + (12 * noteOctave) == noteBehind + (12 * octave) - 1 ||
            note + (12 * noteOctave) == noteBehind + (12 * octave) - 2 ||
            (note == noteBehind && noteOctave == octave) ||
            this.jumpsLeft > 0
          ) {
            this.improvisation.add(new Note(duration, note, noteOctave, 'n'));
            noteSelected = true;
            if (
              note + (12 * noteOctave) != noteBehind + (12 * octave) - 1 &&
              note + (12 * noteOctave) != noteBehind + (12 * octave) - 2 &&
              (note != noteBehind || noteOctave != octave)
            ) {
              this.jumpsLeft--;
            }
          } else {
            noteSelected = false;
          }
          break;
        case 4:
          note = Util.CalculateNoteHeightForChord(acord, 3);
          if (
            firstTime &&
            (Math.abs(noteBehind - note) < 4) &&
            octave == noteOctave
          ) {
            this.improvisation.add(new Note(duration, note, noteOctave, 'b'));
            noteSelected = true;
            break;
          }
          if (
            note + (12 * noteOctave) + 1 == noteBehind + (12 * octave) ||
            note + (12 * noteOctave) - 1 == noteBehind + (12 * octave) ||
            (note == noteBehind && noteOctave == octave) ||
            this.jumpsLeft > 0
          ) {
            this.improvisation.add(new Note(duration, note, noteOctave, 'b'));
            noteSelected = true;
            if (
              note + (12 * noteOctave) + 1 != noteBehind + (12 * octave) &&
              note + (12 * noteOctave) - 1 != noteBehind + (12 * octave) &&
              (note != noteBehind || noteOctave != octave)
            ) {
              this.jumpsLeft--;
            }
          } else {
            noteSelected = false;
          }
          break;
        case 5:
          note = Util.CalculateNoteHeightForChord(acord, 4);
          if (note == noteBehind) if (
            firstTime &&
            (Math.abs(noteBehind - note) < 4) &&
            octave == noteOctave
          ) {
            this.improvisation.add(new Note(duration, note, noteOctave, 'b'));
            noteSelected = true;
            break;
          }
          if (
            note + (12 * noteOctave) + 3 == noteBehind + (12 * octave) ||
            note + (12 * noteOctave) - 1 == noteBehind + (12 * octave) ||
            (note == noteBehind && noteOctave == octave) ||
            this.jumpsLeft > 0
          ) {
            this.improvisation.add(new Note(duration, note, noteOctave, 'n'));
            noteSelected = true;
            if (
              note + (12 * noteOctave) + 3 != noteBehind + (12 * octave) &&
              note + (12 * noteOctave) - 1 != noteBehind + (12 * octave) &&
              (note != noteBehind || noteOctave != octave)
            ) {
              this.jumpsLeft--;
            }
          } else {
            noteSelected = false;
          }
          break;
      }
    }
  }
}
