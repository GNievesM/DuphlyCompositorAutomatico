/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Converters;

import ConverterUtil.DurationConvertionUtil;
import DataDefinition.Note;
import java.util.List;
import static jm.constants.Durations.CT;
import static jm.constants.ProgramChanges.BASS;
import static jm.constants.ProgramChanges.BASSOON;
import static jm.constants.ProgramChanges.FLUTE;
import static jm.constants.ProgramChanges.PIANO;
import static jm.constants.ProgramChanges.SAX;
import static jm.constants.ProgramChanges.VIOLA;
import static jm.music.data.Note.REST;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 *
 * @author gasto_000
 */
public class ImprovisationToJmusicConverter {

    public Part ConvertImprovisation(List<Note> improvisation, int index) {

        if(improvisation == null)
            return null;
        int[] pitchArray = new int[improvisation.size()];
        double[] rhythmArray = new double[improvisation.size()];
        for (int i = 0; i < improvisation.size(); i++) {
            Note n = improvisation.get(i);
            if ((n.getNote() + 12 * n.getOctave()) >= 0) {
                pitchArray[i] = (n.getNote() + 12 * n.getOctave())-1;
            } else {
                pitchArray[i] = REST;
            }
            rhythmArray[i] = DurationConvertionUtil.convertDuration(n.getDuration());

        }

        Phrase phrase1 = new Phrase();
        
        phrase1.addNoteList(pitchArray, rhythmArray);
        if(index == 0)
            phrase1.setDynamic(90);
        if(index == 1)
            phrase1.setDynamic(60);
        Part p = new Part("BAss", PIANO, 0);
        p.add(phrase1);
        return p;
    }

}
