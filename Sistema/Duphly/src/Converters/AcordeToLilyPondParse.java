/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Converters;

import ConverterUtil.DurationConvertionUtil;
import DataDefinition.Chord;
import static LilyPondPartitureCreator.Util.getDuration;
import static LilyPondPartitureCreator.Util.getLetraNotaConInt;
import java.util.List;
import static jm.constants.Durations.CT;
import jm.music.data.Note;

/**
 *
 * @author Gaston
 */
public class AcordeToLilyPondParse {

    int clave;

    public AcordeToLilyPondParse(int clave) {
        this.clave = clave;
    }

    public String[] ParseChordList(List<Chord> listaAcordes) {
        String[] retorno = new String[listaAcordes.size()];
        for (int i = 0; i < listaAcordes.size(); i++) {
            Chord acorde = listaAcordes.get(i);
            String acordeParsed = "";
            String septima = "";
            String Duration = "";
            if (acorde.GetSeptima()) {
                septima = ":7";
            }

            double duration = DurationConvertionUtil.convertDuration((acorde.GetDuration() * 0.9)*4 );
//            if (getDuration(duration) < 1) {
//
//            }
            switch (acorde.GetGrade()) {
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
                case 3:
                    if (acorde.GetSeptima()) {
                        septima = ":7";
                    }
                    acordeParsed += getLetraNotaConInt(clave + 4, 0) + getDuration(duration) + septima;
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
                case 6:
                    if (acorde.GetSeptima()) {
                        septima = ":7";
                    }
                    acordeParsed += getLetraNotaConInt(clave + 9, 0) + getDuration(duration) + septima;
                    break;
                case 7:
                    if (acorde.GetSeptima()) {
                        septima = ":7";
                    }
                    acordeParsed += getLetraNotaConInt(clave + 11, 0) + getDuration(duration) + septima;
                    break;
                //}  
            }

            retorno[i] = acordeParsed;

        }
        return retorno;

    }

}