package ImprovisationManagement;

import DataDefinition.Chord;
import DataDefinition.Note;
import DataDefinition.TupleNameTypeImpRule;
import ImprovisationRules.ImprovisationPurelyRandom;
import ImprovisationRules.Util;
import Style.AbstractStyle;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class ImprovisationCreator {
    private List<Note> improvisation;
    private List<Chord> base;
    private AbstractStyle style;
    
    public ImprovisationCreator(List<Chord> base, AbstractStyle style) {
        this.base = base;
        improvisation = new ArrayList<Note>();
        this.style = style;
    }

    public void applyImprovisationRuleGenerate(GenericImprovisationRule improvisationRule) {
        improvisationRule.style = this.style;
        this.improvisation = improvisationRule.generateImprovisation(this.base);
    }
    
    public void setImprovisation(List<Note> improvisation) { 
        this.improvisation = improvisation;
    }
    
    public void applyImprovisationRuleInAMoment(GenericImprovisationRule improvisationRule, int momentToApply) {
        improvisationRule.style = this.style;
        this.improvisation = improvisationRule.applyRuleInAMoment(this.improvisation, this.base, momentToApply);
    }
     
    public void applyImprovisationRuleVerifyAndCorrect(GenericImprovisationRule improvisationRule) {
        improvisationRule.style = this.style;
        Pair<List<Note>, List<Chord>> sol = improvisationRule.verifyAndCorrectImprovisation(this.improvisation, this.base);
        this.improvisation = sol.getKey();
        this.base = sol.getValue();
    }

    public List<Note> getImprovisation() {
        return improvisation;
    }

    public List<Chord> getBase() {
        return base;
    }

    public void setBase(List<Chord> base) {
        this.base = base;
    }
}
