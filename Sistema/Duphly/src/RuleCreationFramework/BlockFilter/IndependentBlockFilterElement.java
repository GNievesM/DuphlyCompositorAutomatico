/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.BlockFilter;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public abstract class IndependentBlockFilterElement {

    /**
     *
     * @param chords
     * @param melody
     * @return retorna la lista de pares de posiciones inicio fin dentro de la melodia que cumplen la condicion.
     */
    public abstract List<Pair<Double, Double>> Filter(List<Chord> chords, List<Note> melody); 
    
}
