/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.List;

/**
 *
 * @author Gaston
 */
public abstract class FiltroUbicacion {
        public abstract List<Note> satisfecho(List<Note> notas, List<Chord> base);

}
