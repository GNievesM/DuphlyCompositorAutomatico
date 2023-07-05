/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImprovisationRules;

import ConstantDefinition.ConstantsDefinition;
import DataDefinition.Chord;
import DataDefinition.Note;
import DataDefinition.Rhythm;
import ImprovisationManagement.GenericImprovisationRule;
import RhythmCreator.RhythmFactory;
import RhythmRules.SimpleRhythmCreator;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class ImprovisationPurelyRandom extends GenericImprovisationRule {

    @Override
    public List<Note> GenerateImprovisation(List<Chord> base) {
        List<Note> improvisation = new ArrayList<Note>();
           RhythmFactory rf = new RhythmFactory(base);
           Rhythm r = rf.CreateRhythmSequence(new SimpleRhythmCreator());
        for (int i=0 ; i < r.getRhythmSequence().size();i++) {
            
            improvisation.addAll(this.NoteListFormationForChord(r.getRhythmSequence().get(i).getDuration(), r.getRhythmSequence().get(i).getChord()));
            
        }
        return improvisation;
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
    
    
     private List<Note> NoteListFormationForChord(double duration, Chord acord){
        List<Note> retorno = new ArrayList<Note>();
        List<Double> probability = new ArrayList<>();
           for (int i = 1; i < 13; i++) {
            Double uno = new Double(1);
            Double doce = new Double(12);
            probability.add(new Double(uno/doce));
        }
            retorno.add(this.style.validRandomNote(acord, duration, probability));
        
        return retorno;
        
        }
}
