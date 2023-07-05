/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImprovisationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationManagement.GenericImprovisationRule;
import RuleCreationFramework.*;
import RuleCreationFramework.BlockFilter.BlockConditionSelector;
import RuleCreationFramework.BlockFilter.BlockFilter;
import RuleCreationFramework.BlockFilter.BlockSelectors.NoteQuantity;
import RuleCreationFramework.BlockFilter.CompleteBlockFilterRule;
import RuleCreationFramework.BlockFilter.DependentBlockFilterElement;
import RuleCreationFramework.ExcerptFilter.CompleteExcerptFilterRule;
import RuleCreationFramework.ExcerptFilter.DependentExcerptFilterElement;
import RuleCreationFramework.ExcerptFilter.ExcerptConditionSelector;
import RuleCreationFramework.ExcerptFilter.ExcerptFilter;
import RuleCreationFramework.ExcerptFilter.ExcerptSelectors.BiggerThan;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.ModifyExcerpt.Modification;
import RuleCreationFramework.ModifyExcerpt.ModifyExcerpt;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.CompleteModificationRule;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedCondition;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedConditionChecker.GuaranteeDifferenceBetweenNotes;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedConditionChecker.GuaranteeOctave;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationCondition;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationConditionChecker.CheckOctave;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationConditionChecker.DifferenceBetweenNotes;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationConditionChecker.IntervalBetweenNotes;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationPlaceSelector;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules.ChangeMetric;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules.MoveDemiTones;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules.SustituteNote;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules.SustituteValidNote;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class FrameworkTesting extends GenericImprovisationRule {

  @Override
  public List<Note> GenerateImprovisation(List<Chord> base) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Pair<List<Note>, List<Chord>> VerifyAndCorrectImprovisation(
    List<Note> improvisation,
    List<Chord> base
  ) {
    ModificationCondition mc = new CheckOctave(new Comparador(1), 1);
    SpecificModification sm4 = new SustituteNote(true);
    Modification a3 = new CompleteModificationRule(mc, null, sm4, null, 40);
    ModifyExcerpt me = new ModifyExcerpt();
    me.addModification(a3);
    ExcerptFilter e = new ExcerptFilter();
    e.addModification(me);
    BlockFilter bf = new BlockFilter();
    bf.addExcerptFilter(e);
    CreatedRule cr = new CreatedRule(this.style);
    cr.addNewFilter(bf);
    return new Pair<List<Note>, List<Chord>>(
      cr.createMelody(base, improvisation),
      base
    );
  }

  @Override
  public boolean VerifyImprovisationAppliesRule(
    List<Note> improvisation,
    List<Chord> base
  ) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<Note> ApplyRuleInAMoment(
    List<Note> improvisation,
    List<Chord> base,
    int momentToApply
  ) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  private CreatedRule createEmptyFilterRule(ModifyExcerpt m) {
    ExcerptFilter e2 = new ExcerptFilter();
    e2.addModification(m);
    BlockFilter bf2 = new BlockFilter();
    bf2.addExcerptFilter(e2);
    CreatedRule cr2 = new CreatedRule(this.style);
    cr2.addNewFilter(bf2);
    return cr2;
  }

  private ModifyExcerpt eraseRepeats() {
    Comparador comp = new Comparador(0);
    ModificationCondition mc = new IntervalBetweenNotes(comp, 0);
    SpecificModification sm = new MoveDemiTones(true, 1);
    Modification m = new CompleteModificationRule(mc, null, sm, null, 70);
    ModifyExcerpt me = new ModifyExcerpt();
    me.addModification(m);
    return me;
  }

  private ModifyExcerpt durationModification() {
    ModificationCondition mc = new CheckOctave(new Comparador(1), 3);
    SpecificModification sm = new ChangeMetric(2);
    Modification m = new CompleteModificationRule(mc, null, sm, null, 100);
    ModifyExcerpt me = new ModifyExcerpt();
    me.addModification(m);
    return me;
  }

  private ModifyExcerpt createModification() {
    ModificationCondition mc = new CheckOctave(new Comparador(0), 5);
    SpecificModification sm4 = new SustituteNote(true);
    SpecificModification sm5 = new SustituteNote(true);
    GuaranteedCondition gc3 = new GuaranteeOctave(new Comparador(0), 6);
    ModificationPlaceSelector mps = new ModificationPlaceSelector(
      true,
      false,
      false,
      0
    );
    Modification a3 = new CompleteModificationRule(mc, null, sm4, mps, 90);
    Modification a5 = new CompleteModificationRule(mc, gc3, sm5, mps, 90);
    ModifyExcerpt me = new ModifyExcerpt();
    me.addModification(a3);
    me.addModification(a5);
    return me;
  }

  private DependentExcerptFilterElement createExcerptFilter() {
    ExcerptConditionSelector ecs = new BiggerThan(1, null, 15);
    DependentExcerptFilterElement ef = new CompleteExcerptFilterRule(
      null,
      10.0,
      false,
      2,
      null,
      null,
      3,
      3,
      ecs,
      15,
      false
    );

    return ef;
  }

  private DependentBlockFilterElement createBlockFilter() {
    Comparador comp = new Comparador(1);
    BlockConditionSelector bcs = new NoteQuantity(comp, 1, null, 0);
    DependentBlockFilterElement bf = new CompleteBlockFilterRule(
      3,
      bcs,
      null,
      null,
      4,
      null,
      false
    );
    return bf;
  }
}
