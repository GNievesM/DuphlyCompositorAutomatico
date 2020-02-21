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
public class ChangeMetric  extends SpecificModification{
    Integer divition; // hay que tener cuidado que no se puede cambiar cualquier cosa por cualquier otra.
  
    public ChangeMetric(Integer divition){ 
        this.divition = divition;
       
    }
    @Override
    public List<Note> makeModification(List<Chord> base, List<Note> melody, double start, double end, double specificNote, AbstractStyle style) {
        System.out.println("cambie cosas!");
        int position = Util.calculateNotePositionInListByTimeSum(melody, specificNote);
        Note particularNote= melody.get(position);
        particularNote.setDuration(particularNote.getDuration()/divition);
        for (int i = 0; i < divition-1; i++) {
            Note copia = new Note();    
            copia.copyNote(particularNote);
            melody.add(position+i,copia);
        }
        melody.set(position+divition-1,particularNote);
        return melody;
    }

    /*@Override
    public Note mutate(Note element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    @Override
    public Note mutate(List<Chord> base, List<Note> melody, double start, double end, int specificNotePosition, AbstractStyle style) {
        throw new UnsupportedOperationException("No se puede aplicar el algoritmo evolutivo con esta modificacion"); //To change body of generated methods, choose Tools | Templates.
        //int position = specificNotePosition;
        //Note particularNote= melody.get(position);
        //particularNote.setDuration(this.halfduration==true? particularNote.getDuration()/2:duration);
        
        
        //return particularNote;
    }
    
    
}
