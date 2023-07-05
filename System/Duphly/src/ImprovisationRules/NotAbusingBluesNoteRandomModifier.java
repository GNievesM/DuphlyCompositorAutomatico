package ImprovisationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationManagement.GenericImprovisationRule;
import java.util.List;
import javafx.util.Pair;

public class NotAbusingBluesNoteRandomModifier extends GenericImprovisationRule {

    @Override
    public List<Note> GenerateImprovisation(List<Chord> base) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Pair<List<Note>, List<Chord>> VerifyAndCorrectImprovisation(List<Note> improvisation, List<Chord> base) {
        // The blues scale allows for 6 notes, so a normal distribution of the notes would imply each note appearing 16.6% of the time.
        // In this case, to avoid abusing the blues note, we will only allow a 9% occurrence of the blues note.
        int bluesNoteQuantity = (int) Math.round((double) ((double) (improvisation.size()) * 0.09));
        for (int i = 0; i < improvisation.size(); i++) {
            if (Util.IdentifyBluesScaleNoteHeightInChord(Util.LookForBaseChord(base, improvisation, i), improvisation.get(i)) == 3) {
                bluesNoteQuantity--;
                if (bluesNoteQuantity < 0) {
                    int height = 3;
                    while (height == 3)
                        height = (int) Math.rint(Math.random() * 5);
                    improvisation.get(i).setNote(Util.CalculateNoteHeightForChord(Util.LookForBaseChord(base, improvisation, i), height));
                }
            }
        }

        return new Pair<List<Note>, List<Chord>>(improvisation, base);
    }

    @Override
    public boolean VerifyImprovisationAppliesRule(List<Note> improvisation, List<Chord> base) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Note> ApplyRuleInAMoment(List<Note> improvisation, List<Chord> base, int momentToApply) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
