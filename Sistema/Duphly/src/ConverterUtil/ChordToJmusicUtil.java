package ConverterUtil;

import DataDefinition.Chord;
import jm.music.data.CPhrase;


public class ChordToJmusicUtil {

    public static CPhrase CalculateChord(Chord c, int root, double duration) { // I pass the duration to allow creating different metrics with the same chord.
 
      CPhrase chord = new CPhrase();
      chord.addChord(ChordCalculator.pitchArray(c, root), duration);
        return chord;

    }

  
}
