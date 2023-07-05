package DataDefinition;

import java.util.ArrayList;
import java.util.List;

public class Rhythm {
    private List<TupleChordDuration> rhythmSequence;

    public Rhythm() {
        this.rhythmSequence = new ArrayList<>();
    }

    public List<TupleChordDuration> getRhythmSequence() {
        return rhythmSequence;
    }

    public void setRhythmSequence(List<TupleChordDuration> rhythmSequence) {
        this.rhythmSequence = rhythmSequence;
    }

    public void addRhythmNode(TupleChordDuration rhythmNode) {
        this.rhythmSequence.add(rhythmNode);
    }
}
