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

public class EscalasAscendentes extends GenericImprovisationRule {

  @Override
  public Pair<List<Note>, List<Chord>> VerifyAndCorrectImprovisation(
    List<Note> improvisation,
    List<Chord> base
  ) {
    Pair<List<Note>, List<Chord>> m1 =
      this.GenerarMelodia(this.crearReglaCompleta(), improvisation, base);
    return this.ConcatenarMelodias(
        improvisation,
        m1.getKey(),
        base,
        m1.getValue()
      );
  }

  private Pair<List<Note>, List<Chord>> GenerarMelodia(
    CreatedRule cr,
    List<Note> melody,
    List<Chord> base
  ) {
    return new Pair<>(cr.createMelody(base, melody), base);
  }

  private Pair<List<Note>, List<Chord>> ConcatenarMelodias(
    List<Note> m1,
    List<Note> m2,
    List<Chord> base1,
    List<Chord> base2
  ) {
    return ConcatRules.concatMelody(m1, base1, m2, base2);
  }

  private CreatedRule crearReglaCompleta() {
    CreatedRule cr = new CreatedRule(this.style);
    cr.addNewFilter(this.crearContenedorDeFiltrosDeCompases());
    return cr;
  }

  private BlockFilter crearContenedorDeFiltrosDeCompases() {
    BlockFilter bf = new BlockFilter();
    bf.addExcerptFilter(this.crearContenedorDeFiltrosDePasajesAAplicarse());
    bf.addDependentFilter(this.crearReglaCompletaDeFiltroDeCompases());
    return bf;
  }

  private CompleteBlockFilterRule crearReglaCompletaDeFiltroDeCompases() {
    CompleteBlockFilterRule cbfr;
    cbfr =
      new CompleteBlockFilterRule(
        2,
        this.crearCondicionDeFiltroDeCompases(),
        null,
        50.0,
        null,
        null,
        false
      );
    return cbfr;
  }

  private BlockConditionSelector crearCondicionDeFiltroDeCompases() {
    return new NotesTotal(HelperCustomRule.ComparadorMayor(), 4);
  }

  private ExcerptFilter crearContenedorDeFiltrosDePasajesAAplicarse() {
    ExcerptFilter ef = new ExcerptFilter();
    ef.addModification(this.crearContenedorDeModificacionesAAplicarse());
    return ef;
  }

  private CompleteExcerptFilterRule crearReglaDeFiltroDePasajesCompleta() {
    CompleteExcerptFilterRule cefr;
    cefr =
      new CompleteExcerptFilterRule(
        null,
        50.0,
        true,
        10,
        null,
        null,
        2,
        3,
        this.crearCondicionDePasajes(),
        0,
        false
      );
    return cefr;
  }

  private ExcerptConditionSelector crearCondicionDePasajes() {
    return new BiggerThan(5, 4);
  }

  private ModifyExcerpt crearContenedorDeModificacionesAAplicarse() {
    ModifyExcerpt me = new ModifyExcerpt();
    me.addModification(this.crearReglaCompletaDeModificacion());
    return me;
  }

  private CompleteModificationRule crearReglaCompletaDeModificacion() {
    CompleteModificationRule cr;
    cr =
      new CompleteModificationRule(
        null,
        null,
        this.crearCambioParaModificar(),
        null,
        50
      );
    return cr;
  }

  private GuaranteedCondition crearGarantiaModifiacion() {
    return new GuaranteeOctave(HelperCustomRule.ComparadorMayor(), 4);
  }

  private ModificationCondition crearCondicionModificacion() {
    return new CheckOctave(HelperCustomRule.ComparadorMayor(), 4);
  }

  private SpecificModification crearCambioParaModificar() {
    return new SustituteValidNote(HelperCustomRule.ComparadorMayor(), -1, true);
  }

  @Override
  public boolean VerifyImprovisationAppliesRule(
    List<Note> improvisation,
    List<Chord> base
  ) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public List<Note> ApplyRuleInAMoment(
    List<Note> improvisation,
    List<Chord> base,
    int momentToApply
  ) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public List<Note> GenerateImprovisation(List<Chord> base) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
