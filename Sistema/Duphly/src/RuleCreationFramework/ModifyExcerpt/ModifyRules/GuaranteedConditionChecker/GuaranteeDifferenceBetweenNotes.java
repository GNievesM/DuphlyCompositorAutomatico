/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedConditionChecker;

import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationConditionChecker.*;
import DataDefinition.Chord;
import DataDefinition.Note;
import EvolutionaryAlgorithm.IEvolvableClass;
import ImprovisationRules.Util;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedCondition;
//import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationCondition;
import java.util.List;
import EvolutionaryAlgorithm.IFitnessEvaluable;
import EvolutionaryAlgorithm.IMutationAllowed;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public class GuaranteeDifferenceBetweenNotes extends GuaranteedCondition {
    DifferenceBetweenNotes d;
    List<Chord> base;
    List<Note> melody;
    Double start;
    Double end;
    Comparador comp;
    int diffNumber; 
    SpecificModification sm;
    Integer sumResult = null;
    
     public GuaranteeDifferenceBetweenNotes(Comparador comp, int diffNumber){
        this.comp = comp;
        this.diffNumber = diffNumber;
        d = new DifferenceBetweenNotes (comp,diffNumber);
    }

    public GuaranteeDifferenceBetweenNotes(List<Chord> base, List<Note> melody, Double start, Double end, Comparador comp, int diffNumber) {
     
        this.base = base;
        this.melody = melody;
        this.start = start;
        this.end = end;
        this.comp = comp;
        this.diffNumber = diffNumber;
    }
    
     private int targetNumber(){
        if(comp.esMayor())
            return diffNumber+1;
        if(comp.esMenor())
            return diffNumber-1;
        
         return diffNumber;
    }

    @Override
    public boolean checkCondition(List<Chord> base, List<Note> melody, Double start, Double end) {
        this.base= base;
        this.melody= melody;
        this.start = start;
        this.end = end;
        return d.checkCondition(base, melody, start, end);
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
       
   
    protected double calculateDifferenceSum(){
    
        int notePositionStart = Util.calculateNotePositionInListByTimeSum(melody, start);
        int notePositionEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        if(this.sumResult == null)
            this.calculateTotalSum(notePositionStart, notePositionEnd, melody);
        
        
        
       int diffNotExpected = Math.abs(this.sumResult-this.targetNumber());
        
        return 100/diffNotExpected;
    
    
    }
    
    protected List<Pair<Integer,Integer>> calculateFaultyPartition(){
        int notePositionStart = Util.calculateNotePositionInListByTimeSum(melody, start);
        int notePositionEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        List<Pair<Integer,Integer>> particiones  = new ArrayList<> ();
        int solve = this.targetNumber();
        if(sumResult == null)
            this.calculateTotalSum(notePositionStart, notePositionEnd, melody);
        int totalSumAfterFaulty = this.sumResult;
        
         for (int i = notePositionStart; i < notePositionEnd-1; i++) {
            Note toCompare = melody.get(i);
            Note toCompareEnd = melody.get(i+1);
            int diff = Util.getExpandedFormNote(toCompareEnd.getNote(), toCompareEnd.getOctave())-Util.getExpandedFormNote(toCompare.getNote(), toCompare.getOctave()) ;
            if((totalSumAfterFaulty>solve && diff<0) || (totalSumAfterFaulty<solve && diff>0)){
                Pair<Integer,Integer>  faulty = new Pair<Integer,Integer>(new Integer(i),new Integer(i+1));
                particiones.add(faulty);
                totalSumAfterFaulty +=diff;
            
            }
            
            if(satisfiesCondition(totalSumAfterFaulty))
                return particiones;
           
                
        }
        
       return particiones;
    
    
    }

    @Override
    public double evaluateFitness(List<Note> melody, List<Chord> base, double start, double end) {
        this.melody = melody;
        this.base = base;
        this.start= start;
        this.end = end;
        double percentaje =calculateDifferenceSum();
         return percentaje;
    }

    @Override
    public List<Pair<Integer, Integer>> faultyPartition(List<Note> melody, List<Chord> base, double start, double end) {
        this.melody = melody;
        this.base = base;
        this.start= start;
        this.end = end;
        return this.calculateFaultyPartition();
      
    }

       private boolean satisfiesCondition(int difference){
      
        if(difference>diffNumber && comp.esMayor() || difference == diffNumber && comp.esIgual()
           || difference<diffNumber && comp.esMenor())
            return true;
        return false;
    }
    
   
    
}