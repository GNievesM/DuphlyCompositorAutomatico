package LilyPondPartitureCreator;

import java.io.IOException;
import jm.JMC;
import jm.music.data.*;

public class Util {

  public static String getNoteLetter(Note note) {
    String letter = "";
    int pitch = note.getPitch();
    switch (pitch % 12) {
      case 0:
        letter = "c";
        break;
      case 1:
        if (note.isSharp()) {
          letter = "cis";
        } else {
          letter = "ces";
        }
        break;
      case 2:
        letter = "d";
        break;
      case 3:
        if (note.isSharp()) {
          letter = "dis";
        } else {
          letter = "des";
        }
        break;
      case 4:
        letter = "e";
        break;
      case 5:
        letter = "f";
        break;
      case 6:
        if (note.isSharp()) {
          letter = "fis";
        } else {
          letter = "fes";
        }
        break;
      case 7:
        letter = "g";
        break;
      case 8:
        if (note.isSharp()) {
          letter = "gis";
        } else {
          letter = "ges";
        }
        break;
      case 9:
        letter = "a";
        break;
      case 10:
        if (note.isSharp()) {
          letter = "ais";
        } else {
          letter = "aes";
        }
        break;
      case 11:
        letter = "b";
        break;
    }

    return letter;
  }

  public static String getPitchLetterWithAccidental(int pitch, int accidental) {
    String letter = "";

    switch (pitch % 12) {
      case 0:
        letter = "c";
        break;
      case 1:
        if (accidental == 1) {
          letter = "cis";
        } else {
          letter = "ces";
        }
        break;
      case 2:
        letter = "d";
        break;
      case 3:
        if (accidental == 1) {
          letter = "dis";
        } else {
          letter = "des";
        }
        break;
      case 4:
        letter = "e";
        break;
      case 5:
        letter = "f";
        break;
      case 6:
        if (accidental == 1) {
          letter = "fis";
        } else {
          letter = "fes";
        }
        break;
      case 7:
        letter = "g";
        break;
      case 8:
        if (accidental == 1) {
          letter = "gis";
        } else {
          letter = "ges";
        }
        break;
      case 9:
        letter = "a";
        break;
      case 10:
        if (accidental == 1) {
          letter = "ais";
        } else {
          letter = "aes";
        }
        break;
      case 11:
        letter = "b";
        break;
    }

    return letter;
  }

  public static int getOctave(Note note) {
    int pitch = note.getPitch();
    int number = ((pitch - 12) - ((pitch - 12) % 12)) / 12;
    return number;
  }

  public static String getDuration(double duration) {
    double dur = 9.6 / duration;
    String finalDur = "";
    if (dur < 1) {
      return "\\breve ";
    }
    if (dur != (int) dur) {
      int dur2 = (int) (2 * (7.2 / duration));
      finalDur = dur2 + ".";
    } else {
      int durInt = (int) dur;
      finalDur = durInt + "";
    }

    return finalDur;
  }
}
