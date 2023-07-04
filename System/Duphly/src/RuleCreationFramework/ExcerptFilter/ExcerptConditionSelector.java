/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ExcerptFilter;

import DataDefinition.Chord;
import DataDefinition.Note;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public abstract class ExcerptConditionSelector {
    public abstract List<Pair<Double,Double>> applySelector(List<Note> melody, List<Chord> base, double start, double end, int length, List<Pair<Double,Double>> alreadySelected);
    protected boolean sharesPartition(double start,double end, List<Pair<Double,Double>> alreadySelected){
        if(alreadySelected == null) return false;
        return FrameWorkUtil.isInsidePartition(alreadySelected, start) || FrameWorkUtil.isInsidePartition(alreadySelected, end);
    }
}
