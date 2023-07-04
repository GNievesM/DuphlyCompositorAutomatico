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
public class SustituteValidNote  extends SpecificModification{
    Comparador comp;
    int distance;
    boolean nearNote;
    
    public SustituteValidNote(Comparador comp, int distance, boolean nearNote){ // si recibo 1, es la siguiente; -1 la anterior, otro caso cuento hasta llegar al numero.
        this.comp = comp;
        this.distance = distance;
       this.nearNote = nearNote;
    }
    
    @Override
    public List<Note> makeModification(List<Chord> base, List<Note> melody, double start, double end, double specificNote, AbstractStyle style) {
       System.out.println("cambie cosas!");
        int position = Util.calculateNotePositionInListByTimeSum(melody, specificNote);
        if(position+distance <0 || position+distance >= melody.size()) // verifico que la nota de comparacion exista, si no existe no hago nada.
            return melody; 
        
        Chord noteAsociatedChord = Util.LookForBaseChord(base,melody,position);
        Note particularNote= melody.get(position+distance);
        particularNote = new Note(melody.get(position).getDuration(), particularNote.getNote(),particularNote.getOctave(),particularNote.getAccident());
        
        if(comp.esMayor() && nearNote){
            particularNote = style.nextValidNote(noteAsociatedChord, particularNote);
        }
        if(comp.esMenor()&& nearNote){
            particularNote = style.previousValidNote(noteAsociatedChord, particularNote);
        }
        if(comp.esMayor() && !nearNote){
            particularNote = style.biggerValidNote(noteAsociatedChord, particularNote,null);
        }
        if(comp.esMenor()&& !nearNote){
            particularNote = style.smallerValidNote(noteAsociatedChord, particularNote,null);
        }
        melody.set(position, particularNote);
        return melody;
    }

    /*@Override
    public Note mutate(Note element) {
        if(comp.esMayor()){
            element = style.nextValidNote(noteAsociatedChord, particularNote);
        }
        if(comp.esMenor()){
            particularNote = style.previousValidNote(noteAsociatedChord, particularNote);
        }
        
    }*/

    @Override
    public Note mutate(List<Chord> base, List<Note> melody, double start, double end, int specificNotePosition, AbstractStyle style) {
        /*Note element= melody.get(specificNotePosition);
        if(comp.esMayor()){
            element = style.nextValidNote(Util.LookForBaseChord(base, melody, specificNotePosition), element);
        }
        if(comp.esMenor()){
            element = style.previousValidNote(Util.LookForBaseChord(base, melody, specificNotePosition), element);
        }
        return element;
        */
        
        Note element= melody.get(specificNotePosition);
        if(specificNotePosition+distance <0 || specificNotePosition+distance > melody.size()) // verifico que la nota de comparacion exista, si no existe no hago nada. EN particular si escapa del rango la dejo vivir.
            return element; 
        
        Chord noteAsociatedChord = Util.LookForBaseChord(base,melody,specificNotePosition);
        Note particularNote= melody.get(specificNotePosition+distance);
        
        if(comp.esMayor()&& nearNote){
            element = style.nextValidNote(noteAsociatedChord, particularNote);
        }
        if(comp.esMenor()&& nearNote){
            element = style.previousValidNote(noteAsociatedChord, particularNote);
        }
        if(comp.esMayor()&& !nearNote){
            element = style.biggerValidNote(noteAsociatedChord, particularNote,null);
        }
        if(comp.esMenor()&& !nearNote){
            element = style.smallerValidNote(noteAsociatedChord, particularNote,null);
        }
        
        return element;
    }
    
    
}
