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
public class CheckOctave extends ModificationCondition{

    Comparador compare;
    int diffNumber;
    
    public CheckOctave(Comparador comp, int diffNumber){
        this.compare =comp;
        this.diffNumber=diffNumber;
    }
    
    @Override
    public boolean checkCondition(List<Chord> base, List<Note> melody, Double start, Double end) {
      
        int notePositionStart = Util.calculateNotePositionInListByTimeSum(melody, start);
        int notePositionEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        
        for (int i = notePositionStart; i < notePositionEnd; i++) {
            Note toCompare = melody.get(i);
            
            
              if(satisfiesCondition(toCompare))
                return true;
        }
        return false;

    }

    private boolean satisfiesCondition(Note n){
  
        if(n.getOctave()>diffNumber && compare.esMayor() || n.getOctave() == diffNumber && compare.esIgual()
           || n.getOctave()<diffNumber && compare.esMenor())
            return true;
        return false;
    }
    @Override
    public List<Double> listFaultyPositions(List<Chord> base, List<Note> melody, Double start, Double end) {
        List<Double> faultyPosition = new ArrayList<>();
        int startPosition = Util.calculateNotePositionInListByTimeSum(melody, start);
        int endPosition = Util.calculateNotePositionInListByTimeSum(melody, end);
        for (int i = startPosition; i < endPosition; i++) {
            Note n = melody.get(i);
            if(satisfiesCondition(n))
              faultyPosition.add(Util.calculateTimeSumByPosition(melody, i)); // faultyPosition.add(i);//
        }
        
        
        return faultyPosition;
    }
    
    
    
    
}