/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationConditionChecker;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationCondition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gaston
 */
public class CheckTimesANoteAppear extends ModificationCondition {
    Note note;
    boolean absolut;
    int numberOfApparitions;
    Comparador cmp;
    Integer grade;
    
    public CheckTimesANoteAppear(Note note, boolean absolut, int numberOfApparitions, Comparador comp){
        this.note = note;
        this.absolut = absolut;
        this.numberOfApparitions = numberOfApparitions;
        cmp = comp;
        this.grade = null;
    }
    
      public CheckTimesANoteAppear(Integer grade, int numberOfApparitions, Comparador comp){
        this.grade = grade;
        this.numberOfApparitions = numberOfApparitions;
        cmp = comp;
        this.absolut = false;
        this.note = null;
    }
    
    @Override
    public boolean checkCondition(List<Chord> base, List<Note> melody, Double start, Double end) {
        int positionStartIndex = Util.calculateNotePositionInListByTimeSum(melody, start);
        int positionEndIndex = Util.calculateNotePositionInListByTimeSum(melody, end);
        int quantity=0;
        for (int i = positionStartIndex; i < positionEndIndex; i++) {
            Note particularNote = melody.get(i);
         //   if(this.absolut && particularNote.getNote() == this.note.getNote() && particularNote.getOctave() ==this.note.getOctave()
          //     || !this.absolut && particularNote.getNote() == this.note.getNote()  )
            if(this.satisfiesCondition(particularNote,Util.LookForBaseChord(base, melody, i)))  
                quantity++;
        }
    
        return quantity > this.numberOfApparitions && cmp.esMayor() || quantity<this.numberOfApparitions&& cmp.esMenor() || quantity == this.numberOfApparitions && cmp.esIgual();
    }

    private boolean satisfiesCondition(Note particularNote, Chord c){
        if(this.grade != null){
            return Util.calculateNoteGradeForChord(c, particularNote)==this.grade;
        }
        if(this.absolut && particularNote.getNote() == this.note.getNote() && particularNote.getOctave() ==this.note.getOctave()
               || !this.absolut && particularNote.getNote() == this.note.getNote()  )
            return true;
        return false;
    }
    
    @Override
    public List<Double> listFaultyPositions(List<Chord> base, List<Note> melody, Double start, Double end) {
        int positionStartIndex = Util.calculateNotePositionInListByTimeSum(melody, start);
        int positionEndIndex = Util.calculateNotePositionInListByTimeSum(melody, end);
        List<Double> faultyPosition = new ArrayList<>();
        for (int i = positionStartIndex; i < positionEndIndex; i++) {
           Note particularNote = melody.get(i);
           if(this.satisfiesCondition(particularNote,Util.LookForBaseChord(base, melody, i)))
             faultyPosition.add(Util.calculateTimeSumByPosition(melody, i));// faultyPosition.add(i);//  
        }
        
        return faultyPosition;
    }
    
}
