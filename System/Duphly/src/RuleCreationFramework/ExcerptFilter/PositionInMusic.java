/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ExcerptFilter;

import DataDefinition.Note;
import ImprovisationRules.Util;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public class PositionInMusic {
    boolean isPercentaje;
    boolean aleatorio;
    boolean isRelativePosition;
    double specificNumber;
    boolean greaterThan;
    boolean lesserThan;
    boolean start;
    boolean middle;
    boolean end;
    int relativePosition;
    /**
     * 
     * @param isPercentaje
     * @param specificNumber 
     * El porcentaje tiene que ir entre 0 y 1
     */
    public PositionInMusic(boolean isPercentaje, double specificNumber) {
        this.isPercentaje = isPercentaje;
        this.specificNumber = specificNumber;
    }

    public PositionInMusic(boolean aleatorio) {
        this.aleatorio = aleatorio;
    }

    public PositionInMusic(int relativePosition, boolean greaterThan) {
        this.relativePosition = relativePosition;
        this.greaterThan = greaterThan;
        this.lesserThan = !greaterThan;
    }

    public PositionInMusic(boolean start, boolean middle, boolean end) {
        this.start = start;
        this.middle = middle;
        this.end = end;
    }

    public Pair<Double, Double> GetPosition(List<Note> melody, double melodyStart, double melodyEnd ,int length) {
       Pair<Double,Double> position=null;
        if(this.isPercentaje)
            position = this.GetPositionByPercentaje(melody, melodyStart, melodyEnd,length);
        if(this.aleatorio)
            position = this.GetRandomPosition(melody, melodyStart, melodyEnd, length);
        if(this.start || this.middle || this.end)
            position = this.GetPositionByRelativePosition(melody, melodyStart, melodyEnd,length);
        if(this.greaterThan || this.lesserThan)
            position = this.GetPositionByNumber(melody, melodyStart, melodyEnd, length);
        return position;
    }
    
    
    private Pair<Double,Double> GetPositionByPercentaje(List<Note> melody, double melodyStart, double melodyEnd,int length){
        int startPosition = Util.calculateNotePositionInListByTimeSum(melody, melodyStart);
        int endPosition = Util.calculateNotePositionInListByTimeSum(melody, melodyEnd);
        int noteQuantity = endPosition- startPosition;
        
        int percentajePosition = (int) Math.floor(noteQuantity * this.specificNumber /100);
        int partitionPositionStart=-1;
        int partitionPositionEnd=-1;
        partitionPositionStart = percentajePosition - length/2 >= startPosition ? percentajePosition-length/2 :percentajePosition;
        partitionPositionEnd = percentajePosition + length/2 >= endPosition ? percentajePosition+length/2 :percentajePosition;
        if(partitionPositionStart == percentajePosition && partitionPositionEnd == percentajePosition){
           partitionPositionStart = startPosition;
           partitionPositionEnd = endPosition;
        }
        double start = Util.calculateTimeSumByPosition(melody, partitionPositionStart);
        double end = Util.calculateTimeSumByPosition(melody, partitionPositionEnd);
        Pair<Double,Double> solution = new Pair<>(start,end);
        return solution;
    }
    
    private Pair<Double,Double> GetPositionByNumber(List<Note> melody, double melodyStart, double melodyEnd,int length){
        int startPosition = Util.calculateNotePositionInListByTimeSum(melody, melodyStart);
        int endPosition = Util.calculateNotePositionInListByTimeSum(melody, melodyEnd);
        int partitionPositionStart=-1;
        int partitionPositionEnd=-1;
        if(this.greaterThan==true){
            partitionPositionStart = startPosition+this.relativePosition>endPosition ? startPosition: startPosition+this.relativePosition;
            partitionPositionEnd =  (startPosition+this.relativePosition+length > endPosition ? endPosition :startPosition+this.relativePosition+length);
        }
        double start = Util.calculateTimeSumByPosition(melody, partitionPositionStart);
        double end = Util.calculateTimeSumByPosition(melody, partitionPositionEnd);
        Pair<Double,Double> solution = new Pair<>(start,end);
        return solution;
        
    }
    private Pair<Double,Double> GetPositionByRelativePosition(List<Note> melody, double melodyStart, double melodyEnd,int length){
        if(this.middle==true) this.specificNumber = 0.50;
        if(this.start==true) this.specificNumber = 0;
        if(this.end == true) this.specificNumber = 0.80;
        return this.GetPositionByPercentaje(melody, melodyStart, melodyEnd, length);
    
    }
    private Pair<Double,Double> GetRandomPosition(List<Note> melody, double melodyStart, double melodyEnd,int length){
        this.specificNumber = Math.random();
        return this.GetPositionByPercentaje(melody, melodyStart, melodyEnd, length);
    }
}
