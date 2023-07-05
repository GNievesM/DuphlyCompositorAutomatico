package RuleCreationFramework.BlockFilter;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.List;
import javafx.util.Pair;

public abstract class BlockConditionSelector {
    public abstract List<Pair<Integer,Integer>> applySelector(List<Note> melody, List<Chord> base, Integer start, Integer end, Integer blockQuantity);
}
