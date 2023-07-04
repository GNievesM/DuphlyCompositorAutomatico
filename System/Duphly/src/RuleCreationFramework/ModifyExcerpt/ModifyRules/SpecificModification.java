/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import EvolutionaryAlgorithm.Mutator;
import EvolutionaryAlgorithm.SpecificModificationMutator;
import java.util.List;
import Style.AbstractStyle;

/**
 *
 * @author Gaston
 */
public abstract class SpecificModification implements SpecificModificationMutator {
    
    public abstract List<Note> makeModification(List<Chord> base, List<Note> melody, double start, double end, double specificNote, AbstractStyle style);
}
