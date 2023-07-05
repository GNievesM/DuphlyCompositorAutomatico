package EvolutionaryAlgorithm;

import DataDefinition.Chord;
import DataDefinition.Note;
import ImprovisationRules.Util;
import java.util.ArrayList;
import java.util.List;
import RuleCreationFramework.FrameworkUtil.FrameWorkUtil;
import static RuleCreationFramework.FrameworkUtil.FrameWorkUtil.copyMelody;
import Style.AbstractStyle;
import javafx.util.Pair;

public class ConditionEvolvableClass implements IEvolvableClass {
    String id;
    List<Chord> base;
    List<Note> melody; 
    Double start;
    Double end;
    IFitnessEvaluable fe;
    SpecificModificationMutator ma;
    int mutationSpeed;
    AbstractStyle style;
    
    public ConditionEvolvableClass(String id, List<Chord> base, List<Note> melody, Double start, Double end, IFitnessEvaluable fe, SpecificModificationMutator ma, int mutationSpeed, AbstractStyle style) {
        this.base = base;
        this.melody = melody;
        this.start = start;
        this.end = end;
        this.fe = fe;
        this.ma = ma;
        this.id = id;
        this.mutationSpeed = mutationSpeed;
        this.style = style;
    }
    
    @Override
    public double evaluateFitness() {
        return fe.evaluateFitness(this.melody, this.base, this.start, this.end);
    }
    
    @Override
    public void crossOver(IEvolvableClass ec, List<Boolean> changes) {
        ConditionEvolvableClass downEc = (ConditionEvolvableClass) ec;
        int thisStartPosition = Util.calculateNotePositionInListByTimeSum(melody, start);
        int otherStartPosition = Util.calculateNotePositionInListByTimeSum(downEc.melody, downEc.start);
 
        for (int i = 0; i < changes.size(); i++) { 
            if (changes.get(i)) {
                Note other = downEc.melody.get(i + otherStartPosition);
                Note thisNote = this.melody.get(i + thisStartPosition);
               
                int otherNote = other.getNote();
                int otherOctave = other.getOctave();
                
                other.setNote(thisNote.getNote());
                other.setOctave(thisNote.getOctave());
                
                thisNote.setNote(otherNote);
                thisNote.setOctave(otherOctave);
                
                downEc.melody.set(i + otherStartPosition, other);
                this.melody.set(i + thisStartPosition, thisNote);
            }
        }
    }

    @Override
    public void mutate(List<Boolean> mutationPlaces) {
        int startPosition = Util.calculateNotePositionInListByTimeSum(melody, start);
        for (int i = 0; i < mutationPlaces.size(); i++) {
            if (mutationPlaces.get(i)) {
                melody.set(startPosition + i, ma.mutate(this.base, this.melody, this.start, this.end, startPosition + i, this.style));
            }
        }
    }

    public List<Chord> getBase() {
        return base;
    }

    public void setBase(List<Chord> base) {
        this.base = base;
    }

    public List<Note> getMelody() {
        return melody;
    }

    public void setMelody(List<Note> melody) {
        this.melody = melody;
    }

    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    public Double getEnd() {
        return end;
    }

    public void setEnd(Double end) {
        this.end = end;
    }

    public IFitnessEvaluable getFe() {
        return fe;
    }

    public void setFe(IFitnessEvaluable fe) {
        this.fe = fe;
    }

    public SpecificModificationMutator getMa() {
        return ma;
    }

    public void setMa(SpecificModificationMutator ma) {
        this.ma = ma;
    }

    @Override
    public List<Boolean> generateFactorCrossOverList() { 
        int thisStartPosition = Util.calculateNotePositionInListByTimeSum(melody, start);
        int otherStartPosition = Util.calculateNotePositionInListByTimeSum(melody, end);
        int difference = otherStartPosition - thisStartPosition;
        List<Boolean> factor = new ArrayList<>();
        for (int i = 0; i < difference; i++) {
            factor.add(Math.random() * 100 < 50); 
        }
        return factor;
    }

    @Override
    public List<Boolean> generateFactorMutationList() { 
        int thisStartPosition = Util.calculateNotePositionInListByTimeSum(melody, start);
        int otherStartPosition = Util.calculateNotePositionInListByTimeSum(melody, end);
        int difference = otherStartPosition - thisStartPosition;
        List<Boolean> factor = new ArrayList<>();
        for (int i = 0; i < difference; i++) {
            factor.add(false);
        }
        int quantity = this.mutationSpeed > factor.size() ? factor.size() - 1 : this.mutationSpeed;
        while (quantity > 0) {
            int aux = (int) ((Math.random() * 100) % difference); 
            if (factor.get(aux) != true) {
                factor.set(aux, true);
                quantity--;
            }
        }
        return factor;
    }

    @Override
    public void PrintMelody() {
       int startPosition = Util.calculateNotePositionInListByTimeSum(melody, start);
       int endPosition = Util.calculateNotePositionInListByTimeSum(melody, end);
       System.out.print("Melody no " + this.id + ": " );
       for (int i = startPosition; i < endPosition; i++) {
        System.out.print(melody.get(i).getNote() + melody.get(i).getOctave() * 12 + " - ");
       }
       System.out.println("");
    }

    @Override
    public IEvolvableClass clone() {
        return new ConditionEvolvableClass(this.id, this.base, copyMelody(this.melody), this.start, this.end, this.fe, this.ma, this.mutationSpeed, this.style);
    }

    @Override
    public String getId() {
       return this.id;
    }

    @Override
    public List<Pair<Integer, Integer>> faultyPartition() {
       return this.fe.faultyPartition(melody, base, this.start, this.end);
    }
}
