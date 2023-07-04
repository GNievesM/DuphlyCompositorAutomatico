/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedConditionChecker;

import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationConditionChecker.*;
import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedCondition;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationCondition;
import java.util.List;
import EvolutionaryAlgorithm.IFitnessEvaluable;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public class GuaranteeSequence extends GuaranteedCondition implements IFitnessEvaluable{
    
    boolean asc;
    boolean allowRepeat;
    Integer number=null;
    CheckSequency c;
    List<Note> melody;
    List<Chord> base;
    double start;
    double end;

    public GuaranteeSequence(boolean asc, boolean allowRepeat) {
        this.asc = asc;
        this.allowRepeat = allowRepeat;
        c = new CheckSequency(true,asc,5,allowRepeat); 
    }
      public GuaranteeSequence(boolean asc, boolean allowRepeat,Integer length) {
        this.asc = asc;
        this.allowRepeat = allowRepeat;
        this.number = length;
        c = new CheckSequency(true,asc,number,allowRepeat);
    }
    
    private boolean apliesConditions(Note n1, Note n2){
       if(asc && this.allowRepeat && Util.IsHIgherOrEqualPitchThan(n2,n1) ||
            asc && !this.allowRepeat && Util.IsHIgherPitchThan(n2, n1) ||
            !asc && this.allowRepeat && Util.IsLowerOrEqualPitchThan(n2, n1) ||
            !asc && !this.allowRepeat && Util.IsHIgherPitchThan(n1, n2))
            return true;
       return false;
    
    }
    
        

    @Override
    public boolean checkCondition(List<Chord> base, List<Note> melody, Double start, Double end) {
        if(c==null) c = new CheckSequency(true,asc,Util.calculateNotePositionInListByTimeSum(melody, start)-Util.calculateNotePositionInListByTimeSum(melody, end),allowRepeat); 
        return c.checkCondition(base, melody, start, end);
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
    
    protected double calculateDifferenceSum(){
        int notePositionStart = Util.calculateNotePositionInListByTimeSum(melody, start);
        int notePositionEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        int notesAligned =0;
        double correctPercentaje = ((double)100/ (notePositionEnd-notePositionStart)-1);
        double differencePercentaje=0;
        int maxAligned = 0;
        for (int i = notePositionStart; i < notePositionEnd-1; i++) {
            Note n1 = melody.get(i);
            Note n2 = melody.get(i+1);
            if(this.apliesConditions(n1, n2)){
                notesAligned++; 
                if(notesAligned > maxAligned)
                    maxAligned = notesAligned;
            }else{
                notesAligned = 0;
                differencePercentaje += correctPercentaje;
            }
            
            if(this.number != null && notesAligned ==this.number || notesAligned == notePositionEnd-notePositionStart-1)
                return 100;
            
        }
        
        return differencePercentaje+(double)(maxAligned/1000);
    }
    protected List<Pair<Integer,Integer>> calculateFaultyPartition(){
        int notePositionStart = Util.calculateNotePositionInListByTimeSum(melody, start);
        int notePositionEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        List<Pair<Integer,Integer>> particiones  = new ArrayList<> ();
        Integer partitionStart=notePositionStart; // lo cambie ded 0 para que tome como comienzo la primer nota posible.
        boolean insidePartition = true;

        for (int i = notePositionStart; i < notePositionEnd-1; i++) {
            Note n1 = melody.get(i);
            Note n2 = melody.get(i+1);    
            
            if(this.apliesConditions(n1, n2)){
                if(insidePartition){
                    Pair<Integer,Integer> faulty;
                    faulty = new Pair<Integer,Integer>(partitionStart, (i==(notePositionEnd-2)? new Integer(i):new Integer(i+1)));
                    particiones.add(faulty);
                    insidePartition = false;
                }
            }else{
                if(!insidePartition){
                        partitionStart = i == notePositionStart? notePositionStart : i-1;
                        insidePartition=true;
                }
            }
                
        }
        
        
        return particiones;
    
    
    }
    
}