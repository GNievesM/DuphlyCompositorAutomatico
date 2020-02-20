/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChordManagement;

import DataDefinition.Chord;
import java.util.List;

/**
 *
 * @author Gaston
 */
public class ChordOperationUtil {
    
    
    public static boolean baseNotModifiedYet(List<Chord> chords){
        return chords.size()==6;
    }

    public static Chord SubDominant(Chord c) {
        //Chord retunChord = new Chord(c);
      
      /*  if (c.GetGrade() > 4) {
            retunChord.SetGrade((c.GetGrade() + 3) % 7);
            return retunChord;
        }
        retunChord.SetGrade(c.GetGrade() + 3);*/
        //retunChord.SetGrade(ChordOperationUtil.calcGrade(retunChord.GetGrade(),3));
        //return retunChord;
         return chordCalculus(c,3);
    }
    
    public static int calcGrade(int originalGrade, int gradeSum){
        return ((originalGrade-1+gradeSum)%7)+1;
    }

    public static Chord Dominant(Chord c) {
       // Chord retunChord = new Chord(c);

        /*if (c.GetGrade() > 3) {
            retunChord.SetGrade((c.GetGrade() + 4) % 7);
            return retunChord;
        }
        retunChord.SetGrade(c.GetGrade() + 4);*/
        //retunChord.SetGrade(ChordOperationUtil.calcGrade(retunChord.GetGrade(),4));

        return chordCalculus(c,4);
    }
    
    private static Chord chordCalculus(Chord c, int gradeSum){
        Chord sol = new Chord(c);
        sol.setFlat(c.isFlat());
        sol.setSharp(c.isSharp());
        sol.SetGrade(ChordOperationUtil.calcGrade(sol.GetGrade(),gradeSum));
        return sol;
    }
    
    public static boolean isDominantOf(Chord maybeDominant,Chord c){
       return ChordOperationUtil.Dominant(new Chord(c)).GetGrade() == maybeDominant.GetGrade();
    
    }
    
        
    public static boolean isMinorSuperTonicOf(Chord maybeSuperTonic,Chord c){
       return ChordOperationUtil.supertonic(new Chord(c)).GetGrade() == maybeSuperTonic.GetGrade() && maybeSuperTonic.isMinor();    
    }
    
    public static boolean isMinorLeadingToneWithSeventh(Chord maybeLeading, Chord c){
        return ChordOperationUtil.leadingTone(new Chord(c)).GetGrade()== maybeLeading.GetGrade() && maybeLeading.isMinor() && maybeLeading.GetSeptima();
    }
    
    public static Chord leadingTone(Chord c){
      //  Chord result = new Chord(c);
      //  result.SetGrade(result.GetGrade()+6);
        return chordCalculus(c,6);
    }
    
    public static Chord flatSupertonic(Chord c){
        Chord result = ChordOperationUtil.supertonic(c);
        result.setFlat(true);
        return result;
    }
    
    public static Chord minorSupertonic(Chord c){
        Chord result = ChordOperationUtil.supertonic(c);
        result.setMinor(true);
        return result;
    }
    
    public static Chord supertonic(Chord c){
     //   Chord result = new Chord(c);
     //   result.SetGrade(result.GetGrade()+1);
        return chordCalculus(c,1);
    }
    
    public static Chord minorMediant(Chord c){
        Chord result = ChordOperationUtil.mediant(c);
        result.setMinor(true);
        return result;
    }
    
    public static Chord sharp(Chord c){
        Chord result = new Chord(c);
        result.setSharp(true);
        return result;
    }
    public static Chord diminished(Chord c){
        Chord result = new Chord(c);
        result.setDiminishedMode(true);
        return result;
    }
    public static Chord sharpDiminishedSeventh(Chord c){
        Chord result = new Chord (c);
        result = ChordOperationUtil.diminished(result);
        result = ChordOperationUtil.sharp(result);
        result.SetSeptima(true);
        return result;
    }
    
    public static Chord mediant(Chord c){
      //  Chord result = new Chord(c);
      //  result.SetGrade(result.GetGrade()+2);
      //  return result;
      return chordCalculus(c,2);
    }
}
