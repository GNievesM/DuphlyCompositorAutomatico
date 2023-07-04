/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ExcerptFilter.ExcerptSelectors;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ExcerptFilter.ExcerptConditionSelector;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public class Random extends ExcerptConditionSelector{
  

    int quantity;

    public Random(int quantity) {
        this.quantity = quantity;
    }
    
    
    /** 
     * si Recibo el parametro alreadySelected es por que no quiero que seleccione dentro de la mismas cotas. En este caso no considero el parametro length para la busqueda.
     * @param melody
     * @param base
     * @param start
     * @param end
     * @param length
     * @param alreadySelected
     * @return 
     */
    @Override
    public List<Pair<Double, Double>> applySelector(List<Note> melody, List<Chord> base, double start, double end, int length, List<Pair<Double, Double>> alreadySelected) {
       int startPosition = Util.calculateNotePositionInListByTimeSum(melody, start);
       int endPosition = Util.calculateNotePositionInListByTimeSum(melody, end);
       List<Pair<Double,Double>> solution = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
       
            int positionStart = (int)Math.random()* (endPosition-startPosition) + startPosition;
            int positionEnd=positionStart;
            if(positionStart+length> endPosition)
                positionEnd = positionStart -length;
            else
                positionEnd = positionStart + length;
            if(positionEnd <startPosition) return solution;
            if(!this.sharesPartition(positionStart, positionEnd, alreadySelected))
                solution.add(new Pair<>(Util.calculateTimeSumByPosition(melody, positionStart),Util.calculateTimeSumByPosition(melody, positionEnd)));
        }
       
            
       
        return solution;
    }
    
  
    
}
