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
public class GuaranteeIntervalBetweenNotes extends GuaranteedCondition {
    IntervalBetweenNotes d;
    List<Chord> base;
    List<Note> melody;
    Double start;
    Double end;
    Comparador comp;
    int diffNumber; 
    SpecificModification sm;
    
     public GuaranteeIntervalBetweenNotes(Comparador comp, int diffNumber){
        this.comp = comp;
        this.diffNumber = diffNumber;
        d = new IntervalBetweenNotes (comp,diffNumber);
     //   this.sm = sm;
    }

    public GuaranteeIntervalBetweenNotes(List<Chord> base, List<Note> melody, Double start, Double end, Comparador comp, int diffNumber) {
     
        this.base = base;
        this.melody = melody;
        this.start = start;
        this.end = end;
        this.comp = comp;
        this.diffNumber = diffNumber;
       // this.sm = sm;
    }
    
 

    /*@Override
    public int calculateChunkFitness(List<Chord> base, List<Note> melody, Double start, Double end) {
        return 1;//cuentita.
    }*/

    @Override
    public boolean checkCondition(List<Chord> base, List<Note> melody, Double start, Double end) {
        this.base= base;
        this.melody= melody;
        this.start = start;
        this.end = end;
        return d.checkCondition(base, melody, start, end);
    }


    
   
    protected double calculateDifferenceSum(){
        int notePositionStart = Util.calculateNotePositionInListByTimeSum(melody, start);
        int notePositionEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        int heightSum = 0;
        double correctPercentaje = ((double)100/ (notePositionEnd-notePositionStart-1));
        double differencePercentaje=0;
        for (int i = notePositionStart; i < notePositionEnd-1; i++) {
            Note toCompare = melody.get(i);
            Note toCompareEnd = melody.get(i+1);
            //int difference =-(toCompare.getNote()+(toCompare.getOctave()*12)) + (toCompareEnd.getNote()+toCompareEnd.getOctave()*12; 
            int difference = Math.abs(Util.getExpandedFormNote(toCompareEnd.getNote(),toCompareEnd.getOctave())-Util.getExpandedFormNote(toCompare.getNote(),toCompare.getOctave()));
            if(comp.esIgual() && difference == this.diffNumber || comp.esMayor() && difference > this.diffNumber || comp.esMenor() && difference < this.diffNumber)
                differencePercentaje += correctPercentaje;
            
            heightSum += Util.getExpandedFormNote(toCompare.getNote(),toCompare.getOctave()); // hay que arreglar para que esta cuenta tienda a tirar la melodia al medio y no hacia abajo.

        }
        differencePercentaje -= (heightSum / ((notePositionEnd-notePositionStart)+1))/1000; // le resto el promedio, a promedio mas alto peor fitness. 
        
        return differencePercentaje;
    
    
    }
    
        protected List<Pair<Integer,Integer>> calculateFaultyPartition(){
        int notePositionStart = Util.calculateNotePositionInListByTimeSum(melody, start);
        int notePositionEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        List<Pair<Integer,Integer>> particiones  = new ArrayList<> ();
        Integer partitionStart=notePositionStart;
        boolean insidePartition = false;

        for (int i = notePositionStart; i < notePositionEnd-1; i++) {
            Note toCompare = melody.get(i);
            Note toCompareEnd = melody.get(i+1);
            int difference = Math.abs(Util.getExpandedFormNote(toCompareEnd.getNote(),toCompareEnd.getOctave())-Util.getExpandedFormNote(toCompare.getNote(),toCompare.getOctave()));
                 
            if(comp.esIgual() && difference == this.diffNumber || comp.esMayor() && difference > this.diffNumber || comp.esMenor() && difference < this.diffNumber){
                if(insidePartition){
                    Pair<Integer,Integer> faulty;
                    faulty = new Pair<Integer,Integer>(partitionStart, (i==(notePositionEnd-2)? new Integer(i):new Integer(i+1)));
                   // faulty = new Pair<Integer,Integer>(partitionStart, (i==(notePositionEnd-2)? new Integer(i):new Integer(i)));
                    particiones.add(faulty);
                    insidePartition = false;
                }
            }else{
                if(!insidePartition){
                        partitionStart = i == notePositionStart? notePositionStart : i-1;
                       // partitionStart = i == 0? 0 : i;
                        insidePartition=true;
                }
            }
                
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

    
   
    
}