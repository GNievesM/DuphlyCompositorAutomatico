/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.BlockFilter;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public abstract class BlockConditionSelector {
    public abstract List<Pair<Integer,Integer>> applySelector(List<Note> melody, List<Chord> base, Integer start, Integer end, Integer blockQuantity);
}
