protected ConstantsDefinition() {
    blackFigure = 2;
}

public int getFullBarCount() {
    return this.blackFigure * 4;
}

public int getTwelveBars() {
    return this.blackFigure * 4 * 12;
}

public int getTwoBarsFigure() {
    return this.blackFigure * 8;
}

public int get4bars() {
    return this.blackFigure * 16;
}

protected ConstantsDefinition(int blackFigure) {
    this.blackFigure = blackFigure;
}

public static ConstantsDefinition getInstance() {
    if (instance == null) {
        instance = new ConstantsDefinition();
    }
    return instance;
}

public static ConstantsDefinition getInstance(int blackFigure) {
    if (instance == null) {
        instance = new ConstantsDefinition(blackFigure);
    }
    return instance;
}

public int GetBlackFigure() {
    return this.blackFigure;
}
 
public double GetBlackFigureForLilyPond() { 
    List<Note> blackFigure = new ArrayList<Note>();
    blackFigure.add(new Note(ConstantsDefinition.getInstance().GetBlackFigure(), C4, 3, 'b'));
    Part p = DuphlyMusicComposer.getInstance().ConvertImprovisationToJmusic(blackFigure, 0);
    List<Phrase> phraseList = p.getPhraseList();
    List<jm.music.data.Note> noteList = phraseList.get(0).getNoteList();
    return noteList.get(0).getDuration();
}
