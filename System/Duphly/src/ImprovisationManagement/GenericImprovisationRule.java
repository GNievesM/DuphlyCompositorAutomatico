package ImprovisationManagement;

import DataDefinition.Chord;
import DataDefinition.Note;
import Style.AbstractStyle;
import java.util.List;
import javafx.util.Pair;

public abstract class GenericImprovisationRule {
    
    public AbstractStyle style;
    public abstract List<Note> GenerateImprovisation(List<Chord> base);
    public abstract Pair<List<Note>,List<Chord>> VerifyAndCorrectImprovisation(List<Note> improvisation, List<Chord> base);
    public abstract boolean VerifyImprovisationAppliesRule(List<Note> improvisation, List<Chord> base);
    public abstract List<Note> ApplyRuleInAMoment(List<Note> improvisation, List<Chord> base, int momentToApply);
}
