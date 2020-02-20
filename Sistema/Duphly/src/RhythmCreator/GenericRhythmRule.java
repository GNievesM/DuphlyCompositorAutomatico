/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RhythmCreator;

import DataDefinition.Chord;
import DataDefinition.Rhythm;
import java.util.List;

/**
 *
 * @author gasto_000
 */
public abstract class GenericRhythmRule {
    
    public abstract Rhythm GenerateRhytm(List<Chord> base);
}
