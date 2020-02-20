/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework;

import DataDefinition.Chord;
import DataDefinition.Note;
import RuleCreationFramework.BlockFilter.BlockFilter;
import Style.AbstractStyle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gaston
 */
public class CreatedRule {
    
    List<BlockFilter> blockFilters;
    AbstractStyle style; 
    List<CreatedRule> concatedRules;
    
    public List<BlockFilter> getBlockFilters() {
        return blockFilters;
    }

    public void setBlockFilters(List<BlockFilter> blockFilters) {
        this.blockFilters = blockFilters;
    }  
    
    public void addNewFilter(BlockFilter b){
        this.blockFilters.add(b);
    }
    
    public List<Note> createMelody(List<Chord> base, List<Note> melody){
        for(CreatedRule r: concatedRules){
            melody = r.createMelody(base, melody);
        }
        for (BlockFilter b : this.blockFilters) {
            melody = b.applyFilter(base, melody,this.style);
        }
        return melody;
    }
    
    public CreatedRule(AbstractStyle style){
        this.blockFilters = new ArrayList<>();
        this.style = style;
        this.concatedRules = new ArrayList<>();
    }
    
    public CreatedRule concatRule(CreatedRule rule){
        concatedRules.add(rule);
        return this;
    } 
    
    
    
}
