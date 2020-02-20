/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ExcerptFilter;

import DataDefinition.Chord;
import DataDefinition.Note;
import RuleCreationFramework.FiltroFormaCompases;
import RuleCreationFramework.FiltroPorcentaje;
import RuleCreationFramework.FiltroUbicacion;
import RuleCreationFramework.ModifyExcerpt.ModifyExcerpt;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import Style.AbstractStyle;

/**
 *
 * @author Gaston
 */
public class ExcerptFilter {
    List<ModifyExcerpt> modify;
    List<IndependentExcerptFilterElement> inFilters;
    List<DependentExcerptFilterElement> deFilter;
    
    public List<Note> applyFilter(List<Chord> base, List<Note> melody, Double start, Double end, AbstractStyle style){
       List<Note> modifiedMelody = melody;
       List<Pair<Double,Double>> filtered = applyFilters(base,modifiedMelody,start,end);
       for(ModifyExcerpt me:modify){
           for (Pair<Double,Double> par: filtered) {
               modifiedMelody = me.Modify(base, melody,par.getKey(), par.getValue(), style);
           }
       }
       return modifiedMelody;
    }
    
    private List<Pair<Double,Double>>  applyFilters(List<Chord> base, List<Note> melody, Double start,Double end){
        List<Pair<Double,Double>> filteredExcerpt = new ArrayList<>(); 
        for(IndependentExcerptFilterElement filter: inFilters){
            filteredExcerpt.addAll(filter.Filter(base, melody, start, end));
        }
        for(DependentExcerptFilterElement df: deFilter){
            df.Filter(base, melody,start,end, filteredExcerpt);
        }
        if(inFilters.isEmpty() && deFilter.isEmpty()){
            filteredExcerpt.add(new Pair<Double,Double>(start,end));
        }
        return filteredExcerpt;
    }
    
    public void addDependentFilter(DependentExcerptFilterElement d){
        this.deFilter.add(d);
    }
 /*   public void addIndependentFilter(IndependentExcerptFilterElement i){
        this.inFilters.add(i);
    }*/
    public void addModification(ModifyExcerpt f){
        this.modify.add(f);
    }
        
    public ExcerptFilter(){
        this.deFilter = new ArrayList<>();
        this.inFilters = new ArrayList<>();
        this.modify = new ArrayList<>();
    }
    
}
