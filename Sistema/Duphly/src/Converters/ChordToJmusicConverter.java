/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Converters;

import ConstantDefinition.ConstantsDefinition;
import ConverterUtil.ChordToJmusicUtil;
import ConverterUtil.DurationConvertionUtil;
import DataDefinition.Chord;
import java.util.List;
import static jm.constants.Durations.CT;
import static jm.constants.Pitches.C4;
import static jm.constants.ProgramChanges.BASS;
import static jm.constants.ProgramChanges.FLUTE;
import static jm.constants.ProgramChanges.PIANO;
import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Phrase;

/**
 *
 * @author gasto_000
 */
public class ChordToJmusicConverter {
    //private Score bluesBase= new Score("BluesBase");

    private Part bluesPart;
    private int rootPitch;

    public ChordToJmusicConverter(int rootPitch) {
        bluesPart = new Part("Bass", PIANO, 0);
        this.rootPitch = rootPitch;
        // this.bluesPart  Part flute = new Part("Flute", FLUTE, 0);

    }

    public ChordToJmusicConverter() {
        bluesPart = new Part("Piano", PIANO, 0);
        this.rootPitch = C4;
    }

    private void bluesRythm(Chord chord) {

        double duration = DurationConvertionUtil.convertDuration(chord.GetDuration());
        if (chord.GetDuration() > ConstantsDefinition.getInstance().GetBlackFigure()) {
            this.bluesPart.addCPhrase(ChordToJmusicUtil.CalculateChord(chord, rootPitch, duration / 2));
            this.bluesPart.addCPhrase(ChordToJmusicUtil.CalculateChord(chord, rootPitch, duration * 2 / 6));// le da duracion de dos trecillos de corchea
            this.bluesPart.addCPhrase(ChordToJmusicUtil.CalculateChord(chord, rootPitch, duration * 1 / 6));// le da la duracion del trecillo restante.
        } else {
            this.bluesPart.addCPhrase(ChordToJmusicUtil.CalculateChord(chord,  rootPitch, duration));
        }
    }

    public Part TransformBase(List<Chord> chordList) {
        for (int i = 0; i < chordList.size(); i++) {
            Chord aux = chordList.get(i);
            this.bluesRythm(aux);
        }
        return this.bluesPart;
    }

}
