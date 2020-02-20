/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataDefinition;

/**
 *
 * @author gasto_000
 */
public class TupleChordDuration {
    Chord chord;
    double duration;

    public TupleChordDuration(Chord c, double duration) {
        this.chord = c;
        this.duration = duration;
    }

    public Chord getChord() {
        return chord;
    }

    public double getDuration() {
        return duration;
    }

    public void setC(Chord c) {
        this.chord = c;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
}
