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
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public class Form extends BlockConditionSelector{
    boolean asc;
    Integer pitch;
    Integer octave;
    Integer noteQuantity;

    public Form(boolean asc, Integer pitch, Integer octave, Integer noteQuantity) {
        this.asc = asc;
        this.pitch = pitch;
        this.octave = octave;
        this.noteQuantity = noteQuantity;
    }
 
    @Override
    public List<Pair<Integer, Integer>> applySelector(List<Note> melody, List<Chord> base, Integer start, Integer end, Integer blockQuantity) {
       
        List<Pair<Integer,Integer>> solution = new ArrayList<>();        
        for(int i =start;i<end;){
            Double selectionStart = Util.calculateTimeSumByPosition(melody,Util.getBlockPositionByNumber(melody, i));
            Double selectionEnd = Util.calculateTimeSumByPosition(melody,Util.getBlockPositionByNumber(melody, i+blockQuantity));
            int indexStart = Util.calculateNotePositionInListByTimeSum(melody, selectionStart);
            int indexEnd = Util.calculateNotePositionInListByTimeSum(melody, selectionEnd);
            boolean found=false;
            int count= 0;
            for(int j=indexStart;j<indexEnd-1;j++){
                
                if(melody.get(j).getNote()==pitch && (octave == null ||  melody.get(j).getOctave() == this.octave))
                    found = true;
                
                if(found && ((!Util.IsHIgherPitchThan(melody.get(j), melody.get(j+1)) && asc) || Util.IsHIgherPitchThan(melody.get(j), melody.get(j+1)))){
                    count++;
                }else{
                    count=0;   
                    found = false;
                }
                
                if(count == noteQuantity){
                    Pair<Integer,Integer> workingInterval = new Pair<>(i, i+blockQuantity);
                    solution.add(workingInterval);
                    i+=blockQuantity;  
                    j = indexEnd;
                }
                
            }
            if(count != noteQuantity)
                i++;
            
            
            
        }
        
        return solution;
    }
    
}
