package DataDefinition;

public class Note {
    private double duration;
    private int note;
    private int octave;
    private char accident;
    
    public Note() {
        duration = 0;
        note = 0;
        octave = 0;
        accident = 'n';
    }
    
    public Note(double duration, int note, int octave, char accident) {
        this.duration = duration;
        this.note = note;
        this.octave = octave;
        this.accident = accident;
    }

    public double getDuration() {
        return duration;
    }

    public void copyNote(Note n) {
        this.accident = n.accident;
        this.duration = n.duration;
        this.note = n.note;
        this.octave = n.octave;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getOctave() {
        return octave;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    public char getAccident() {
        return accident;
    }

    public void setAccident(char accident) {
        this.accident = accident;
    }
}
