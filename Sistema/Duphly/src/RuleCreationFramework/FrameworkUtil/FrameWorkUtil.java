/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.FrameworkUtil;

import DataDefinition.Note;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public class FrameWorkUtil {
    public static List<Note> copyMelody (List<Note> l){
        List<Note> newList = new ArrayList<Note>();
        for(Note n : l){
            Note newNote = new Note(n.getDuration(), n.getNote(), n.getOctave(),n.getAccident());
            newList.add(newNote);
        }
            
       return newList;
        
    }
    
     /**
     * retorna true si se luego de una tirada random de probaiblidad teoricamente normal 
     * sale como resultado un numero menor al de la probailidad de que se aplique, de forma que si tiro 100 veces deberia tener
     * 1 de cada 100 pega, entonces si es 1porciento la probaildad deberia salir 1 de cada 100 veces.
     * @return 
     */    
     public static boolean applyByProbability(double probability){
        double random = Math.random();
        if(random <= probability)
            return true;
        return false;
    }
     
     // se puede mejorar la busqueda. 
    public static boolean isInsidePartition(List<Pair<Double,Double>> partitions, Double place){
        for(Pair<Double,Double> p : partitions){
            if(place>= p.getKey() && place<=p.getValue())
                return true;
        }
        return false;
    }
}
