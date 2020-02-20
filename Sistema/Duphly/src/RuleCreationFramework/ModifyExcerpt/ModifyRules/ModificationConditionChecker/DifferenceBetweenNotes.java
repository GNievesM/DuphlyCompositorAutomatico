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
public class DifferenceBetweenNotes extends ModificationCondition{
    Comparador compare;
    int diffNumber;
    Integer sumResult =null;

    public DifferenceBetweenNotes(Comparador compare, int diffNumber) {
        this.compare = compare;
        this.diffNumber = diffNumber;
    }

    
    @Override //regla que checkea que todas las notas guarden una determinada distancia con la siguiente.
    public boolean checkCondition(List<Chord> base, List<Note> melody, Double start, Double end) {
        int notePositionStart = Util.calculateNotePositionInListByTimeSum(melody, start);
        int notePositionEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        if(sumResult == null)
            this.calculateTotalSum(notePositionStart, notePositionEnd, melody);
        return this.satisfiesCondition(this.diffNumber);
   
    }
    
       private void calculateTotalSum(int notePositionStart, int notePositionEnd, List<Note> melody){
        int totalSum =0;
        for (int i = notePositionStart; i < notePositionEnd-1; i++) {
            Note toCompare = melody.get(i);
            Note toCompareEnd = melody.get(i+1);
            totalSum += Util.getExpandedFormNote(toCompareEnd.getNote(), toCompareEnd.getOctave())-Util.getExpandedFormNote(toCompare.getNote(), toCompare.getOctave()) ;  
           
        }
            this.sumResult =  totalSum;
       }
       
    private boolean satisfiesCondition(int difference){
      
        if(difference>diffNumber && compare.esMayor() || difference == diffNumber && compare.esIgual()
           || difference<diffNumber && compare.esMenor())
            return true;
        return false;
    }

    @Override
    public List<Double> listFaultyPositions(List<Chord> base, List<Note> melody, Double start, Double end) {
        int notePositionStart = Util.calculateNotePositionInListByTimeSum(melody, start);
        int notePositionEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        if(sumResult == null)
            this.calculateTotalSum(notePositionStart, notePositionEnd, melody);
        int solve = this.targetNumber();
        List<Double> faultyPosition = new ArrayList<>();
        int totalSumAfterFaulty = this.sumResult;
        
        for (int i = notePositionStart; i < notePositionEnd-1; i++) {
            Note toCompare = melody.get(i);
            Note toCompareEnd = melody.get(i+1);
            int diff = Util.getExpandedFormNote(toCompareEnd.getNote(), toCompareEnd.getOctave())-Util.getExpandedFormNote(toCompare.getNote(), toCompare.getOctave());
            if((totalSumAfterFaulty>solve && diff<0) || (totalSumAfterFaulty<solve && diff>0)){
                faultyPosition.add(Util.calculateTimeSumByPosition(melody, i+1));
                totalSumAfterFaulty +=diff;
            }
            if(satisfiesCondition(totalSumAfterFaulty))
                return faultyPosition;
                
        }
        
        return faultyPosition;
    }
    
         
    
    
    private int targetNumber(){
        if(compare.esMayor())
            return diffNumber+1;
        if(compare.esMenor())
            return diffNumber-1;
        
         return diffNumber;
    }
    
    
    
    
}
