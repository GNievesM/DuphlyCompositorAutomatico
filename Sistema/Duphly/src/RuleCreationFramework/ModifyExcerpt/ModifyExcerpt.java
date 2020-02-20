/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.ArrayList;
import java.util.List;
import Style.AbstractStyle;

/**
 *
 * @author Gaston
 */
public class ModifyExcerpt {
    List<Modification> modifications;
    public List<Note> Modify(List<Chord> base, List<Note> melody, Double start, Double end, AbstractStyle style){
        List<Note> modified = melody;
        for(Modification m : modifications){
            modified = m.Modify(base, modified, start, end, style);
        }
        return modified;
    }
    public ModifyExcerpt(){
        this.modifications = new ArrayList<>(); 
    }
    public void addModification(Modification m){
        this.modifications.add(m);
        
    }
}
