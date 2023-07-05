package RuleCreationFramework.BlockFilter;

import DataDefinition.Note;
import RuleCreationFramework.ExcerptFilter.ExcerptFilter;
import DataDefinition.Chord;
import ImprovisationRules.Util;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import Style.AbstractStyle;

public class BlockFilter {
    List<DependentBlockFilterElement> deFilters;
    List<ExcerptFilter> excerptFilter;
    
    /**
     * Applies the block filter and excerpt filters to the melody.
     * 
     * @param base The base chords.
     * @param melody The melody notes.
     * @param style The abstract style.
     * @return The filtered melody.
     */
    public List<Note> applyFilter(List<Chord> base, List<Note> melody, AbstractStyle style) {
        List<Note> result = melody;
        List<Pair<Double, Double>> filteringResult = this.applyFilters(base, result);
        for (ExcerptFilter e : excerptFilter) {
            for (Pair<Double, Double> pair : filteringResult) {
                result = e.applyFilter(base, result, pair.getKey(), pair.getValue(), style);
            }
        }
        return result;        
    }

    private List<Pair<Double, Double>> applyFilters(List<Chord> base, List<Note> melody) {
        List<Pair<Double, Double>> result = new ArrayList<>();
        for (DependentBlockFilterElement fd : this.deFilters) {
            result = fd.filter(base, melody, result);
        }
        if (this.deFilters.isEmpty()) {
            result.add(new Pair<>(0.0, Util.getMelodyTotalLength(melody))); 
        }
        return result;
    }
    
    public void addDependentFilter(DependentBlockFilterElement d) {
        this.deFilters.add(d);
    }

    public void addExcerptFilter(ExcerptFilter f) {
        this.excerptFilter.add(f);
    }
        
    public BlockFilter() {
        this.deFilters = new ArrayList<>();
        this.excerptFilter = new ArrayList<>();
    }
}
