/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import EvolutionaryAlgorithm.ConditionEvolvableClass;
import EvolutionaryAlgorithm.GenericEvolutionaryAlgorithm;
import EvolutionaryAlgorithm.IEvolvableClass;
import ImprovisationRules.Util;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import RuleCreationFramework.ModifyExcerpt.Modification;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteeSolver.DivideAndConquerEvolutionarySolve;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteeSolver.EvolutionarySolve;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteeSolver.GuaranteeConditionSolver;
import Style.AbstractStyle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Gaston
 */
/**
 * This rule is responsible for identifying whether a condition is met and performing the modification on the melodic line until the desired condition is achieved.
 * Conditionals: If there is no guarantee, the rule is simply applied to all elements of the excerpt.
 * If there is a guarantee, a method is used to solve the guarantee.
 * If the beginning is modified (the object is not null), it is applied to determine the start.
 * The rule is applied if the probability is met. Number between 0-1.
 */
public class CompleteModificationRule extends Modification {
    ModificationCondition initialCondition;
    GuaranteedCondition guaranteedCondition;
    SpecificModification modification;
    ModificationPlaceSelector mps;
    double probability;

    /**
     * Constructor of the class that receives the following parameters:
     * @param initialCondition Condition for a modification to be performed. If the condition is not met, nothing is done. If the rule has no condition, it is used to determine the change points.
     * @param guaranteedCondition Condition to maximize. Although the ideal would be to guarantee it in all cases, it is not always possible since it depends on the specific modification to be made to the melody, as sometimes the solution is impossible.
     * @param modification Specific modification to be used to achieve the condition or in cases determined by the initial condition.
     * @param mps Class responsible for storing and calculating the starting position from which the modification will be made.
     * @param probability Number between 0 and 1. Probability of applying the modification. In case there is a guarantee to fulfill, it is checked once (whether or not to achieve the guarantee with all necessary modifications).
     * If there is no guarantee, before making a modification on each case returned by the modification condition, it is checked that this probability is met in a random roll.
     */
    public CompleteModificationRule(ModificationCondition initialCondition, GuaranteedCondition guaranteedCondition, SpecificModification modification, ModificationPlaceSelector mps, double probability) {
        this.initialCondition = initialCondition;
        this.guaranteedCondition = guaranteedCondition;
        this.modification = modification;
        this.mps = mps;
        this.probability = probability / 100;
    }

    /**
     * Modifies a music excerpt by calculating, according to the ModificationPlaceSelector class, the starting point. If the initialCondition condition is not met,
     * the original melody is returned. If there is no guarantee after executing the procedure, all positions that do not meet the initial condition are calculated,
     * and the modification is applied to those positions, checking the probability before modifying each position. Finally, if there is a guarantee, the probability is calculated
     * to see if an algorithm is actually applied to improve the condition. If so, an evolutionary algorithm is used, in this case a biological algorithm, where the search space
     * and the number of iterations are minimized in order to achieve faster traversal.
     * @param base
     * @param melody
     * @param start
     * @param end
     * @param style
     * @return
     */
    @Override
    public List<Note> Modify(List<Chord> base, List<Note> melody, Double start, Double end, AbstractStyle style) {
        double modifiedStart = mps != null ? mps.startPosition(melody, start, end) : start;
        List<Note> melodyCopy = FrameWorkUtil.copyMelody(melody);
        List<Note> result = melodyCopy;
        if (initialCondition != null && !initialCondition.checkCondition(base, melodyCopy, modifiedStart, end))
            return result;
        if (guaranteedCondition == null) {
            List<Double> positionList;
            if (initialCondition != null)
                positionList = initialCondition.listFaultyPositions(base, melodyCopy, modifiedStart, end);
            else
                positionList = this.calculateAllPositions(modifiedStart, end, melody);

            for (int i = 0; i < positionList.size(); i++) {
                if (this.applyByProbability()) {
                    result = modification.makeModification(base, melodyCopy, modifiedStart, end, positionList.get(i), style);
                }
            }
        } else {
            if (this.applyByProbability()) {
                GuaranteeConditionSolver gcs = new DivideAndConquerEvolutionarySolve(70, 500, 10, 50, 20, 1000);
                result = gcs.Solve(base, melodyCopy, modifiedStart, end, style, this.guaranteedCondition, this.modification);
            }
        }

        return result;
    }

    private List<Double> calculateAllPositions(double modifiedStart, double end, List<Note> melody) {
        List<Double> sol = new ArrayList<Double>();
        int posStart = Util.calculateNotePositionInListByTimeSum(melody, modifiedStart);
        int posEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        for (int i = posStart; i < posEnd; i++) {
            sol.add(Util.calculateTimeSumByPosition(melody, i));
        }
        return sol;
    }

    /**
     * Returns true if, after a theoretically normal random probability roll,
     * the result is a number less than the probability to be applied, so if I roll 100 times I should have
     * 1 out of 100 hits, so if it is 1 percent probability it should happen 1 out of 100 times.
     * @return
     */
    private boolean applyByProbability() {
        double random = Math.random();
        if (random <= this.probability)
            return true;
        return false;
    }
}
