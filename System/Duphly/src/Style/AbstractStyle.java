/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Style;

import ConverterUtil.ChordCalculator;
import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gaston
 */
public abstract class AbstractStyle {
    
    List<Double> probabilityDistributionMajor; // the probability has to sum 1 and is necesary to be from 1 to 12.
    List<Double> probabilityDistributionMinor; // the probability has to sum 1 and is necesary to be from 1 to 12.
    int maxOctave;
    int minOctave;
    int melodyRoot;
    
    /**
     * Determina la clave de la musica
     * @param noteHeight
     */
    public void setMelodyRoot(int noteHeight){this.melodyRoot = noteHeight;}    

    /**
     * Determina la maxima octava que se va a utilizar
     * @param maxOctave
     */
    public void setMaxOctave(int maxOctave){this.maxOctave = maxOctave;}

    /**
     * Determina la minima octava que se va a utilizar
     * @param minOctave
     */
    public void setMinOctave(int minOctave){this.minOctave =minOctave;}

    /**
     * Setea un conjunto de probabilidades para la seleccion aleatoria de notas
     *  basado en si la forma del acorde de la base
     * @param probabilityPerNoteMajor
     * @param probabilityPerNoteMinor
     */
    public void changeProbability(List<Double> probabilityPerNoteMajor,List<Double> probabilityPerNoteMinor){
        this.probabilityDistributionMajor = probabilityPerNoteMajor;
        this.probabilityDistributionMinor = probabilityPerNoteMinor;
    };
    
    
    public int getMaxOctave(){return this.maxOctave;}
    public int getMinOctave(){return this.minOctave;}
       /**
     *Dada una nota y la cantidad de semi tonos los suma a la nota, si es negativo baja la altura
     * si la cantidad de semitonos excede los limites de octava retorna la nota original
     * @param n
     * @param demitones
     * @return
     */
    public Note addDemiTones(Note n, int demitones){
        int noteNumber = Util.getExpandedFormNote(n.getNote(), n.getOctave())+demitones;
        if(Util.getSmallFormNoteOctave(noteNumber)<=this.maxOctave && Util.getSmallFormNoteOctave(noteNumber )>= this.minOctave){
            n.setNote(Util.getSmallFormNotePitch(noteNumber));
            n.setOctave(Util.getSmallFormNoteOctave(noteNumber));
        }
        return n;
    }
    /**
     * Determina una nota valida(que pertenezca al acorde) utilizando el esquema
     * de probabilidad que se recibe por paraemtro, en caso que no se reciba
     * determina el esquema de probabilidad segun la forma de la base. 
     * 
     * @param c
     * @param duration
     * @param optionalProbability
     * @return
     */
    public abstract Note validRandomNote(Chord c, double duration, List<Double> optionalProbability);

    /**
     * Retorna la siguiente nota valida(que pertenezca al acorde) en forma ascendente,
     * si no existe una nota valida mayor retorna la mismqa que recibio
     * @param c
     * @param n
     * @return
     */
    public abstract Note nextValidNote(Chord c, Note n);

    /**
     * REtorna la anterior nota valida(que pertenezca al acorde) en forma descendente,
     * si no existe una nota valida menor retorna la misma nota que recibio.
     * @param c
     * @param n
     * @return
     */
    public abstract Note previousValidNote(Chord c, Note n);

    /**
     * Retorna una nota valida(que pertenezca al acorde) mayor a la que recibio usando el esquema de probabiilidad,
     * si no existe una nota mayor retorna la misma nota que recibio.
     * @param c
     * @param n
     * @param optionalProbability
     * @return
     */
    public abstract Note biggerValidNote(Chord c, Note n, List<Double> optionalProbability);

    /**
     * REtorna una nota valida menor(que pertenezca al acorde) a la que recibio usando el esquema de probabilidad,
     * si no existe ua nota menor reotrna la misma nota que recibio
     * @param c
     * @param n
     * @param optionalProbability
     * @return
     */
    public abstract Note smallerValidNote(Chord c,  Note n, List<Double> optionalProbability);

    /**
     * Retorna una nota aleatoria segun el esquema de probabilidad, si el esquema es nulo 
     * utiliza el interno de la clase. Esta funcion permite cualquier nota
     * (notas de paso, apoyaturas, etc)
     * @param c
     * @param duration
     * @param optionalProbability
     * @return
     */
    public abstract Note ProbabilityRandomNote(Chord c, double duration, List<Double> optionalProbability);

    /**
     * Retorna la siguiente nota que tenga una probabilidad igual o mayor a probBiggerThan
     * 
     * @param c
     * @param n
     * @param optionalProbability
     * @return
     */
    public abstract Note nextProbabilityNote(Chord c,  Note n, List<Double> optionalProbability, double probBiggerThan);

    /**
     * Retorna la nota anterior que tenga una probaiblidad igual o menor a probBiggerThan.
     * @param c
     * @param n
     * @param optionalProbability
     * @return
     */
    public abstract Note previousProbabilityNote(Chord c,Note n, List<Double> optionalProbability, double probBiggerThan);

    /**
     * Retorna una nota mayor a la recibida por parametro segun el esquema de probabilidad.
     * @param c
     * @param n
     * @param optionalProbability
     * @return
     */
    public abstract Note biggerProbabilityNote(Chord c,  Note n, List<Double> optionalProbability);

    /**
     * Retorna una nota menor a la recibida por parametro segun el esquema de probabilidad.
     * @param c
     * @param n
     * @param optionalProbability
     * @return
     */
    public abstract Note smallerProbabilityNote(Chord c,   Note n, List<Double> optionalProbability);
    
    /**
     * Dada una altura y un acorde, calcula la nota para la altura recibida 
     * segun la tonica del acorde. Ejemplo Do mayor y 5, retorna Fa, suma 5 semitonos a la tonica.
     * @param c
     * @param height
     * @return
     */
    protected int calculateNoteHeightBaseOnChord(Chord c, int height){ // la altura va a estar entre 1 y 12 y lo cuento a partir de la tonica de la base
        return calculateNoteName(returnBaseChordTonicHeight(c),height);
    }

    /**
     * Retorna la altura de la nota correspondiente a la tonica del acorde. 
     * Numero del 1 al 12 que representa todas las notas.
     * @param c
     * @return
     */
    protected int returnBaseChordTonicHeight(Chord c){
        return ChordCalculator.calculateChordFirst(c, melodyRoot);
    }

    /**
     * Dado el primer grado del acorde  y una altura, retorna la suma de los semitonos
     * al primer grado dando entre 1 y 12 (la representacion de las notas)
     * @param chordFirstGrade
     * @param height
     * @return
     */
    protected int calculateNoteName(int chordFirstGrade, int height){
        return ((chordFirstGrade+height -1)%12); // resto uno por que necesito que este entre 0 y 11 para calcular el modulo y no sumo 1 mas para que la suma de semitonos quede correcta,
        // ejemplos nota 1, altura 1 tiene que dar 1 -> 1 + 1-1 modulo 12  = 1; nota 10 -> 1 + 10-1 mod 12 = 10
    }

    /**
     * Retorna un numero entre 1 y 12 haciendo uso de la probaiblidad que se pase por paraemtro
     * @param probabilityToUse
     * @return
     */
    protected int calculateRandomUsingProbability(List<Double>probabilityToUse){
        int random = (int)Math.rint(Math.random()*100); 
        int solvedPitch = 1;
        int rangeStart=0;
        for (int i = 0; i < probabilityToUse.size(); i++) { // preguntar cuanto es la probabilidad del 1 y devolver uno para eso voy sumando las probabilidades y checkeo que sea mayor al random.
        //    if ((rangeStart<=random && random >=rangeStart + probabilityToUse.get(i)*100)){
        //        solvedPitch++;
       //         rangeStart += probabilityToUse.get(i)*100;            
        //       }   
            if(rangeStart<=random&&random<=rangeStart+probabilityToUse.get(i)*100)
                return solvedPitch;
            solvedPitch++;
            rangeStart += probabilityToUse.get(i)*100;
        }
        return solvedPitch;
    }
    
    /**
     * Dada una altura, calcula su valor correspondiente entre 1 y 12, considera 
     * numeros mayores al 12 y menores al 1.
     * @param note
     * @return
     */
    protected int calculateNoteOutOfBounds(int note){
        if(note<13 && note>0)
            return note;
        if(note>0)
            return 1+((note -1)%12);
        return 12+note; 
    }

    /**
     * retorna un numero aleatorio entre min octave y max octave
     * @return
     */
    protected int randomOctave(){
        int randomOctave = 0;
        while(randomOctave<minOctave || randomOctave>maxOctave){
            randomOctave = (int)Math.rint(Math.random()*maxOctave);
        }
        return randomOctave;
    }

    /**
     * Retorna si la nota es bemol o natural (no recuerdo si se utiliza en el sistema)
     * @param height
     * @return
     */
    protected char calculateAccident(int height){
        return height<6? (height%2 == 0? 'b' :'n') : (height%2==0?'n':'b');
    }
    
    /**
     * Retorna si una nota es mas grande que otra(considera octava y el numero entre 1 y 12).
     * @param n1
     * @param n2
     * @return
     */
    protected boolean noteIsBigger(Note n1, Note n2){
        return Util.getExpandedFormNote(n1.getNote(), n1.getOctave())>Util.getExpandedFormNote(n2.getNote(),n2.getOctave());
    }

    /**
     * retorna si una nota es menor que otra(octava y numero de nota)
     * @param n1
     * @param n2
     * @return
     */
    protected boolean noteIsSmaller(Note n1, Note n2){
        return Util.getExpandedFormNote(n1.getNote(), n1.getOctave())<Util.getExpandedFormNote(n2.getNote(),n2.getOctave());
    }

    /**
     * retorna si dos notas son iguales (octava y numero de nota)
     * @param n1
     * @param n2
     * @return
     */
    protected boolean noteIsEqual(Note n1, Note n2){
        return Util.getExpandedFormNote(n1.getNote(), n1.getOctave())==Util.getExpandedFormNote(n2.getNote(),n2.getOctave());
    }
    
    /**
     * Filtra la lista quitando los elementos que no alcanzan la probabilidad pasada por parametro. 
     * Redistribuye la probabilidad para que siga sumando 1.
     * @param n1
     * @param n2
     * @return
     */
    protected List<Double> filterProbSmallerThan(List<Double> probabilityList, double prob){
        List<Double> sol = new ArrayList();
        double totalDiscarded = 0;
        int quantityNotDiscarded = 0;
        for (int i = 0; i < probabilityList.size(); i++) {
            if(probabilityList.get(i)< prob){
                sol.add(new Double(0));
                totalDiscarded += probabilityList.get(i);
            }
            else{
                sol.add(probabilityList.get(i));
                quantityNotDiscarded++;

            }
        }
        
        for(int i=0; i<sol.size();i++)
            if(sol.get(i)>0)
                sol.set(i, sol.get(i)+ totalDiscarded/quantityNotDiscarded);
        
        return sol;
    }
    
     /**
     *retorna la lista de notas validas para una detemrinada base
     * @param n1
     * @return
     */
    protected abstract List<Integer> validNotesPerBase(Chord base);
       /**
     * retorna la lista de alturas validas para cualquier acorde. Se pasa un acorde
     * para el caso que saber el modo del acorde varie el resultado.
     * @param n1
     * @return
     */
    
    protected abstract List<Integer> validHeightsAbstractForm(Chord base);
     /**
     * Filtra la lista de probabilidad eliminando todas las notas que no sean validas y redistribuyendo la probabilidad.
     * @param n1
     * @param n2
     * @return
     */
    protected List<Double> filterValidNotes(List<Double> probabilityList, Chord base){
        List<Integer> validHeights = this.validHeightsAbstractForm(base);
        List<Double> result = new ArrayList<Double>();
        double probDiscarded = 0;
        int quantityDiscarded = 0;
        for(int i=0;i<probabilityList.size();i++) // las probabilidades se mapean del 1 al 12
            if(!validHeights.contains(i+1)){  // se suma 1, por que la lista va de 0 a 11, y las notas estan entre el 1y el 12.
                result.add(new Double(0));
                probDiscarded += probabilityList.get(i);
            }else{
                result.add(probabilityList.get(i));
                quantityDiscarded++;
            }
        
        for (int i = 0; i < result.size(); i++) {
            if(result.get(i)>0)
                result.set(i, result.get(i)+probDiscarded/quantityDiscarded);
        }
        return result;
    }
}
