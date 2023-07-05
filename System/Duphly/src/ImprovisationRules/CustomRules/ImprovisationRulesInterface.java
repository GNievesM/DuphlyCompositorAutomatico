package ImprovisationRules.CustomRules;

import DataDefinition.TupleNameTypeImpRule;
import ImprovisationRules.AgregarEscalasCromaticas;
import ImprovisationRules.BluesScaleOnEveryChordNormalOctave;
import ImprovisationRules.DashedDirectionContinuity;
import ImprovisationRules.DirectionContinuity;
import ImprovisationRules.DirectionContinuityAllowingEquals;
import ImprovisationRules.DirectionContinuityImplementation2;
import ImprovisationRules.FrameworkTesting;
import ImprovisationRules.ImprovisationPurelyRandom;
import ImprovisationRules.ImprovisationPurelyRandomNotAbusingBluesNote;
import ImprovisationRules.LimitJumpsPerBar;
import ImprovisationRules.LimitJumpsPerBarNotAbusingBluesNote;
import ImprovisationRules.NotAbusingBluesNoteEqualModifier;
import ImprovisationRules.NotAbusingBluesNoteRandomModifier;
import ImprovisationRules.RepeatingNotes;
import java.util.ArrayList;
import java.util.List;
import ImprovisationRules.*;

public class ImprovisationRulesInterface {
    List<TupleNameTypeImpRule> existingRules;
    private static ImprovisationRulesInterface instance=null;
    
    public static ImprovisationRulesInterface getInstance(){
        if(instance == null)
            instance = new ImprovisationRulesInterface();
        return instance;
    }
    
    public List<TupleNameTypeImpRule> getRuleList(){
        return existingRules;
    }
    
    protected ImprovisationRulesInterface(){
        existingRules = new ArrayList<TupleNameTypeImpRule>();
        existingRules.add(new TupleNameTypeImpRule("Random Creation", "Generate", true, new ImprovisationPurelyRandom()));
        existingRules.add(new TupleNameTypeImpRule("Limit Jumps Per Bar", "Generate", true, new LimitJumpsPerBar()));
        existingRules.add(new TupleNameTypeImpRule("Random Creation Without Abusing Blues Note", "Generate", true, new ImprovisationPurelyRandomNotAbusingBluesNote()));
        existingRules.add(new TupleNameTypeImpRule("Limit Jumps Per Bar Without Abusing Blues Note", "Generate", true, new LimitJumpsPerBarNotAbusingBluesNote()));
        existingRules.add(new TupleNameTypeImpRule("Direction Continuity", "Verify and Correct", false, new DirectionContinuity()));
        existingRules.add(new TupleNameTypeImpRule("Blues Scale on Every Chord", "Generate", true, new BluesScaleOnEveryChordNormalOctave()));
        existingRules.add(new TupleNameTypeImpRule("Dashed Direction Continuity", "Verify and Correct", false, new DashedDirectionContinuity()));
        existingRules.add(new TupleNameTypeImpRule("Direction Continuity Allowing Equals", "Verify and Correct", false, new DirectionContinuityAllowingEquals()));
        existingRules.add(new TupleNameTypeImpRule("Decrease Blues Note Usage Randomly", "Verify and Correct", false, new NotAbusingBluesNoteRandomModifier()));
        existingRules.add(new TupleNameTypeImpRule("Decrease Blues Note Usage by Replacing with Previous Note", "Verify and Correct", false, new NotAbusingBluesNoteEqualModifier()));
        existingRules.add(new TupleNameTypeImpRule("Direction Continuity Variation", "Verify and Correct", false, new DirectionContinuityImplementation2()));
        existingRules.add(new TupleNameTypeImpRule("Increase Note Repetition", "Verify and Correct", false, new RepeatingNotes()));
        existingRules.add(new TupleNameTypeImpRule("Framework Testing", "Verify and Correct", false, new FrameworkTesting()));
        existingRules.add(new TupleNameTypeImpRule("Custom Rule Template", "Verify and Correct", false,
