/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import EvolutionaryAlgorithm.Mutator;
import ImprovisationRules.Util;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import java.util.List;
import Style.AbstractStyle;

/**
 *
 * @author Gaston
 */
public class MoveDemiTones extends SpecificModification{
    boolean up;
    int quantity;
    AbstractStyle style;
    
    public MoveDemiTones(boolean up, int quantity){ // si me pasan false copio la anterior
        this.up = up;
        this.quantity = quantity;
       
    }
    
    @Override
    public List<Note> makeModification(List<Chord> base, List<Note> melody, double start, double end,double specificNote,AbstractStyle style) {
      
        int position = Util.calculateNotePositionInListByTimeSum(melody, specificNote);
        Note particularNote= melody.get(position);
        melody.set(position,style.addDemiTones(particularNote, up? quantity : -quantity));
        return melody;
    }

  /*  @Override
    public Note mutate(Note element) {
        return style.addDemiTones(element, up? quantity : -quantity);
        //return Util.addDemiTones(element,  up? quantity : -quantity);
    }
*/
    @Override
    public Note mutate(List<Chord> base, List<Note> melody, double start, double end, int specificNotePosition, AbstractStyle style) {
       return style.addDemiTones(melody.get(specificNotePosition), up? quantity : -quantity);
    }
    
    
}
