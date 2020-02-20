/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImprovisationRules;

import ConstantDefinition.ConstantsDefinition;
import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationManagement.GenericImprovisationRule;
import ImprovisationRules.Util;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author gasto_000
 */
public class BluesScaleOnEveryChordNormalOctave extends GenericImprovisationRule {

    @Override
    public List<Note> GenerateImprovisation(List<Chord> base) {
        List<Note> result = new ArrayList<Note>();
        for (int i = 0; i < base.size(); i++) {
            if(base.get(i).GetDuration()== ConstantsDefinition.getInstance().GetBlackFigure()*8){
                 int j = base.get(i).GetDuration();
                 double ih = ((double) base.get(i).GetDuration())/16;
                result.add(Util.CalculateNoteForChord(base.get(i), 0,((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 1,((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 2,((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 3, ((double) base.get(i).GetDuration())/16,6));
                result.add(Util.CalculateNoteForChord(base.get(i), 4, ((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 3, ((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 2, ((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 1, ((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 0,((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 1,((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 2,((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 3, ((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 4, ((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 3, ((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 2, ((double) base.get(i).GetDuration())/16, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 1, ((double) base.get(i).GetDuration())/16, 6));
        }
            if(base.get(i).GetDuration()== ConstantsDefinition.getInstance().GetBlackFigure()*4){
                result.add(Util.CalculateNoteForChord(base.get(i), 0,((double) base.get(i).GetDuration())/8, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 1,((double) base.get(i).GetDuration())/8, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 2,((double) base.get(i).GetDuration())/8, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 3, ((double) base.get(i).GetDuration())/8, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 4, ((double) base.get(i).GetDuration())/8, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 3, ((double) base.get(i).GetDuration())/8, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 2, ((double) base.get(i).GetDuration())/8, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 1, ((double) base.get(i).GetDuration())/8, 6));
            }
            if(base.get(i).GetDuration()== ConstantsDefinition.getInstance().GetBlackFigure()*2){
                result.add(Util.CalculateNoteForChord(base.get(i), 0,((double) base.get(i).GetDuration())/4, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 1,((double) base.get(i).GetDuration())/4, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 2,((double) base.get(i).GetDuration())/4, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 3, ((double) base.get(i).GetDuration())/4,6));
            }
            if(base.get(i).GetDuration()== ConstantsDefinition.getInstance().GetBlackFigure()){
                result.add(Util.CalculateNoteForChord(base.get(i), 0,((double) base.get(i).GetDuration())/2, 6));
                result.add(Util.CalculateNoteForChord(base.get(i), 1,((double) base.get(i).GetDuration())/2, 6));
            }
            if(base.get(i).GetDuration()== ConstantsDefinition.getInstance().GetBlackFigure()/2){
                result.add(Util.CalculateNoteForChord(base.get(i), 0,((double) base.get(i).GetDuration()), 6));
            }
         //   result.add(Util.CalculateNoteForChord(base.get(i), 3, ((double) base.get(i).GetDuration())/8, 4));
           // result.add(Util.CalculateNoteForChord(base.get(i), 2, ((double) base.get(i).GetDuration())/8, 4));
           // result.add(Util.CalculateNoteForChord(base.get(i), 1, ((double) base.get(i).GetDuration())/8, 4));
            
            
            }
            
        
        return result;
    }

    @Override
    public Pair<List<Note>,List<Chord>> VerifyAndCorrectImprovisation(List<Note> improvisation, List<Chord> base) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
