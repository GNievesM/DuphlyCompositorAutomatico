/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImprovisationRules;

import ConstantDefinition.ConstantsDefinition;
import DataDefinition.Chord;
import DataDefinition.Note;
import Style.AbstractStyle;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author gasto_000
 */
public class Util {
    int minOctave = 3;
    int maxOctave = 5;
    
    /**
     * compara dos notas y retorna si la primera es mayor o igual en tono a la segunda (compara alturas)
     * @param n1
     * @param n2
     * @return
     */
    public static boolean IsHIgherOrEqualPitchThan(Note n1, Note n2) { 
        return n1.getNote() + (12 * n1.getOctave()) >= n2.getNote() + (12 * n2.getOctave());
    }

    /**
     * compara dos notas y retorna si la primera es mayor a la segunda en tono(compara alturas)
     * @param n1
     * @param n2
     * @return 
     */
    public static boolean IsHIgherPitchThan(Note n1, Note n2) {
        return n1.getNote() + (12 * n1.getOctave()) > n2.getNote() + (12 * n2.getOctave());
    }

    /**
     * Compara dos notas y retorna si la primera es menor o igual en tono a la segunda(compara alturas)
     * @param n1
     * @param n2
     * @return 
     */
    public static boolean IsLowerOrEqualPitchThan(Note n1, Note n2) {

        return !Util.IsHIgherOrEqualPitchThan(n1, n2) || (Util.IsHIgherOrEqualPitchThan(n1, n2) && Util.IsHIgherOrEqualPitchThan(n2, n1));
    }

    /**
     * Compara dos notas y retorna si la primera es menor que la segunda en tono.(compara alturas)
     * @param n1
     * @param n2
     * @return
     */
    public static boolean IsEqualPitch(Note n1, Note n2) {
        return n1.getNote() + (12 * n1.getOctave()) == n2.getNote() + (12 * n2.getOctave());
    }

    /**
     *  Dada la base armonica, una linea melodica y la posicion de una nota en la linea melodica, retorna el acorde de base para dicha nota.
     * @param base
     * @param improvisation
     * @param index
     * @return
     */
    public static Chord LookForBaseChord(List<Chord> base, List<Note> improvisation, int index) {
        double totalDuration = 0;
        for (int i = 0; i < index; i++) {
            totalDuration += improvisation.get(i).getDuration();
        }
         totalDuration = (double)((double)Math.round(totalDuration*100)/100);
        int baseDuration = 0;
        for (int i = 0; i < base.size() - 1; i++) {
            if ((double) base.get(i).GetDuration() > totalDuration - (double) baseDuration) {
                return base.get(i);
            }
            baseDuration += base.get(i).GetDuration();
        }
        return base.get(base.size() - 1);
    }

    /**
     * Dado un acorde y una nota, retorna cuantos semitonos hay para la nota correcta asumiendo la escala de blues.
     * @param c
     * @param n
     * @return
     */
    public static int IdentifyHeightSemiToneInChord(Chord c, Note n) {
        int result = 0;
        int ChordGradre = c.GetGrade();
        int NoteGrade = n.getNote();
        if (ChordGradre == NoteGrade) {
            result = 1;
        }
        if (NoteGrade == ((ChordGradre - 1 + 11) % 12)) { // noteGrade es un numero entre 0 y 11, los acordes van de 0-7, 
            result = 11;
        }
        if (NoteGrade == ((ChordGradre - 1 + 4) % 12)) {
            result = 4;
        }
        if (NoteGrade == ((ChordGradre - 1 + 6) % 12)) {
            result = 6;
        }
        if (NoteGrade == ((ChordGradre - 1 + 7) % 12)) {
            result = 7;
        }
        if (NoteGrade == ((ChordGradre - 1 + 8) % 12)) {
            result = 8;
        }

        return result;
    }

    /**
     * Retorna el numero entre 0 y 5 que representa una nota para un acorde
     * @param c
     * @param n
     * @return
     */
    public static int IdentifyBluesScaleNoteHeightInChord(Chord c, Note n) {
          int result = 0;
        int grade = 1;
        switch (c.GetGrade()) {
            case 1:
                grade = 1;
                break;
            case 2:
                grade = 3;
                break;
            case 3:
                grade = 5;
                break;
            case 4:
                grade = 6;
                break;
            case 5:
                grade = 8;
                break;
            case 6:
                grade = 10;
                break;
            case 7:
                grade = 12;
                break;

        }
        
        
     
        int ChordGradre =grade;
        int NoteGrade = n.getNote();
       
        
        if (ChordGradre == NoteGrade) {
            result = 0;
        }
        if (NoteGrade ==((grade + 9) % 12) + 1) {
            result = 5;
        }
        if (NoteGrade == ((grade + 2) % 12) + 1) {
            result = 1;
        }
        if (NoteGrade == ((grade + 4) % 12) + 1) {
            result = 2;
        }
        if (NoteGrade == ((grade + 5) % 12) + 1) {
            result = 3;
        }
        if (NoteGrade == ((grade + 6) % 12) + 1) {
            result = 4;
        }

        return result;
    }

    /**
     *
     * @param c Aocrde sobre el cual se desea calcular la altura de la nota.
     * @param height Altura con respecto al acorde que se desea calcular. Las
     * altura estan denotadas con numero del 0 al 5, represetando el 0 la
     * primera nota de la escala de blues y asi consiguientemente el resto
     *
     * @return retorna la altura asociada a ese acorde dependiendo del numero de
     * nota en la escala de blues.
     */
    public static int CalculateNoteHeightForChord(Chord c, int height) {
        int result = 0;
        int grade = 1;
        switch (c.GetGrade()) {
            case 1:
                grade = 1;
                break;
            case 2:
                grade = 3;
                break;
            case 3:
                grade = 5;
                break;
            case 4:
                grade = 6;
                break;
            case 5:
                grade = 8;
                break;
            case 6:
                grade = 10;
                break;
            case 7:
                grade = 12;
                break;

        }
        switch (height) {
            case 0:
                result = grade;
                break;
            case 1:
                result = ((grade + 2) % 12) + 1;
                break;
            case 2:
                result = ((grade + 4) % 12) + 1;
                break;
            case 3:
                result = ((grade + 5) % 12) + 1;
                break;
            case 4:
                result = ((grade + 6) % 12) + 1;
                break;
            case 5:
                result = ((grade + 9) % 12) + 1;
                break;
        }

        return result;
    }

    /**
     * DAdo un acorde, una altura, una duracion y una octava se retorna una nota
     * de la escala de blues valida para el acorde.
     * @param c
     * @param height
     * @param duration
     * @param octave
     * @return
     */
    public static Note CalculateNoteForChord(Chord c, int height, double duration, int octave) {
        Note returnNote = new Note();
        returnNote.setDuration(duration);
        returnNote.setNote(Util.CalculateNoteHeightForChord(c, height));
        returnNote.setOctave(octave);
        if (height % 2 == 0) {
            returnNote.setAccident('n');
        } else {
            returnNote.setAccident('b');
        }

        return returnNote;
    }

    /**
     *
     * @param lowestOctave
     * @param octaveQuantity
     * @return
     */
    public static int GetOctaveLimitedBetween(int lowestOctave, int octaveQuantity) {
        return (int) Math.rint(Math.random() * (octaveQuantity - 1)) + lowestOctave;
    }

    /**
     * Retorna un numero entre el 0 y el 5 que representa una nota con respecto 
     * a la base, siendo que le baja la probabilidad a que salga la nota de blues.
     * @return
     */
    public static int RandomizePitchNotAbusingBluesNote() {
        int NoteToImprovisation = (int) Math.rint(Math.random() * 99);// Multiplico por 100 para asignar probabiilidades.
        if (NoteToImprovisation <= 9) {
            NoteToImprovisation = 3;
        } else {
            NoteToImprovisation = (NoteToImprovisation - 10) / 18;
            if (NoteToImprovisation == 3) {
                NoteToImprovisation = 5;
            }
        }
        return NoteToImprovisation;
    }

    /**
     * Retorna la altura de una nota de 
     * forma expandida de la nota, es decir, el numero que la representa
     * de forma absoluta dentro de todas las posibles alturas.
     * @param pitch
     * @param octave
     * @return
     */
    public static int getExpandedFormNote(int pitch, int octave) {
        return pitch + octave * 12;
    }

    /**
     *Retorna una altura abosulta en un par nota y octava.
     * @param expandedNote
     * @return
     */
    public static Pair<Integer, Integer> getSmallFormNotePitchOctave(int expandedNote) {
        return new Pair(((expandedNote - 1) % 12) + 1, expandedNote / 12);
    }

    /**
     * Retorna unicamente el numero correspondiente a la nota dada una nota
     * expandida.
     * @param expandedNote
     * @return
     */
    public static int getSmallFormNotePitch(int expandedNote) {
        return ((expandedNote - 1) % 12) + 1;
    }

    /**
     *Dada una nota en su valor absoluto (expandido) retorna la octava correspondiente
     * a la nota.
     * @param expandedNote
     * @return
     */
    public static int getSmallFormNoteOctave(int expandedNote) {
        if(expandedNote/12 == 0)
        return expandedNote / 12 -1;
        return expandedNote/12;
    }

    /**
     * Dado el grado de un acorde calcula la nota que le corresponde a la siguiente
     * raiz del acorde.
     * @param c
     * @return
     */
    public static int CalcChord(Chord c) {
        int grade = 1;
        switch (c.GetGrade()) {
            case 1:
                grade = 1;
                break;
            case 2:
                grade = 3;
                break;
            case 3:
                grade = 5;
                break;
            case 4:
                grade = 6;
                break;
            case 5:
                grade = 8;
                break;
            case 6:
                grade = 10;
                break;
            case 7:
                grade = 12;
                break;

        }
        return grade;
    }
    
    /**
     * Retorna en que posicion se encuentra una nota basandose en la suma de 
     * todas las duraciones de las notas.
     * @param notes
     * @param sum
     * @return
     */
    public static int calculateNotePositionInListByTimeSum(List<Note> notes, double sum){
        int result = 0;
        if(sum == 0)
            return 0;
        for(int i = 0; i< notes.size() && sum >0.1; i++){
            Note note = notes.get(i);
            if(sum>0.1){
                sum = sum - note.getDuration();
                result++;
            }
        }
        return result;
    }

    /**
     * calcula la suma de tiempos de toda la melodia hasta un numero de nota en 
     * la lista.
     * @param notes
     * @param position
     * @return
     */
    public static double calculateTimeSumByPosition(List<Note> notes, int position){
        double timeSum = 0;
        for (int i = 0; i < position; i++) {
            timeSum +=notes.get(i).getDuration();
        }
        return timeSum;
    }
    
    /**
     * Dada una nota y un numero de semitonos, suma los semitonos a la nota, modifica la nota del parametro.
     * @param n
     * @param cuantity
     * @return
     */
    public static Note addDemiTones(Note n, int cuantity){
        int noteNumber = Util.getExpandedFormNote(n.getNote(), n.getOctave())+cuantity;
            n.setNote(Util.getSmallFormNotePitch(noteNumber));
            n.setOctave(Util.getSmallFormNoteOctave(noteNumber));
        
        return n;
        
    }
    

    /**
     * retorna una nota en base al acorde que se esta evaluando.
     * @param c
     * @return
     */
    public static Note GetRandomNoteBasedOnChord(Chord c){
        
        return null;
    }
    
    /**
     * Retorna la cantidad de compases que se encuentran en la musica
     */
    public static int GetBlockQuantity(List<Note> melody){
        
        int blockNumbers;
        int blockSize = ConstantsDefinition.getInstance().GetBlackFigure()*4;
        double count = 0;
        for (int i = 0; i < melody.size(); i++) {
            count +=melody.get(i).getDuration();
        }
        blockNumbers = (int) Math.round(count / blockSize);
        return blockNumbers;
    
    }
    /**
     * Dada la melodia y un numero de compas retorna la posicion de la primer nota que se encuentra dentro del compas. contando compases desde el 0 
     * @param melody
     * @param blockNumber
     * @return 
     */
    public static int getBlockPositionByNumber(List<Note> melody, int blockNumber){
        int blockSize = ConstantsDefinition.getInstance().GetBlackFigure()*4;
        double count =0;
        int i ;
        int currentBlock =0 ;
        for (i = 0; i < melody.size() && currentBlock != blockNumber; i++) {
            count += melody.get(i).getDuration();
            if(count%blockSize ==0 || blockSize-count%blockSize <= 0.1)
                currentBlock++;
                
        }
        
        return i;
    }
    
    public static double countBlocks(List<Note> melody, double startBlock, double endBlock){
        int blockSize = ConstantsDefinition.getInstance().GetBlackFigure()*4;
        double count =0;
        int startPosition = Util.calculateNotePositionInListByTimeSum(melody,startBlock);
        int endPosition = Util.calculateNotePositionInListByTimeSum(melody,startBlock);
        for (int i = startPosition; i < endPosition ; i++) {
            count += melody.get(i).getDuration();
            if(count%blockSize == 0)
              count++;
                
        }
        
        return count;
    }
    public static double getMelodyTotalLength(List<Note> melody){
        double total =0;
        for(Note n : melody){
            total += n.getDuration();
        }
        return total;
    }
    
    public static int calculateNoteGradeForChord(Chord c, Note n){
        int chordBasePitch = Util.CalcChord(c);
        int pitchForMath = n.getNote()<chordBasePitch? 12+n.getNote() : n.getNote();
        int semiTonesDistance = pitchForMath- chordBasePitch;
        return Util.calculateGradeBySemitones(semiTonesDistance);
    
    }
    
     
    
    
    public static int calculateGradeBySemitones(int semitonesDistance){
        int result = 0;
        switch (semitonesDistance){
            case 0 : result =1;break;
            case 1 : result =1;break;
            case 2 : result =2;break;
            case 3 : result =2;break;
            case 4 : result = 3;break;
            case 5 : result = 4;break;
            case 6 : result = 4;break;
            case 7 : result = 5;break;
            case 8 : result = 5;break;
            case 9 : result = 6;break;
            case 10 : result = 6;break;
            case 11: result =7;break;
        }
        return result;
    }
    
    public static int returnChordBasePitch(Chord c, int root){
        int result =0;
        switch (c.GetGrade()){
            case 1:result = root;break;
            case 2: result = root+2;break;
            case 3: result = root +4;break;
            case 4: result = root +5;break;
            case 5: result = root + 7;break;
            case 6: result = root + 9;break;
            case 7: result = root + 11;break;
        }
        return result;    
    }
    
    public static Note returnNoteAfterAddingGradesToChord(Chord c,  int demitones, int octave, double duration){
        Note n = new Note();
        n.setAccident('n');
        n.setOctave(octave);
        n.setDuration(duration);
        n.setNote(Util.returnChordBasePitch(c, 1));
        n = Util.addDemiTones(n, demitones);
        return n;    
    }
}
