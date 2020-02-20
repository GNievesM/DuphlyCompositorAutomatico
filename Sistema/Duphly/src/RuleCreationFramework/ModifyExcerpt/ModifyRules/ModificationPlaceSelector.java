/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules;

import DataDefinition.Note;
import ImprovisationRules.Util;
import java.util.List;

/**
 *
 * @author Gaston
 */
public class ModificationPlaceSelector {
    boolean relativePosition =false;
    int position = 0;
    boolean start = false;
    boolean middle = false;
    boolean end = false;
    // start determina si se empieza desde el incio del tramo
    // midlle determina si se empieza del medio del tramo
    // end determina si se arranca del final del tramo (3/4)
    // posicion relativa determina x notas ddespues del inicio estipulado.
    public ModificationPlaceSelector(boolean start,  boolean middle, boolean end,int relativePosition){
        this.relativePosition = relativePosition!=0;
        this.position = relativePosition;
        this.start = start;
        this.middle = middle;
        this.end = end;        
    }
    
    // determina el punto en el que se va a empezar la modificacion basado en los parametros del constructor
    // en el caso del principio simplemente empieza del mismo, 
    // en el caso de que haya un comienzo estipulado arranca desde ese punto
    // en el caso que se pida el medio, calcula la mitad aproximadamente como inicio
    // en el caso que se quiera empezar desde el final calcula 75 porciento como inicio
    // sino usa la posicion relativa desde el comienzo (cuenta notas a partir del inicio)
    public double startPosition(List<Note> melody, double start, double end){
        if(this.start)
            return start;
        if(this.middle){
            double approxMiddle = (end - start) / 2  +  start;
            return Util.calculateTimeSumByPosition(melody,Util.calculateNotePositionInListByTimeSum(melody, approxMiddle));
        }
        if(this.end){
            double approxMiddle = ((end - start) / 4)*3  +  start;
            return Util.calculateTimeSumByPosition(melody,Util.calculateNotePositionInListByTimeSum(melody, approxMiddle));
        }
        return Util.calculateTimeSumByPosition(melody,Util.calculateNotePositionInListByTimeSum(melody, start)+this.position);
    }
}
