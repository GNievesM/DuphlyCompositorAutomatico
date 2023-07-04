/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.BlockFilter.BlockSelectors;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.BlockFilter.BlockConditionSelector;
import RuleCreationFramework.FrameworkUtil.Comparador;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public class NoteQuantity extends BlockConditionSelector{

   
    Comparador comp;
    Integer pitch;
    Integer octave;
    Integer specificNumber;
    Integer grade;

    public NoteQuantity(Comparador comp, Integer pitch, Integer octave, Integer specificNumber) {
        this.comp = comp;
        this.pitch = pitch;
        this.octave = octave;
        this.specificNumber = specificNumber;
        this.grade = null;
    }
    public NoteQuantity(Comparador comp,  Integer specificNumber, Integer grade) {
        this.comp = comp;
        this.pitch = null;
        this.octave = null;
        this.specificNumber = specificNumber;
        this.grade = grade;
    }
    
    
    @Override
    public List<Pair<Integer, Integer>> applySelector(List<Note> melody, List<Chord> base, Integer start, Integer end, Integer blockQuantity) {
       
        List<Pair<Integer,Integer>> solution = new ArrayList<>();        
        for(int i =start;i<end;){
            Double selectionStart =Util.calculateTimeSumByPosition(melody,Util.getBlockPositionByNumber(melody, i));
            Double selectionEnd =Util.calculateTimeSumByPosition(melody,Util.getBlockPositionByNumber(melody, i+blockQuantity));
            int indexStart = Util.calculateNotePositionInListByTimeSum(melody, selectionStart);
            int indexEnd = Util.calculateNotePositionInListByTimeSum(melody, selectionEnd);
            int count=0;
            for(int j=indexStart;j<indexEnd;j++){
                if((this.pitch!=null && melody.get(j).getNote()==pitch && (octave == null ||  melody.get(j).getOctave() == this.octave))
                   || (this.grade != null && this.grade == Util.calculateNoteGradeForChord(Util.LookForBaseChord(base, melody, j), melody.get(j))))
                    count++;
            }
            
            if(count == specificNumber && comp.esIgual()||
               count > specificNumber && comp.esMayor() ||
               count < specificNumber && comp.esMenor()){                
                   Pair<Integer,Integer> workingInterval = new Pair<>(i, i+blockQuantity);
                   solution.add(workingInterval);
                   i+=blockQuantity;  
            }else i++;
            
            count=0;    
        }
        
        return solution;
    }
    
}
