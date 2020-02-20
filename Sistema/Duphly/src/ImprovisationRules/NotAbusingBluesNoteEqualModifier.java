/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImprovisationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationManagement.GenericImprovisationRule;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author gasto_000
 */
public class NotAbusingBluesNoteEqualModifier extends GenericImprovisationRule {

    @Override
    public List<Note> GenerateImprovisation(List<Chord> base) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pair<List<Note>,List<Chord>> VerifyAndCorrectImprovisation(List<Note> improvisation, List<Chord> base) {
           //La escala de blues admite 6 notas, por lo que una distribucion normal de las notas implicaria que cada nota aparezca un 16,6 %, por lo que, evitar el abuso 
        // en este caso se considerara cambiar la distribucion permitira unicamente un 9 porciento de apariciones de la nota de blues
        int bluesNoteQuantity =(int)Math.round( (double)((double)(improvisation.size()) * 0.09));
        for (int i = 0; i < improvisation.size(); i++) {
            if(Util.IdentifyBluesScaleNoteHeightInChord(Util.LookForBaseChord(base, improvisation, i), improvisation.get(i)) ==3){
                bluesNoteQuantity --;
                if(bluesNoteQuantity < 0){
                    if(Util.IdentifyBluesScaleNoteHeightInChord(Util.LookForBaseChord(base, improvisation, i-1), improvisation.get(i-1)) !=3)
                    improvisation.get(i).setNote(improvisation.get(i-1).getNote());
                    else if(Util.IdentifyBluesScaleNoteHeightInChord(Util.LookForBaseChord(base, improvisation, i+1), improvisation.get(i+1)) !=3)
                           improvisation.get(i).setNote(improvisation.get(i+1).getNote());
                    else {
                        int height = 3;
                        while(height == 3 )
                          height=(int)  Math.rint(Math.random()*5);
                        improvisation.get(i).setNote(Util.CalculateNoteHeightForChord(Util.LookForBaseChord(base, improvisation, i),height ));
                    }
                }
            }
        }
        
        return new Pair<List<Note>,List<Chord>>(improvisation,base);
        
    }

    @Override
    public boolean VerifyImprovisationAppliesRule(List<Note> improvisation, List<Chord> base) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Note> ApplyRuleInAMoment(List<Note> improvisation, List<Chord> base, int momentToApply) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
