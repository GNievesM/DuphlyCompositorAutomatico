/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import java.util.List;
import Style.AbstractStyle;

/**
 *
 * @author Gaston
 */
public class SustituteRandomValidNote  extends SpecificModification{
    Comparador comp;
    int distance;
    List<Double> prob;
    boolean constraint;
    public SustituteRandomValidNote(Comparador comp, int distance, List<Double> optionalProbability){ // si recibo 1, es la siguiente; -1 la anterior, otro caso cuento hasta llegar al numero.
        this.comp = comp;
        this.distance = distance;
        this.prob = optionalProbability;
        constraint = true;
    }
     public SustituteRandomValidNote(boolean constraint){ // si recibo 1, es la siguiente; -1 la anterior, otro caso cuento hasta llegar al numero.
        this.comp = null;
        this.distance = 0;
        this.prob = null;
        constraint = false;
    } 
    @Override
    public List<Note> makeModification(List<Chord> base, List<Note> melody, double start, double end, double specificNote, AbstractStyle style) {
        int position = Util.calculateNotePositionInListByTimeSum(melody, specificNote);
        System.out.println("cambie cosas!");
        if(position+distance <0 || position+distance > melody.size() || comp.esIgual()) // verifico que la nota de comparacion exista, si no existe no hago nada.
            return melody; 
        
        Chord noteAsociatedChord = Util.LookForBaseChord(base,melody,position);
        Note particularNote= melody.get(position+distance);
        particularNote = new Note(melody.get(position).getDuration(), particularNote.getNote(),particularNote.getOctave(),particularNote.getAccident());

        if(this.constraint == false)
            particularNote = style.validRandomNote(noteAsociatedChord, particularNote.getDuration(), prob);
        else{
            if(comp.esMayor()){
                particularNote = style.biggerValidNote(noteAsociatedChord, particularNote, prob);
            }
            if(comp.esMenor()){
                particularNote = style.smallerValidNote(noteAsociatedChord, particularNote,prob);
            }
        }
        melody.set(position, particularNote);
        return melody;
    }

  /*  @Override
    public Note mutate(Note element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    @Override
    public Note mutate(List<Chord> base, List<Note> melody, double start, double end, int specificNotePosition, AbstractStyle style) {
        Note element= melody.get(specificNotePosition);
        int position =specificNotePosition;
        
        if(position+distance <0 || position+distance > melody.size()) // verifico que la nota de comparacion exista, si no existe no hago nada.
            return element; 
        
        Chord noteAsociatedChord = Util.LookForBaseChord(base,melody,position);
        Note particularNote= melody.get(position+distance);
        if(this.constraint == false)
            element = style.validRandomNote(noteAsociatedChord, particularNote.getDuration(), prob);
        else{
           if(comp.esMayor()){
                 element = style.biggerValidNote(noteAsociatedChord, particularNote, prob);
            }
           if(comp.esMenor()){
                 element = style.smallerProbabilityNote(noteAsociatedChord, particularNote,prob);
           }
        }
        return element;
    }
    
    
}
