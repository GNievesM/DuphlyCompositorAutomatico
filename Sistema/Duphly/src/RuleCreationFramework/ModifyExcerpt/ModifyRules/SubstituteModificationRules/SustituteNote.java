/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import java.util.List;
import Style.AbstractStyle;

/**
 *
 * @author Gaston
 */
public class SustituteNote  extends SpecificModification{
    int pitch;
    int octave;
    boolean noteOnly;
    boolean silence;
    
    public SustituteNote(int pitch, int octave, boolean noteOnly){ // si me pasan false copio la octava anterior
        this.pitch = pitch;
        this.octave = octave;
        this.noteOnly = noteOnly;
    }
    public SustituteNote(boolean silence){ // si me pasan false copio la octava anterior
        this.pitch = 0;
        this.octave = -1;
        this.noteOnly = false;
        this.silence = silence;
    }
    @Override
    public List<Note> makeModification(List<Chord> base, List<Note> melody, double start, double end,double specificNote, AbstractStyle style) {
        int position = Util.calculateNotePositionInListByTimeSum(melody, specificNote);
       System.out.println("cambie cosas!");
        Note particularNote= melody.get(position);
       if(this.silence){
           particularNote.setNote(0);
           particularNote.setOctave(-1);
       }else{
            particularNote.setNote(pitch);
            if(!noteOnly)
                particularNote.setOctave(octave);
        }
        melody.set(position,particularNote);
        return melody;
    }

    /*@Override
    public Note mutate(Note element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    @Override
    public Note mutate(List<Chord> base, List<Note> melody, double start, double end, int specificNotePosition, AbstractStyle style) {
        int position = specificNotePosition;
        Note particularNote= melody.get(position);
        if(this.silence){
           particularNote.setNote(0);
           particularNote.setOctave(-1);
        }else{
            particularNote.setNote(pitch);
            if(!noteOnly)
                particularNote.setOctave(octave);
        }
        
        return particularNote;
    }
    
    
}
