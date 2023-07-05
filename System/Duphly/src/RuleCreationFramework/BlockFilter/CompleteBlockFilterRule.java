package RuleCreationFramework.BlockFilter;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ExcerptFilter.PositionInMusic;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class CompleteBlockFilterRule extends DependentBlockFilterElement {
    Integer blockQuantity;
    BlockConditionSelector bcs;
    BlockPositionInMusic bpim;
    Double probabilityOfBeingSelected;
    Integer blockSelectionQuantity;
    Double blockPercentajes;
    boolean selectionComplement;
    
    /**
     * Mandatory parameters:
     * - blockQuantity: Determines the number of measures to select.
     * - bcs: Block Condition Selector that determines a condition for selection. If omitted, the rule "number of notes greater than 1" can be used to skip it.
     * - bpim: BlockPositionInMusic determines a boundary for the search.
     * - probabilityOfBeingSelected: Probability (between 0 and 1) of selecting each set of measures.
     * - blockSelectionQuantity: Limits the number of sets of measures.
     * - blockPercentajes: Limits the number of sets of measures to a percentage.
     * 
     * @param blockQuantity
     * @param bcs
     * @param bpim
     * @param probabilityOfBeingSelected
     * @param blockSelectionQuantity
     * @param blockPercentajes
     * @param selectionComplement
     */
    public CompleteBlockFilterRule(Integer blockQuantity, BlockConditionSelector bcs, BlockPositionInMusic bpim, Double probabilityOfBeingSelected, Integer blockSelectionQuantity, Double blockPercentajes, boolean selectionComplement) {
        this.blockQuantity = blockQuantity;
        this.bcs = bcs;
        this.bpim = bpim;
        this.probabilityOfBeingSelected = probabilityOfBeingSelected != null ? probabilityOfBeingSelected / 100 : null;
        this.blockSelectionQuantity = blockSelectionQuantity;
        this.blockPercentajes = blockPercentajes != null ? blockPercentajes / 100 : null;
        this.selectionComplement = selectionComplement;
    }
    
    public List<Pair<Double, Double>> filter(List<Chord> base, List<Note> melody, List<Pair<Double, Double>> partitions) {
        Pair<Integer, Integer> boundaries = new Pair<>(0, Util.GetBlockQuantity(melody));
        if (bpim != null) {
            boundaries = bpim.GetPosition(melody, this.blockQuantity);
        }
        Integer start = boundaries.getKey();
        Integer end = boundaries.getValue();
       
        List<Pair<Integer, Integer>> blockList = bcs.applySelector(melody, base, start, end, this.blockQuantity);
        blockList = this.limitBlockQuantity(blockList);     
       
        blockList = this.filterByProbability(blockList);
        List<Pair<Double, Double>> blocks = this.transformBlockToNoteTime(melody, blockList);
        partitions.addAll(blocks);
        return this.applyComplement(blocks, this.getNoteTimeForBlock(melody, start), this.getNoteTimeForBlock(melody, end));
    }
    
    private List<Pair<Integer, Integer>> filterByProbability(List<Pair<Integer, Integer>> resultPartitions) {
        if (this.probabilityOfBeingSelected == null)
            return resultPartitions;
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        for (Pair<Integer, Integer> partition : resultPartitions) {
            if (FrameWorkUtil.applyByProbability(this.probabilityOfBeingSelected))
                result.add(partition);
        }
        return result;
    }

    private List<Pair<Integer, Integer>> limitBlockQuantity(List<Pair<Integer, Integer>> partitionSelection) {
        if (this.blockSelectionQuantity == null && this.blockPercentajes == null)
            return partitionSelection;
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        int quantity = (int) (this.blockPercentajes == null ? this.blockSelectionQuantity : partitionSelection.size() * this.blockPercentajes);
        for (int i = 0; i < quantity && i < partitionSelection.size(); i++) {
            result.add(partitionSelection.get(i));
        }
        return result;
    }
    
    private List<Pair<Double, Double>> transformBlockToNoteTime(List<Note> melody, List<Pair<Integer, Integer>> blockSelection) {
        List<Pair<Double, Double>> solution = new ArrayList<>();
        for (Pair<Integer, Integer> p : blockSelection) {
            Pair<Double, Double> section = new Pair<>(Util.calculateTimeSumByPosition(melody, Util.getBlockPositionByNumber(melody, p.getKey())), Util.calculateTimeSumByPosition(melody, Util.getBlockPositionByNumber(melody, p.getValue())));
            solution.add(section);
        }
        return solution;
    }
    
    private Double getNoteTimeForBlock(List<Note> melody, Integer blockNumber) {
        return Util.calculateTimeSumByPosition(melody, Util.getBlockPositionByNumber(melody, blockNumber));
    }
    
    private List<Pair<Double, Double>> applyComplement(List<Pair<Double, Double>> intervals, double start, double end) {
        if (!this.selectionComplement || intervals == null)
            return intervals;
        List<Pair<Double, Double>> complement = new ArrayList<>();
        if (intervals.get(0).getKey() != start) {
            complement.add(new Pair<>(start, intervals.get(0).getKey()));
        }
        double newPartitionStart = intervals.get(0).getValue();
        
        for (int i = 1; i < intervals.size(); i++) {
            Pair<Double, Double> complementary = new Pair<>(newPartitionStart, intervals.get(i).getKey());
            newPartitionStart = intervals.get(i).getValue();
            complement.add(complementary);
        }
        if (intervals.get(intervals.size() - 1).getValue() != end) {
            complement.add(new Pair<>(intervals.get(intervals.size() - 1).getValue(), end));
        }
        return complement;
    }
}
