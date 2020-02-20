/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.ModifyExcerpt.ModifyRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import EvolutionaryAlgorithm.ConditionEvolvableClass;
import EvolutionaryAlgorithm.GenericEvolutionaryAlgorithm;
import EvolutionaryAlgorithm.IEvolvableClass;
import ImprovisationRules.Util;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import RuleCreationFramework.ModifyExcerpt.Modification;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteeSolver.DivideAndConquerEvolutionarySolve;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteeSolver.EvolutionarySolve;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteeSolver.GuaranteeConditionSolver;
import Style.AbstractStyle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gaston
 */
/** 
 * esta regla se va a encargar de identificar si se cumple una condicion, y realizar la modificacion sobre la linea melodica hasta lograr 
* la condicion que se desea garantizar.
* Condicionales: Si no existe garantia simplmemnete la aplica la regla sobre todos los elementos del tramo. 
* si existe garantia utiliza algun metodo para resolver la garantia
* si se modifica el principio (el objeto es != null) se aplica eso para saber el inicio. 
* se aplica la regla en caso de que se cumpla la probabilidad. Numero entre 0-1
* */
public class CompleteModificationRule extends Modification{
    ModificationCondition initialCondition;
    GuaranteedCondition guaranteedCondition;
    SpecificModification modification;
    ModificationPlaceSelector mps;
    double probability;

    /**
     * Constructor de la clase que recibe los siguientes parametros:
     * @param initialCondition Condiicion para que se realice una modificacion, si no se cumple no se hace aboslutamente nada. Si la regla no tiene condicion se utiliza para deterimnar los puntos de cambio
     * @param guaranteedCondition Condicion que se intenta maximizar. Si bien el ideal seria que se garantice en todos los casos, no siempre es posible, ya que depende de la modificacion especifica que se
     * desee hacer sobre la melodia, siendo que a veces la solucion es imposible.
     * @param modification  Modificacion particular que se va a utilizar para lo9grar que se cumpla la condicion o en aquellos casos que la condicion inicial determine. 
     * @param mps Clase encargada de guardar y calcular la posicion de comienzo a partir de la cual se realizara la modificacion.
     * @param probability numero entre 0 y 1.Probabilidad de que se aplique la modificacion. En caso de que haya una garantia que cumplir, se checkea una vez (se intenta o no lograr la garantia con todas las modifiaciones necesarias)
     * En caso que no haya garantia, antes de realizar una modifiacion sobre cada caso retornado por la condicion de modificacion se checkea que se cumpla esta probabilidad en una tirada random.
     */
    public CompleteModificationRule(ModificationCondition initialCondition, GuaranteedCondition guaranteedCondition, SpecificModification modification,ModificationPlaceSelector mps, double probability){
        this.initialCondition = initialCondition;
        this.guaranteedCondition = guaranteedCondition;
        this.modification = modification;
        this.mps = mps;
        this.probability=probability/100;
    }
    /**
     * Modifica un trecho de musica siendo que se calcula segun la clase ModificationPlaceSelector el punto desde donde va a empezar. En caso que no se cumpla la condicion de initialCondition
     * se retorna la melodia intacta. Si no existe una garantia par aluego de ejecutar el procedimiento, se calcula todos los lugares que incumplen con la condicion inicial y se aplica la modifiacion sore esos lugares, siendo que 
     * antes de modificar cada lugar se calcula en base a la probabilidad si se hace o no. Por ultimo si hay una garantia, se calcula la probabilidad para ver si efectivamente se aplica un algoritmo que 
     * mejore la condicion, en caso de ser asi se aplica un algoritmo de mejora, en este caso se usa un algoritmo biologico. Donde se minimiza el espacio de busqueda y la cantidad de iteraciones con el fin de 
     * lograr un recorrido mas rapido. 
     * @param base
     * @param melody
     * @param start
     * @param end
     * @param style
     * @return 
     */
    @Override
    public List<Note> Modify(List<Chord> base, List<Note> melody, Double start, Double end, AbstractStyle style) {
        double modifiedStart=mps!=null? mps.startPosition(melody, start, end): start;
        List<Note> melodyCopy = FrameWorkUtil.copyMelody(melody);
        List<Note> result = melodyCopy;
        if(initialCondition!=null && !initialCondition.checkCondition(base, melodyCopy, modifiedStart, end))
           return result;
       if(guaranteedCondition == null){
           List<Double> positionList;
           if(initialCondition != null)
               positionList = initialCondition.listFaultyPositions(base,melodyCopy,modifiedStart,end);
           else 
               positionList = this.calculateAllPositions(modifiedStart,end,melody);
           
           for (int i = 0; i <positionList.size(); i++) {
               if(this.applyByProbability()){ 
                    result = modification.makeModification(base, melodyCopy, modifiedStart, end, positionList.get(i), style);
               }
           }
       }else{      
           if(this.applyByProbability()){
                //GuaranteeConditionSolver gcs = new DivideAndConquerEvolutionarySolve(70, 1000,0,50,12,3000);
                GuaranteeConditionSolver gcs = new DivideAndConquerEvolutionarySolve(70, 500,10,50,20,1000);
                result = gcs.Solve(base, melodyCopy, modifiedStart, end, style, this.guaranteedCondition, this.modification);
           }
       }
    

        return result;
    }
    
    private List<Double> calculateAllPositions(double modifiedStart, double end, List<Note> melody){
        List<Double> sol = new ArrayList<Double>();
        int posStart= Util.calculateNotePositionInListByTimeSum(melody, modifiedStart);
        int posEnd = Util.calculateNotePositionInListByTimeSum(melody, end);
        for (int i = posStart; i < posEnd; i++) {
            sol.add(Util.calculateTimeSumByPosition(melody, i));
        }
        return sol;
    }
    /**
     * retorna true si se luego de una tirada random de probaiblidad teoricamente normal 
     * sale como resultado un numero menor al de la probailidad de que se aplique, de forma que si tiro 100 veces deberia tener
     * 1 de cada 100 pega, entonces si es 1porciento la probaildad deberia salir 1 de cada 100 veces.
     * @return 
     */
    private boolean applyByProbability(){
        double random = Math.random();
        if(random <= this.probability)
            return true;
        return false;
    }
    
}
