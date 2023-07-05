/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

public class RepeatingNotes extends GenericImprovisationRule {

  int continuityConstant = 4;

  @Override
  public List<Note> GenerateImprovisation(List<Chord> base) {
    List<Note> improvisation = new ArrayList<Note>();
    int noteDirectionQuantity = 0;
    boolean goingUp = true;
    RhythmFactory rtf = new RhythmFactory(base);
    Rhythm r = rtf.CreateRhythmSequence(new SimpleRhythmCreator());

    Note firstNote = Util.CalculateNoteForChord(
      base.get(0),
      Util.RandomizePitchNotAbusingBluesNote(),
      r.getRhythmSequence().get(0).getDuration(),
      Util.GetOctaveLimitedBetween(5, 3)
    );

    double barCount = 0;
    for (int i = 0; i < r.getRhythmSequence().size() - 1; i++) {
      if (noteDirectionQuantity == 0) {
        int directionRandom = (int) Math.rint(Math.random());
        if (directionRandom == 0) {
          goingUp = false;
        } else {
          goingUp = true;
        }
      }
      int actualNoteExpanded =
        improvisation.get(i).getNote() +
        (improvisation.get(i).getOctave() * 12);
      boolean found = false;
      while (!found) {
        if (goingUp) {
          actualNoteExpanded++;
        } else {
          actualNoteExpanded--;
        }
        Note n = new Note(
          r.getRhythmSequence().get(i + 1).getDuration(),
          actualNoteExpanded % 12 + 1,
          actualNoteExpanded / 12,
          'n'
        );
        int h = Util.IdentifyHeightSemiToneInChord(
          r.getRhythmSequence().get(i + 1).getChord(),
          n
        );
        if (h != 0) {
          Util.CalculateNoteForChord(
            r.getRhythmSequence().get(i + 1).getChord(),
            h,
            r.getRhythmSequence().get(i + 1).getDuration(),
            actualNoteExpanded / 12
          );
          found = true;
        }
      }
      barCount += r.getRhythmSequence().get(i).getDuration();
      noteDirectionQuantity++;
      if (barCount == ConstantsDefinition.getInstance().GetBlackFigure() * 4) {
        barCount = 0;
      }
      if (noteDirectionQuantity == this.continuityConstant) {
        noteDirectionQuantity = 0;
      }
    }
    return improvisation;
  }

  @Override
  public Pair<List<Note>, List<Chord>> VerifyAndCorrectImprovisation(
    List<Note> improvisation,
    List<Chord> base
  ) {
    return new Pair<List<Note>, List<Chord>>(
      this.AnotherImplementation(base, improvisation),
      base
    );
  }

  private List<Note> AnotherImplementation(
    List<Chord> base,
    List<Note> improvisation
  ) {
    boolean goingUp = true;
    int total = this.continuityConstant;
    for (int i = 0; i < improvisation.size() - 1; i++) {
      if (total == this.continuityConstant) {
        if (
          Util.IsHIgherOrEqualPitchThan(
            improvisation.get(i),
            improvisation.get(i + 1)
          )
        ) {
          goingUp = false;
        } else {
          goingUp = true;
        }

        total--;
      } else {
        if (
          Util.IsHIgherPitchThan(
            improvisation.get(i),
            improvisation.get(i + 1)
          ) &&
          goingUp == true &&
          improvisation.get(i + 1).getOctave() <= 8
        ) {
          Note n = improvisation.get(i + 1);

          while (
            Util.IsHIgherPitchThan(improvisation.get(i), n) &&
            n.getOctave() <= 8
          ) {
            int notePitch = n.getNote();
            int noteOctave = n.getOctave();
            int extendedNotationNote = Util.getExpandedFormNote(
              notePitch,
              noteOctave
            );
            Chord c = Util.LookForBaseChord(base, improvisation, i + 1);

            if (notePitch < Util.CalcChord(c)) {
              notePitch += 12;
            }
            switch (notePitch - Util.CalcChord(c)) {
              case 0:
                notePitch =
                  Util.getSmallFormNotePitch(extendedNotationNote + 3);
                noteOctave =
                  Util.getSmallFormNoteOctave(extendedNotationNote + 3);
                break;
              case 3:
                notePitch =
                  Util.getSmallFormNotePitch(extendedNotationNote + 2);
                noteOctave =
                  Util.getSmallFormNoteOctave(extendedNotationNote + 2);
                break;
              case 5:
                notePitch =
                  Util.getSmallFormNotePitch(extendedNotationNote + 1);
                noteOctave =
                  Util.getSmallFormNoteOctave(extendedNotationNote + 1);
                break;
              case 6:
                notePitch =
                  Util.getSmallFormNotePitch(extendedNotationNote + 1);
                noteOctave =
                  Util.getSmallFormNoteOctave(extendedNotationNote + 1);
                break;
              case 7:
                notePitch =
                  Util.getSmallFormNotePitch(extendedNotationNote + 3);
                noteOctave =
                  Util.getSmallFormNoteOctave(extendedNotationNote + 3);
                break;
              case 10:
                notePitch =
                  Util.getSmallFormNotePitch(extendedNotationNote + 2);
                noteOctave =
                  Util.getSmallFormNoteOctave(extendedNotationNote + 2);
                break;
              default:
                System.out.println("La nota la pifie");
                break;
            }
            n.setNote(notePitch);
            n.setOctave(noteOctave);
          }
          improvisation.set(i + 1, n);
          total--;
        } else if (
          Util.IsHIgherPitchThan(
            improvisation.get(i + 1),
            improvisation.get(i)
          ) &&
          goingUp == false &&
          improvisation.get(i + 1).getOctave() >= 5
        ) {
          Note n = improvisation.get(i + 1);
          boolean goingDown = false;
          while (
            Util.IsHIgherPitchThan(n, improvisation.get(i)) &&
            improvisation.get(i + 1).getOctave() >= 5
          ) {
            int notePitch = n.getNote();
            int noteOctave = n.getOctave();
            int extendedNotationNote = Util.getExpandedFormNote(
              notePitch,
              noteOctave
            );

            Chord c = Util.LookForBaseChord(base, improvisation, i + 1);
            if (notePitch < Util.CalcChord(c)) {
              notePitch += 12;
            }
            switch (notePitch - Util.CalcChord(c)) {
              case 0:
                notePitch =
                  Util.getSmallFormNotePitch(extendedNotationNote - 2);
                if (
                  noteOctave ==
                  Util.getSmallFormNoteOctave(extendedNotationNote - 2) &&
                  goingDown
                ) noteOctave =
                  Util.getSmallFormNoteOctave(extendedNotationNote - 2) -
                  1; else noteOctave =
                  Util.getSmallFormNoteOctave(extendedNotationNote - 2);
                break;
              case 3:
                notePitch =
                  Util.getSmallFormNotePitch(extendedNotationNote - 3);

                noteOctave =
                  Util.getSmallFormNoteOctave(extendedNotationNote - 3);
                break;
              case 5:
                notePitch =
                  Util.getSmallFormNotePitch(extendedNotationNote - 2);
                noteOctave =
                  Util.getSmallFormNoteOctave(extendedNotationNote - 2);
                goingDown = true;
                break;
              case 6:
                notePitch =
                  Util.getSmallFormNotePitch(extendedNotationNote - 1);
                noteOctave =
                  Util.getSmallFormNoteOctave(extendedNotationNote - 1);
                break;
              case 7:
                notePitch =
                  Util.getSmallFormNotePitch(extendedNotationNote - 1);
                noteOctave =
                  Util.getSmallFormNoteOctave(extendedNotationNote - 1);
                break;
              case 10:
                notePitch =
                  Util.getSmallFormNotePitch(extendedNotationNote - 3);
                noteOctave =
                  Util.getSmallFormNoteOctave(extendedNotationNote - 3);
                break;
            }
            n.setNote(notePitch);
            n.setOctave(noteOctave);
          }
          improvisation.set(i + 1, n);
          total--;
        }
        if (total == 0) {
          total = this.continuityConstant;
        }
      }
    }

    return improvisation;
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
}
