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
public class NotePercentaje extends BlockConditionSelector{
    
    Comparador comp;
    Integer pitch;
    Integer octave;
    Integer percentage;
    Integer grade;

    public NotePercentaje(Comparador comp, Integer pitch, Integer octave, Integer percentage) {
        this.comp = comp;
        this.pitch = pitch;
        this.octave = octave;
        this.percentage = percentage;
        this.grade = null;
    }
 
    public NotePercentaje(Comparador comp, Integer grade, Integer percentage) {
        this.comp = comp;
        this.pitch = null;
        this.octave = null;
        this.percentage = percentage;
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
                if((this.pitch != null && melody.get(j).getNote()==pitch && (octave == null ||  melody.get(j).getOctave() == this.octave)) 
                    ||(this.grade != null && this.grade == Util.calculateNoteGradeForChord(Util.LookForBaseChord(base, melody, j), melody.get(j))))
                    count++;
            }
            double appearancePercentage = count/(indexEnd - indexStart);
            
            if(appearancePercentage == percentage && comp.esIgual()||
               appearancePercentage > percentage && comp.esMayor() ||
               appearancePercentage < percentage && comp.esMenor()){                
                   Pair<Integer,Integer> workingInterval = new Pair<>(i, i+blockQuantity);
                   solution.add(workingInterval);
                   i+=blockQuantity;  
            }else i++;
            
              
        }
        
        return solution;
    }
    
    
}
