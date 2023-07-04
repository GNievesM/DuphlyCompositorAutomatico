/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ExcerptFilter;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.BlockFilter.DependentBlockFilterElement;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */




/**
 * @author Gaston
 */
public class CompleteExcerptFilterRule extends DependentExcerptFilterElement{
    PositionInMusic pin; 
    Double selectionProbability;
    boolean exclusive; 
    Integer excerptQuantity;
    Double excerptsPercentaje;
    Integer position;
    Integer quantityPreviousNotes;
    Integer quantityForwardNotes;
    ExcerptConditionSelector condition;
    int excerptLength;
    double melodyStart;
    double melodyEnd;
    boolean complement;
    
    public CompleteExcerptFilterRule( PositionInMusic pin, Double selectionProbability, boolean exclusive, Integer excerptQuantity, Double excerptsPercentaje, Integer position, Integer quantityPreviousNotes, Integer quantityForwardNotes, ExcerptConditionSelector condition, int excerptLength, boolean complement) {
        this.pin = pin;
        this.selectionProbability = selectionProbability!=null? selectionProbability/100 : null;
        this.exclusive = exclusive;
        this.excerptQuantity = excerptQuantity;
        this.excerptsPercentaje = excerptsPercentaje!=null? excerptsPercentaje/100 : null;
        this.position = position;
        this.quantityPreviousNotes = quantityPreviousNotes;
        this.quantityForwardNotes = quantityForwardNotes;
        this.condition = condition;
        this.excerptLength = excerptLength;
        this.complement=complement;
    }
    
    @Override
     public List<Pair<Double, Double>> Filter(List<Chord> base, List<Note> melody, double melodyStart, double melodyEnd, List<Pair<Double, Double>> excerptsList) {
       this.melodyStart = melodyStart;
       this.melodyEnd= melodyEnd;
       Pair<Double,Double> boundaries = new Pair<>(melodyStart,melodyEnd);
       if(pin!= null)
           boundaries = pin.GetPosition(melody,melodyStart,melodyEnd,this.excerptLength);
       double start = boundaries.getKey();
       double end = boundaries.getValue();
       List<Pair<Double,Double>> excerptList=null;
       
       if(condition != null) excerptList = condition.applySelector(melody,base,start,end,this.excerptLength,exclusive?excerptsList:null);
       else this.selectBySize(excerptList, melody);
       
       excerptList = this.limitExcerptSize(excerptList);     
       this.addForwardAndPreviousNotes(excerptList,melody);         
       this.filterByProbability(excerptList);
       excerptsList.addAll(excerptList);
       return this.applyComplement(excerptList,melodyStart,melodyEnd);
    }
    
    private List<Pair<Double,Double>> filterByProbability(List<Pair<Double,Double>> resultPartitions){
        if(selectionProbability==null)
            return resultPartitions;
        List<Pair<Double,Double>> result = new ArrayList<>();
        for(Pair<Double,Double> partition : resultPartitions)
            if(FrameWorkUtil.applyByProbability(this.selectionProbability))
                result.add(partition);
       
        if(position!=null){ // si no es nulo es por que se quiere devolver una detemrinada posicion dentro de los resultados
           List<Pair<Double,Double>> pos = new ArrayList<>();
           if(result.size()>position);
           pos.add(result.get(position));
           return pos;
        }
        return result;
    }
    // si hay exclusividad chequea que la particion no se pise, sino solo suma
    private void addForwardAndPreviousNotes(List<Pair<Double,Double>> partitions, List<Note> melody){
        if(this.quantityForwardNotes == null && this.quantityPreviousNotes == null || this.quantityForwardNotes == 0 && this.quantityPreviousNotes==0 )
            return;
        //List<Pair<Double,Double>> solution = new ArrayList<>();
       // for(Pair<Double,Double> pair : partitions){
       for(int i =0; i<partitions.size();i++){
            if(this.quantityForwardNotes>0)  addForwardNotesToPartition(i,partitions, melody);
            if(this.quantityPreviousNotes>0) addPreviousNotesToPartition(i,partitions, melody);        
        }
        
        
       
    }
    // importante que se haga el set a la particion
    // esto se puede mejorar ordenando por el comienzo de las particiones y haciendo una busqueda por bi particion tal vez
    private void addForwardNotesToPartition(int partitionIndex, List<Pair<Double,Double>> partitionSelection, List<Note> melody){
        //int partitionIndex = partitionSelection.lastIndexOf(partition);
        Pair<Double,Double> partition = partitionSelection.get(partitionIndex);
        int endPosition = Util.calculateNotePositionInListByTimeSum(melody, partition.getValue());
        if(endPosition + this.quantityForwardNotes >= melody.size()) return;
            
        
        double partitionNewEnd = Util.calculateTimeSumByPosition(melody,(endPosition+this.quantityForwardNotes>this.melodyEnd)?endPosition :endPosition+this.quantityForwardNotes);
        Pair<Double,Double> addedPartition = new Pair<>(partition.getKey(),partitionNewEnd);
        if(!FrameWorkUtil.isInsidePartition(partitionSelection, partitionNewEnd))
            partitionSelection.set(partitionIndex, addedPartition);
        
    }
    private void addPreviousNotesToPartition(int partitionIndex, List<Pair<Double,Double>> partitionSelection,List<Note> melody){
       // int partitionIndex = partitionSelection.lastIndexOf(partition);
        Pair<Double,Double> partition = partitionSelection.get(partitionIndex);
        int startPosition = Util.calculateNotePositionInListByTimeSum(melody, partition.getKey());
        if(startPosition - this.quantityPreviousNotes < 0) return;
            
        double partitionNewStart = Util.calculateTimeSumByPosition(melody,(startPosition-this.quantityPreviousNotes<this.melodyStart)?startPosition: startPosition-this.quantityPreviousNotes);
        Pair<Double,Double> substractedPartition = new Pair<>(partitionNewStart,partition.getValue());
        if(!FrameWorkUtil.isInsidePartition(partitionSelection, partitionNewStart))
            partitionSelection.set(partitionIndex, substractedPartition); // cayo por fuera de indice revisar posibles casos.
    }
    
  
    private List<Pair<Double,Double>> limitExcerptSize(List<Pair<Double,Double>> partitionSelection){
        if(this.excerptQuantity== null && this.excerptsPercentaje == null)
            return partitionSelection;
        List<Pair<Double,Double>> result = new ArrayList<>();
        int quantity = (int) (this.excerptsPercentaje == null? this.excerptQuantity :partitionSelection.size() * this.excerptsPercentaje );
        for (int i = 0; i < quantity && i<partitionSelection.size(); i++) {
            result.add(partitionSelection.get(i));
        }
         return result;
      }


    private List<Pair<Double,Double>> applyComplement(List<Pair<Double,Double>> intervals, double start, double end){
        if(this.complement == false || intervals == null)
             return intervals;
     
        List<Pair<Double,Double>> complement= new ArrayList<Pair<Double,Double>>();
        if(intervals.get(0).getKey() != start){
            complement.add(new Pair<>(start,intervals.get(0).getKey()));
        }
        double newPartitionStart=intervals.get(0).getValue();
        
        for (int i = 1; i < intervals.size(); i++) {
      
            Pair<Double,Double> complementary = new Pair<>(newPartitionStart,intervals.get(i).getKey());
            newPartitionStart = intervals.get(i).getValue();
            complement.add(complementary);
        }
        if(intervals.get(intervals.size()-1).getValue() != end)
            complement.add(new Pair<>(intervals.get(intervals.size()-1).getValue(),end));
        return complement;
    }

    private List<Pair<Double,Double>> selectBySize(List<Pair<Double,Double>> excerptList,List<Note> melody){
        List<Pair<Double,Double>> sol = new ArrayList<>();
        if(this.exclusive == true){
            for(Pair<Double,Double> p:excerptList){
                int posStart = Util.calculateNotePositionInListByTimeSum(melody, p.getKey());
                int posEnd = Util.calculateNotePositionInListByTimeSum(melody, p.getValue());
                for (int i = posStart; i < posEnd; i+= this.excerptLength) {
                    if(i+excerptLength < posEnd)
                        sol.add(new Pair<>(Util.calculateTimeSumByPosition(melody, i), Util.calculateTimeSumByPosition(melody, i+this.excerptLength)));
                }
            
            }
        
        
        }
            
        return sol;
    }
    
}
