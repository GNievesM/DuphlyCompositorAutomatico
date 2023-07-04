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

public class ChordRuleOpA4 extends GenericChordRule {

    @Override
    protected List<Chord> ApplyThisRule(Chord c) {
       
        List<Chord> sol = new ArrayList<Chord>();
        if(rulePrecondition(c)){
            c.setThirtinth(true);
            sol.add(c);
        }
        return sol;        
    }
    
     private boolean rulePrecondition(Chord c1){
        return !c1.GetSeptima()&& !c1.isDiminishedMode() && !c1.isEmptyMode() && !c1.isMSeventh()
                &&!c1.isMinor() && !c1.isNinth() && !c1.isSevenPlusFive() && !c1.isSeventhComa()
                && !c1.isSixth() && !c1.isTenth() && !c1.isThirtinth() && !c1.isbTone();
    }
}
