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
public class Note {
    private double duration;
    private int note;
    private int octave;
    private char accident;
    public Note(){
    duration =0;
    note =0;
    octave = 0;
    accident='n';
    }
    public Note(double duration,int note,int octave,char accident){
     this.duration =duration;
    this.note =note;
    this.octave = octave;
    this.accident=accident;
    }
    /**
     * @return the duration
     */
    public double getDuration() {
        return duration;
    }

    public void copyNote(Note n){
        this.accident = n.accident;
        this.duration = n.duration;
        this.note = n.note;
        this.octave= n.octave;
    }
    /**
     * @param duration the duration to set
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     * @return the nota
     */
    public int getNote() {
        return note;
    }

    /**
     * @param nota the nota to set
     */
    public void setNote(int note) {
        this.note = note;
    }

    /**
     * @return the octava
     */
    public int getOctave() {
        return octave;
    }

    /**
     * @param octava the octava to set
     */
    public void setOctave(int octave) {
        this.octave = octave;
    }

    /**
     * @return the alteracion
     */
    public char getAccident() {
        return accident;
    }

    /**
     * @param alteracion the alteracion to set
     */
    public void setAccident(char accident) {
        this.accident = accident;
    }
    
    
}
