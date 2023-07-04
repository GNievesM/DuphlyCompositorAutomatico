/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.List;

/**
 *
 * @author Gaston
 */
public abstract class ModificationCondition {
    /**
     * Checkeo la condicion sobre todo el pasaje, desde el principio al fin
     * @param base
     * @param melody
     * @param start
     * @param end
     * @return 
     */
    public abstract boolean checkCondition(List<Chord> base, List<Note> melody, Double start, Double end);
    /**
     * retorna las posiciones de las notas que se encuentran fuera de lugar segun la regla para luego ser modificadas.
     * @param base
     * @param melody
     * @param start
     * @param end
     * @return 
     */
    public abstract List<Double> listFaultyPositions(List<Chord> base, List<Note> melody, Double start, Double end);
}
