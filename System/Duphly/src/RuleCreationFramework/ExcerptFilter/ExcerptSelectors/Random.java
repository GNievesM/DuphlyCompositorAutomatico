package RuleCreationFramework.ExcerptFilter.ExcerptSelectors;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ExcerptFilter.ExcerptConditionSelector;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class Random extends ExcerptConditionSelector {
    int quantity;

    public Random(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * If the parameter alreadySelected is received, it means that I don't want to select within the same bounds. In this case, I don't consider the length parameter for the search.
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
       List<Pair<Double, Double>> solution = new ArrayList<>();

       for (int i = 0; i < quantity; i++) {
           int positionStart = (int) (Math.random() * (endPosition - startPosition) + startPosition);
           int positionEnd;

           if (positionStart + length > endPosition)
               positionEnd = positionStart - length;
           else
               positionEnd = positionStart + length;

           if (positionEnd < startPosition)
               return solution;

           if (!this.sharesPartition(positionStart, positionEnd, alreadySelected)) {
               double timeSumStart = Util.calculateTimeSumByPosition(melody, positionStart);
               double timeSumEnd = Util.calculateTimeSumByPosition(melody, positionEnd);
               solution.add(new Pair<>(timeSumStart, timeSumEnd));
           }
       }
       
       return solution;
    }
}
