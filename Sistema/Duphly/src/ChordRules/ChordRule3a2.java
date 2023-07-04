/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChordRules;

import ChordManagement.ChordOperationUtil;
import ConstantDefinition.ConstantsDefinition;
import DataDefinition.Chord;
import ChordManagement.GenericChordRule;
import java.util.ArrayList;
import java.util.List;

public class ChordRule3a2 extends GenericChordRule {

    @Override
    protected List<Chord> seekChordPlace(List<Chord> base, int tempo) {
        int twelveBarsFigure = ConstantsDefinition.getInstance().getTwelveBars();
        if (tempo >= twelveBarsFigure - 1) // If the tempo exceeds the 12-bar figure, which is equivalent to 12 bars in blues, return the base chords as is.
        {
            return base; // Return the base chords without modifications.
        }
        int sumTimes = 0;
        for (int i = 0; i < base.size() - 1; i++) { // The rule cannot be applied to the last chord
            if (sumTimes == tempo && rule3a2Precondition(base.get(i), base.get(i + 1), tempo)) {
                int duration = base.get(i).GetDuration(); // Store the duration to preserve it.
                Chord transformedChord = this.TransformChord(base.get(i + 1)); // This rule only modifies the first chord of the two it uses, leaving the rest of the chords intact.
                transformedChord.SetDuration(duration);
                base.set(i, transformedChord);
            }
            sumTimes += base.get(i).GetDuration();
        }
        return base;
    }

    private boolean VerifyRoot(int tempo, int grade) {
        int positionInRootStructure = tempo / ConstantsDefinition.getInstance().getTwoBarsFigure(); // Divide by 16 to determine which section of the base chords the chord is in and know what the root should be.
        boolean returnValidated = false;
        switch (positionInRootStructure) {
            case 0:
            case 1:
            case 3:
            case 5:
                returnValidated = 1 == grade;
                break;
            case 2:
                returnValidated = 4 == grade;
                break;
            case 4:
                returnValidated = 5 == grade;
                break;
        }
        return returnValidated;
    }

    private Chord TransformChord(Chord c1) {
        Chord chord1 = new Chord(c1);
        chord1 = ChordOperationUtil.Dominant(chord1);
        chord1.SetSeptima(true);
        chord1.setMinor(true);
        return chord1;
    }
    
    private boolean rule3a2Precondition(Chord c1, Chord c2, int tempo) {
        return c1.GetSeptima() == false && c1.isMinor() == false && VerifyRoot(tempo, c1.GetGrade()) && c2.GetSeptima(); 
    }

    @Override
    protected List<Chord> ApplyThisRule(Chord c) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }
}
