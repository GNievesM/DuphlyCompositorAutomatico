/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChordManagement;

import ConstantDefinition.ConstantsDefinition;
import DataDefinition.Chord;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericChordRule {

    /**
     * Applies a rule and returns the new list of chords. The entire chord list is requested because the rule may have conditions on continuity.
     * @param base List of chords that contains the base on which the rule is applied.
     * @param tempo
     * @return New list of chords after applying the rule.
     */
    public List<Chord> ApplyRule(List<Chord> base, int tempo) {
        return this.seekChordPlace(base, tempo);
    }
    
    // Applies a rule and returns the new list of chords. The entire chord list is requested because the rule may have conditions on continuity.
    protected List<Chord> seekChordPlace(List<Chord> base, int tempo) {
        int twelveBarsFigure = ConstantsDefinition.getInstance().GetBlackFigure() * 16 * 3;
        
        if (tempo >= twelveBarsFigure - 1) {
            // If the tempo exceeds the number of beats in 12 bars, return the base without modifications.
            return base;
        }
        
        List<Chord> modifiedList = new ArrayList<>();
        int tempoSum = 0;
        
        for (int i = 0; i < base.size(); i++) {
            if (tempoSum == tempo && base.get(i).GetDuration() > 1) {
                List<Chord> chordRule = this.ApplyThisRule(base.get(i));
                
                if (chordRule.size() > 0) {
                    for (int l = 0; l < chordRule.size(); l++) {
                        modifiedList.add(chordRule.get(l));
                    }
                } else {
                    modifiedList.add(base.get(i));
                }
            } else {
                modifiedList.add(base.get(i));
            }
            
            tempoSum += base.get(i).GetDuration();
        }
        
        return modifiedList;
    }
  
    protected abstract List<Chord> ApplyThisRule(Chord c);
}
