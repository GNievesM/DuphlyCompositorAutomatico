package RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import java.util.List;
import Style.AbstractStyle;

public class ChangeMetric extends SpecificModification {
    Integer division;

    public ChangeMetric(Integer division) {
        this.division = division;
    }

    @Override
    public List<Note> makeModification(List<Chord> base, List<Note> melody, double start, double end, double specificNote, AbstractStyle style) {
        int position = Util.calculateNotePositionInListByTimeSum(melody, specificNote);
        Note particularNote = melody.get(position);
        particularNote.setDuration(particularNote.getDuration() / division);
        for (int i = 0; i < division - 1; i++) {
            Note copy = new Note();
            copy.copyNote(particularNote);
            melody.add(position + i, copy);
        }
        melody.set(position + division - 1, particularNote);
        return melody;
    }

    @Override
    public Note mutate(List<Chord> base, List<Note> melody, double start, double end, int specificNotePosition, AbstractStyle style) {
        throw new UnsupportedOperationException("Evolutionary algorithm cannot be applied with this modification");
    }
}
