package RhythmRules;

import ConstantDefinition.ConstantsDefinition;
import DataDefinition.Chord;
import DataDefinition.Note;
import DataDefinition.Rhythm;
import DataDefinition.TupleChordDuration;
import RhythmCreator.GenericRhythmRule;
import java.util.ArrayList;
import java.util.List;

public class SimpleRhythmCreator extends GenericRhythmRule {

    @Override
    public Rhythm GenerateRhythm(List<Chord> base) {
        Rhythm rhythmSequence = new Rhythm();

        for (int i = 0; i < base.size(); i++) {
            Chord chord = base.get(i);

            int factor = chord.getDuration();
            int blackFigure = ConstantsDefinition.getInstance().getBlackFigure();
            int calculateFactor = (int) Math.rint(Math.random() * 4) + 1;
            if (chord.getDuration() >= blackFigure * 8) {
                switch (calculateFactor) {
                    case 1:
                        factor = 32;
                        break;
                    case 4:
                        factor = 24;
                        break;
                    case 2:
                        factor = 16;
                        break;
                    case 3:
                        factor = 8;
                        break;
                }
            } else if (chord.getDuration() >= blackFigure * 4) {
                switch (calculateFactor) {
                    case 1:
                        factor = 16;
                        break;
                    case 4:
                        factor = 12;
                        break;
                    case 2:
                        factor = 8;
                        break;
                    case 3:
                        factor = 4;
                        break;
                }
            } else if (chord.getDuration() == blackFigure * 2) {
                switch (calculateFactor) {
                    case 1:
                        factor = 6;
                        break;
                    case 2:
                        factor = 8;
                        break;
                    case 3:
                        factor = 4;
                        break;
                    case 4:
                        factor = 2;
                        break;
                }
            } else if (chord.getDuration() == blackFigure) {
                switch (calculateFactor) {
                    case 1:
                        factor = 3;
                        break;
                    case 2:
                        factor = 1;
                        break;
                    case 3:
                        factor = 4;
                        break;
                    case 4:
                        factor = 2;
                        break;
                }
            } else if (chord.getDuration() == blackFigure / 2) {
                switch (calculateFactor) {
                    case 1:
                        factor = 2;
                        break;
                    case 2:
                        factor = 1;
                        break;
                    case 3:
                        factor = 2;
                        break;
                    case 4:
                        factor = 2;
                        break;
                }
            }

            for (int j = 0; j < factor; j++) {
                rhythmSequence.addRhythmNode(new TupleChordDuration(chord, (double) chord.getDuration() / factor));
            }
        }
        return rhythmSequence;
    }

}
