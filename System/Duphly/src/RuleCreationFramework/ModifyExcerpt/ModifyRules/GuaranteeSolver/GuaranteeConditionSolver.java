/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteeSolver;

import DataDefinition.Chord;
import DataDefinition.Note;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedCondition;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import Style.AbstractStyle;
import java.util.List;

/**
 *
 * @author Gaston
 */
public abstract class GuaranteeConditionSolver {
    
    public abstract List<Note> Solve(List<Chord> base, List<Note> melody, Double start, Double end, AbstractStyle style, GuaranteedCondition gc, SpecificModification sm);
    
}
