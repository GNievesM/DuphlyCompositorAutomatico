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
public class DirectionContinuityImplementation2 extends GenericImprovisationRule{

   
    @Override
    public List<Note> GenerateImprovisation(List<Chord> base) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pair<List<Note>,List<Chord>> VerifyAndCorrectImprovisation(List<Note> improvisation, List<Chord> base) {
       
        
        for (int i = 0; i < improvisation.size()-2; i++) {
            if(Util.IsHIgherOrEqualPitchThan(improvisation.get(i), improvisation.get(i+1)) && Util.IsHIgherOrEqualPitchThan(improvisation.get(i+1), improvisation.get(i+2)) ||
                  Util.IsHIgherOrEqualPitchThan(improvisation.get(i+2), improvisation.get(i+1)) && Util.IsHIgherOrEqualPitchThan(improvisation.get(i+1), improvisation.get(i))  )
            {   i+=2; }
            else{
                if(Util.IsHIgherOrEqualPitchThan(improvisation.get(i), improvisation.get(i+1))){
                    Note n = improvisation.get(i+2);
                    int notePitch = n.getNote();
                    int noteOctave = n.getOctave();
                    int extendedNotationNote = notePitch + (noteOctave * 12);
                    Chord c = Util.LookForBaseChord(base, improvisation, i);
                    switch(notePitch - Util.CalcChord(c)){
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
                    improvisation.set(i+2,n );
                            }
                  if(Util.IsHIgherOrEqualPitchThan(improvisation.get(i+1), improvisation.get(i))){
                    Note n = improvisation.get(i+2);
                    int notePitch = n.getNote();
                    int noteOctave = n.getOctave();
                    int extendedNotationNote = notePitch + (noteOctave * 12);
                    Chord c = Util.LookForBaseChord(base, improvisation, i);
                    switch(notePitch - Util.CalcChord(c)){
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
                    improvisation.set(i+2,n );
                            }
                  i+=2;
            }
            
            
        }
 
            
        
        return new Pair<List<Note>,List<Chord>>(improvisation,base);
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
