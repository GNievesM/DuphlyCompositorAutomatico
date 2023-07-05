package Testing;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import java.util.List;

public class TestImprovisation {

    public static boolean testImprovisation(List<Note> improvisation, List<Chord> base) {
        boolean returnValue = true;
        for (int i = 0; i < improvisation.size(); i++) {
            int notePitch = improvisation.get(i).getNote();
            int chordFirst = Util.calculateChord(Util.findBaseChord(base, improvisation, i));
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
                returnValue = false;
                System.out.println("Found an error");
                System.out.println("Note: " + notePitch + ", ChordFirst: " + chordFirst);
            }
        }
        return returnValue;
    }
}
