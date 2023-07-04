/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.BlockFilter;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ExcerptFilter.PositionInMusic;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public class CompleteBlockFilterRule extends DependentBlockFilterElement{
   
    Integer blockQuantity;
    BlockConditionSelector bcs;
    BlockPositionInMusic bpim;
    Double probabilityOfBeingSelected;
    Integer blockSelectionQuantity;
    Double blockPercentajes;
    boolean selectionComplement;
    
    /**
     * Parametros obligatorios : BlockQuantity que determina la cantidad de compases que se van a seleccionar
     * Block Condition selector que determina una condicion para seleccionar, si se quiere omitir, se puede utilzar la regla cantidad de notas mayor a 1 y con eso ya se omite
     * el resto se pueden pasar nulos. 
     * BlockPositionInMusic determina una cota sobre la cual se realiza la busqueda
     * probabilityofbeingSElected es una probabilidad entre 0 y 1 de seleccionar cada conjunto de compases.
     * BlockSelectionQuantity limita la cantidad de conjuntos de compases
     * BlockPercentajes limita a un porcentaje la cantidad de conjunto de compases.
     * 
     * @param blockQuantity
     * @param bcs
     * @param bpim
     * @param probabilityOfBeingSelected
     * @param blockSelectionQuantity
     * @param blockPercentajes 
     */
    public CompleteBlockFilterRule(Integer blockQuantity, BlockConditionSelector bcs, BlockPositionInMusic bpim, Double probabilityOfBeingSelected, Integer blockSelectionQuantity, Double blockPercentajes, boolean selectionComplement) {
        this.blockQuantity = blockQuantity;
        this.bcs = bcs;
        this.bpim = bpim;
        this.probabilityOfBeingSelected = probabilityOfBeingSelected!=null? probabilityOfBeingSelected/100 : null;
        this.blockSelectionQuantity = blockSelectionQuantity;
        this.blockPercentajes = blockPercentajes!=null? blockPercentajes/100 : null;
        this.selectionComplement = selectionComplement;
    }
    
    public List<Pair<Double, Double>> filter(List<Chord> base, List<Note> melody, List<Pair<Double, Double>> partitions) { // falto utilizar partitions!!!!!! grave!!
       Pair<Integer,Integer> boundaries= new Pair<>(new Integer(0), Util.GetBlockQuantity(melody));
       if(bpim!=null){
           boundaries = bpim.GetPosition(melody,this.blockQuantity);
           
       }
       Integer start = boundaries.getKey();
       Integer end = boundaries.getValue();
       
       List<Pair<Integer,Integer>> blockList = bcs.applySelector(melody,base,start,end, this.blockQuantity);
       blockList = this.limitBlockQuantity(blockList);     
        
       blockList = this.filterByProbability(blockList);
       List<Pair<Double, Double>> blocks =this.transformBlockToNoteTime(melody, blockList);
       partitions.addAll(blocks);
       return this.applyComplement(blocks,this.getNoteTimeForBlock(melody, start),this.getNoteTimeForBlock(melody, end));
    }
    
    private List<Pair<Integer,Integer>> filterByProbability(List<Pair<Integer,Integer>> resultPartitions){
        if(this.probabilityOfBeingSelected==null)
            return resultPartitions;
        List<Pair<Integer,Integer>> result = new ArrayList<>();
        for(Pair<Integer,Integer> partition : resultPartitions)
            if(FrameWorkUtil.applyByProbability(this.probabilityOfBeingSelected))
                result.add(partition);
        
        return result;
    }

  
    private List<Pair<Integer,Integer>> limitBlockQuantity(List<Pair<Integer,Integer>> partitionSelection){
        if(this.blockSelectionQuantity== null && this.blockPercentajes == null)
            return partitionSelection;
        List<Pair<Integer,Integer>> result = new ArrayList<>();
        int quantity = (int) (this.blockPercentajes == null? this.blockSelectionQuantity :partitionSelection.size() * this.blockPercentajes);
        for (int i = 0; i < quantity&&i<partitionSelection.size() ; i++) {
            result.add(partitionSelection.get(i));
        }
         return result;
      }
    
    private List<Pair<Double,Double>> transformBlockToNoteTime(List<Note> melody,List<Pair<Integer,Integer>> blockSelection){
        List<Pair<Double,Double>> solution = new ArrayList<>();
        for (Pair<Integer,Integer> p: blockSelection) {
            Pair<Double,Double> section = new Pair<>(Util.calculateTimeSumByPosition(melody, Util.getBlockPositionByNumber(melody,p.getKey())),Util.calculateTimeSumByPosition(melody, Util.getBlockPositionByNumber(melody,p.getValue())));
            solution.add(section);
        }
        
        return solution;
    }
    
    private Double getNoteTimeForBlock(List<Note> melody, Integer blockNumber){
        return Util.calculateTimeSumByPosition(melody, Util.getBlockPositionByNumber(melody,blockNumber));
    }
    private List<Pair<Double,Double>> applyComplement(List<Pair<Double,Double>> intervals, double start, double end){
        if(this.selectionComplement == false || intervals==null)
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
    
}
