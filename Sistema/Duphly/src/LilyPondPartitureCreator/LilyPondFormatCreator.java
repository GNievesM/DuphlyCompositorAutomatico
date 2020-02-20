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

    public void LilyPondCreator(List<Note> partiture, String[] chords,String ruta) {

        String[] chordsPrueba = chords;
        List<Note> na = partiture;

//        if (partiture != null) {
//            na = partiture.getPhrase(0).getNoteArray();
//        }

        /*partitura es el string que se va escribir en el txt y luego va a el lilypond
         es el texto total
         */
        String partitura = "";
        int trillizos = 0;
        boolean ComienzoTriple = false;

        //recorro el array de notas
        for (int i = 0; i < na.size(); i++) {
            Note notaActual = na.get(i);
            int p = na.get(i).getPitch();
           
            //esto es para hallar la octava, apartir de el pitch de jmusic
            /* c0 d0 e0...b0 c1..c2...
             el p-12 es para sacarle los N1 que habia antes del cero,luego lo 
             le resto la diferencia hasta el c hay es un numero de divisible entre 12
             12 es el numero de semitonos entre un numero y otro, y luego lo divido entre 12 y obtengo la octava
            
             e4= 64= 52-(52%12)/12== (52-4)/12=4
             */
            String letra = "";
            int numero = ((p - 12) - ((p - 12) % 12)) / 12;
            String comillaNumero = "";

        
            
            //aca se hizo un cambio y se bajo todo una octava
            if(notaActual.getPitch()==REST){comillaNumero ="";letra="r";}
            else{
                switch (numero) {

                    case 1:
                        comillaNumero = ",,,";
                        break;
                    case 2:
                        comillaNumero = ",,";
                        break;
                    case 3:
                        comillaNumero = ",";
                        break;
                    case 4:
                        comillaNumero = "";
                        break;
                    case 5:
                        comillaNumero = "\'";
                        break;
                }
            
            //esta es la seccion de hallar la letra osea la nota
            
                switch (p % 12) {
                    case 0:
                        letra = "c";
                        break;
                    case 1:
                        if (notaActual.isSharp()) {
                            letra = "cis";
                        } else {
                            letra = "des";
                        }

                        break;
                    case 2:
                        letra = "d";
                        break;
                    case 3:
                        if (notaActual.isSharp()) {
                            letra = "dis";
                        } else {
                            letra = "ees";
                        }

                        break;
                    case 4:
                        letra = "e";
                        break;
                    case 5:
                        letra = "f";
                        break;
                    case 6:
                        if (notaActual.isSharp()) {
                            letra = "fis";
                        } else {
                            letra = "ges";
                        }
                        
                        break;
                    case 7:
                        letra = "g";
                        break;
                    case 8:
                        if (notaActual.isSharp()) {
                            letra = "gis";
                        } else {
                            letra = "aes";
                        }

                        break;
                    case 9:
                        letra = "a";
                        break;
                    case 10:
                        if (notaActual.isSharp()) {
                            letra = "ais";
                        } else {
                            letra = "bes";
                        }

                        break;
                    case 11:
                        letra = "b";
                        break;
                 }
            }
         
            //aca se multiplica por 4
            //aca se modifica la duracion para adaptarla al formato de lily 
            double dur = 9.6 / notaActual.getDuration();
            // double dur = ConstantsDefinition.getInstance().GetBlackFigureForLilyPond() * 4 / notaActual.getDuration();
            String dur3 = "";
            /*esta cuenta de aca es para saber si la nota tiene puntillo,
             la manera de saberlo es si la duracion tiene coma en ese caso se le hace
             otra operacion para adaptar el numero
             */
            if (dur != (int) dur) {
                int dur2 = (int) (2 * (7.2 / notaActual.getDuration()));
                dur3 = dur2/4 + ".";
            } else {
                int durint = (int) dur;
                dur3 = durint/4 + "";
            }

            System.out.println("duracion1 : " + notaActual.getDuration() + " duracion2: " + dur3);
//            partitura += letra + " ";

            if ((Integer.parseInt(dur3) % 3 == 0) && !ComienzoTriple) {
                trillizos = 2;
                ComienzoTriple = true;
            }

            if (ComienzoTriple) {

                switch (trillizos) {
                    case 2:
                        letra = " \\tuplet 3/2 {"+letra;

                        int durTupla = (int) (Integer.parseInt(dur3) / 1.5);
                        letra = letra + comillaNumero + durTupla;
                        trillizos--;
                        break;

                    case 1:
                        letra = letra + comillaNumero;
                        trillizos--;
                        break;
                    case 0:
                        ComienzoTriple = false;
                        letra = letra + comillaNumero;
                        letra += "}";
                        break;
                }
            } else {
                if (!ComienzoTriple) {
                    letra = letra + comillaNumero + dur3;
                }
             }
            partitura += letra + " ";
            
        
         
        }
        String ChordAGuardar = "";
        ArchivoGrabacion arch = new ArchivoGrabacion(ruta+".ly");
        arch.grabarLinea("\\version" + " \"" + "2.18.2" + "\"");
        //parte de los chords
        arch.grabarLinea("<<");
        arch.grabarLinea("\\chords {");
        for (int i = 0; i < chordsPrueba.length; i++) {
            ChordAGuardar += chordsPrueba[i] + " ";
        }
        arch.grabarLinea(ChordAGuardar);
        arch.grabarLinea("}");

        //lo de las notas y todo eso
        arch.grabarLinea("{ ");
        arch.grabarLinea("\\clef bass");
        arch.grabarLinea(partitura);
        arch.grabarLinea("}");
        arch.grabarLinea(">>");
        arch.cerrar();
    }
    /*
     public String[] ParseChordList(List<Chord> listaChords) {
     String[] retorno = new String[listaChords.size()];
     for (int i = 0; i < listaChords.size(); i++) {
     Chord acorde = listaChords.get(i);
     String acordeParsed = "";
     String septima = "";
     //Note n = new Note();
     double duration = (((CT * 2) / 4 * acorde.GetDuracion()) * 2) * 0.9;
     switch (acorde.GetGrado()) {
     case 1:
     if (acorde.GetSeptima()) {
     septima = ":7";
     }
     acordeParsed += getLetraNotaConInt(clave, 0) + getDuration(duration) + septima;
     break;
     case 2:
     if (acorde.GetSeptima()) {
     septima = ":7";
     }
     acordeParsed += getLetraNotaConInt(clave + 2, 0) + getDuration(duration) + septima;
     break;
     case 4:
     if (acorde.GetSeptima()) {
     septima = ":7";
     }
     acordeParsed += getLetraNotaConInt(clave + 5, 0) + getDuration(duration) + septima;
     break;
     case 5:
     if (acorde.GetSeptima()) {
     septima = ":7";
     }
     acordeParsed += getLetraNotaConInt(clave + 7, 0) + getDuration(duration) + septima;
     break;

     //}  
     }
     retorno[i] = acordeParsed;

     }
     return retorno;

     }
     */
}
