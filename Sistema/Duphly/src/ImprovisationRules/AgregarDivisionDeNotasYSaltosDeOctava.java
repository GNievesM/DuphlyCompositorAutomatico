/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImprovisationRules;
import ImprovisationRules.CustomRules.*;
import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationManagement.GenericImprovisationRule;
import RuleCreationFramework.*;
import RuleCreationFramework.BlockFilter.*;
import RuleCreationFramework.BlockFilter.BlockSelectors.*;
import RuleCreationFramework.ExcerptFilter.*;
import RuleCreationFramework.ExcerptFilter.ExcerptSelectors.*;
import RuleCreationFramework.FrameworkUtil.*;
import RuleCreationFramework.ModifyExcerpt.*;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.*;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.GuaranteedConditionChecker.*;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.ModificationConditionChecker.*;
import RuleCreationFramework.ModifyExcerpt.ModifyRules.SubstituteModificationRules.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Gaston
 */
public class AgregarDivisionDeNotasYSaltosDeOctava extends GenericImprovisationRule  {

    @Override
    public Pair<List<Note>,List<Chord>> VerifyAndCorrectImprovisation(List<Note> improvisation, List<Chord> base) {
        
      Pair<List<Note>,List<Chord>> m1 = this.GenerarMelodia(this.crearReglaCompleta(), improvisation, base);
        return this.ConcatenarMelodias(improvisation, m1.getKey(),base , m1.getValue());
        
        
    }
    private Pair<List<Note>,List<Chord>> GenerarMelodia(CreatedRule cr, List<Note> melody, List<Chord> base){
    
        return new Pair<> (cr.createMelody(base, melody),base);
    }
    private Pair<List<Note>,List<Chord>> ConcatenarMelodias(List<Note> m1, List<Note> m2, List<Chord> base1, List<Chord> base2){
        return ConcatRules.concatMelody(m1, base1, m2, base2);
    }
    
    private CreatedRule crearReglaCompleta(){
        CreatedRule cr = new CreatedRule(this.style);
        cr.addNewFilter(this.crearContenedorDeFiltrosDeCompases());        
        return cr;
    }
    private BlockFilter crearContenedorDeFiltrosDeCompases(){
        BlockFilter bf = new BlockFilter();
        bf.addExcerptFilter(this.crearContenedorDeFiltrosDePasajesAAplicarse());
        bf.addDependentFilter(this.crearReglaCompletaDeFiltroDeCompases());
        return bf;
    }
    
    private CompleteBlockFilterRule crearReglaCompletaDeFiltroDeCompases(){
        CompleteBlockFilterRule cbfr;
        cbfr = new CompleteBlockFilterRule(2,this.crearCondicionDeFiltroDeCompases(),null,60.0,null,null,false);
        return cbfr;
    }
    
    private BlockConditionSelector crearCondicionDeFiltroDeCompases(){
        //return new Form(true,1,null,3);
        
        //return new NotePercentaje(HelperCustomRule.ComparadorMayor(),1,null,20);
        
        //return new NoteQuantity(HelperCustomRule.ComparadorMenor(), 1,null, 4);
        
        //return new Sequence(null,HelperCustomRule.createNoteSequency());
        
        return new NotesTotal(HelperCustomRule.ComparadorMayor(), 0);
    }
    
    private ExcerptFilter crearContenedorDeFiltrosDePasajesAAplicarse(){
        ExcerptFilter ef = new ExcerptFilter();
        ef.addModification(this.crearContenedorDeModificacionesAAplicarse());
     //   ef.addDependentFilter(this.crearReglaDeFiltroDePasajesCompleta());
        return ef;    
    
    }
    
    private CompleteExcerptFilterRule crearReglaDeFiltroDePasajesCompleta(){
        CompleteExcerptFilterRule cefr;
        cefr = new CompleteExcerptFilterRule(null, 50.0, true, 10, null, null, 2, 3, this.crearCondicionDePasajes(), 0, false);
        return cefr;
    }
    
    private ExcerptConditionSelector crearCondicionDePasajes(){
        //return new ContinuosNotesTendency(5);
        
        //return new DifferenceBeetweenNotes(HelperCustomRule.ComparadorMayor(),3,3);
        
        //return new Position(3);
        
        //return new Random(10);
        
        //return new Sequency(HelperCustomRule.createGradesSequency(),HelperCustomRule.ComparadorMayor(),null,null,true,3,true);
        
        //return new SmallerThan(5,3);
        
        //return new SpecifiNote(1,null);
        
        //return new SpecificGrade(HelperCustomRule.ComparadorMayor(), 3, 5, HelperCustomRule.ComparadorMenor());
        
        return new BiggerThan(5,4);

    }
    
    private ModifyExcerpt crearContenedorDeModificacionesAAplicarse(){
        ModifyExcerpt me = new ModifyExcerpt();
        me.addModification(this.crearReglaCompletaDeModificacion());
        me.addModification(crearReglaCompletaDeModificacionDeOctava());
        return me;
    }
    
    private CompleteModificationRule crearReglaCompletaDeModificacion(){
        CompleteModificationRule cr;
        cr = new CompleteModificationRule(this.crearCondicionModificacion(),
                                          null,
                                          this.crearCambioParaModificar(),
                                          null,100);
        return cr;
    }
    private CompleteModificationRule crearReglaCompletaDeModificacionDeOctava(){
        CompleteModificationRule cr;
        cr = new CompleteModificationRule(this.crearCondicionModificacion(),
                                          null,
                                          this.crearCambioParaModificarOctava(),
                                          null,50);
        return cr;
    }
    private GuaranteedCondition crearGarantiaModifiacion(){
        //return new GuaranteeDifferenceBetweenNotes(HelperCustomRule.ComparadorMayor(),3);
        
       //return new GuaranteeIntervalBetweenNotes(HelperCustomRule.ComparadorMayor(),3);
       
        /* Note dummyNote = HelperCustomRule.createNote(1, null); // el 1 refiere a la nota Do y el null implica que no me interesa la octava
        return new GuaranteePercentage(dummyNote,(dummyNote.getOctave()!= -1),50,HelperCustomRule.ComparadorMayor());*/
       
        /*Note dummyNote = HelperCustomRule.createNote(1, null); // el 1 refiere a la nota Do y el null implica que no me interesa la octava
       return new GuaranteeTimesANoteAppear(dummyNote,(dummyNote.getOctave()!=-1),10,HelperCustomRule.ComparadorMenor());*/
       
        //return new GuaranteeSequence(true, true, 3);
        
       return new GuaranteeOctave(HelperCustomRule.ComparadorMayor(),4);
        
    }
   
    private ModificationCondition crearCondicionModificacion(){
       return new CheckOctave(HelperCustomRule.ComparadorIgual(),4);        
        
       // return new CheckPercentage(5,30,HelperCustomRule.ComparadorMenor());
        
       // return new CheckSequency(true,true,3,true);
        
        //Note dummyNote = HelperCustomRule.createNote(1, 5); // el 1 refiere a la nota Do y el null implica que no me interesa la octava
        //return new CheckTimesANoteAppear(dummyNote, false, 0,  HelperCustomRule.ComparadorMayor());
        
       // return new DifferenceBetweenNotes(HelperCustomRule.ComparadorMenor(),5);
       // return new IntervalBetweenNotes(HelperCustomRule.ComparadorMayor(),2);
        
    
    
    } 
    
   private SpecificModification crearCambioParaModificar(){
       //return new AddSequency(false,1);
       
       return new ChangeMetric(2);
       
       //return new CopyMelodyNote(true);
         
       //return new SubstituteForASequency(HelperCustomRule.createNoteSequency(),true);
       
       //return new SustituteNote(true);
       
       //return new SustituteRandomValidNote(false);
       
       //return new SustituteValidNote(HelperCustomRule.ComparadorMayor(),3,false);
       
        //return new MoveDemiTones(true,12);
   }
   private SpecificModification crearCambioParaModificarOctava(){
             
        return new MoveDemiTones(true,12);
   }
    
    
    @Override
    public boolean VerifyImprovisationAppliesRule(List<Note> improvisation, List<Chord> base) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Note> ApplyRuleInAMoment(List<Note> improvisation, List<Chord> base, int momentToApply) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     @Override
    public List<Note> GenerateImprovisation(List<Chord> base) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
