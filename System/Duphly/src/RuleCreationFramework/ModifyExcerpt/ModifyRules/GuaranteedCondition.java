/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules;

import DataDefinition.Chord;
import DataDefinition.Note;

import EvolutionaryAlgorithm.IFitnessEvaluable;
import EvolutionaryAlgorithm.IMutationAllowed;
import java.util.List;

/**
 *
 * @author Gaston
 */
public abstract class GuaranteedCondition implements IFitnessEvaluable {
    public abstract boolean checkCondition(List<Chord> base, List<Note> melody, Double start, Double end);
 //   public abstract int calculateChunkFitness(List<Chord> base, List<Note> melody, Double start, Double end);


}
