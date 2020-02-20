/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConverterUtil;

import DataDefinition.Chord;
import jm.music.data.CPhrase;

/**
 *
 * @author gasto_000
 */
public class ChordToJmusicUtil {

   // public static CPhrase CalculateChord(char mode, int grade, boolean septima, int root, double duration) {
    public static CPhrase CalculateChord(Chord c, int root, double duration) { // le paso la duracion para permitir crear diferentes metricas con el mismo acorde.
 
    /*   int chordFirst =  CalculateChordFirst(grade, root);
        int[] pitchArray = new int[4];
        CPhrase chord = new CPhrase();
        if (mode == 'M') {
            pitchArray[0] = chordFirst;
            pitchArray[1] = chordFirst + 4;
            pitchArray[2] = chordFirst + 7;
            pitchArray[3] = chordFirst + 12;
            if (septima == true) {
                pitchArray[3] = chordFirst + 10;
            }
            
            chord.addChord(pitchArray, duration);
        }*/
      CPhrase chord = new CPhrase();
      chord.addChord(ChordCalculator.pitchArray(c, root), duration);
        return chord;

    }

  
}
