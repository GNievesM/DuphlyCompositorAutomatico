package RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SpecificModification;
import java.util.List;
import Style.AbstractStyle;

public class SustituteValidNote extends SpecificModification {
    Comparador comp;
    int distance;
    boolean nearNote;

    public SustituteValidNote(Comparador comp, int distance, boolean nearNote) {
        this.comp = comp;
        this.distance = distance;
        this.nearNote = nearNote;
    }

    @Override
    public List<Note> makeModification(List<Chord> base, List<Note> melody, double start, double end, double specificNote, AbstractStyle style) {
        int position = Util.calculateNotePositionInListByTimeSum(melody, specificNote);
        if (position + distance < 0 || position + distance >= melody.size())
            return melody;

        Chord noteAsociatedChord = Util.LookForBaseChord(base, melody, position);
        Note particularNote = melody.get(position + distance);
        particularNote = new Note(melody.get(position).getDuration(), particularNote.getNote(), particularNote.getOctave(), particularNote.getAccident());

        if (comp.isGreater() && nearNote) {
            particularNote = style.nextValidNote(noteAsociatedChord, particularNote);
        }
        if (comp.isLess() && nearNote) {
            particularNote = style.previousValidNote(noteAsociatedChord, particularNote);
        }
        if (comp.isGreater() && !nearNote) {
            particularNote = style.biggerValidNote(noteAsociatedChord, particularNote, null);
        }
        if (comp.isLess() && !nearNote) {
            particularNote = style.smallerValidNote(noteAsociatedChord, particularNote, null);
        }
        melody.set(position, particularNote);
        return melody;
    }

    @Override
    public Note mutate(List<Chord> base, List<Note> melody, double start, double end, int specificNotePosition, AbstractStyle style) {

        Note element = melody.get(specificNotePosition);
        if (specificNotePosition + distance < 0 || specificNotePosition + distance > melody.size())
            return element;

        Chord noteAsociatedChord = Util.LookForBaseChord(base, melody, specificNotePosition);
        Note particularNote = melody.get(specificNotePosition + distance);

        if (comp.isGreater() && nearNote) {
            element = style.nextValidNote(noteAsociatedChord, particularNote);
        }
        if (comp.isLess() && nearNote) {
            element = style.previousValidNote(noteAsociatedChord, particularNote);
        }
        if (comp.isGreater() && !nearNote) {
            element = style.biggerValidNote(noteAsociatedChord, particularNote, null);
        }
        if (comp.isLess() && !nearNote) {
            element = style.smallerValidNote(noteAsociatedChord, particularNote, null);
        }

        return element;
    }
}
