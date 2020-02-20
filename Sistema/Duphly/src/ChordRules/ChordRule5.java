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

/**
 *
 * @author gasto_000
 */
public class ChordRule5 extends GenericChordRule {

    @Override
    protected List<Chord> seekChordPlace(List<Chord> base, int tempo) {
        int twelveBarsFigure = ConstantsDefinition.getInstance().getTwelveBars();
        if (tempo >= twelveBarsFigure - 1) // 32 equivale a 16 negras, por ende 4 compases, multiplicado por 3 son los 12 compases del blues, si excede este numero  es por que excede los 12 compases.
        {
            return base; // devuelvo la base sin modificaciones.
        }
        int sumaTiempos = 0;
        for (int i = 0; i < base.size() - 2; i++) {//No se puede aplicar la regla sobre el ultimo acorde ni el penultimo
            if (sumaTiempos == tempo && rule5Precondition(base.get(i),base.get(i+1), base.get(i+2))){//&& base.get(i).GetSeptima() == false && base.get(i + 1).GetSeptima() == true && VerifyRoot(tempo, base.get(i).GetGrade())) {
               base.set(i+1, ChordOperationUtil.minorSupertonic(base.get(i)));
               base.set(i+2, ChordOperationUtil.minorMediant(base.get(i)));         
            }
            sumaTiempos += base.get(i).GetDuration();
        }
        return base;
    }

   
   
    private boolean rule5Precondition(Chord c1, Chord c2,Chord c3){
        return !c1.GetSeptima()&& !c2.GetSeptima()&& !c3.GetSeptima() &&
               !c1.isMinor() && !c2.isMinor()&& !c3.isMinor() &&
                c1.GetGrade()== c2.GetGrade() && c2.GetGrade() == c3.GetGrade();     
    }

    @Override
    protected List<Chord> ApplyThisRule(Chord c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
