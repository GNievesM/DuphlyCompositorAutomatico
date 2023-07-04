/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChordRules;

import ChordManagement.GenericChordRule;
import DataDefinition.Chord;
import java.util.ArrayList;
import java.util.List;

public class ChordRule1 extends GenericChordRule {

    @Override
    protected List<Chord> ApplyThisRule(Chord c) {
        List<Chord> ChordList = new ArrayList<Chord>();
        Chord returnChord = new Chord(false,c.isMinor(),c.GetGrade(),c.GetDuration()/2);
        Chord returnChord2 = new Chord(returnChord);
        returnChord2.SetSeptima(c.GetSeptima());
        returnChord.setFlat(c.isFlat());
        returnChord.setSharp(c.isSharp());
        returnChord2.setFlat(c.isFlat());
        returnChord2.setSharp(c.isSharp());
        ChordList.add(returnChord);
        ChordList.add(returnChord2);
   

        return ChordList;
    }

}
