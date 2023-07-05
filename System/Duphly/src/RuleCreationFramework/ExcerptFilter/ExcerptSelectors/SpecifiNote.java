package RuleCreationFramework.ExcerptFilter.ExcerptSelectors;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ExcerptFilter.ExcerptConditionSelector;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class SpecifiNote extends ExcerptConditionSelector {
    Integer note;
    Integer octave;

    public SpecifiNote(Integer note, Integer octave) {
        this.note = note;
        this.octave = octave;
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
       boolean useHeight = octave != null;
       boolean useNote = note != null;
       List<Pair<Double, Double>> solution = new ArrayList<>();
       
       for (int i = startPosition; i < endPosition; i++) {
           if ((melody.get(i).getNote() == note && !useHeight && useNote) || 
               (useHeight && useNote && melody.get(i).getNote() == note && melody.get(i).getOctave() == octave) ||
               (!useNote && melody.get(i).getOctave() == octave)) {
               
               double position = Util.calculateTimeSumByPosition(melody, i);
               Pair<Double, Double> pair = new Pair<>(position, position);
               
               if (alreadySelected == null || (alreadySelected != null && !this.sharesPartition(position, position, alreadySelected))) {
                   solution.add(pair);
               }
           }
        }
        
        return solution;
    }
}
