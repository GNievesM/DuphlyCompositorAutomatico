/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImprovisationRules;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationManagement.GenericImprovisationRule;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author gasto_000
 */
public class DashedDirectionContinuity extends GenericImprovisationRule{

    int continuityConstant = 12;
    int dashSize = 3;
    @Override
    public List<Note> GenerateImprovisation(List<Chord> base) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pair<List<Note>,List<Chord>> VerifyAndCorrectImprovisation(List<Note> improvisation, List<Chord> base) {
        Pair<List<Note>,List<Chord>> par = new Pair<List<Note>,List<Chord>>(this.AnotherImplementation(base, improvisation),base);
        
        return par;
       
    }

    private List<Note> AnotherImplementation(List<Chord> base, List<Note> improvisation){
    boolean goingUp=true;
    int total = this.continuityConstant;
    for (int i = 0; i < improvisation.size()-1; i++) {
        if(total == this.continuityConstant){
            if(Util.IsHIgherOrEqualPitchThan(improvisation.get(i), improvisation.get(i+1)))
                goingUp=false;
            else
                goingUp=true;
  
        } 
        else{ if(improvisation.size()< i + dashSize+1 )
                  i+=dashSize;
                   if(Util.IsHIgherOrEqualPitchThan(improvisation.get(i), improvisation.get(i+1)) && goingUp == true){
                 
                    Note n = improvisation.get(i+1);
                    int notePitch = n.getNote();
                    int noteOctave = n.getOctave();
                    int extendedNotationNote = notePitch + (noteOctave * 12);
                    Chord c = Util.LookForBaseChord(base, improvisation, i);
                    switch(notePitch -Util.CalcChord(c)){
                        case 0 : notePitch = (extendedNotationNote+3) % 12; 
                                noteOctave = (extendedNotationNote+3)/12;
                                break;
                        case 3 : notePitch = (extendedNotationNote+2) % 12; 
                                noteOctave = (extendedNotationNote+2)/12;
                                break;
                        case 5 : notePitch = (extendedNotationNote+1) % 12; 
                                noteOctave = (extendedNotationNote+1)/12;
                                break;
                        case 6 : notePitch = (extendedNotationNote+1) % 12; 
                                noteOctave = (extendedNotationNote+1)/12;
                                break;
                        case 7 : notePitch = (extendedNotationNote+3) % 12; 
                                noteOctave = (extendedNotationNote+3)/12;
                                break;
                                
                        case 10 : notePitch = (extendedNotationNote+2) % 12; 
                                noteOctave = (extendedNotationNote+2)/12;
                                break;
                    }
                    n.setNote(notePitch);
                    n.setOctave(noteOctave);
                    improvisation.set(i+1,n );
                    total--;
                            }
                  if(Util.IsHIgherOrEqualPitchThan(improvisation.get(i+1), improvisation.get(i))&& goingUp == false){
                         Note n = improvisation.get(i+1);
                    int notePitch = n.getNote();
                    int noteOctave = n.getOctave();
                    int extendedNotationNote = notePitch + (noteOctave * 12);
                    Chord c = Util.LookForBaseChord(base, improvisation, i);
                    switch(notePitch -Util.CalcChord(c)){
                        case 0 : notePitch = (extendedNotationNote-2) % 12; 
                                noteOctave = (extendedNotationNote-2)/12;
                                break;
                        case 3 : notePitch = (extendedNotationNote-3) % 12; 
                                noteOctave = (extendedNotationNote-3)/12;
                                break;
                        case 5 : notePitch = (extendedNotationNote-2) % 12; 
                                noteOctave = (extendedNotationNote-2)/12;
                                break;
                        case 6 : notePitch = (extendedNotationNote-1) % 12; 
                                noteOctave = (extendedNotationNote-1)/12;
                                break;
                        case 7 : notePitch = (extendedNotationNote-1) % 12; 
                                noteOctave = (extendedNotationNote-1)/12;
                                break;
                                
                        case 10 : notePitch = (extendedNotationNote-3) % 12; 
                                noteOctave = (extendedNotationNote-3)/12;
                                break;
                    }
                    n.setNote(notePitch);
                    n.setOctave(noteOctave);
                    improvisation.set(i+1,n );
                    total --;
                            }
                  //i++;
                  if(total == 0 )
                      total = this.continuityConstant;
                  
            }
            
            
        }
 
            
        
        return improvisation;
    }
   
    
    @Override
    public boolean VerifyImprovisationAppliesRule(List<Note> improvisation, List<Chord> base) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Note> ApplyRuleInAMoment(List<Note> improvisation, List<Chord> base, int momentToApply) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
