/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteeSolver;

import DataDefinition.Chord;
import DataDefinition.Note;
import EvolutionaryAlgorithm.ConditionEvolvableClass;
import EvolutionaryAlgorithm.GenericEvolutionaryAlgorithm;
import EvolutionaryAlgorithm.IEvolvableClass;
import ImprovisationRules.Util;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedCondition;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import Style.AbstractStyle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gaston
 */
public class EvolutionarySolve extends GuaranteeConditionSolver {

  int tresholdPercenage;
  int poblationQuantity;
  int crossOverPercentage;
  int mutationPercentage;
  int mutationLimitPerSubject;
  int iterationLimit;

  public EvolutionarySolve(
    int tresholdPercenage,
    int poblationQuantity,
    int crossOverPercentage,
    int mutationPercentage,
    int mutationLimitPerSubject,
    int iterationLimit
  ) {
    this.tresholdPercenage = tresholdPercenage;
    this.poblationQuantity = poblationQuantity;
    this.crossOverPercentage = crossOverPercentage;
    this.mutationPercentage = mutationPercentage;
    this.mutationLimitPerSubject = mutationLimitPerSubject;
    this.iterationLimit = iterationLimit;
  }

  @Override
  public List<Note> Solve(
    List<Chord> base,
    List<Note> melody,
    Double start,
    Double end,
    AbstractStyle style,
    GuaranteedCondition gc,
    SpecificModification sm
  ) {
    List<IEvolvableClass> init = new ArrayList<>();

    for (int i = 0; i < this.poblationQuantity; i++) {
      ConditionEvolvableClass cec = new ConditionEvolvableClass(
        "" + i,
        base,
        FrameWorkUtil.copyMelody(melody),
        start,
        end,
        gc,
        sm,
        this.mutationLimitPerSubject,
        style
      );
      cec.mutate(cec.generateFactorCrossOverList());
      init.add(cec);
    }

    GenericEvolutionaryAlgorithm ea = new GenericEvolutionaryAlgorithm(
      this.tresholdPercenage,
      poblationQuantity,
      (this.poblationQuantity * mutationPercentage) / 100,
      (this.poblationQuantity * this.mutationPercentage / 100),
      init,
      this.iterationLimit
    );
    return ((ConditionEvolvableClass) ea.findBestSolution()).getMelody();
  }
}
