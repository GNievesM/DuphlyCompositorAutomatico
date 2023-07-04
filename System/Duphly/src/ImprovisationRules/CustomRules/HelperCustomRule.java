/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImprovisationRules.CustomRules;

import DataDefinition.Note;
import RuleCreationFramework.BlockFilter.BlockPositionInMusic;
import RuleCreationFramework.ExcerptFilter.PositionInMusic;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationPlaceSelector;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gaston
 */
public class HelperCustomRule {
    public static Comparador ComparadorMayor(){
        return new Comparador(1);
    }
    public static Comparador ComparadorMenor(){
        return new Comparador(-1);
    }
    public static Comparador ComparadorIgual(){
        return new Comparador(0);
    }
    public static BlockPositionInMusic CompasPosicionComienzo(){
        return new BlockPositionInMusic(true, false, false);
    } 
    public static BlockPositionInMusic CompasPosicionMedio(){
        return new BlockPositionInMusic(false,true,false);
    }
    public static BlockPositionInMusic CompasPosicionFin(){
        return new BlockPositionInMusic(false,false,true);
    }
    public static BlockPositionInMusic CompasPosicionRelativaMayor(int numero){
        return new BlockPositionInMusic(numero, true);
    }
    public static BlockPositionInMusic CompasPosicionRelativaMenor(int numero){
        return new BlockPositionInMusic(numero, false);
    }
    public static BlockPositionInMusic CompasPosicionPorcentaje(int numero){
        return new BlockPositionInMusic(true, numero);
    }    
    public static BlockPositionInMusic CompasPosicionAleatoria(){
        return new BlockPositionInMusic(true);
    }
    public static PositionInMusic PasajePosicionComienzo(){
        return new PositionInMusic(true, false, false);
    }
    public static PositionInMusic PasajePosicionMedio(){
        return new PositionInMusic(false, true, false);
    }
    public static PositionInMusic PasajePosicionFin(){
        return new PositionInMusic(false, false, true);
    }
    public static PositionInMusic PasajePosicionRelativaMayor(int numero){
        return new PositionInMusic(numero,true);
    }
    public static PositionInMusic PasajePosicionRelativaMenor(int numero){
        return new PositionInMusic(numero,false);
    }
    
    public static PositionInMusic PasajePosicionPorcentaje(int numero){
        return new PositionInMusic(true,numero);
    }
    public static PositionInMusic PasajeAleatorio(){
        return new PositionInMusic(true);
    }
    
    public static ModificationPlaceSelector LugarModificacionPrincipio(){
        return new ModificationPlaceSelector(true,false,false,0);
    }
    public static ModificationPlaceSelector LugarModificacionMedio(){
        return new ModificationPlaceSelector(false,true,false,0);
    }
    public static ModificationPlaceSelector LugarModificacionFin(){
        return new ModificationPlaceSelector(false,false,true,0);
    }
    public static ModificationPlaceSelector LugarModificacionRelativo(int numero){
        return new ModificationPlaceSelector(false,false,false,numero);
    }
     /***
     * El parametro pitch es un numero del 1 al 12 que representa todas las posibles notas, siendo el 1 el do y el 12 el si.
     * El parametro optionalOctave en caso que no se quiera usar se debe pasar como null.
     * @param pitch
     * @param optionalOctave
     * @return 
     */    
    public static Note createNote(int pitch, Integer optionalOctave){
        return new Note(-1,pitch,optionalOctave,'n');
    }
    /***
     * crea una sequencia de notas con alturas, el valor que se le pasa en la duracion es irrelevante por que no se considera en ningun momento.
     * @return 
     */
    public static List<Note> createNoteSequency(){
        List<Note> sequency= new ArrayList<Note>();
        sequency.add(new Note(1,1,5,'n'));
        sequency.add(new Note(1,3,5,'n'));
        sequency.add(new Note(1,5,5,'n'));
        return sequency;
    }
    
    /***
     * crea una lista de grados, contiene en particular 3 grados 1,3 y 5
     * @return 
     */
    public static List<Integer> createGradesSequency(){
        List<Integer> sequency = new ArrayList<Integer>();
        sequency.add(new Integer(1));
        sequency.add(new Integer(3));
        sequency.add(new Integer(5));
        return sequency;
    
    }
    
}
