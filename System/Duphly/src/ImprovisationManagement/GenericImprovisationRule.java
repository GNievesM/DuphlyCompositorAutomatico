/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImprovisationManagement;

import DataDefinition.Chord;
import DataDefinition.Note;
import Style.AbstractStyle;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author gasto_000
 */
public abstract class GenericImprovisationRule {
    
    public AbstractStyle style;
    public abstract List<Note> GenerateImprovisation(List<Chord> base);
    public abstract Pair<List<Note>,List<Chord>> VerifyAndCorrectImprovisation(List<Note> improvisation, List<Chord> base);
    public abstract boolean VerifyImprovisationAppliesRule(List<Note> improvisation, List<Chord> base);
    public abstract List<Note> ApplyRuleInAMoment(List<Note> improvisation, List<Chord> base, int momentToApply);
}
