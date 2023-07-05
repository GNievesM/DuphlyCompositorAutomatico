/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules;

import DataDefinition.Note;
import ImprovisationRules.Util;
import java.util.List;

/**
 *
 * Author: Gaston
 */
public class ModificationPlaceSelector {
    boolean relativePosition = false;
    int position = 0;
    boolean start = false;
    boolean middle = false;
    boolean end = false;
    
    // start determines if modification starts from the beginning of the excerpt
    // middle determines if modification starts from the middle of the excerpt
    // end determines if modification starts from the end of the excerpt (3/4)
    // relative position determines the number of notes after the specified start point
    public ModificationPlaceSelector(boolean start, boolean middle, boolean end, int relativePosition) {
        this.relativePosition = relativePosition != 0;
        this.position = relativePosition;
        this.start = start;
        this.middle = middle;
        this.end = end;
    }
    
    // Determines the starting point for the modification based on the constructor parameters
    // If start is true, it starts from the specified start point
    // If middle is true, it calculates the approximate middle point as the starting point
    // If end is true, it calculates 75 percent of the excerpt duration as the starting point
    // If none of the above is true, it uses the relative position from the start point (counting notes from the beginning)
    public double startPosition(List<Note> melody, double start, double end) {
        if (this.start)
            return start;
        
        if (this.middle) {
            double approxMiddle = (end - start) / 2 + start;
            return Util.calculateTimeSumByPosition(melody, Util.calculateNotePositionInListByTimeSum(melody, approxMiddle));
        }
        
        if (this.end) {
            double approxMiddle = ((end - start) / 4) * 3 + start;
            return Util.calculateTimeSumByPosition(melody, Util.calculateNotePositionInListByTimeSum(melody, approxMiddle));
        }
        
        return Util.calculateTimeSumByPosition(melody, Util.calculateNotePositionInListByTimeSum(melody, start) + this.position);
    }
}
