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
import duphly.DuphlyMusicComposer;
import java.util.ArrayList;

/**
 *
 * @author Gaston
 */
public class AddSequency extends SpecificModification{
     boolean after=false;
     
    List<Note> sequence = null;
    
    boolean respectBase =false;
    List<Integer> grades=null;
   
    boolean betweenPrevious =false;
    int semitoneQuantityJumps=1;

    public AddSequency(boolean after, List<Note> sequence) {
        this.after = after;
        this.sequence = sequence;
    }

    public AddSequency(boolean after, boolean respectBase, List<Integer> grades) {
        this.after = after;
        this.respectBase = respectBase;
        this.grades = grades;
    }

    public AddSequency(boolean betweenPrevious, int semitoneQuantityJumps) {
        this.after = after;
        this.betweenPrevious = betweenPrevious;
        this.semitoneQuantityJumps = semitoneQuantityJumps;
    }
   
  
    @Override
    public List<Note> makeModification(List<Chord> base, List<Note> melody, double start, double end, double specificNote, AbstractStyle style) {
        System.out.println("cambie cosas!");
        
        int position = Util.calculateNotePositionInListByTimeSum(melody, specificNote);
        int lastPosition= Util.calculateNotePositionInListByTimeSum(melody, end);
        if(this.sequence!= null)
            return this.addAbsoluteNotes(melody, position);
        else if(this.grades != null)
            return this.addRelativeSequence(melody, base, position, style);
        
        
        return this.addInBetweenSequence(melody, position);
    }

    private List<Note> addAbsoluteNotes(List<Note> melody, int specificPlace){
        
        Note noteToChange = melody.get(specificPlace);
        double duration = noteToChange.getDuration()/this.sequence.size();
        noteToChange.setDuration(duration);
        for(int i=this.sequence.size()-1; i>=0;i--){
            Note n = this.sequence.get(i);
            Note toAdd = new Note(noteToChange.getDuration(),n.getNote(),n.getOctave(),n.getAccident());
            melody.add(this.after? specificPlace+1:specificPlace,toAdd);
            
        }
        return melody;
    }
    
    private List<Note> addRelativeSequence(List<Note> melody, List<Chord> base, int specificPlace, AbstractStyle style){
        Note noteToChange = melody.get(specificPlace);
        double duration = noteToChange.getDuration()/this.sequence.size();
        noteToChange.setDuration(duration);
        Chord baseChordOnNote = Util.LookForBaseChord(base, melody, specificPlace);
        
        for(int i = this.grades.size()-1; i>=0;i--){
            int grade = this.grades.get(i);
            Note addedGradeNote = new Note(noteToChange.getDuration(),noteToChange.getNote(),noteToChange.getOctave(),noteToChange.getAccident());
            if(this.respectBase==false)
                 addedGradeNote= Util.addDemiTones(addedGradeNote, grade);
            else {                
                addedGradeNote = Util.returnNoteAfterAddingGradesToChord(baseChordOnNote, grade, noteToChange.getOctave(), duration);
            }
            
            Note toAdd = new Note(addedGradeNote.getDuration(),addedGradeNote.getNote(),addedGradeNote.getOctave(),addedGradeNote.getAccident());
            melody.add(this.after? specificPlace+1:specificPlace,toAdd);
            
        }
        return melody;
        
    }
    
    private List<Note> addInBetweenSequence(List<Note> melody, int specificPlace){
        if(specificPlace+1 > melody.size() || specificPlace-1 < 0) return melody;

        System.out.println("voy a agregar cromaticas");
        
        Note noteToChange = melody.get(specificPlace);
        Note intervalNote = melody.get(this.betweenPrevious? specificPlace-1 : specificPlace+1 );
        int expandedFormToChange = Util.getExpandedFormNote(noteToChange.getNote(), noteToChange.getOctave());
        int expandedFormInterval = Util.getExpandedFormNote(intervalNote.getNote(), intervalNote.getOctave());
        int quantityOfDemiTonesInterval = Math.abs(expandedFormInterval-expandedFormToChange);
        int quantityOfNotes = quantityOfDemiTonesInterval/this.semitoneQuantityJumps;
        double duration = noteToChange.getDuration()/(double)quantityOfNotes;
        noteToChange.setDuration(duration);
        
        int reverseCaseForBefore = 1;
        for(int i =quantityOfNotes-1; i>0;i--){
            Note addedGradeNote = new Note(noteToChange.getDuration(),noteToChange.getNote(),noteToChange.getOctave(),noteToChange.getAccident());
            int multiplier =1;
            if(expandedFormToChange - expandedFormInterval> 0)
                multiplier=-1;
            if(this.betweenPrevious==true){ //Si agrego antes tengo que meter primero las notas mas cercanas a la que estoy desplazando
                addedGradeNote = Util.addDemiTones(addedGradeNote, reverseCaseForBefore*multiplier*this.semitoneQuantityJumps);
                reverseCaseForBefore ++;
            }else{ // si agrego despues tengo que meter las notas mas lejanas primero
                addedGradeNote = Util.addDemiTones(addedGradeNote, i*multiplier*this.semitoneQuantityJumps);
            }
            Note toAdd = new Note(addedGradeNote.getDuration(),addedGradeNote.getNote(),addedGradeNote.getOctave(),addedGradeNote.getAccident());
//int demitoneMultiplier = -(expandedFormToChange-expandedFormInterval)/this.semitoneQuantityJumps;
            melody.add(this.betweenPrevious==false? specificPlace+1:specificPlace,toAdd);
            
        }
        
        
        
        return melody;
    }
    
  
    @Override
    public Note mutate(List<Chord> base, List<Note> melody, double start, double end, int specificNotePosition, AbstractStyle style) {
       throw new UnsupportedOperationException("Esta regla no se puede usar con las garantias."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
