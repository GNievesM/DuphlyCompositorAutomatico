/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConverterUtil;

import ConstantDefinition.ConstantsDefinition;
import static jm.constants.Durations.CT;

public class DurationConvertionUtil {
    public static double convertDuration(double duration){
     return CT * (duration/ (double)ConstantsDefinition.getInstance().GetBlackFigure());
    }
}
