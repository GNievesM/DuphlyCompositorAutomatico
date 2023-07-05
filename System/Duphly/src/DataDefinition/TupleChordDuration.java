package DataDefinition;

public class TupleChordDuration {
    Chord chord;
    double duration;

    public TupleChordDuration(Chord chord, double duration) {
        this.chord = chord;
        this.duration = duration;
    }

    public Chord getChord() {
        return chord;
    }

    public double getDuration() {
        return duration;
    }

    public void setChord(Chord chord) {
        this.chord = chord;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
