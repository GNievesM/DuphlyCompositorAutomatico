/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.BlockFilter;

import DataDefinition.Note;
import RuleCreationFramework.ExcerptFilter.ExcerptFilter;
import DataDefinition.Chord;
import ImprovisationRules.Util;
import java.util.ArrayList;

import java.util.List;
import javafx.util.Pair;
import Style.AbstractStyle;

/**
 *
 * @author Gaston
 */
public class BlockFilter {
    List<DependentBlockFilterElement> deFilters;
    //List<IndependentBlockFilterElement> inFilters;
    
    List<ExcerptFilter> excerptFilter;
        /**
     *
     * @param cantidadCompases
     * @return retorna la lista de pares de posiciones inicio fin dentro de la melodia que cumplen la condicion.
     */
    public List<Note> applyFilter(List<Chord> base, List<Note> melody, AbstractStyle style){
        List<Note> result = melody;
        List<Pair<Double,Double>> filteringResult = this.applyFilters(base, result);
        for (ExcerptFilter e : excerptFilter){
            
            for (Pair<Double,Double> pair:filteringResult) {
               // result = e.applyFilter(base, result, pair.getKey(), pair.getValue(), style);
                result = e.applyFilter(base, result, pair.getKey(), pair.getValue(), style);
            }
        }
        return result;        
    }
   // aplica los filtros y al resultado le aplica la lista de subfiltros

    private List<Pair<Double,Double>> applyFilters(List<Chord> base, List<Note> melody){
        List<Pair<Double,Double>> result = new ArrayList<>();
      /*  for (IndependentBlockFilterElement f : this.inFilters) {
            result.addAll(f.Filter(base, melody));
        }*/
        for(DependentBlockFilterElement fd:this.deFilters){
            result = fd.filter(base, melody, result);
        }
        if(/*this.inFilters.isEmpty() &&*/ this.deFilters.isEmpty())
            result.add(new Pair<>(0.0,Util.getMelodyTotalLength(melody))); 
        return result;
    }
    
    public void addDependentFilter(DependentBlockFilterElement d){
        this.deFilters.add(d);
    }
   /*public void addIndependentFilter(IndependentBlockFilterElement i){
        this.inFilters.add(i);
    }*/
    public void addExcerptFilter(ExcerptFilter f){
        this.excerptFilter.add(f);
    }
        
    public BlockFilter(){
        this.deFilters = new ArrayList<>();
      //  this.inFilters = new ArrayList<>();
        this.excerptFilter = new ArrayList<>();
    }
}


/*
    Cosas sin resolver aun. 
Como manejar las posiciones, las posiciones de las notas no funcionan, hay que usar la suma de los tiempos, por uqe la cantidad de notas van a variar con las modificaciones.
*/