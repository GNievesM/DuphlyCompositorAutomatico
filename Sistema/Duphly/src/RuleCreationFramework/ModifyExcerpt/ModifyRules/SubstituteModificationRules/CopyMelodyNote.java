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
public class CopyMelodyNote extends SpecificModification{
    boolean siguiente;
    
    public CopyMelodyNote(boolean siguiente){ // si me pasan false copio la anterior
        this.siguiente = siguiente;
    }
    @Override
    public List<Note> makeModification(List<Chord> base, List<Note> melody, double start, double end, double specificNote, AbstractStyle style) {
        int position = Util.calculateNotePositionInListByTimeSum(melody, specificNote);
        
        melody.set(position, melody.get(position + (this.siguiente? 1 : -1)));
        return melody;
    }


    @Override
    public Note mutate(List<Chord> base, List<Note> melody, double start, double end, int specificNotePosition, AbstractStyle style) {
        int position = specificNotePosition;
        Note n = melody.get(position);
        if(specificNotePosition>0 && specificNotePosition<melody.size())
            n = melody.get(position + (this.siguiente ? 1 : -1));
        return n;
    }
    
    
}
