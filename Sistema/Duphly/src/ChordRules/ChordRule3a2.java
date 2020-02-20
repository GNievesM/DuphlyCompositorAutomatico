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
public class ChordRule3a2 extends GenericChordRule {

    @Override
    protected List<Chord> seekChordPlace(List<Chord> base, int tempo) {
        int twelveBarsFigure = ConstantsDefinition.getInstance().getTwelveBars();
        if (tempo >= twelveBarsFigure - 1) // 32 equivale a 16 negras, por ende 4 compases, multiplicado por 3 son los 12 compases del blues, si excede este numero  es por que excede los 12 compases.
        {
            return base; // devuelvo la base sin modificaciones.
        }
        int sumaTiempos = 0;
        for (int i = 0; i < base.size() - 1; i++) {//No se puede aplicar la regla sobre el ultimo acorde
            if (sumaTiempos == tempo && rule3a2Precondition(base.get(i),base.get(i+1),tempo)){//&& base.get(i).GetSeptima() == false && base.get(i + 1).GetSeptima() == true && VerifyRoot(tempo, base.get(i).GetGrade())) {
                int duration = base.get(i).GetDuration(); // debo guardar la duracion para no cambiarla.
                Chord transformedChord = this.TransformChord(base.get(i + 1)); //esta regla solo modifica el primer acorde de los dos que utiliza, por lo que el resto de los acordes queda intacto.
                transformedChord.SetDuration(duration);
                base.set(i, transformedChord);
            }
            sumaTiempos += base.get(i).GetDuration();
        }
        return base;
    }

    private boolean VerifyRoot(int tempo, int grade) {
        int positionInRootStructure = tempo / ConstantsDefinition.getInstance().getTwoBarsFigure(); //divido entre 16 para descubrir en que seccion de los acordes base se encuentra el acorde y saber cual debia ser la raiz.
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
   //     chord1.SetGrade(c1.GetGrade());
        chord1 = ChordOperationUtil.Dominant(chord1);
        chord1.SetSeptima(true);
        chord1.setMinor(true);
   //     chord1.SetDuration(c1.GetDuration());
        return chord1;

    }
    
    private boolean rule3a2Precondition(Chord c1, Chord c2,int tempo){
        return c1.GetSeptima()==false && c1.isMinor()==false && VerifyRoot(tempo, c1.GetGrade()) && c2.GetSeptima(); 
    
    }

    @Override
    protected List<Chord> ApplyThisRule(Chord c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
