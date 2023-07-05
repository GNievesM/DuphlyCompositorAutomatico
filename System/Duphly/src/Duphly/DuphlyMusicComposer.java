package duphly;

import ImprovisationRules.CustomRules.ImprovisationRulesInterface;
import ChordRules.*;
import ConstantDefinition.ConstantsDefinition;
import ChordManagement.BaseChordsCreator;
import DataDefinition.Chord;
import DataDefinition.PairRuleMoment;
import ChordManagement.GenericChordRule;
import Converters.AcordeToLilyPondParse;
import Converters.ChordToJmusicConverter;
import Converters.ImprovisationToJmusicConverter;
import DataDefinition.Note;
import DataDefinition.TupleNameTypeImpRule;
import ImprovisationManagement.GenericImprovisationRule;
import ImprovisationManagement.ImprovisationCreator;
import ImprovisationRules.ImprovisationPurelyRandom;
import ImprovisationRules.*;
import ImprovisationRules.Util;
import ImprovisationRules.BluesScaleOnEveryChord;
import LilyPondPartitureCreator.LilyPondFormatCreator;
import Style.AbstractStyle;
import Style.BluesStyle;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Write;

public class DuphlyMusicComposer {

    private static DuphlyMusicComposer instance = null;
    BaseChordsCreator bcc;
    int blackFigure;
    List<Chord> internalBase;
    List<Note> internalImprovisation;
    private boolean hasBase;
    private boolean hasRules;
    List<Pair<String, String>> melodyRules;
    Score melody;
    ArrayList<Note> improvisationNoteList;
    AbstractStyle style;
    private boolean addBluesScale = true;

    public List<Pair<String, String>> getMelodyRules() {
        return melodyRules;
    }

    public void setInternalBase(List<Chord> internalBase) {
        this.internalBase = internalBase;
    }

    public void setMelodyRules(List<Pair<String, String>> melodyRules) {
        this.melodyRules = melodyRules;
    }

    public Score getMelody() {
        return melody;
    }

    public void setMelody(Score melody) {
        this.melody = melody;
    }

    public ArrayList<Note> getImprovisationNoteList() {
        return improvisationNoteList;
    }

    public void setImprovisationNoteList(ArrayList<Note> improvisationNoteList) {
        this.improvisationNoteList = improvisationNoteList;
    }

    public boolean hasBase() {
        return hasBase;
    }

    public void setHasBase(boolean hasBase) {
        this.hasBase = hasBase;
    }

    public boolean hasRules() {
        return hasRules;
    }

    public void setHasRules(boolean hasRules) {
        this.hasRules = hasRules;
    }

    public List<Chord> getInternalBase() {
        return internalBase;
    }

    public List<Note> getInternalImprovisation() {
        return internalImprovisation;
    }

    protected DuphlyMusicComposer() {
        bcc = new BaseChordsCreator();
        blackFigure = ConstantsDefinition.getInstance(2).GetBlackFigure();
        style = new BluesStyle();
        style.setMaxOctave(6);
        style.setMinOctave(4);
        style.setMelodyRoot(1);
    }

    /**
     * Returns an instance of DuphlyMusicComposer that exposes methods to be used in the system.
     *
     * @return DuphlyMusicComposer instance
     */
    public static DuphlyMusicComposer getInstance() {
        if (instance == null) {
            instance = new DuphlyMusicComposer();
        }
        return instance;
    }

    /**
     * Creates the basic initial base on which rules will be applied.
     *
     * @return List of chords representing the basic base on which rules can be applied.
     */
    public List<Chord> createSimpleBase() {
        this.internalBase = bcc.createBaseChords();
        return bcc.createBaseChords();
    }

    public void newSimpleBase() {
        this.internalBase = this.createSimpleBase();
    }


    /**
     *
     * Given a base, a moment within the music, and a rule number (1-3),
     * applies the specified rule to the chord at that moment.
     *
     * @param crotchetToApply The number representing the moment at which the rule should be applied.
     * @param base The list of chords representing the base that needs to be modified.
     * @param ruleNumber The number of the rule to be applied (1-3).
     * @return The modified list of chords.
     */
    public List<Chord> ApplyRule(int crotchetToApply, List<Chord> base, int ruleNumber) {

        base = bcc.applyRuleToBase(GetRuleBasedOnNumber(ruleNumber), crotchetToApply, base);
        return base;
    }

    public List<Chord> ApplyInternalRule(int crotchetToApply, int ruleNumber) {
        return this.ApplyRule(crotchetToApply, internalBase, ruleNumber);
    }
    
    private GenericChordRule GetRuleBasedOnNumber(int ruleNumber){
       return bcc.GetChordRuleList().get(ruleNumber).getKey();
    }

    /**
     * Given a base, a position within the chord list, and a rule number (1-3),
     * applies the specified rule to the element in the list that contains the chords.
     *
     * @param position The index in the list where the chord is located on which the rule should be applied.
     * @param base The list of chords that make up the base to be modified.
     * @param ruleNumber The rule number to be applied (from 1 to 3).
     * @return The modified list of chords.
     */
    public List<Chord> ApplyRulePerPosition(int position, List<Chord> base, int ruleNumber) {
        GenericChordRule rule = this.GetRuleBasedOnNumber(ruleNumber);
        this.internalBase = bcc.applyRulePerPosition(rule, position, this.internalBase);
        base = bcc.applyRulePerPosition(rule, position, base);
        return base;
    }

    public void ApplyRulePerPosition(int position, int ruleNumber) {
        this.internalBase = this.ApplyRulePerPosition(position, internalBase, ruleNumber);
    }

    /**
     * Receives a base to modify and a list of PairRuleMoment objects (an object that
     * stores a rule and a specific moment within the music to apply the rule).
     *
     * @param base The base on which the rules are applied.
     * @param MomentToApplyAndRuleNumberList The list of moments in the music and rule numbers (1-3).
     * @return The modified list of chords.
     */
    public List<Chord> ApplyRuleList(List<Chord> base, List<PairRuleMoment> MomentToApplyAndRuleNumberList) {

        for (int i = 0; i < MomentToApplyAndRuleNumberList.size(); i++) {
            base = this.ApplyRule((int) MomentToApplyAndRuleNumberList.get(i).getMomentOrPositionToApply(), base, (int) MomentToApplyAndRuleNumberList.get(i).getRule());
        }
        return base;
    }

    public void ApplyInternalRuleList(List<PairRuleMoment> MomentToApplyAndRuleNumberList) {

        this.ApplyRuleList(internalBase, MomentToApplyAndRuleNumberList);
    }

    /**
     * Receives a base to modify and a list of PairRuleMoment objects (an object that
     * stores a rule and a position within the list to apply the rule).
     *
     * @param base The base on which the rules are applied.
     * @param PositionAndRuleNumberList The list of positions in the list and rule numbers (1-3).
     * @return The modified list of chords.
     */
    public List<Chord> ApplyRulePerPositionList(List<Chord> base, List<PairRuleMoment> PositionAndRuleNumberList) {

        for (int i = 0; i < PositionAndRuleNumberList.size(); i++) {
            base = this.ApplyRulePerPosition((int) PositionAndRuleNumberList.get(i).getMomentOrPositionToApply(), base, (int) PositionAndRuleNumberList.get(i).getRule());
        }
        return base;
    }

    public void ApplyInternalRulePerPositionList(List<PairRuleMoment> PositionAndRuleNumberList) {
        this.internalBase = this.ApplyRulePerPositionList(internalBase, PositionAndRuleNumberList);
    }

    /**
     * Returns the list of existing chord rules for the current base.
     *
     * @return The list of existing chord rules.
     */
    public List<String> getExistingChordRules() {
        return this.bcc.ruleList();
    }

    /**
     * Creates a random base. Returns the list of chords.
     *
     * @return The list of chords representing the random base.
     */
    public List<Chord> CreateRandomBase() {
        int ruleQuantity = (int) Math.rint(Math.random() * blackFigure * 8 * 6 * 2);
        List<Chord> base = this.CreateSimpleBase();
        List<PairRuleMoment> rulesMomentToApply = new ArrayList<>();
        for (int i = 0; i < ruleQuantity; i++) {
            int indexToApply = (int) Math.rint(Math.random() * base.size());
            int ruleToApply = (int) Math.rint(Math.random() * (this.bcc.ruleList().size() - 1));
            rulesMomentToApply.add(new PairRuleMoment(indexToApply, ruleToApply));
        }
        return this.ApplyRulePerPositionList(base, rulesMomentToApply);
    }


    public void CreateInternalRandomBase() {
        this.internalBase = this.CreateRandomBase();
    }

    /**
     * Converts a list of chords (base) to a score. For this, it needs the base note
     * to be specified, which is provided by the user.
     *
     * @param base
     * @param baseNote
     * @return
     */
    public Score ConvertChordToJmusic(List<Chord> base, int baseNote) {
        Score bluesBase = new Score();
        ChordToJmusicConverter ctj = new ChordToJmusicConverter();
        bluesBase.addPart(ctj.TransformBase(base));
        bluesBase.setTempo(100);

        return bluesBase;
    }

    public Score ConvertInternalChordToJmusic(int baseNote) {
        Score bluesBase = new Score();
        ChordToJmusicConverter ctj = new ChordToJmusicConverter();
        bluesBase.addPart(ctj.TransformBase(this.internalBase));
        bluesBase.setTempo(70);
        return bluesBase;
    }

    /**
     * Convierte una improvisacion(lista de notas) a un Part de JMusic.
     *
     * @param improvisation
     * @return
     */
    public Part ConvertImprovisationToJmusic(List<Note> improvisation, int index) {

        return new ImprovisationToJmusicConverter().ConvertImprovisation(improvisation,index);
    }

    public Part ConvertInternalImprovisationToJmusic() {

        return new ImprovisationToJmusicConverter().ConvertImprovisation(this.internalImprovisation,0);
    }

    /**
     * Returns the corresponding score of converting an improvisation and a base
     * to jmusic. Receives the user-selected main note or tonic.
     *
     * @param improvisation
     * @param base
     * @param baseNote
     * @return
     */
    public Score ConvertImprovisationAndBaseToJmusic(List<Note> improvisation, List<Chord> base, int baseNote) {
        boolean addBluesScale = this.addBluesScale; // adds the blues scale to the melody.
        Score score;

        if (addBluesScale) {
            List<Note> bass = this.ApplyImprovisationRuleGenerate(new BluesScaleOnEveryChord(), base);
            List<List<Note>> melodyList = new ArrayList<List<Note>>();
            melodyList.add(improvisation);
            melodyList.add(bass);
            score = this.ConvertImprovisationsAndBaseToJmusic(melodyList, base, baseNote);

        } else {
            score = this.ConvertChordToJmusic(base, baseNote);
            score.add(this.ConvertImprovisationToJmusic(improvisation, 0));
            score.setTempo(70);
        }
        return score;
    }

    public Score ConvertImprovisationsAndBaseToJmusic(List<List<Note>> improvisation, List<Chord> base, int baseNote) {
        Score score = this.ConvertChordToJmusic(base, baseNote);
        for (int i = 0; i < improvisation.size(); i++) {
            score.add(this.ConvertImprovisationToJmusic(improvisation.get(i),i));
        }

        score.setTempo(70);
        return score;

    }

    public Score ConvertInternalImprovisationAndBaseToJmusic(int baseNote) {
        Score score = this.ConvertChordToJmusic(this.internalBase, baseNote);
        score.add(this.ConvertImprovisationToJmusic(this.internalImprovisation,0));
        score.setTempo(70);
        return score;

    }

    /**
     * Given the list of chords representing the base, the root note
     * (JMusic representation, e.g., C4 represented by 60, each number
     * represents a semitone), and the list of notes representing the
     * improvisation, write the corresponding MIDI file with the name
     * passed as a parameter without writing the extension.
     *
     * @param base The base used by the user to create the music.
     * @param baseNote The note selected by the user as the tonic or primary note.
     * @param improvisation The list of notes in the generated improvisation.
     * @param midiName The name of the output MIDI file.
     */
    public void CreateMidiFile(List<Chord> base, int baseNote, List<Note> improvisation, String midiName) {
        Write.midi(this.ConvertImprovisationAndBaseToJmusic(improvisation, base, baseNote), midiName + ".mid");
    }

    public void CreateMidiFileMultipleMelodies(List<Chord> base, int baseNote, List<List<Note>> improvisations, String midiName) {
        Write.midi(this.ConvertImprovisationsAndBaseToJmusic(improvisations, base, baseNote), midiName + ".mid");
    }

    public void CreateInternalMidiFile(int baseNote, String midiName) {
        Write.midi(this.ConvertImprovisationAndBaseToJmusic(this.internalImprovisation, this.internalBase, baseNote), midiName + ".mid");
    }

    /**
     * Given a score S, write the corresponding MIDI file under the given name.
     *
     * @param s        The composed music score.
     * @param midiName The name of the MIDI file.
     */
    public void CreateMidiFile(Score s, String midiName) {
        Write.midi(s, midiName + ".mid");
    }

    public List<Note> ApplyImprovisationRuleGenerate(GenericImprovisationRule rule, List<Chord> base) {
        List<Note> improvisation = rule.GenerateImprovisation(base);
        return improvisation;
    }

    public void CrearPartitura(String ruta) {
        AcordeToLilyPondParse a = new AcordeToLilyPondParse(60);

        List<Phrase> listPhrase = DuphlyMusicComposer.getInstance().ConvertImprovisationToJmusic(DuphlyMusicComposer.getInstance().getListaNotasImprovisacion(), 0).getPhraseList();
        ArrayList<jm.music.data.Note> listNotes = new ArrayList<jm.music.data.Note>();

        for (Phrase phrase : listPhrase) {
            listNotes.addAll(phrase.getNoteList());
        }
        LilyPondFormatCreator.getInstance().LilyPondCreator(listNotes, a.ParseChordList(DuphlyMusicComposer.getInstance().getInternalBase()), ruta);
    }


    public ArrayList<jm.music.data.Note> getNotesList() {
        List<Part> listPart = getMelody().getPartList();
        ArrayList<Phrase> listPhrase = new ArrayList<Phrase>();
        ArrayList<jm.music.data.Note> listNotes = new ArrayList<jm.music.data.Note>();
        for (Part parte : listPart) {
            listPhrase.addAll(parte.getPhraseList());
        }
        for (Phrase frase : listPhrase) {
            listNotes.addAll(frase.getNoteList());
        }
        return listNotes;

    }

    public List<Note> ApplyImprovisationRuleInAMoment(GenericImprovisationRule rule, List<Chord> base, List<Note> improvisation) {
        return rule.ApplyRuleInAMoment(improvisation, base, blackFigure);
    }

    public Pair<List<Note>,List<Chord>> ApplyImprovisationRuleVerifyAndCorrect(GenericImprovisationRule rule, List<Chord> base, List<Note> improvisation) {
        Pair<List<Note>,List<Chord>> appliedRule =  rule.VerifyAndCorrectImprovisation(improvisation, base);
        return appliedRule;
    }
    
    /**
     * Returns the list of improvisation rules and their types (Generate, Modify, Apply at a moment). 
     * This list should be displayed in the interface for the user to select which rules to apply and in what form.
     *
     * @return The list of improvisation rule names and types as pairs.
     */
    public List<Pair<String, String>> GetImprovisationRulesNamesAndType() {
        List<Pair<String, String>> returnList = new ArrayList<>();
        List<TupleNameTypeImpRule> existingRules = ImprovisationRulesInterface.getInstance().getRuleList();
        
        for (TupleNameTypeImpRule tuple : existingRules) {
            returnList.add(new Pair(tuple.getDescription(), tuple.getType()));
        }
        
        return returnList;
    }

    /**
     * Receives the list of rule-name and rule-type pairs selected by the user to apply. 
     * These should be the same (i.e., not modified) as the ones returned by the GetImprovisationRulesNamesAndType function.
     *
     * @param nameAndType The list of pairs that the user wants to apply.
     * @param base The base to be used for the specific improvisation.
     * @param improvisation The existing improvisation.
     * @return The created improvisation as a list of notes.
     */
    public List<Note> ApplyImprovisationRuleListByName(List<Pair<String, String>> nameAndType, List<Chord> base, List<Note> improvisation) {
        List<Note> improvisationCreated = new ArrayList<Note>();
        ImprovisationCreator impCreator = new ImprovisationCreator(base, this.style);
        
        if (improvisation != null) {
            impCreator.setImprovisation(improvisation);
        }
        
        if (improvisation == null && nameAndType != null && !nameAndType.get(0).getValue().equals("Generar")) {
            List<Pair<String, String>> nuevaLista = new ArrayList<Pair<String, String>>();
            
            for (int i = 0; i < nameAndType.size(); i++) {
                if (nameAndType.get(i).getValue().equals("Generar")) {
                    nuevaLista.add(nameAndType.get(i));
                    nameAndType.remove(i);
                }
            }
        
            nameAndType = nuevaLista;
        }
    
        List<TupleNameTypeImpRule> ruleList = ImprovisationRulesInterface.getInstance().getRuleList();
        
        for (int i = 0; i < nameAndType.size(); i++) {
            for (TupleNameTypeImpRule rule : ruleList) {
                if (rule.getDescription() == nameAndType.get(i).getKey()) {
                    if (rule.isGenerate())
                        impCreator.ApplyImprovisationRuleGenerate(rule.getGir());                    
                    else
                        impCreator.ApplyImprovisationRuleVerifyAndCorrect(rule.getGir());
                    
                    improvisationCreated = impCreator.getImprovisation();
                }
            }
        }
        
        return improvisationCreated;
    }

    /**
     * Applies the internal improvisation rule list based on the selected rule names and types.
     *
     * @param nameAndType The list of pairs that the user wants to apply.
     */
    public void ApplyInternalImprovisationRuleListByName(List<Pair<String, String>> nameAndType) {
        this.internalImprovisation = this.ApplyImprovisationRuleListByName(nameAndType, this.internalBase, null);
    }

}
