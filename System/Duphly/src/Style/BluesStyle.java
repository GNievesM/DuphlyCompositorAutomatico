package Style;

import ConverterUtil.ChordCalculator;
import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;


public class BluesStyle extends AbstractStyle {

    @Override
    public Note validRandomNote(Chord c, double duration, List<Double> optionalProbability) {
        List<Double> probabilityToUse = (optionalProbability == null ? (c.isMinor() ? this.probabilityDistributionMinor : this.probabilityDistributionMajor) : optionalProbability);
        int calculateRandomUsingProbability = this.calculateRandomUsingProbability(this.filterValidNotes(probabilityToUse, c));
        int height = this.calculateNoteHeightBaseOnChord(c, calculateRandomUsingProbability);
        Note result = new Note(duration, height, this.randomOctave(), this.calculateAccident(height));
        return result;
    }

    @Override
    public Note nextValidNote(Chord c, Note n) {
        List<Integer> chordNotes = this.validNotesPerBase(c);
        int noteResult = n.getNote() + 1;
        int octave = n.getOctave();
        while (!chordNotes.contains(noteResult)) {
            noteResult++;
            if (noteResult > 11) {
                noteResult = this.calculateNoteOutOfBounds(noteResult);
                octave++;
            }
        }
        if (octave > maxOctave) {
            return n;
        }

        Note solution = new Note(n.getDuration(), noteResult, octave, this.calculateAccident(octave));
        return solution;
    }

    private Boolean belongsTo(int note, int[] noteArray) {
        for (int i = 0; i < noteArray.length; i++) {
            if (note == noteArray[i]) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Note previousValidNote(Chord c, Note n) {
        List<Integer> chordNotes = this.validNotesPerBase(c);

        int noteResult = n.getNote() - 1;
        int octave = n.getOctave();
        while (!chordNotes.contains(noteResult)) {
            noteResult--;
            if (noteResult < 1) {
                noteResult = this.calculateNoteOutOfBounds(noteResult);
                octave--;
            }
        }
        if (octave < minOctave) {
            return n;
        }

        Note solution = new Note(n.getDuration(), noteResult, octave, this.calculateAccident(octave));
        return solution;
    }

    @Override
    public Note biggerValidNote(Chord c, Note n, List<Double> optionalProbability) {
        if (this.noteIsEqual(n, this.nextValidNote(c, n))) {
            return n;
        }
        Note result = this.validRandomNote(c, n.getDuration(), optionalProbability);
        while (this.noteIsSmaller(result, n) || this.noteIsEqual(result, n)) {
            result = this.validRandomNote(c, n.getDuration(), optionalProbability);
        }

        return result;

    }

    @Override
    public Note smallerValidNote(Chord c, Note n, List<Double> optionalProbability) {
        if (this.noteIsEqual(n, this.previousValidNote(c, n))) {
            return n;
        }
        Note result = this.validRandomNote(c, n.getDuration(), optionalProbability);
        while (this.noteIsBigger(result, n) || this.noteIsEqual(result, n)) {
            result = this.validRandomNote(c, n.getDuration(), optionalProbability);
        }

        return result;
    }

    @Override
    public Note ProbabilityRandomNote(Chord c, double duration, List<Double> optionalProbability) {
        int calculateRandomUsingProbability = this.calculateRandomUsingProbability(optionalProbability == null ? (c.isMinor() ? this.probabilityDistributionMinor : this.probabilityDistributionMajor) : optionalProbability);
        int height = this.calculateNoteHeightBaseOnChord(c, calculateRandomUsingProbability);
        return new Note(duration, height, this.randomOctave(), this.calculateAccident(height));

    }

    @Override
    public Note biggerProbabilityNote(Chord c, Note n, List<Double> optionalProbability) {
        if (n.getNote() == 12 && n.getOctave() == maxOctave) {
            return n;
        }
        Note result = this.ProbabilityRandomNote(c, n.getDuration(), optionalProbability);
        while (this.noteIsSmaller(result, n) || this.noteIsEqual(result, n)) {
            result = this.validRandomNote(c, n.getDuration(), optionalProbability);
        }

        return result;
    }

    @Override
    public Note smallerProbabilityNote(Chord c, Note n, List<Double> optionalProbability) {
        if (n.getNote() == 1 && n.getOctave() == minOctave) {
            return n;
        }
        Note result = this.ProbabilityRandomNote(c, n.getDuration(), optionalProbability);
        while (this.noteIsBigger(result, n) || this.noteIsEqual(result, n)) {
            result = this.validRandomNote(c, n.getDuration(), optionalProbability);
        }

        return result;
    }

    @Override
    public Note nextProbabilityNote(Chord c, Note n, List<Double> optionalProbability, double probBiggerThan) {
        List<Double> probability = this.filterProbSmallerThan(
                optionalProbability != null ? optionalProbability : c.isMinor() ? this.probabilityDistributionMinor : this.probabilityDistributionMajor,
                probBiggerThan);

        int octave = n.getOctave();
        int noteResult = n.getNote();
        while (probability.get(noteResult) == 0) {
            if (noteResult > 12) {
                octave++;
            }
            noteResult = this.calculateNoteOutOfBounds(noteResult + 1);

        }
        return new Note(n.getDuration(), noteResult, octave, this.calculateAccident(noteResult));
    }

    @Override
    public Note previousProbabilityNote(Chord c, Note n, List<Double> optionalProbability, double probBiggerThan) {
        List<Double> probability = this.filterProbSmallerThan(
                optionalProbability != null ? optionalProbability : c.isMinor() ? this.probabilityDistributionMinor : this.probabilityDistributionMajor,
                probBiggerThan);

        int octave = n.getOctave();
        int noteResult = n.getNote();
        while (probability.get(noteResult) == 0) {
            if (noteResult < 1) {
                octave--;
            }
            noteResult = this.calculateNoteOutOfBounds(noteResult - 1);

        }
        return new Note(n.getDuration(), noteResult, octave, this.calculateAccident(noteResult));
    }


    @Override
    protected List<Integer> validNotesPerBase(Chord base) {
        List<Integer> noteList = new ArrayList<>();
        List<Integer> abstractList = this.validHeightsAbstractForm(base);
        for (Integer in : abstractList) {
            noteList.add(this.calculateNoteHeightBaseOnChord(base, in.intValue()));
        }

        return noteList;
    }

    @Override
    protected List<Integer> validHeightsAbstractForm(Chord base) {
        List<Integer> result = new ArrayList();
        result.add(1);
        result.add(4);
        result.add(6);
        result.add(7);
        result.add(8);
        result.add(11);
        return result;
    }
}
