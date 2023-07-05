package RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationConditionChecker;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationCondition;
import java.util.ArrayList;
import java.util.List;

public class CheckTimesANoteAppear extends ModificationCondition {
    Note note;
    boolean absolute;
    int numberOfAppearances;
    Comparador cmp;
    Integer grade;
    
    public CheckTimesANoteAppear(Note note, boolean absolute, int numberOfAppearances, Comparador comp){
        this.note = note;
        this.absolute = absolute;
        this.numberOfAppearances = numberOfAppearances;
        cmp = comp;
        this.grade = null;
    }
    
    public CheckTimesANoteAppear(Integer grade, int numberOfAppearances, Comparador comp){
        this.grade = grade;
        this.numberOfAppearances = numberOfAppearances;
        cmp = comp;
        this.absolute = false;
        this.note = null;
    }
    
    @Override
    public boolean checkCondition(List<Chord> base, List<Note> melody, Double start, Double end) {
        int positionStartIndex = Util.calculateNotePositionInListByTimeSum(melody, start);
        int positionEndIndex = Util.calculateNotePositionInListByTimeSum(melody, end);
        int quantity = 0;
        for (int i = positionStartIndex; i < positionEndIndex; i++) {
            Note particularNote = melody.get(i);
            if (this.satisfiesCondition(particularNote, Util.LookForBaseChord(base, melody, i))) {
                quantity++;
            }
        }
    
        return (quantity > this.numberOfAppearances && cmp.isGreater()) ||
               (quantity < this.numberOfAppearances && cmp.isLess()) ||
               (quantity == this.numberOfAppearances && cmp.isEqual());
    }

    private boolean satisfiesCondition(Note particularNote, Chord chord){
        if (this.grade != null) {
            return Util.calculateNoteGradeForChord(chord, particularNote) == this.grade;
        }
        if (this.absolute && particularNote.getNote() == this.note.getNote() && particularNote.getOctave() == this.note.getOctave()
           || !this.absolute && particularNote.getNote() == this.note.getNote()) {
            return true;
        }
        return false;
    }
    
    @Override
    public List<Double> listFaultyPositions(List<Chord> base, List<Note> melody, Double start, Double end) {
        int positionStartIndex = Util.calculateNotePositionInListByTimeSum(melody, start);
        int positionEndIndex = Util.calculateNotePositionInListByTimeSum(melody, end);
        List<Double> faultyPositions = new ArrayList<>();
        for (int i = positionStartIndex; i < positionEndIndex; i++) {
           Note particularNote = melody.get(i);
           if (this.satisfiesCondition(particularNote, Util.LookForBaseChord(base, melody, i))) {
             faultyPositions.add(Util.calculateTimeSumByPosition(melody, i));
           }
        }
        
        return faultyPositions;
    }
}
