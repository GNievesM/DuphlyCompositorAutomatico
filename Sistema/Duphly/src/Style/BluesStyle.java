/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Style;

import ConverterUtil.ChordCalculator;
import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Gaston
 */
public class BluesStyle extends AbstractStyle {

//// TAL VEZ FALTA AGREGAR LA NOTA DE BLUES ENTRE LAS NOTAS VALIDAS.

    /*Toma una nota aleatoria del acorde basado en la probailidad dada.*/
    @Override
    public Note validRandomNote(Chord c, double duration, List<Double> optionalProbability) {
        List<Double> probabilityToUse = (optionalProbability == null ? (c.isMinor() ? this.probabilityDistributionMinor : this.probabilityDistributionMajor) : optionalProbability);
        int calculateRandomUsingProbability = this.calculateRandomUsingProbability(this.filterValidNotes(probabilityToUse, c));
       // int[] chordNoteHeights = ChordCalculator.pitchArray(c, melodyRoot);
   //     int validNote = chordNoteHeights[calculateRandomUsingProbability % chordNoteHeights.length];
    //    int height = this.calculateNoteHeightBaseOnChord(c, validNote);
        int height = this.calculateNoteHeightBaseOnChord(c,calculateRandomUsingProbability);
        Note result = new Note(duration, height, this.randomOctave(), this.calculateAccident(height));
        return result;
    }

    @Override
    public Note nextValidNote(Chord c, Note n) {
        //int[] chordNotes = ChordCalculator.pitchArray(c, melodyRoot);
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
        if (octave > maxOctave)//caso en el que no haya solucion por que se va de las octavas permitidas.
        {
            return n;
        }

        Note solution = new Note(n.getDuration(), noteResult, octave, this.calculateAccident(octave));
        return solution;
    }

    private Boolean pertenece(int nota, int[] arrayDeNotas) {
        for (int i = 0; i < arrayDeNotas.length; i++) {
            if (nota == arrayDeNotas[i]) {
                return true;
            }
        }
        return false;

    }

    @Override
    public Note previousValidNote(Chord c, Note n) {
       // int[] chordNotes = ChordCalculator.pitchArray(c, melodyRoot);
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
        if (octave < minOctave)//caso en el que no haya solucion por que se va de las octavas permitidas y retorna la misma nota que recibio
        {
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
        if (n.getNote() == 12 && n.getOctave() == maxOctave) // me fijo si es valida una nota mayor.
        {
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
        if (n.getNote() == 1 && n.getOctave() == minOctave) // me fijo si es valida una nota menor
        {
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
        for(Integer in:abstractList){
            noteList.add(this.calculateNoteHeightBaseOnChord(base, in.intValue()));
        }
            
        return noteList;
    }

    @Override
    protected List<Integer> validHeightsAbstractForm(Chord base) {
        List<Integer> result = new ArrayList();
  
        if(!base.isMinor()){
            result.add(1);
            result.add(4);
            result.add(6);
            result.add(7);
            result.add(8);
            result.add(11);
        }else{
            result.add(1);
            result.add(3);
            result.add(4);
            result.add(5);
            result.add(8);
            result.add(10);
            
        }
        return result;
    }
}
