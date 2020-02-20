/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImprovisationManagement;

import DataDefinition.Chord;
import DataDefinition.Note;
import DataDefinition.TupleNameTypeImpRule;
import ImprovisationRules.ImprovisationPurelyRandom;
import ImprovisationRules.Util;
import Style.AbstractStyle;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author gasto_000
 */
public class ImprovisationCreator {
    private List<Note> improvisation;
    private List<Chord> base;
    private AbstractStyle style;
    public ImprovisationCreator(List<Chord> base, AbstractStyle style){
        this.base=base;
        improvisation = new ArrayList<Note>();
        this.style = style;
    }

   

    public void ApplyImprovisationRuleGenerate(GenericImprovisationRule improvisationRule){
        improvisationRule.style =this.style;
        this.improvisation = improvisationRule.GenerateImprovisation(this.base);
    //    for (int i = 0; i < this.improvisation.size(); i++) {
      //      System.out.println("Nota: " + this.improvisation.get(i) + "Acorde : " + Util.LookForBaseChord(base, improvisation, i).GetGrade());
            
     //   }
    }
    public void setImprovisation(List<Note> improvisation){ this.improvisation= improvisation;}
    public void ApplyImprovisationRuleInAMoment(GenericImprovisationRule improvisationRule,int momentToApply){
        improvisationRule.style =this.style;
        this.improvisation=improvisationRule.ApplyRuleInAMoment(this.improvisation, this.base, momentToApply);
    }
     
    public void ApplyImprovisationRuleVerifyAndCorrect(GenericImprovisationRule improvisationRule){
        improvisationRule.style =this.style;
        Pair<List<Note>,List<Chord>> sol= improvisationRule.VerifyAndCorrectImprovisation(this.improvisation,this.base);
        this.improvisation=sol.getKey();
        this.base = sol.getValue();
    }
    /**
     * @return the improvisation
     */
    public List<Note> getImprovisation() {
        return improvisation;
    }



    /**
     * @return the base
     */
    public List<Chord> getBase() {
        return base;
    }

    /**
     * @param base the base to set
     */
    public void setBase(List<Chord> base) {
        this.base = base;
    }
    

    
}
