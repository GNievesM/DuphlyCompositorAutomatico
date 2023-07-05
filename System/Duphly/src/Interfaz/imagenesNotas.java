
package interfaz;

import ConstantDefinition.ConstantsDefinition;
import java.util.ArrayList;

public class imagenesNotas {

    public static String ImagenDuracion(int dur) {
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
        return "";
    }
ƒ
}
