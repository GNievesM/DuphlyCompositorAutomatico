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

public class ChordRule4 extends GenericChordRule {

    @Override
    protected List<Chord> seekChordPlace(List<Chord> base, int tempo) {
        int twelveBarsFigure = ConstantsDefinition.getInstance().getTwelveBars();
        if (tempo >= twelveBarsFigure - 1) // If the tempo exceeds the 12-bar figure, which is equivalent to 12 bars in blues, return the base chords as is.
        {
            return base; // Return the base chords without modifications.
        }
        int sumTimes = 0;
        for (int i = 0; i < base.size() - 1; i++) { // The rule cannot be applied to the last chord
            if (sumTimes == tempo && rule4Precondition(base.get(i), base.get(i + 1))) {
                Chord c1 = ChordOperationUtil.flatSupertonic(base.get(i + 1));
                c1.SetDuration(base.get(i).GetDuration());
                c1.SetSeptima(true);
                base.set(i, c1);
            }
            sumTimes += base.get(i).GetDuration();
        }
        return base;
    }

    private boolean rule4Precondition(Chord c1, Chord c2) {
        return c1.GetSeptima() && !c1.isMinor() && ChordOperationUtil.isDominantOf(c1, c2);
    }

    @Override
    protected List<Chord> ApplyThisRule(Chord c) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }
}
