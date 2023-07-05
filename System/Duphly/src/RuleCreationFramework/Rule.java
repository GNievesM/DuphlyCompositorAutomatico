package RuleCreationFramework;
import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationManagement.GenericImprovisationRule;
import java.util.List;
import javafx.util.Pair;

public abstract class Rule extends GenericImprovisationRule {

    CreatedRule cr;

    public Rule(CreatedRule cr) {
        this.cr = cr;
    }    
    
    @Override
    public List<Note> GenerateImprovisation(List<Chord> base) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
