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


public class ChordRule6b extends GenericChordRule {

    @Override
    protected List<Chord> seekChordPlace(List<Chord> base, int tempo) {
        int twelveBarsFigure = ConstantsDefinition.getInstance().getTwelveBars();
        if (tempo >= twelveBarsFigure - 1) // If the tempo exceeds the 12-bar figure, which is equivalent to 12 bars in blues, return the base chords as is.
        {
            return base; // Return the base chords without modifications.
        }
        int sumTimes = 0;
        for (int i = 0; i < base.size() - 2; i++) { // The rule cannot be applied to the last two chords
            if (sumTimes == tempo && rule6bPrecondition(base.get(i), base.get(i + 1), base.get(i + 2))) {
                Chord modified = ChordOperationUtil.sharpDiminishedSeventh(base.get(i));
                modified.setMinor(false);
                base.set(i + 1, modified);
            }
            sumTimes += base.get(i).GetDuration();
        }
        return base;
    }

    private boolean rule6bPrecondition(Chord c1, Chord c2, Chord c3) {
        return !c1.GetSeptima() && !c2.GetSeptima() && !c3.GetSeptima() &&
                c1.isMinor() == c2.isMinor() &&
                c1.GetGrade() == c2.GetGrade() && ChordOperationUtil.isMinorSuperTonicOf(c3, c1);
    }

    @Override
    protected List<Chord> ApplyThisRule(Chord c) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }
}
