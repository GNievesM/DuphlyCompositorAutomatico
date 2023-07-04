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
public class SubstituteForASequency extends SpecificModification{
    List<Note> sequence;
    boolean completeNote;
   
    public SubstituteForASequency(List<Note> sequence, boolean completeNote){ // si me pasan false copio la anterior
        this.sequence = sequence;
        this.completeNote=completeNote;
    }
    @Override
    public List<Note> makeModification(List<Chord> base, List<Note> melody, double start, double end, double specificNote, AbstractStyle style) {
        int position = Util.calculateNotePositionInListByTimeSum(melody, specificNote);
        int lastPosition= Util.calculateNotePositionInListByTimeSum(melody, end);
        System.out.println("cambie cosas!");
        for (int i = position, sequencePosition = 0; i < lastPosition && sequencePosition<sequence.size(); i++,sequencePosition++) {
            Note modifying= melody.get(i);
            modifying.setNote(this.sequence.get(sequencePosition).getNote());
            if(completeNote)
                modifying.setOctave(this.sequence.get(sequencePosition).getOctave());
            melody.set(i, modifying);
        }       
        return melody;
    }

    /*public Note mutate(Note element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    @Override
    public Note mutate(List<Chord> base, List<Note> melody, double start, double end, int specificNotePosition, AbstractStyle style) {
        int position = specificNotePosition;
        int lastPosition= Util.calculateNotePositionInListByTimeSum(melody, end);
        Note firstNote= melody.get(specificNotePosition);
        for (int i = position, sequencePosition = 0; i < lastPosition && sequencePosition<sequence.size(); i++,sequencePosition++) {
            Note modifying= melody.get(i);
            modifying.setNote(this.sequence.get(sequencePosition).getNote());
            if(completeNote)
                modifying.setOctave(this.sequence.get(sequencePosition).getOctave());
            melody.set(i, modifying);
        }       
        return firstNote;
    }
    
    
    
}
