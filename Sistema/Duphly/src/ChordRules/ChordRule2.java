/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChordRules;

import ChordManagement.ChordOperationUtil;
import DataDefinition.Chord;
import ChordManagement.GenericChordRule;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gasto_000
 */
public class ChordRule2 extends GenericChordRule {

    @Override
    protected List<Chord> ApplyThisRule(Chord c) {
        List<Chord> returnChordList = new ArrayList<Chord>();
            Chord returnChord1 = c.createCopyWithHalfDuration();
        Chord returnChord2 = new Chord(returnChord1);
        returnChord2.SetSeptima(false);
        returnChord2.setMinor(false);
        returnChord1.setFlat(c.isFlat());
        returnChord1.setSharp(c.isSharp());
        returnChord2.setFlat(c.isFlat());
        returnChord2.setSharp(c.isSharp());
        returnChordList.add(returnChord1);
        returnChordList.add(ChordOperationUtil.SubDominant(returnChord2));
        
        return returnChordList;
    }

}
