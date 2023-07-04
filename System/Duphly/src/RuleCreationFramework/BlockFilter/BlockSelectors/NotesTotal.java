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
public class NotesTotal extends BlockConditionSelector{
    Comparador comp;
    Integer specificNumber;

    public NotesTotal(Comparador comp, Integer specificNumber) {
        this.comp = comp;
        this.specificNumber = specificNumber;
    } 
  

    @Override
    public List<Pair<Integer, Integer>> applySelector(List<Note> melody, List<Chord> base, Integer start, Integer end, Integer blockQuantity) {
       
        List<Pair<Integer,Integer>> solution = new ArrayList<>();        
        for(int i =start;i<end;){
            Double selectionStart =Util.calculateTimeSumByPosition(melody,Util.getBlockPositionByNumber(melody, i));
            Double selectionEnd =Util.calculateTimeSumByPosition(melody,Util.getBlockPositionByNumber(melody, i+blockQuantity));
            int indexStart = Util.calculateNotePositionInListByTimeSum(melody, selectionStart);
            int indexEnd = Util.calculateNotePositionInListByTimeSum(melody, selectionEnd);
            int difference = indexEnd - indexStart;
            if(comp.esIgual() && difference ==this.specificNumber ||
               comp.esMayor() && difference > this.specificNumber ||
               comp.esMenor() && difference< this.specificNumber){
                Pair<Integer,Integer> workingInterval = new Pair<>(i, i+blockQuantity);
                solution.add(workingInterval);
                i+=blockQuantity;            
            }
            else i++;
                
        
        
        
        }
        
        return solution;
    }
    
}
