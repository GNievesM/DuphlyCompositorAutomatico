/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RhythmRules;

import ConstantDefinition.ConstantsDefinition;
import DataDefinition.Chord;
import DataDefinition.Note;
import DataDefinition.Rhythm;
import DataDefinition.TupleChordDuration;
import RhythmCreator.GenericRhythmRule;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gasto_000
 */
public class SimpleRhythmCreator extends GenericRhythmRule{

    @Override
    public Rhythm GenerateRhytm(List<Chord> base) {
      Rhythm rhythmSequence = new Rhythm();
      
        for (int i = 0; i <base.size() ; i++) {
        Chord acord = base.get(i);
        
        int factor = acord.GetDuration();
        int blackFigure=ConstantsDefinition.getInstance().GetBlackFigure();
        int calculateFactor = (int) Math.rint(Math.random()*4) +1;
           if(acord.GetDuration()>=blackFigure*8)
            switch(calculateFactor){
                case 1: factor=32;break;
              //  case 1: factor=8;break;
                case 4:factor = 24;break;
              //  case 4: factor = 4;break;
                case 2: factor = 16;break;
                case 3:factor = 8;break;
               
            }           
        else
        if(acord.GetDuration()>=blackFigure*4)
            switch(calculateFactor){
                case 1: factor=16;break;
              //  case 1: factor=8;break;
                case 4:factor = 12;break;
              //  case 4: factor = 4;break;
                case 2: factor = 8;break;
                case 3:factor = 4;break;
               
            }           
        else
            if(acord.GetDuration() == blackFigure*2)
                switch(calculateFactor){
                    case 1:factor = 6;break;
                 //   case 1 : factor = 2;break;
                case 2: factor = 8;break;
                  //  case 2: factor = 4;break;
                case 3:factor = 4;break;
                case 4:factor = 2;break;
               
            }           
            else if (acord.GetDuration()== blackFigure)
                switch(calculateFactor){
                case 1:factor = 3;break;
               //     case 1: factor = 2;break;
                case 2: factor = 1;break;
                case 3:factor = 4;break;
                case 4:factor = 2;break;
             //   case 3:factor = 1;break;
               
            }           
            else if(acord.GetDuration()==blackFigure/2)
              switch(calculateFactor){
          
                  case 1:factor = 2;break;
                case 2: factor = 1;break;
            //     case 1:factor = 1;break;
            //     case 3:factor = 2;break;
             //     case 4:factor =1;break;
                case 3:factor = 2;break;
                case 4:factor = 2;break;
               
            }           
            for (int j = 0; j < factor; j++) {
                if((double)((double)acord.GetDuration()/factor)== (double)ConstantsDefinition.getInstance().GetBlackFigure()*2/3){
             //       System.out.println("cree una blanca trecillo de valor:" +(double)ConstantsDefinition.getInstance().GetBlackFigure()*2/3);
              //      System.out.println("Acordes base: " + acord.GetDuration());   
              //      System.out.println("factor: " + factor);
               //     System.out.println("Resultado de notas:" + (double)((double)acord.GetDuration()/factor));
                }
                   rhythmSequence.AddRhythmNode(new TupleChordDuration(acord, (double)((double)acord.GetDuration()/factor)));
            }
   
        }
        return rhythmSequence;
    }
    
}
