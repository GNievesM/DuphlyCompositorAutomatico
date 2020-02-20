/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.List;
import Style.AbstractStyle;


/**
 *
 * @author Gaston
 */
public abstract class Modification {

    /**
     *
     * @param base
     * @param Melody
     * @param start
     * @param end
     */
    public abstract List<Note> Modify(List<Chord> base, List<Note> Melody, Double start, Double end, AbstractStyle style);
    
}
