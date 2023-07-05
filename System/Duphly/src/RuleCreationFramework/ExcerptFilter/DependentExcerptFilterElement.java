/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ExcerptFilter;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.List;
import javafx.util.Pair;

public abstract class DependentExcerptFilterElement {
    
    public abstract List<Pair<Double,Double>> Filter(List<Chord> base, List<Note> melody,double melodyStart, double melodyEnd, List<Pair<Double,Double>> excerptsList);
}
