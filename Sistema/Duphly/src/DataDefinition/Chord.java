/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataDefinition;

import ConstantDefinition.ConstantsDefinition;

/**
 *
 * @author gasto_000
 */
public class Chord {
    
    boolean septima; // determina si el acorde tiene septima
    boolean minor; // determina si el acorde es menor
    // representar los modos? boolean 
    int grade; // determina el grado del acorde con respecto a la tonalidad.
    int duration; // determina la duracion, 2 es equivalente a una negra, la cual se definio como la unidad minima para un acrode en base al estilo musical.
    //corchea es 1, blanca es 4, redonda es 8, cuadrada es 16
    boolean diminishedMode;
    boolean flat ; // determina si el acorde es bemol. 
    boolean sharp;
    boolean seventhComa;
    boolean ninth;
    boolean thirtinth;
    boolean sevenPlusFive;
    boolean sixth;
    boolean emptyMode;
    boolean MSeventh;
    boolean bTone;
    boolean tenth;

    public boolean isTenth() {
        return tenth;
    }

    public void setTenth(boolean tenth) {
        this.tenth = tenth;
    }

    public boolean isMSeventh() {
        return MSeventh;
    }

    public void setMSeventh(boolean MSeventh) {
        this.MSeventh = MSeventh;
    }

    public boolean isbTone() {
        return bTone;
    }

    public void setbTone(boolean bTone) {
        this.bTone = bTone;
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
    public Chord(boolean septima, boolean minor, int grade, int duration) {
        this.septima = septima;
        this.minor = minor;
        this.grade = grade;
        this.duration = duration;
    }

    public Chord createCopyWithHalfDuration() {
        return new Chord(this.septima, this.minor, this.grade, this.duration/2);
    }
    
    

    public Chord(Chord c) {
        this.septima = c.GetSeptima();
        this.minor = c.isMinor();
        this.grade = c.GetGrade();
        this.duration = c.GetDuration();
       // this.flat= c.isFlat();
       // this.sharp = c.isSharp();
    }

    public boolean isMinor() {
        return minor;
    }

    public void setMinor(boolean minor) {
        this.minor = minor;
    }

    public Chord() {
        this.septima = false;
        this.minor = false;
        this.grade = 0;
        this.duration = ConstantsDefinition.getInstance().GetBlackFigure();
    }

    public boolean GetSeptima() {
        return this.septima;
    }

    public void SetSeptima(boolean septima) {
        this.septima = septima;
    }

    public int GetGrade() {
        return this.grade;
    }

    public void SetGrade(int grade) {
        this.grade = grade;
    }

    public int GetDuration() {
        return this.duration;
    }

    public void SetDuration(int duration) {
        this.duration = duration;
    }
}
