/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Converters;

import ConverterUtil.DurationConvertionUtil;
import DataDefinition.Chord;
import static LilyPondPartitureCreator.Util.getDuration;
import static LilyPondPartitureCreator.Util.getPitchLetterWithInt;
import java.util.List;
import static jm.constants.Durations.CT;
import jm.music.data.Note;


public class ChordToLilyPondParse {

    int key;

    public ChordToLilyPondParse(int key) {
        this.key = key;
    }

    public String[] parseChordList(List<Chord> chordList) {
        String[] result = new String[chordList.size()];
        for (int i = 0; i < chordList.size(); i++) {
            Chord chord = chordList.get(i);
            String parsedChord = "";
            String seventh = "";
            String duration = "";
            if (chord.hasSeventh()) {
                seventh = ":7";
            }

            double chordDuration = DurationConvertionUtil.convertDuration((chord.getDuration() * 0.9) * 4);
            seventh = chord.hasSeventh ? ":7" : "";
            int keyPlus = 0;

            switch (chord.getGrade()) {
                case 1:
                    keyPlus = 0;
                    break;
                case 2:
                    keyPlus = 2;                    
                    break;
                case 3:
                    keyPlus = 4;
                    break;
                case 4:
                    keyPlus = 5;
                    break;
                case 5:
                    keyPlus = 7;
                    break;
                case 6:
                    keyPlus = 9;
                    break;
                case 7:
                    keyPlus = 11;
                    break;
            }

            parsedChord += getPitchLetterWithInt(key + keyPlus, 0) + getDuration(chordDuration) + seventh;

            result[i] = parsedChord;

        }
        
        return result;

    }


}