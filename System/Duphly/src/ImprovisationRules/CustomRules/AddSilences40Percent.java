package ImprovisationRules.CustomRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationManagement.GenericImprovisationRule;
import RuleCreationFramework.*;
import RuleCreationFramework.BlockFilter.*;
import RuleCreationFramework.BlockFilter.BlockSelectors.*;
import RuleCreationFramework.ExcerptFilter.*;
import RuleCreationFramework.ExcerptFilter.ExcerptSelectors.*;
import RuleCreationFramework.FrameworkUtil.*;
import RuleCreationFramework.ModifyExcerpt.*;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.*;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedConditionChecker.*;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationConditionChecker.*;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class AddSilences40Percent extends GenericImprovisationRule {

    @Override
    public Pair<List<Note>, List<Chord>> VerifyAndCorrectImprovisation(List<Note> improvisation, List<Chord> base) {
        Pair<List<Note>, List<Chord>> m1 = this.GenerateMelody(this.createCompleteRule(), improvisation, base);
        return m1;
    }

    private Pair<List<Note>, List<Chord>> GenerateMelody(CreatedRule cr, List<Note> melody, List<Chord> base) {
        return new Pair<>(cr.createMelody(base, melody), base);
    }

    private Pair<List<Note>, List<Chord>> ConcatenateMelodies(List<Note> m1, List<Note> m2, List<Chord> base1, List<Chord> base2) {
        return ConcatRules.concatMelody(m1, base1, m2, base2);
    }

    private CreatedRule createCompleteRule() {
        CreatedRule cr = new CreatedRule(this.style);
        cr.addNewFilter(this.createMeasureFilterContainer());
        return cr;
    }

    private BlockFilter createMeasureFilterContainer() {
        BlockFilter bf = new BlockFilter();
        bf.addExcerptFilter(this.createPassageFilterContainer());
        return bf;
    }

    private CompleteBlockFilterRule createCompleteMeasureFilterRule() {
        CompleteBlockFilterRule cbfr = new CompleteBlockFilterRule(2, this.createMeasureFilterCondition(), null, 100.0, null, null, false);
        return cbfr;
    }

    private BlockConditionSelector createMeasureFilterCondition() {
        return new NotesTotal(HelperCustomRule.LessThanComparator(), 10);
    }

    private ExcerptFilter createPassageFilterContainer() {
        ExcerptFilter ef = new ExcerptFilter();
        ef.addModification(this.createModificationContainer());
        return ef;
    }

    private CompleteExcerptFilterRule createCompletePassageFilterRule() {
        CompleteExcerptFilterRule cefr = new CompleteExcerptFilterRule(null, 50.0, true, 10, null, null, 2, 3, this.createPassageCondition(), 0, false);
        return cefr;
    }

    private ExcerptConditionSelector createPassageCondition() {
        return new BiggerThan(5, 4);
    }

    private ModifyExcerpt createModificationContainer() {
        ModifyExcerpt me = new ModifyExcerpt();
        me.addModification(this.createCompleteModificationRule());
        return me;
    }

    private CompleteModificationRule createCompleteModificationRule() {
        CompleteModificationRule cr = new CompleteModificationRule(null, null, this.createModificationChange(), null, 40);
        return cr;
    }

    private GuaranteedCondition createModificationGuarantee() {
        return new GuaranteeOctave(HelperCustomRule.GreaterThanComparator(), 4);
    }

    private ModificationCondition createModificationCondition() {
        return new CheckOctave(HelperCustomRule.LessThanComparator(), 5);
    }

    private SpecificModification createModificationChange() {
        return new SustituteNote(true);
    }

    @Override
    public boolean VerifyImprovisationAppliesRule(List<Note> improvisation, List<Chord> base) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Note> ApplyRuleInAMoment(List<Note> improvisation, List<Chord> base, int momentToApply) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Note> GenerateImprovisation(List<Chord> base) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
