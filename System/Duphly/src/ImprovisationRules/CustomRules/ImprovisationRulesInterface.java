/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImprovisationRules.CustomRules;

import DataDefinition.TupleNameTypeImpRule;
import ImprovisationRules.AgregarEscalasCromaticas;
import ImprovisationRules.BluesScaleOnEveryChordNormalOctave;
import ImprovisationRules.DashedDirectionContinuity;
import ImprovisationRules.DirectionContinuity;
import ImprovisationRules.DirectionContinuityAllowingEquals;
import ImprovisationRules.DirectionContinuityImplementation2;
import ImprovisationRules.FrameworkTesting;
import ImprovisationRules.ImprovisationPurelyRandom;
import ImprovisationRules.ImprovisationPurelyRandomNotAbusingBluesNote;
import ImprovisationRules.LimitJumpsPerBar;
import ImprovisationRules.LimitJumpsPerBarNotAbusingBluesNote;
import ImprovisationRules.NotAbusingBluesNoteEqualModifier;
import ImprovisationRules.NotAbusingBluesNoteRandomModifier;
import ImprovisationRules.RepeatingNotes;
import java.util.ArrayList;
import java.util.List;
import ImprovisationRules.*;
import ImprovisationRules.CustomRules.*;

/**
 *
 * @author Gaston
 */
public class ImprovisationRulesInterface {
    List<TupleNameTypeImpRule> existingRules;
    private static ImprovisationRulesInterface instance=null;
    
    public static ImprovisationRulesInterface getInstance(){
        if(instance == null)
            instance = new ImprovisationRulesInterface();
        return instance;
    }
    
    public List<TupleNameTypeImpRule> getRuleList(){
        return existingRules;
    }
    
    protected ImprovisationRulesInterface(){
        existingRules = new ArrayList<TupleNameTypeImpRule>();
        existingRules.add(new TupleNameTypeImpRule("Creacion aleatoria","Generar", true, new ImprovisationPurelyRandom()));
        existingRules.add(new TupleNameTypeImpRule("Limitar saltos por compas","Generar", true, new LimitJumpsPerBar()));
        existingRules.add(new TupleNameTypeImpRule("Creacion aleatoria sin abusar la nota de blues","Generar", true, new ImprovisationPurelyRandomNotAbusingBluesNote()));
        existingRules.add(new TupleNameTypeImpRule("Limitar saltos por compas sin abusar la nota de blues","Generar", true, new LimitJumpsPerBarNotAbusingBluesNote()));
        existingRules.add(new TupleNameTypeImpRule("Continuidad en el fraseo","Generar", true, new DirectionContinuity()));
        existingRules.add(new TupleNameTypeImpRule("Escala de blues sobre acordes","Generar", true, new BluesScaleOnEveryChordNormalOctave()));
        existingRules.add(new TupleNameTypeImpRule("Limitar difusion en la direccion","Verificar y corregir", false, new DashedDirectionContinuity()));
        existingRules.add(new TupleNameTypeImpRule("Continuidad en el fraseo sin permitir repetidas","Verificar y corregir", false, new DirectionContinuity()));
        existingRules.add(new TupleNameTypeImpRule("Continuidad en el fraseo permitiendo repetidas","Verificar y corregir", false, new DirectionContinuityAllowingEquals()));
        existingRules.add(new TupleNameTypeImpRule("Disminuir utilizacion de la nota de blues aleatorio","Verificar y corregir", false, new NotAbusingBluesNoteRandomModifier()));
        existingRules.add(new TupleNameTypeImpRule("Disminuir utilizacion de la nota de blues sustituyendo por la nota anterior","Verificar y corregir", false, new NotAbusingBluesNoteEqualModifier()));
        existingRules.add(new TupleNameTypeImpRule("Continuidad en el fraseo con variante","Verificar y corregir", false, new DirectionContinuityImplementation2()));
        existingRules.add(new TupleNameTypeImpRule("Aumentar repeticion de notas","Verificar y corregir", false, new RepeatingNotes()));
  
        existingRules.add(new TupleNameTypeImpRule("testeo del framework","Verificar y corregir", false, new FrameworkTesting()));
        existingRules.add(new TupleNameTypeImpRule("regla custom template", "Verificar y corregir", false, new CustomRuleTemplate()));
        existingRules.add(new TupleNameTypeImpRule("Agregar escalas cromaticas en saltos cada 2 compases", "Verificar y corregir", false, new AgregarEscalasCromaticas()));
        existingRules.add(new TupleNameTypeImpRule("Agregar silencios en la octava 4", "Verificar y corregir", false, new AgregarSilencios()));
        existingRules.add(new TupleNameTypeImpRule("Agregar division de notas y salto de octava", "Verificar y corregir", false, new AgregarDivisionDeNotasYSaltosDeOctava()));
        existingRules.add(new TupleNameTypeImpRule("Agregar dividir nota y aumentar un semitono", "Verificar y corregir", false, new AgregarDivisionDeNotasYAumentoDeUnSemitono()));
        existingRules.add(new TupleNameTypeImpRule("Escalas ascendentes", "Verificar y corregir", false, new EscalasAscendentes()));
        existingRules.add(new TupleNameTypeImpRule("Escalas descendentes", "Verificar y corregir", false, new EscalasDescendentes()));
        existingRules.add(new TupleNameTypeImpRule("AgregarSilenciosBajos", "Verificar y corregir", false, new AgregarSilenciosBajos()));
        existingRules.add(new TupleNameTypeImpRule("Aumentar un tono repetidas", "Verificar y corregir", false, new AumentarUnTonoRepetidas()));
        existingRules.add(new TupleNameTypeImpRule("Disminuir un tono repetidas", "Verificar y corregir", false, new DisminuirUnTonoRepetidas()));
        existingRules.add(new TupleNameTypeImpRule("Agregar 40 pociento de silencio", "Verificar y corregir", false, new AgregarSilencios40porciento()));
        

  }
    
}
