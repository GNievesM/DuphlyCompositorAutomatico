/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChordRules;

import ChordManagement.ChordOperationUtil;
import ChordManagement.GenericChordRule;
import DataDefinition.Chord;
import java.util.List;

/**
 *
 * @author Gaston
 */
public class ChordRule0 extends GenericChordRule{

    @Override
    protected List<Chord> seekChordPlace(List<Chord> base, int tempo){
     if(ChordOperationUtil.baseNotModifiedYet(base)){
       boolean setTo = true;
         if(base.get(0).isMinor())
           setTo= false;
         
       base.get(0).setMinor(setTo);
       base.get(2).setMinor(setTo);
       base.get(3).setMinor(setTo);
       base.get(5).setMinor(setTo);
     }
        
       return base;
    }
    
    @Override
    protected List<Chord> ApplyThisRule(Chord c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
