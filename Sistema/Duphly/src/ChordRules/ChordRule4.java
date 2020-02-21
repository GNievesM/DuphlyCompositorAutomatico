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
public class ChordRule4 extends GenericChordRule {

    @Override
    protected List<Chord> seekChordPlace(List<Chord> base, int tempo) {
        int twelveBarsFigure = ConstantsDefinition.getInstance().getTwelveBars();
        if (tempo >= twelveBarsFigure - 1) // 32 equivale a 16 negras, por ende 4 compases, multiplicado por 3 son los 12 compases del blues, si excede este numero  es por que excede los 12 compases.
        {
            return base; // devuelvo la base sin modificaciones.
        }
        int sumaTiempos = 0;
        for (int i = 0; i < base.size() - 1; i++) {//No se puede aplicar la regla sobre el ultimo acorde
            if (sumaTiempos == tempo && rule4Precondition(base.get(i),base.get(i+1))){//&& base.get(i).GetSeptima() == false && base.get(i + 1).GetSeptima() == true && VerifyRoot(tempo, base.get(i).GetGrade())) {
                Chord c1 = ChordOperationUtil.flatSupertonic(base.get(i+1));
                c1.SetDuration(base.get(i).GetDuration());
                c1.SetSeptima(true);
                base.set(i, c1);
            }
            sumaTiempos += base.get(i).GetDuration();
        }
        return base;
    }

    
    private boolean rule4Precondition(Chord c1, Chord c2){
        return c1.GetSeptima() && !c1.isMinor() && ChordOperationUtil.isDominantOf(c1, c2);     
    }

    @Override
    protected List<Chord> ApplyThisRule(Chord c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
