/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gasto_000
 */
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
    public void AddRhythmNode(TupleChordDuration rhytmNode){
    
        this.rhythmSequence.add(rhytmNode);
    }
    
}
