/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import java.util.List;

/**
 *
 * @author gasto_000
 */
public class TestImprovisation {

    public static boolean testImprovisation(List<Note> improvisation, List<Chord> base) {
        boolean retorno= true;
        for (int i = 0; i < improvisation.size(); i++) {
            int notePitch = improvisation.get(i).getNote();
            int chordFirst = Util.CalcChord(Util.LookForBaseChord(base, improvisation, i));
            if (notePitch < chordFirst) {
                notePitch = notePitch + 12;
            }
            int distance = notePitch - chordFirst;
            if (distance == 1
                    || distance == 2
                    || distance == 4
                    || distance == 8
                    || distance == 9
                    || distance == 11) {
                retorno = false;
                System.out.println("Encontre un error");
                System.out.println("Nota : " + notePitch + "chordFirst: "+ chordFirst);
            }
        }
        return retorno;

    }
}
