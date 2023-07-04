/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public class ConcatRules {
    public static CreatedRule concatRule(CreatedRule r1, CreatedRule r2){
        return r2.concatRule(r1);    
    }
    public static Pair<List<Note>,List<Chord>> concatMelody(List<Note> m1, List<Chord> base1, List<Note> m2, List<Chord> base2){
        
        List<Note> result = new ArrayList<>();
        for(Note n:m1){
            Note copy = new Note();
            copy.copyNote(n);
            result.add(n);
        }   
        
        
        for(Note n:m2){
            Note copy = new Note();
            copy.copyNote(n);
            result.add(copy);
        }
        List<Chord> baseResult =new ArrayList<>();
        for(Chord c : base2){
            baseResult.add(c);
        }
        for(Chord c: baseResult){
            base1.add(c);
        }
        
        Pair<List<Note>,List<Chord>> solPair = new Pair<>(result,base1);
        
        return solPair;
    }
}
