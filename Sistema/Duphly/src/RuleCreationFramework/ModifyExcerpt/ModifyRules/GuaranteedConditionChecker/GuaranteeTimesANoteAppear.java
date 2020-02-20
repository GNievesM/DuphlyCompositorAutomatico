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
public class GuaranteeTimesANoteAppear extends GuaranteedCondition implements IFitnessEvaluable{
    CheckTimesANoteAppear c;
    Note note;
    Integer grade;
    boolean absolut;
    int numberOfApparitions;
    Comparador cmp;
    
    List<Note> melody;
    List<Chord> base;
    double start;
    double end;
    
     public GuaranteeTimesANoteAppear(Note note, boolean absolut, int numberOfApparitions, Comparador comp){
         this.note = note;
         this.absolut = absolut;
         this.numberOfApparitions = numberOfApparitions;
         this.cmp = comp;
         this.grade = null;
         c = new CheckTimesANoteAppear (note, absolut, numberOfApparitions, comp);
    }
    
    public GuaranteeTimesANoteAppear(Integer grade, int numberOfApparitions, Comparador comp){
        this.note = null;
        this.absolut = false;
        this.numberOfApparitions = numberOfApparitions;
        this.cmp = comp;
        this.grade = grade;
        c = new CheckTimesANoteAppear (grade, numberOfApparitions, comp);
    }


    @Override
    public boolean checkCondition(List<Chord> base, List<Note> melody, Double start, Double end) {
        return c.checkCondition(base, melody, start, end);
    }

     private boolean isTheNote(Note particularNote, Chord c){
        if(this.grade != null){
            return Util.calculateNoteGradeForChord(c, particularNote)==this.grade;
        }
        if(this.absolut && particularNote.getNote() == this.note.getNote() && particularNote.getOctave() ==this.note.getOctave()
               || !this.absolut && particularNote.getNote() == this.note.getNote()  )
            return true;
        return false;
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

    
    protected double calculateDifferenceSum(){
        int notePositionStart = Util.calculateNotePositionInListByTimeSum(melody, start);
        int notePositionEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        int heightSum = 0;
        int appeareanceQuantity=0;
        double differencePercentaje=0;
        for (int i = notePositionStart; i < notePositionEnd; i++) {
            Note toCompare = melody.get(i);
            if(this.isTheNote(toCompare,Util.LookForBaseChord(base, melody, i)))
                appeareanceQuantity ++;
            
            heightSum += Util.getExpandedFormNote(toCompare.getNote(),toCompare.getOctave()); // hay que arreglar para que esta cuenta tienda a tirar la melodia al medio y no hacia abajo.

        }
        
        if(appeareanceQuantity> this.numberOfApparitions && this.cmp.esMayor() || appeareanceQuantity<this.numberOfApparitions&&this.cmp.esMenor()
                || this.numberOfApparitions== appeareanceQuantity && this.cmp.esIgual())
            differencePercentaje = 100;
        else{
            if(appeareanceQuantity<this.numberOfApparitions)
                differencePercentaje= 100* ((double)appeareanceQuantity/(double)this.numberOfApparitions);
            else if(appeareanceQuantity>this.numberOfApparitions)
                differencePercentaje = 100* ((double)this.numberOfApparitions /(double)appeareanceQuantity);
            else differencePercentaje = appeareanceQuantity/appeareanceQuantity+1;
        
        }
        
        differencePercentaje -= (heightSum / ((notePositionEnd-notePositionStart)+1))/1000; // le resto el promedio, a promedio mas alto peor fitness. 
        
        return differencePercentaje;
    }
       
    
    protected List<Pair<Integer,Integer>> calculateFaultyPartition(){
        int notePositionStart = Util.calculateNotePositionInListByTimeSum(melody, start);
        int notePositionEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        List<Pair<Integer,Integer>> particiones  = new ArrayList<> ();
        Integer partitionStart=notePositionStart; // lo cambie ded 0 para que tome como comienzo la primer nota posible.
        boolean insidePartition = false;

        for (int i = notePositionStart; i < notePositionEnd-1; i++) {
            Note n = melody.get(i);
                 
            if(this.isTheNote(n,Util.LookForBaseChord(base, melody, i))){
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
    @Override
    public List<Pair<Integer, Integer>> faultyPartition(List<Note> melody, List<Chord> base, double start, double end) {
        this.melody = melody;
        this.base = base;
        this.start= start;
        this.end = end;
        return this.calculateFaultyPartition();
    }
    
}