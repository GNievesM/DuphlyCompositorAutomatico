/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConverterUtil;

import ConstantDefinition.ConstantsDefinition;
import static jm.constants.Durations.CT;

/**
 *
 * @author gasto_000
 */
public class DurationConvertionUtil {
    public static double convertDuration(double duration){
      //  if(duration == (double)ConstantsDefinition.getInstance().GetBlackFigure()*2/3)
      //  System.out.println("Duracion: "+(CT * (duration/ ConstantsDefinition.getInstance().GetBlackFigure())));
     return CT * (duration/ (double)ConstantsDefinition.getInstance().GetBlackFigure());
    }
}
