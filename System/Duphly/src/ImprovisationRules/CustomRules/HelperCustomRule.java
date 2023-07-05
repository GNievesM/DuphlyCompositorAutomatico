package ImprovisationRules.CustomRules;

import DataDefinition.Note;
import RuleCreationFramework.BlockFilter.BlockPositionInMusic;
import RuleCreationFramework.ExcerptFilter.PositionInMusic;
import RuleCreationFramework.FrameworkUtil.Comparador;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationPlaceSelector;
import java.util.ArrayList;
import java.util.List;

public class HelperCustomRule {
    public static Comparador GreaterComparator() {
        return new Comparador(1);
    }
    
    public static Comparador LessComparator() {
        return new Comparador(-1);
    }
    
    public static Comparador EqualComparator() {
        return new Comparador(0);
    }
    
    public static BlockPositionInMusic StartBlockPosition() {
        return new BlockPositionInMusic(true, false, false);
    } 
    
    public static BlockPositionInMusic MiddleBlockPosition() {
        return new BlockPositionInMusic(false, true, false);
    }
    
    public static BlockPositionInMusic EndBlockPosition() {
        return new BlockPositionInMusic(false, false, true);
    }
    
    public static BlockPositionInMusic RelativeGreaterBlockPosition(int number) {
        return new BlockPositionInMusic(number, true);
    }
    
    public static BlockPositionInMusic RelativeLessBlockPosition(int number) {
        return new BlockPositionInMusic(number, false);
    }
    
    public static BlockPositionInMusic PercentageBlockPosition(int number) {
        return new BlockPositionInMusic(true, number);
    }    
    
    public static BlockPositionInMusic RandomBlockPosition() {
        return new BlockPositionInMusic(true);
    }
    
    public static PositionInMusic StartExcerptPosition() {
        return new PositionInMusic(true, false, false);
    }
    
    public static PositionInMusic MiddleExcerptPosition() {
        return new PositionInMusic(false, true, false);
    }
    
    public static PositionInMusic EndExcerptPosition() {
        return new PositionInMusic(false, false, true);
    }
    
    public static PositionInMusic RelativeGreaterExcerptPosition(int number) {
        return new PositionInMusic(number, true);
    }
    
    public static PositionInMusic RelativeLessExcerptPosition(int number) {
        return new PositionInMusic(number, false);
    }
    
    public static PositionInMusic PercentageExcerptPosition(int number) {
        return new PositionInMusic(true, number);
    }
    
    public static PositionInMusic RandomExcerptPosition() {
        return new PositionInMusic(true);
    }
    
    public static ModificationPlaceSelector StartModificationPlace() {
        return new ModificationPlaceSelector(true, false, false, 0);
    }
    
    public static ModificationPlaceSelector MiddleModificationPlace() {
        return new ModificationPlaceSelector(false, true, false, 0);
    }
    
    public static ModificationPlaceSelector EndModificationPlace() {
        return new ModificationPlaceSelector(false, false, true, 0);
    }
    
    public static ModificationPlaceSelector RelativeModificationPlace(int number) {
        return new ModificationPlaceSelector(false, false, false, number);
    }
    
    /**
     * The pitch parameter is a number from 1 to 12 that represents all possible notes,
     * where 1 represents C and 12 represents B.
     * The optionalOctave parameter should be passed as null if not used.
     *
     * @param pitch
     * @param optionalOctave
     * @return
     */
    public static Note createNote(int pitch, Integer optionalOctave) {
        return new Note(-1, pitch, optionalOctave, 'n');
    }
    
    /**
     * Creates a sequence of notes with pitches. The duration parameter is irrelevant
     * because it is not considered at any moment.
     *
     * @return
     */
    public static List<Note> createNoteSequence() {
        List<Note> sequence = new ArrayList<Note>();
        sequence.add(new Note(1, 1, 5, 'n'));
        sequence.add(new Note(1, 3, 5, 'n'));
        sequence.add(new Note(1, 5, 5, 'n'));
        return sequence;
    }
    
    /**
     * Creates a list of grades, specifically containing grades 1, 3, and 5.
     *
     * @return
     */
    public static List<Integer> createGradesSequence() {
        List<Integer> sequence = new ArrayList<Integer>();
        sequence.add(new Integer(1));
        sequence.add(new Integer(3));
        sequence.add(new Integer(5));
        return sequence;
    }
}
