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
public class IntervalBetweenNotes extends ModificationCondition{


    
    Comparador compare;
    int diffNumber;

    public IntervalBetweenNotes(Comparador compare, int diffNumber) {
        this.compare = compare;
        this.diffNumber = diffNumber;
    }
        
    @Override //regla que checkea que todas las notas guarden una determinada distancia con la siguiente.
    public boolean checkCondition(List<Chord> base, List<Note> melody, Double start, Double end) {
        int notePositionStart = Util.calculateNotePositionInListByTimeSum(melody, start);
        int notePositionEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        
        for (int i = notePositionStart; i < notePositionEnd && i<melody.size(); i++) {
            Note toCompare = melody.get(i);
            Note toCompareEnd = melody.get(i+1);
            if(this.satisfiesCondition(toCompare, toCompareEnd))
                return true;
        }
        return false;
     
    }
    
       private boolean satisfiesCondition(Note toCompare, Note toCompareEnd){
        int difference= Math.abs(Util.getExpandedFormNote(toCompare.getNote(),toCompare.getOctave())- Util.getExpandedFormNote(toCompareEnd.getNote(), toCompareEnd.getOctave())); 
        if(difference>diffNumber && compare.esMayor() || difference == diffNumber && compare.esIgual()
           || difference<diffNumber && compare.esMenor())
            return true;
        return false;
    }

    @Override
    public List<Double> listFaultyPositions(List<Chord> base, List<Note> melody, Double start, Double end) {
          int notePositionStart = Util.calculateNotePositionInListByTimeSum(melody, start);
        int notePositionEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        List<Double> faultyPosition = new ArrayList<>();

        for (int i = notePositionStart; i < notePositionEnd-1; i++) {
            Note toCompare = melody.get(i);
            Note toCompareEnd = melody.get(i+1);
            if(this.satisfiesCondition(toCompare, toCompareEnd))
                   faultyPosition.add(Util.calculateTimeSumByPosition(melody, i));//faultyPosition.add(i);//
        }
        return faultyPosition;
    }
    
    
   
}
