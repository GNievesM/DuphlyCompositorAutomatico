package RuleCreationFramework.ModifyExcerpt.ModifyRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.List;


public abstract class ModificationCondition {

    public abstract boolean checkCondition(List<Chord> base, List<Note> melody, Double start, Double end);

    public abstract List<Double> listFaultyPositions(List<Chord> base, List<Note> melody, Double start, Double end);
}
