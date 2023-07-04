/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChordManagement;

import ChordRules.ChordRule0;
import ChordRules.ChordRule1;
import ChordRules.ChordRule2;
import ChordRules.ChordRule3;
import ChordRules.ChordRule3a2;
import ChordRules.ChordRule3b;
import ChordRules.ChordRule4;
import ChordRules.ChordRule5;
import ChordRules.ChordRule6a;
import ChordRules.ChordRule6b;
import ChordRules.ChordRule6c;
import ChordRules.ChordRuleOpA1;
import ChordRules.ChordRuleOpA2;
import ChordRules.ChordRuleOpA3;
import ChordRules.ChordRuleOpA4;
import ChordRules.ChordRuleOpB1;
import ChordRules.ChordRuleOpB2;
import ChordRules.ChordRuleOpB3;
import ChordRules.ChordRuleOpC1;
import ChordRules.ChordRuleOpC2;
import ChordRules.ChordRuleOpD1;
import ChordRules.ChordRuleOpD2;
import ConstantDefinition.ConstantsDefinition;
import DataDefinition.Chord;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.util.Pair;


public class BaseChordsCreator {

    private List<Chord> base;

    public List<Chord> getBase() {
        return base;
    }
    

    public BaseChordsCreator() {
        int blackFigure = ConstantsDefinition.getInstance().GetBlackFigure();
        int twoBarFigure = blackFigure * 8;
        base = new ArrayList<>();
        base.add(this.createChord(false, 1, twoBarFigure));
        base.add(this.createChord(true, 1, twoBarFigure));
        base.add(this.createChord(false, 4, twoBarFigure));
        base.add(this.createChord(false, 1, twoBarFigure));
        base.add(this.createChord(true, 5, twoBarFigure));
        base.add(this.createChord(false, 1, twoBarFigure));
        
    }

    public List<Chord> createBaseChords() {
        List<Chord> chordList = new ArrayList<Chord>();
        int blackFigure = ConstantsDefinition.getInstance().GetBlackFigure();
        int twoBarFigure = blackFigure * 8;
        chordList.add(this.createChord(false, 1, twoBarFigure));
        chordList.add(this.createChord(true, 1, twoBarFigure));
        chordList.add(this.createChord(false, 4, twoBarFigure));
        chordList.add(this.createChord(false, 1, twoBarFigure));
        chordList.add(this.createChord(true, 5, twoBarFigure));
        chordList.add(this.createChord(false, 1, twoBarFigure));
        return chordList;
    }

    private Chord createChord(boolean hasSeventh, int grade, int duration) {
        Chord chord = new Chord();
        chord.SetDuration(duration);
        chord.SetGrade(grade);
        chord.SetSeventh(hasSeventh);

        return chord;
    }

    public List<Pair<GenericChordRule, String>> getChordRuleList(){
        List<Pair<GenericChordRule,String>> result = new ArrayList<>();
        result.add(new Pair<>(new ChordRule0(),"Rule 0"));
        result.add(new Pair<>(new ChordRule1(),"Rule 1"));
        result.add(new Pair<>(new ChordRule2(),"Rule 2"));
        result.add(new Pair<>(new ChordRule3(),"Rule 3a1"));
        result.add(new Pair<>(new ChordRule3a2(),"Rule 3a2"));
        result.add(new Pair<>(new ChordRule3b(),"Rule 3b"));
        result.add(new Pair<>(new ChordRule4(),"Rule 4"));
        result.add(new Pair<>(new ChordRule5(),"Rule 5"));
        result.add(new Pair<>(new ChordRule6a(),"Rule 6a"));
        result.add(new Pair<>(new ChordRule6b(),"Rule 6b"));
        result.add(new Pair<>(new ChordRule6c(),"Rule 6c"));
        result.add(new Pair<>(new ChordRuleOpA1(),"Optional a1"));
        result.add(new Pair<>(new ChordRuleOpA2(),"Optional a2"));
        result.add(new Pair<>(new ChordRuleOpA3(),"Optional a3"));
        result.add(new Pair<>(new ChordRuleOpA4(),"Optional a4"));
        result.add(new Pair<>(new ChordRuleOpB1(),"Optional b1")); 
        result.add(new Pair<>(new ChordRuleOpB2(),"Optional b2"));
        result.add(new Pair<>(new ChordRuleOpB3(),"Optional b3"));
        result.add(new Pair<>(new ChordRuleOpC1(),"Optional c1"));
        result.add(new Pair<>(new ChordRuleOpC2(),"Optional c2"));
        result.add(new Pair<>(new ChordRuleOpD1(),"Optional d1"));
        result.add(new Pair<>(new ChordRuleOpD2(),"Optional d2"));
       
           
        return result;
        
    }
    
    public List<String> getRuleList() {
        List<String> ruleList = new ArrayList<String>();
        for (Iterator<Pair<GenericChordRule,String>> iterator = this.getChordRuleList().iterator(); iterator.hasNext();) {
            ruleList.add(iterator.next().getValue());            
        }
        
        return ruleList;

    }

    public List<Chord> applyRuleToBase(GenericChordRule rule, int crotchetToApply, List<Chord> base) {
        return rule.ApplyRule(base, crotchetToApply);
    }
    
    public void applyRuleToObjectBase(GenericChordRule rule, int crotchetToApply) {
        this.base = rule.ApplyRule(this.base, crotchetToApply);
    }
 
    public void applyRuleToObjectPerPosition(GenericChordRule rule, int position) {
        if (position >= this.base.size()) {
            return ;
        }

        int tempo = 0;
        int i = 0;
        while (i < position) {

            tempo += this.base.get(i).GetDuration();
            i++;
        }

        this.base= rule.ApplyRule(this.base, tempo);
    }
    
    public List<Chord> applyRulePerPosition(GenericChordRule rule, int position, List<Chord> base) {
        if (position >= base.size()) {
            return base;
        }

        int tempo = 0;
        int i = 0;
        while (i < position) {

            tempo += base.get(i).GetDuration();
            i++;
        }
        List<Chord> result = rule.ApplyRule(base, tempo);
       
        return result;
    }
}
