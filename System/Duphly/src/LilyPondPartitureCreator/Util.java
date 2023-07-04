/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LilyPondPartitureCreator;

import java.io.IOException;
import jm.JMC;
import jm.music.data.*;

public class Util {

    public static String getLetraNota(Note nota) {
        String letra = "";
        int pitch = nota.getPitch();
        switch (pitch % 12) {
            case 0:
                letra = "c";
                break;
            case 1:
                if (nota.isSharp()) {
                    letra = "cis";
                } else {
                    letra = "ces";
                }

                break;
            case 2:
                letra = "d";
                break;
            case 3:
                if (nota.isSharp()) {
                    letra = "dis";
                } else {
                    letra = "des";
                }

                break;
            case 4:
                letra = "e";
                break;
            case 5:
                letra = "f";
                break;
            case 6:
                if (nota.isSharp()) {
                    letra = "fis";
                } else {
                    letra = "fes";
                }

                break;
            case 7:
                letra = "g";
                break;
            case 8:
                if (nota.isSharp()) {
                    letra = "gis";
                } else {
                    letra = "ges";
                }

                break;
            case 9:
                letra = "a";
                break;
            case 10:
                if (nota.isSharp()) {
                    letra = "ais";
                } else {
                    letra = "aes";
                }

                break;
            case 11:
                letra = "b";
                break;
        }

        return letra;
    }

    public static String getPitchLetterWithInt(int pitch, int accidente) {
        //accidente segun el valor significa: 0 ninguno, 1 que la nota sostenida ,2 que la nota es una nota bemol
        String letra = "";

        switch (pitch % 12) {
            case 0:
                letra = "c";
                break;
                
            case 1:
                if (accidente == 1) {
                    letra = "cis";
                } else {
                    letra = "ces";
                }
                break;
                
            case 2:
                letra = "d";
                break;
                
            case 3:
                if (accidente == 1) {
                    letra = "dis";
                } else {
                    letra = "des";
                }
                break;
                
            case 4:
                letra = "e";
                break;
                
            case 5:
                letra = "f";
                break;
                
            case 6:
                if (accidente == 1) {
                    letra = "fis";
                } else {
                    letra = "fes";
                }
                break;
                
            case 7:
                letra = "g";
                break;
                
            case 8:
                if (accidente == 1) {
                    letra = "gis";
                } else {
                    letra = "ges";
                }
                break;
                
            case 9:
                letra = "a";
                break;
                
            case 10:
                if (accidente == 1) {
                    letra = "ais";
                } else {
                    letra = "aes";
                }
                break;
                
            case 11:
                letra = "b";
                break;
        }

        return letra;
    }

    public static int getOctava(Note nota) {
        int pitch = nota.getPitch();
        int numero = ((pitch - 12) - ((pitch - 12) % 12)) / 12;
        return numero;
    }

    public static String getDuration(double duration ) {
        double dur = 9.6 / duration;
        String durFinal= "";
        if (dur<1) {
            return "\\breve ";
        }
        /*esta cuenta de aca es para saber si la nota tiene puntillo,
        la manera de saberlo es si la duracion tiene coma en ese caso se le hace
        otra operacion para adaptar el numero
        */
        if (dur != (int) dur) {
            int dur2 = (int) (2 * (7.2 / duration));
            durFinal = dur2 + ".";
        } else {
            int durint = (int) dur;
            durFinal = durint + "";
        }
 
        return durFinal;
    }
}
