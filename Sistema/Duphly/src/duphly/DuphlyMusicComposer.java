/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author gasto_000
 */
public class DuphlyMusicComposer {

    private static DuphlyMusicComposer instance = null;
   // GenericChordRule rule1, rule2, rule3;
    BaseChordsCreator bcc;
    int blackFigure;
    List<Chord> internalBase;
    List<Note> internalImprovisation;
    private boolean hayBase;
    private boolean hayReglas;
    List<Pair<String, String>> melodyRules;
    Score melody;
    ArrayList<Note> listaNotasImprovisacion;
    AbstractStyle style;
    private boolean addBluesScale=true;
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

    public ArrayList<Note> getListaNotasImprovisacion() {
        return listaNotasImprovisacion;
    }

    public void setListaNotasImprovisacion(ArrayList<Note> listaNotasImprovisacion) {
        this.listaNotasImprovisacion = listaNotasImprovisacion;
    }

    public boolean isHayBase() {
        return hayBase;
    }

    public void setHayBase(boolean hayBase) {
        this.hayBase = hayBase;
    }

    public boolean isHayReglas() {
        return hayReglas;
    }

    public void setHayReglas(boolean hayReglas) {
        this.hayReglas = hayReglas;
    }

    public List<Chord> getInternalBase() {
        return internalBase;
    }

    public List<Note> getInternalImprovisation() {
        return internalImprovisation;
    }

    protected DuphlyMusicComposer() {
    //    rule1 = new ChordRule1();
    //    rule2 = new ChordRule2();
    //    rule3 = new ChordRule3();
        bcc = new BaseChordsCreator();
        blackFigure = ConstantsDefinition.getInstance(2).GetBlackFigure();
        style = new BluesStyle();
        style.setMaxOctave(6);
        style.setMinOctave(4);
        style.setMelodyRoot(1);
        

    }

    /**
     * Devuelve una instancia de DuphlyMusicComposer que expone los metodos a
     * utilizarse en el sistema.
     *
     * @return
     */
    public static DuphlyMusicComposer getInstance() {
        if (instance == null) {
            instance = new DuphlyMusicComposer();
        }
        return instance;
    }

    /**
     * Crea la base inicial basica sobre la cual se aplicaran reglas
     *
     * @return Lista de acordes que representan la base basica sobre la cual se
     * pueden aplicar reglas.
     */
    public List<Chord> CreateSimpleBase() {

        this.internalBase = bcc.createBaseChords();
        return bcc.createBaseChords();

    }

    public void NewSimpleBase() {
        this.internalBase = this.CreateSimpleBase();
    }

    /**
     *
     * Dad una base, un momento dentro de la musica y un numero de regla(1-3),
     * aplica dicha regla, en el acorde que se encuentre en dicho momento.
     *
     * @param crotchetToApply Numero que representa el momento en el cual se
     * desea aplicar la regla
     * @param base Lista de acordes que se desea modificar que representa la
     * base.
     * @param ruleNumber Numero de la regla que se desea aplicar(1-3)
     * @return
     */
    public List<Chord> ApplyRule(int crotchetToApply, List<Chord> base, int ruleNumber) {

        base = bcc.applyRuleToBase(GetRuleBasedOnNumber(ruleNumber), crotchetToApply, base);
      /*  switch (ruleNumber) {
            case 1:
                base = bcc.applyRuleToBase(rule1, crotchetToApply, base);

                break;
            case 2:
                base = bcc.applyRuleToBase(rule2, crotchetToApply, base);

                break;
            case 3:
                base = bcc.applyRuleToBase(rule3, crotchetToApply, base);

                break;

        }*/

        return base;
    }

    public List<Chord> ApplyInternalRule(int crotchetToApply, int ruleNumber) {

        return this.ApplyRule(crotchetToApply, internalBase, ruleNumber);
    }
    
    private GenericChordRule GetRuleBasedOnNumber(int ruleNumber){
       return bcc.GetChordRuleList().get(ruleNumber).getKey();
    }

    /**
     * Dada una base, una posicion dentro de la lista de acordes y un numero de
     * regla(1-3), aplica dicha regla, en dicho elemento de la lista que
     * contiene los acordes.
     *
     * @param position Index de la lista en la cual se encuentra el acorde sobre
     * el cual se desea aplicar la regla
     * @param base Lista de acordes que componen la base que se desea modificar
     * @param ruleNumber Numero de regla a aplicar(del 1 al 3)
     * @return
     */
    public List<Chord> ApplyRulePerPosition(int position, List<Chord> base, int ruleNumber) {
        GenericChordRule rule = this.GetRuleBasedOnNumber(ruleNumber);
         this.internalBase = bcc.applyRulePerPosition(rule, position, this.internalBase);
         base = bcc.applyRulePerPosition(rule, position, base);
        /*switch (ruleNumber) {
            case 1:
                this.internalBase = bcc.applyRulePerPosition(rule1, position, this.internalBase);
                base = bcc.applyRulePerPosition(rule1, position, base);
                break;
            case 2:
                this.internalBase = bcc.applyRulePerPosition(rule2, position, this.internalBase);
                base = bcc.applyRulePerPosition(rule2, position, base);
                break;
            case 3:
                this.internalBase = bcc.applyRulePerPosition(rule3, position, this.internalBase);
                base = bcc.applyRulePerPosition(rule3, position, base);
                break;

        }*/
        return base;
    }

    public void ApplyRulePerPosition(int position, int ruleNumber) {
        this.internalBase = this.ApplyRulePerPosition(position, internalBase, ruleNumber);
    }

    /**
     * Recibe una base a modificar y una lista de PairRuleMoment(objeto que
     * guarda una regla y un momento en particular dentro de la musica para
     * aplicar la regla.
     *
     * @param base Base sobre la cual se aplican las reglas
     * @param MomentToApplyAndRuleNumberList Lista de momentos en la musica y
     * numeros de regla(1-3)
     * @return
     */
    public List<Chord> ApplyRuleList(List<Chord> base, List<PairRuleMoment> MomentToApplyAndRuleNumberList) {

        for (int i = 0; i < MomentToApplyAndRuleNumberList.size(); i++) {
            base = this.ApplyRule((int) MomentToApplyAndRuleNumberList.get(i).getMomentOrPositionToApply(), base, (int) MomentToApplyAndRuleNumberList.get(i).getRule());

            //for (int j = 0; j < base.size(); j++) {
            //      System.out.println(base.get(j).GetGrade());
            // }
            //   System.out.println("Use la regla " + (int) MomentToApplyAndRuleNumberList.get(i).getRule());
        }
        return base;
    }

    public void ApplyInternalRuleList(List<PairRuleMoment> MomentToApplyAndRuleNumberList) {

        this.ApplyRuleList(internalBase, MomentToApplyAndRuleNumberList);
    }

    /**
     * Recibe una base a modificar, y una lista de PairRuleMoment(objeto que
     * guarda una regla y una posicion dentro de la lista para aplicar la regla.
     *
     * @param base Base sobre la cual se aplican las reglas.
     * @param PositionAndRuleNumberList Lista de posiciones en la lista y
     * numeros de regla(1-3)
     * @return
     */
    public List<Chord> ApplyRulePerPositionList(List<Chord> base, List<PairRuleMoment> PositionAndRuleNumberList) {

        for (int i = 0; i < PositionAndRuleNumberList.size(); i++) {
            base = this.ApplyRulePerPosition((int) PositionAndRuleNumberList.get(i).getMomentOrPositionToApply(), base, (int) PositionAndRuleNumberList.get(i).getRule());

            //   for (int j = 0; j < base.size(); j++) {
            //       System.out.println(base.get(j).GetGrade() + "  Duracion : " + base.get(j).GetDuration());
///
            //}
            // System.out.println("Use la regla " + (int) PositionAndRuleNumberList.get(i).getRule());
        }
        return base;
    }

    public void ApplyInternalRulePerPositionList(List<PairRuleMoment> PositionAndRuleNumberList) {
        this.internalBase = this.ApplyRulePerPositionList(internalBase, PositionAndRuleNumberList);
    }

    /**
     * Devuelve la lista de reglas para la base existentes al momento.
     *
     * @return
     */
    public List<String> getExistingChordRules() {
        return this.bcc.ruleList();
    }

    /**
     * Crea una base aleatoria. Devuelve la lista de acordes.
     *
     * @return
     */
    public List<Chord> CreateRandomBase() {
        int ruleQuantity = (int) Math.rint(Math.random() * blackFigure * 8 * 6 * 2);// Asumo que la cantidad maxima de reglas que se aplicaran son el equivalente a la cantidad de corcheas existentens en los 12 compases
        List<Chord> base = this.CreateSimpleBase();
        List<PairRuleMoment> rulesMomentToApply = new ArrayList<>();
        for (int i = 0; i < ruleQuantity; i++) {
            int indexToApply = (int) Math.rint(Math.random() * base.size());
            int ruleToApply = (int) Math.rint(Math.random() * (this.bcc.ruleList().size() - 1) );//+ 1);// numberofrules
            rulesMomentToApply.add(new PairRuleMoment(indexToApply, ruleToApply));
        }
        return this.ApplyRulePerPositionList(base, rulesMomentToApply);
    }

    public void CreateInternalRandomBase() {
        this.internalBase = this.CreateRandomBase();
    }

    /**
     * Convierte una lista de acordes (base) a un score. PAra esto necesita que
     * se le especifique la nota base, la cual es provista por el usuario.
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
    public Part ConvertImprovisationToJmusic(List<Note> improvisation) {

        return new ImprovisationToJmusicConverter().ConvertImprovisation(improvisation);
    }

    public Part ConvertInternalImprovisationToJmusic() {

        return new ImprovisationToJmusicConverter().ConvertImprovisation(this.internalImprovisation);
    }

    /**
     * Devuele el score correspondiente de convertir una base y una
     * improvisacion a jmusic. Recibe la nota principal o tonica seleccionada
     * por el usuario
     *
     * @param improvisation
     * @param base
     * @param baseNote
     * @return
     */
    public Score ConvertImprovisationAndBaseToJmusic(List<Note> improvisation, List<Chord> base, int baseNote) {
        boolean addBluesScale = this.addBluesScale; // agrega la escala de blues a la melodia.
        Score score;
        //      for (Chord b : base) {
        //          if (b.GetDuration() != ConstantsDefinition.getInstance().GetBlackFigure() * 4 ||b.GetDuration() != ConstantsDefinition.getInstance().GetBlackFigure() * 8 || b.GetDuration() != ConstantsDefinition.getInstance().GetBlackFigure() * 4) {
        //             baseIsWhites = false;
        //         }
        //     }

        if (addBluesScale) {
            List<Note> bajo = this.ApplyImprovisationRuleGenerate(new BluesScaleOnEveryChord(), base);
            List<List<Note>> MelodyList = new ArrayList<List<Note>>();
            MelodyList.add(bajo);
            MelodyList.add(improvisation);
            score = this.ConvertImprovisationsAndBaseToJmusic(MelodyList, base, baseNote);

        } else {
            score = this.ConvertChordToJmusic(base, baseNote);
            score.add(this.ConvertImprovisationToJmusic(improvisation));
            score.setTempo(70);
        }
        return score;

    }

    public Score ConvertImprovisationsAndBaseToJmusic(List<List<Note>> improvisation, List<Chord> base, int baseNote) {
        Score score = this.ConvertChordToJmusic(base, baseNote);
        for (int i = 0; i < improvisation.size(); i++) {
            score.add(this.ConvertImprovisationToJmusic(improvisation.get(i)));
        }

        score.setTempo(70);
        return score;

    }

    public Score ConvertInternalImprovisationAndBaseToJmusic(int baseNote) {
        Score score = this.ConvertChordToJmusic(this.internalBase, baseNote);
        score.add(this.ConvertImprovisationToJmusic(this.internalImprovisation));
        score.setTempo(70);
        return score;

    }

    /**
     * Dada la lista de acordes que representa la base, la nota raiz
     * (Representacion JMusic, ej;C4 representado por el 60, cada numero
     * representa un semitono), y la lista de notas que representan la
     * improvisacion, escribe el archivo midi correspondiente, con el nombre
     * pasado por parametro, sin escribir la extension.
     *
     * @param base Base que el usuario utilizo para la creacion de la musica.
     * @param baseNote Nota que el usuuario selecciono como tonica o principal.
     * @param improvisation Lista de notas de la improvisacion generada
     * @param midiName Nombre del archivo midi de salida.
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
     * Dado un score S escribe el midi correspondiente, bajo el nombre pasasdo
     * por parametro
     *
     * @param s Score de la musica compuesta.
     */
    public void CreateMidiFile(Score s, String midiName) {
        Write.midi(s, midiName + ".mid");
    }

    public List<Note> ApplyImprovisationRuleGenerate(GenericImprovisationRule rule, List<Chord> base) {
        List<Note> improvisation = rule.GenerateImprovisation(base);
        //   for (int i = 0; i < improvisation.size(); i++) {
        //   System.out.println("Nota: " + improvisation.get(i).getNote() + " Acorde : " + Util.CalcChord(Util.LookForBaseChord(base, improvisation, i)));

        //  }
        return improvisation;
    }

    public void CrearPartitura(String ruta) {
        AcordeToLilyPondParse a = new AcordeToLilyPondParse(60);

        List<Phrase> listPhrase = DuphlyMusicComposer.getInstance().ConvertImprovisationToJmusic(DuphlyMusicComposer.getInstance().getListaNotasImprovisacion()).getPhraseList();
        ArrayList<jm.music.data.Note> listNotes = new ArrayList<jm.music.data.Note>();

        for (Phrase frase : listPhrase) {
            listNotes.addAll(frase.getNoteList());
        }
        // DuphlyMusicComposer.getInstance().ConvertImprovisationToJmusic(DuphlyMusicComposer.getInstance().getListaNotasImprovisacion()).get
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
        return appliedRule;//.getKey(); //////// wiiiiit
    }

    /**
     * Devuelve la lista de reglas de improvisacion y su forma(Generar,
     * Modificar, Aplicar en un momento). Esta Lista debe mostrarse en la
     * interfaz para que el usuario pueda seleccionar cuales desea aplicar y en
     * que forma.
     *
     * @return
     */
    public List<Pair<String, String>> GetImprovisationRulesNamesAndType() {
      /*  Pair<String, String> rule1 = new Pair<String, String>("Creacion aleatoria", "Generar");
        Pair<String, String> rule2 = new Pair<String, String>("Limitar saltos por compas", "Generar");
        Pair<String, String> rule15 = new Pair<String, String>("Creacion aleatoria sin abusar la nota de blues", "Generar");
        Pair<String, String> rule16 = new Pair<String, String>("Limitar saltos por compas sin abusar la nota de blues", "Generar");

        Pair<String, String> rule14 = new Pair<String, String>("Escala de blues sobre acordes", "Generar");

        Pair<String, String> rule4 = new Pair<String, String>("Limitar difusion en la direccion", "Verificar y Corregir");
        Pair<String, String> rule5 = new Pair<String, String>("Continuidad en el fraseo sin permitir repetidas", "Verificar y Corregir");
        Pair<String, String> rule6 = new Pair<String, String>("Continuidad en el fraseo permitiendo repetidas", "Verificar y Corregir");
        Pair<String, String> rule7 = new Pair<String, String>("Disminuir utilizacion de la nota de blues aleatorio", "Verificar y Corregir");
        Pair<String, String> rule8 = new Pair<String, String>("Disminuir utilizacion de la nota de blues sustituyendo por la nota anterior", "Verificar y Corregir");
        Pair<String, String> rule9 = new Pair<String, String>("Continuidad en el fraseo con variante", "Verificar y Corregir");
        Pair<String, String> rule10 = new Pair<String, String>("Aumentar repeticion de notas", "Verificar y Corregir");

        List<Pair<String, String>> returnList = new ArrayList<>();
        returnList.add(rule1);
        returnList.add(rule2);

        returnList.add(rule4);
        returnList.add(rule5);
        returnList.add(rule6);
        returnList.add(rule7);
        returnList.add(rule8);
        returnList.add(rule9);
        returnList.add(rule10);
        returnList.add(rule14);
        returnList.add(rule15);
        returnList.add(rule16);*/
      List<Pair<String, String>> returnList = new ArrayList<>();
      List<TupleNameTypeImpRule> existingRules = ImprovisationRulesInterface.getInstance().getRuleList();
      for(TupleNameTypeImpRule tuple:existingRules){
          returnList.add(new Pair(tuple.getDescription(),tuple.getType()));
      }
        return returnList;
    }

    /**
     * Recibe la lista de pares de reglas y formas que el usuario selecciono
     * para aplicar. Estas deben ser las mismas (es decir sin sufrir
     * modificaciones los elementos de la lista) que devuelve la funcion
     * GetImprovisationRulesNamesAndType.
     *
     * @param nameAndType Lista de pares que el usuario desea aplicar.
     * @param base Base que se va a utilizar para la improvisacion puntual
     * @return
     */
    public List<Note> ApplyImprovisationRuleListByName(List<Pair<String, String>> nameAndType, List<Chord> base, List<Note> improvisation) {

        List<Note> improvisationCreated = new ArrayList<Note>();
        
        ImprovisationCreator impCreator = new ImprovisationCreator(base,this.style);
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
            for(TupleNameTypeImpRule rule: ruleList){
                if(rule.getDescription() == nameAndType.get(i).getKey()){
                    if(rule.isGenerate())
                        impCreator.ApplyImprovisationRuleGenerate(rule.getGir());                    
                    else
                        impCreator.ApplyImprovisationRuleVerifyAndCorrect(rule.getGir());
                    improvisationCreated=impCreator.getImprovisation();
                }
            }
        }
            /*switch (nameAndType.get(i).getKey()) {
                case "Creacion aleatoria":
                    impCreator.ApplyImprovisationRuleGenerate(new ImprovisationPurelyRandom());
                    improvisationCreated = impCreator.getImprovisation();
                    break;
                case "Limitar saltos por compas":
                    impCreator.ApplyImprovisationRuleGenerate(new LimitJumpsPerBar());
                    improvisationCreated = impCreator.getImprovisation();
                    break;
                case "Creacion aleatoria sin abusar la nota de blues":
                    impCreator.ApplyImprovisationRuleGenerate(new ImprovisationPurelyRandomNotAbusingBluesNote());
                    improvisationCreated = impCreator.getImprovisation();
                    break;
                case "Limitar saltos por compas sin abusar la nota de blues":
                    impCreator.ApplyImprovisationRuleGenerate(new LimitJumpsPerBarNotAbusingBluesNote());
                    improvisationCreated = impCreator.getImprovisation();
                    break;
                case "Continuidad en el fraseo":
                    impCreator.ApplyImprovisationRuleGenerate(new DirectionContinuity());
                    improvisationCreated = impCreator.getImprovisation();
                    break;
                case "Escala de blues sobre acordes":
                    impCreator.ApplyImprovisationRuleGenerate(new BluesScaleOnEveryChordNormalOctave());
                    improvisationCreated = impCreator.getImprovisation();
                    break;
                case "Limitar difusion en la direccion":

                    impCreator.ApplyImprovisationRuleVerifyAndCorrect(new DashedDirectionContinuity());
                    improvisationCreated = impCreator.getImprovisation();
                    break;
                case "Continuidad en el fraseo sin permitir repetidas":

                    impCreator.ApplyImprovisationRuleVerifyAndCorrect(new DirectionContinuity());
                    improvisationCreated = impCreator.getImprovisation();
                    break;
                case "Continuidad en el fraseo permitiendo repetidas":

                    impCreator.ApplyImprovisationRuleVerifyAndCorrect(new DirectionContinuityAllowingEquals());
                    improvisationCreated = impCreator.getImprovisation();
                    break;
                case "Disminuir utilizacion de la nota de blues aleatorio":

                    impCreator.ApplyImprovisationRuleVerifyAndCorrect(new NotAbusingBluesNoteRandomModifier());
                    improvisationCreated = impCreator.getImprovisation();
                    break;
                case "Disminuir utilizacion de la nota de blues sustituyendo por la nota anterior":

                    impCreator.ApplyImprovisationRuleVerifyAndCorrect(new NotAbusingBluesNoteEqualModifier());
                    improvisationCreated = impCreator.getImprovisation();
                    break;
                case "Continuidad en el fraseo con variante":

                    impCreator.ApplyImprovisationRuleVerifyAndCorrect(new DirectionContinuityImplementation2());
                    improvisationCreated = impCreator.getImprovisation();
                    break;
                case "Aumentar repeticion de notas":

                    impCreator.ApplyImprovisationRuleVerifyAndCorrect(new RepeatingNotes());
                    improvisationCreated = impCreator.getImprovisation();
                    break;

            }
        }*/
        return improvisationCreated;
    }

    public void ApplyInternalImprovisationRuleListByName(List<Pair<String, String>> nameAndType) {
        this.internalImprovisation = this.ApplyImprovisationRuleListByName(nameAndType, this.internalBase, null);
    }
}
