/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ExcerptFilter.ExcerptSelectors;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ExcerptFilter.ExcerptConditionSelector;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public class DifferenceBeetweenNotes extends ExcerptConditionSelector{
    Comparador comp;
    Integer number;
    Integer defectLength;

    public DifferenceBeetweenNotes(Comparador comp, Integer number, Integer defectLength) {
        this.comp = comp;
        this.number = number;
        this.defectLength= defectLength;
    }
   
    @Override
    public List<Pair<Double, Double>> applySelector(List<Note> melody, List<Chord> base, double start, double end, int length, List<Pair<Double, Double>> alreadySelected) {
      int startPosition = Util.calculateNotePositionInListByTimeSum(melody, start);
      int endPosition = Util.calculateNotePositionInListByTimeSum(melody, end);
      List<Pair<Double,Double>> solution = new ArrayList<>();  
      
      for(int i= startPosition, intervalStart = i,correct=0; i < endPosition-1;i++){          
          Note present = melody.get(i);
          Note next = melody.get(i+1);
          int difference = Math.abs(Util.getExpandedFormNote(present.getNote(), present.getOctave())-Util.getExpandedFormNote(next.getNote(), next.getOctave()));
          if(difference ==this.number && this.comp.esIgual()||
             difference < this.number && this.comp.esMenor() ||
             difference > this.number && this.comp.esMayor()){
              if(intervalStart !=0)
                correct ++;
          }else {
              correct = 0;
              intervalStart = i+1;
          }
               
                  
              
          if(correct == this.defectLength){
              addInterval(solution,intervalStart,intervalStart+correct,melody,alreadySelected);
              intervalStart=i+1;
              correct = 0;
          }
              
              
      }
      
      return solution;

    }
    
   private void addInterval(List<Pair<Double,Double>> solution, int indexStart,int indexEnd, List<Note> melody, List<Pair<Double,Double>> alreadySelected){
        Pair<Double,Double> interval = new Pair<Double,Double>(Util.calculateTimeSumByPosition(melody, indexStart), Util.calculateTimeSumByPosition(melody, indexEnd));
        if(!this.sharesPartition(indexStart, indexEnd, alreadySelected)){
            solution.add(interval);
        }
      
      }
    
}
