package LilyPondPartitureCreator;

import ConstantDefinition.ConstantsDefinition;
import archivo.ArchivoGrabacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jm.JMC;
import jm.music.data.*;

public class LilyPondFormatCreator implements JMC {

    private static LilyPondFormatCreator instance = null;

    private LilyPondFormatCreator() {
    }

    public static LilyPondFormatCreator getInstance() {
        if (instance == null) {
            instance = new LilyPondFormatCreator();
        }
        return instance;
    }

    public void createLilyPondFormat(List<Note> partiture, String[] chords, String path) {

        String[] testChords = chords;
        List<Note> noteList = partiture;

        String score = "";
        int triplets = 0;
        boolean startTriplet = false;

        for (int i = 0; i < noteList.size(); i++) {
            Note currentNote = noteList.get(i);
            int pitch = noteList.get(i).getPitch();
            String letter = "";
            int number = ((pitch - 12) - ((pitch - 12) % 12)) / 12;
            String octaveModifier = "";

            if (currentNote.getPitch() == REST) {
                octaveModifier = "";
                letter = "r";
            } else {
                switch (number) {
                    case 1:
                        octaveModifier = ",,,";
                        break;
                    case 2:
                        octaveModifier = ",,";
                        break;
                    case 3:
                        octaveModifier = ",";
                        break;
                    case 4:
                        octaveModifier = "";
                        break;
                    case 5:
                        octaveModifier = "\'";
                        break;
                }

                switch (pitch % 12) {
                    case 0:
                        letter = "c";
                        break;
                    case 1:
                        if (currentNote.isSharp()) {
                            letter = "cis";
                        } else {
                            letter = "des";
                        }
                        break;
                    case 2:
                        letter = "d";
                        break;
                    case 3:
                        if (currentNote.isSharp()) {
                            letter = "dis";
                        } else {
                            letter = "ees";
                        }
                        break;
                    case 4:
                        letter = "e";
                        break;
                    case 5:
                        letter = "f";
                        break;
                    case 6:
                        if (currentNote.isSharp()) {
                            letter = "fis";
                        } else {
                            letter = "ges";
                        }
                        break;
                    case 7:
                        letter = "g";
                        break;
                    case 8:
                        if (currentNote.isSharp()) {
                            letter = "gis";
                        } else {
                            letter = "aes";
                        }
                        break;
                    case 9:
                        letter = "a";
                        break;
                    case 10:
                        if (currentNote.isSharp()) {
                            letter = "ais";
                        } else {
                            letter = "bes";
                        }
                        break;
                    case 11:
                        letter = "b";
                        break;
                }
            }

            double duration = 9.6 / currentNote.getDuration();
            String durationString = "";

            if (duration != (int) duration) {
                int duration2 = (int) (2 * (7.2 / currentNote.getDuration()));
                durationString = duration2 / 4 + ".";
            } else {
                int durationInt = (int) duration;
                durationString = durationInt / 4 + "";
            }

            System.out.println("duration1: " + currentNote.getDuration() + " duration2: " + durationString);

            if ((Integer.parseInt(durationString) % 3 == 0) && !startTriplet) {
                triplets = 2;
                startTriplet = true;
            }

            if (startTriplet) {
                switch (triplets) {
                    case 2:
                        letter = " \\tuplet 3/2 {" + letter;
                        int tripletDuration = (int) (Integer.parseInt(durationString) / 1.5);
                        letter = letter + octaveModifier + tripletDuration;
                        triplets--;
                        break;
                    case 1:
                        letter = letter + octaveModifier;
                        triplets--;
                        break;
                    case 0:
                        startTriplet = false;
                        letter = letter + octaveModifier;
                        letter += "}";
                        break;
                }
            } else {
                if (!startTriplet) {
                    letter = letter + octaveModifier + durationString;
                }
            }

            score += letter + " ";
        }

        String chordsToSave = "";
        ArchivoGrabacion file = new ArchivoGrabacion(path + ".ly");
        file.grabarLinea("\\version" + " \"" + "2.18.2" + "\"");

        file.grabarLinea("<<");
        file.grabarLinea("\\chords {");
        for (int i = 0; i < testChords.length; i++) {
            chordsToSave += testChords[i] + " ";
        }
        file.grabarLinea(chordsToSave);
        file.grabarLinea("}");

        file.grabarLinea("{ ");
        file.grabarLinea("\\clef bass");
        file.grabarLinea(score);
        file.grabarLinea("}");
        file.grabarLinea(">>");
        file.cerrar();
    }
}
