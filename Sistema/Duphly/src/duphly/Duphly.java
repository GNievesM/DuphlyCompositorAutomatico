/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duphly;

import ConstantDefinition.ConstantsDefinition;
import DataDefinition.Chord;
import DataDefinition.Note;
import DataDefinition.PairRuleMoment;
import ImprovisationRules.DirectionContinuity;
import ImprovisationRules.*;
import ImprovisationRules.ImprovisationPurelyRandom;
import ImprovisationRules.LimitJumpsPerBar;
import ImprovisationRules.BluesScaleOnEveryChord;
import Testing.TestImprovisation;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import static jm.constants.Pitches.C4;
import jm.music.data.Part;

/**
 *
 * @author gasto_000
 */
public class Duphly {

    /**
     * @param args the command line arguments
     */
    public static void main1() {
        DuphlyMusicComposer dmc = DuphlyMusicComposer.getInstance();
        List<Chord> newBase = DuphlyMusicComposer.getInstance().CreateSimpleBase();
        List<Pair<Integer, Integer>> rulePairs = new ArrayList<>();
        rulePairs.add(new Pair(0, 2));
        rulePairs.add(new Pair(16, 1));
        rulePairs.add(new Pair(32, 1));
        rulePairs.add(new Pair(48, 1));
        rulePairs.add(new Pair(64, 1));
        rulePairs.add(new Pair(80, 1));
        rulePairs.add(new Pair(64, 3));
        rulePairs.add(new Pair(56, 3));

        ArrayList<PairRuleMoment> rulePairsPosition;
        rulePairsPosition = new ArrayList<>();
        rulePairsPosition.add(new PairRuleMoment(0, 2));
        rulePairsPosition.add(new PairRuleMoment(2, 1));
        rulePairsPosition.add(new PairRuleMoment(4, 1));
        rulePairsPosition.add(new PairRuleMoment(6, 1));
        rulePairsPosition.add(new PairRuleMoment(8, 1));
        rulePairsPosition.add(new PairRuleMoment(10, 1));
        rulePairsPosition.add(new PairRuleMoment(8, 3));
        rulePairsPosition.add(new PairRuleMoment(7, 3));

        //newBase = dmc.ApplyRuleList(newBase, rulePairs);
        newBase = dmc.ApplyRulePerPositionList(newBase, rulePairsPosition);
        //  newBase=dmc.CreateRandomBase();

        dmc.CreateMidiFile(dmc.ConvertChordToJmusic(newBase, C4), "BaseSolo");
        List<Note> improvisation = dmc.ApplyImprovisationRuleGenerate(new ImprovisationPurelyRandom(), newBase);
        dmc.CreateMidiFile(newBase, C4, improvisation, "01-PurelyRandom");
        List<Note> improvisation2 = dmc.ApplyImprovisationRuleVerifyAndCorrect(new DirectionContinuity(), newBase, improvisation).getKey();
        dmc.CreateMidiFile(newBase, C4, improvisation2, "02-PurelyRandomContinuity");
        // improvisation =dmc.ApplyImprovisationRuleVerifyAndCorrect(new DirectionContinuityImplementation2(), newBase, improvisation);
        //  List<Note> improvisation4 = dmc.ApplyImprovisationRuleVerifyAndCorrect(new DirectionContinuity(), newBase, improvisation3);

        //      dmc.CreateMidiFile(newBase, C4, improvisation2, "PurelyRandomContinuity");
        List<Note> improvisation3 = dmc.ApplyImprovisationRuleGenerate(new LimitJumpsPerBar(), newBase);
        dmc.CreateMidiFile(newBase, C4, improvisation3, "03-LimitJumpPerBar");
        List<Note> improvisation4 = dmc.ApplyImprovisationRuleVerifyAndCorrect(new DirectionContinuity(), newBase, improvisation3).getKey();
        dmc.CreateMidiFile(newBase, C4, improvisation4, "04-LimitJumpContinuity");
        //      dmc.CreateMidiFile(newBase, C4, improvisation4, "LimitJumpContinuity");
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        List<Note> imp = dmc.ApplyImprovisationRuleGenerate(new LimitJumpsPerBarNotAbusingBluesNote(), newBase);
        dmc.CreateMidiFile(newBase, C4, imp, "1-LimitJumpPerBarNotAbusignBluesNote");
        List<Note> imp1 = dmc.ApplyImprovisationRuleGenerate(new ImprovisationPurelyRandomNotAbusingBluesNote(), newBase);
        dmc.CreateMidiFile(newBase, C4, imp1, "2-RandomNotAbusignBluesNote");
         List<Note> imp2 = dmc.ApplyImprovisationRuleGenerate(new DirectionContinuity(), newBase);
         dmc.CreateMidiFile(newBase, C4, imp2, "3-DirectionContinuityGenerator");
          List<Note> impAux= new ArrayList<Note>();
          impAux.addAll(imp1);
          List<Note> imp3 = dmc.ApplyImprovisationRuleVerifyAndCorrect(new DashedDirectionContinuity(), newBase, impAux).getKey();
        dmc.CreateMidiFile(newBase, C4, imp3, "4-RandomPlusDashedDirectionContinuity");
        impAux= new ArrayList<Note>();
         impAux.addAll(imp1);
          List<Note> imp4 = dmc.ApplyImprovisationRuleVerifyAndCorrect(new DirectionContinuityAllowingEquals(), newBase, impAux).getKey();
        dmc.CreateMidiFile(newBase, C4, imp4, "5-RandomPlusDashedDirectionContinuityAllowingEquals");
         impAux= new ArrayList<Note>(); 
        impAux.addAll(imp1);
          List<Note> imp5 = dmc.ApplyImprovisationRuleVerifyAndCorrect(new DirectionContinuityNoRepCount(), newBase, impAux).getKey();
        dmc.CreateMidiFile(newBase, C4, imp5, "6-RandomPlusDashedDirectionContinuityNoRepCount");
         impAux= new ArrayList<Note>();
        impAux.addAll(imp1);
          List<Note> imp6 = dmc.ApplyImprovisationRuleVerifyAndCorrect(new NotAbusingBluesNoteEqualModifier(), newBase, improvisation3).getKey();
        dmc.CreateMidiFile(newBase, C4, imp6, "7-LimitJumpPerBarPlusNotAbusingBluesNoteModifier");
         impAux= new ArrayList<Note>();
        impAux.addAll(imp1);
          List<Note> imp7 = dmc.ApplyImprovisationRuleVerifyAndCorrect(new RepeatingNotes(), newBase, improvisation2).getKey();
        dmc.CreateMidiFile(newBase, C4, imp3, "4-PurelyRandom+RepeatingNotes");
        
        List<Note> TEST = dmc.ApplyImprovisationRuleGenerate(new BluesScaleOnEveryChord(), newBase);
        dmc.CreateMidiFile(newBase,C4,TEST, "listeningScales");
      
        List<List<Note>> MelodyList = new ArrayList<List<Note>>();
        MelodyList.add(TEST);
        MelodyList.add(imp);
        dmc.CreateMidiFileMultipleMelodies(newBase,C4, MelodyList, "src/PruebaExtraCon2Melodias");
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(!TestImprovisation.testImprovisation(improvisation, newBase))
            System.out.println("Falle en improvisation");
       if(!TestImprovisation.testImprovisation(improvisation2, newBase))
            System.out.println("Falle en improvisation2");
        if(!TestImprovisation.testImprovisation(improvisation3, newBase))
            System.out.println("falle en improvistion3");
        if(!TestImprovisation.testImprovisation(improvisation4, newBase))
            System.out.println("Falle en improvisation4");
        if(!TestImprovisation.testImprovisation(imp, newBase))
            System.out.println("Falle en imp");
        if(!TestImprovisation.testImprovisation(imp1, newBase))
            System.out.println("Falle en imp1");
      if(!TestImprovisation.testImprovisation(imp2, newBase))
            System.out.println("Falle en imp2");
      if(!TestImprovisation.testImprovisation(imp3, newBase))
            System.out.println("Falle en imp3");
      if(!TestImprovisation.testImprovisation(imp4, newBase))
            System.out.println("falle en imp4");
      if(!TestImprovisation.testImprovisation(imp5, newBase))
            System.out.println("falle en imp5");
      if(!TestImprovisation.testImprovisation(imp6, newBase))
            System.out.println("falle en imp6");
      if(!TestImprovisation.testImprovisation(imp7, newBase))
            System.out.println("falle en imp7"
                    + "");
           if(!TestImprovisation.testImprovisation(TEST, newBase))
            System.out.println("falle en TEST"
                    + "");
        
        List<Note> blackFigure = new ArrayList<Note>();
        blackFigure.add(new Note(ConstantsDefinition.getInstance().GetBlackFigure(), C4, 3, 'b'));
        Part p = dmc.ConvertImprovisationToJmusic(blackFigure,0);
        double duracionnegra = ConstantsDefinition.getInstance().GetBlackFigureForLilyPond();
    //    System.out.println("DuracionNegra : " + duracionnegra);
       // System.out.println("DuracionBlanca: " + duracionnegra * 2);
      //  System.out.println("duracionREdonda: " + duracionnegra * 4);
      //  System.out.println("duracionCorchea: 0" + duracionnegra / 2);
      //  System.out.println("duracionSemiCorche: " + duracionnegra / 4);
       // System.out.println("duraciontrecillodecorche: " + duracionnegra / 3);
       // System.out.println("duraciontrecillosdeblanca:" + (duracionnegra * 2) / 3);
       // for (int i = 0; i < newBase.size(); i++) {
        //    System.out.println(newBase.get(i).GetGrade() + "  :  " + newBase.get(i).GetSeptima() + " Duracion :" + newBase.get(i).GetDuration());

        //}
    }

}
