package DataDefinition;

import ConstantDefinition.ConstantsDefinition;

public class Chord {

    boolean seventh;
    boolean minor;
    int grade; // determines the chord's degree with respect to the key.
    int duration; // determines the duration, 2 is equivalent to a quarter note, which was defined as the minimum unit for a chord based on the musical style.
    // eighth note is 1, whole note is 4, half note is 8, whole note is 16
    boolean diminishedMode;
    boolean flat;
    boolean sharp;
    boolean seventhComa;
    boolean ninth;
    boolean thirtinth;
    boolean sevenPlusFive;
    boolean sixth;
    boolean emptyMode;
    boolean majorSeventh;
    boolean flatTone;
    boolean tenth;

    public boolean isTenth() {
        return tenth;
    }

    public void setTenth(boolean tenth) {
        this.tenth = tenth;
    }

    public boolean isMajorSeventh() {
        return majorSeventh;
    }

    public void setMajorSeventh(boolean majorSeventh) {
        this.majorSeventh = majorSeventh;
    }

    public boolean isFlatTone() {
        return flatTone;
    }

    public void setFlatTone(boolean flatTone) {
        this.flatTone = flatTone;
    }

    public boolean isSeventhComa() {
        return seventhComa;
    }

    public void setSeventhComa(boolean seventhComa) {
        this.seventhComa = seventhComa;
    }

    public boolean isNinth() {
        return ninth;
    }

    public void setNinth(boolean ninth) {
        this.ninth = ninth;
    }

    public boolean isThirtinth() {
        return thirtinth;
    }

    public void setThirtinth(boolean thirtinth) {
        this.thirtinth = thirtinth;
    }

    public boolean isSevenPlusFive() {
        return sevenPlusFive;
    }

    public void setSevenPlusFive(boolean sevenPlusFive) {
        this.sevenPlusFive = sevenPlusFive;
    }

    public boolean isSixth() {
        return sixth;
    }

    public void setSixth(boolean sixth) {
        this.sixth = sixth;
    }

    public boolean isEmptyMode() {
        return emptyMode;
    }

    public void setEmptyMode(boolean emptyMode) {
        this.emptyMode = emptyMode;
    }

    public boolean isSharp() {
        return sharp;
    }

    public void setSharp(boolean sharp) {
        this.sharp = sharp;
    }

    public boolean isDiminishedMode() {
        return diminishedMode;
    }

    public void setDiminishedMode(boolean diminishedMode) {
        this.diminishedMode = diminishedMode;
    }

    public boolean isFlat() {
        return flat;
    }

    public void setFlat(boolean flat) {
        this.flat = flat;
    }

    public Chord(boolean seventh, boolean minor, int grade, int duration) {
        this.seventh = seventh;
        this.minor = minor;
        this.grade = grade;
        this.duration = duration;
    }

    public Chord createCopyWithHalfDuration() {
        return new Chord(this.seventh, this.minor, this.grade, this.duration / 2);
    }

    public Chord(Chord c) {
        this.seventh = c.getSeventh();
        this.minor = c.isMinor();
        this.grade = c.getGrade();
        this.duration = c.getDuration();
    }

    public boolean isMinor() {
        return minor;
    }

    public void setMinor(boolean minor) {
        this.minor = minor;
    }

    public Chord() {
        this.seventh = false;
        this.minor = false;
        this.grade = 0;
        this.duration = ConstantsDefinition.getInstance().getQuarterNote();
    }

    public boolean getSeventh() {
        return this.seventh;
    }

    public void setSeventh(boolean seventh) {
        this.seventh = seventh;
    }

    public int getGrade() {
        return this.grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
