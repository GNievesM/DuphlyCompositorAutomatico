/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

//import ChordManagement.Acorde;
//import ChordManagement.BaseChordCreator;
import ConstantDefinition.ConstantsDefinition;
import java.util.ArrayList;

/**
 *
 * @author alumnoFI
 */
public class imagenesNotas {

    public static String ImagenDuracion(int dur) {
        //el switch no se puede con double

        if (dur == ConstantsDefinition.getInstance().GetBlackFigure() * 8) {
            return "src\\Cuadrada.PNG";
        }

        if (dur == ConstantsDefinition.getInstance().GetBlackFigure() * 4) {
            return "src\\redonda.PNG";
        }

        if (dur == ConstantsDefinition.getInstance().GetBlackFigure() * 2) {
            return "src\\blanca.PNG";
        }
        if (dur == ConstantsDefinition.getInstance().GetBlackFigure()) {
            return "src\\negra.PNG";

        }
        if (dur == ConstantsDefinition.getInstance().GetBlackFigure() / 2) {
            return "src\\corchea.PNG";
        }
        //poner un if si es entero
        /*
         if (dur == 0.25) {
         return "D:\\JavaApplication2\\src\\semicorchea.PNG";
         }
         if (dur == 0.125) {
         return "D:\\JavaApplication2\\src\\fusa.PNG";
         }
         if (dur == (1 / 16)) {
         return "D:\\JavaApplication2\\src\\semifusa.PNG";
         }*/

        //no encontro el numero, 
        return "";
    }

}
