package RuleCreationFramework;

import DataDefinition.Chord;
import DataDefinition.Note;
import java.util.List;

public abstract class FiltroUbicacion {
        public abstract List<Note> satisfecho(List<Note> notas, List<Chord> base);

}
