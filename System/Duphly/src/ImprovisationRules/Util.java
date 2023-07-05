package ImprovisationRules;

import ConstantDefinition.ConstantsDefinition;
import DataDefinition.Chord;
import DataDefinition.Note;
import Style.AbstractStyle;
import java.util.List;
import javafx.util.Pair;

public class Util {

  int minOctave = 3;
  int maxOctave = 5;

  /**
   * Compares two notes and returns whether the first note is higher or equal in pitch to the second note (compares heights)
   * @param n1
   * @param n2
   * @return
   */
  public static boolean isHigherOrEqualPitchThan(Note n1, Note n2) {
    return (
      n1.getNote() +
      (12 * n1.getOctave()) >=
      n2.getNote() +
      (12 * n2.getOctave())
    );
  }

  /**
   * Compares two notes and returns whether the first note is higher in pitch than the second note (compares heights)
   * @param n1
   * @param n2
   * @return
   */
  public static boolean isHigherPitchThan(Note n1, Note n2) {
    return (
      n1.getNote() +
      (12 * n1.getOctave()) >
      n2.getNote() +
      (12 * n2.getOctave())
    );
  }

  /**
   * Compares two notes and returns whether the first note is lower or equal in pitch to the second note (compares heights)
   * @param n1
   * @param n2
   * @return
   */
  public static boolean isLowerOrEqualPitchThan(Note n1, Note n2) {
    return (
      !isHigherOrEqualPitchThan(n1, n2) ||
      (isHigherOrEqualPitchThan(n1, n2) && isHigherOrEqualPitchThan(n2, n1))
    );
  }

  /**
   * Compares two notes and returns whether the first note is equal in pitch to the second note (compares heights)
   * @param n1
   * @param n2
   * @return
   */
  public static boolean isEqualPitch(Note n1, Note n2) {
    return (
      n1.getNote() +
      (12 * n1.getOctave()) ==
      n2.getNote() +
      (12 * n2.getOctave())
    );
  }

  /**
   * Given the harmonic base, a melodic line, and the position of a note in the melodic line, returns the base chord for that note.
   * @param base
   * @param improvisation
   * @param index
   * @return
   */
  public static Chord lookForBaseChord(
    List<Chord> base,
    List<Note> improvisation,
    int index
  ) {
    double totalDuration = 0;
    for (int i = 0; i < index; i++) {
      totalDuration += improvisation.get(i).getDuration();
    }
    totalDuration = (double) ((double) Math.round(totalDuration * 100) / 100);
    int baseDuration = 0;
    for (int i = 0; i < base.size() - 1; i++) {
      if (
        (double) base.get(i).getDuration() >
        totalDuration -
        (double) baseDuration
      ) {
        return base.get(i);
      }
      baseDuration += base.get(i).getDuration();
    }
    return base.get(base.size() - 1);
  }

  /**
   * Given a chord and a note, returns the number of semitones to the correct note assuming the blues scale.
   * @param c
   * @param n
   * @return
   */
  public static int identifySemitonesInChord(Chord chord, Note note) {
    int semitones = 0;
    int chordGrade = chord.getGrade();
    int noteGrade = note.getNote();

    // Compare note grade with chord grade and calculate semitones
    switch (noteGrade) {
      case chordGrade:
        semitones = 1;
        break;
      case (chordGrade - 1 + 11) % 12:
        semitones = 11;
        break;
      case (chordGrade - 1 + 4) % 12:
        semitones = 4;
        break;
      case (chordGrade - 1 + 6) % 12:
        semitones = 6;
        break;
      case (chordGrade - 1 + 7) % 12:
        semitones = 7;
        break;
      case (chordGrade - 1 + 8) % 12:
        semitones = 8;
        break;
    }

    return semitones;
  }

  /**
   * Returns the number between 0 and 5 that represents a note for a chord.
   * @param c
   * @param n
   * @return
   */
  public static int identifyBluesScaleNoteHeightInChord(Chord c, Note n) {
    int result = 0;
    int grade = 1;
    switch (c.getGrade()) {
      case 1:
        grade = 1;
        break;
      case 2:
        grade = 3;
        break;
      case 3:
        grade = 5;
        break;
      case 4:
        grade = 6;
        break;
      case 5:
        grade = 8;
        break;
      case 6:
        grade = 10;
        break;
      case 7:
        grade = 12;
        break;
    }

    int chordGrade = grade;
    int noteGrade = n.getNote();

    if (chordGrade == noteGrade) {
      result = 0;
    }
    if (noteGrade == ((grade + 9) % 12) + 1) {
      result = 5;
    }
    if (noteGrade == ((grade + 2) % 12) + 1) {
      result = 1;
    }
    if (noteGrade == ((grade + 4) % 12) + 1) {
      result = 2;
    }
    if (noteGrade == ((grade + 5) % 12) + 1) {
      result = 3;
    }
    if (noteGrade == ((grade + 6) % 12) + 1) {
      result = 4;
    }

    return result;
  }

  /**
   * Calculates the note height for a chord based on the specified height value.
   * @param c The chord for which the note height is to be calculated.
   * @param height The height value representing the note in the blues scale (0-5).
   * @return The associated note height for the chord based on the blues scale.
   */
  public static int calculateNoteHeightForChord(Chord c, int height) {
    int result = 0;
    int grade = 1;
    switch (c.getGrade()) {
      case 1:
        grade = 1;
        break;
      case 2:
        grade = 3;
        break;
      case 3:
        grade = 5;
        break;
      case 4:
        grade = 6;
        break;
      case 5:
        grade = 8;
        break;
      case 6:
        grade = 10;
        break;
      case 7:
        grade = 12;
        break;
    }
    switch (height) {
      case 0:
        result = grade;
        break;
      case 1:
        result = ((grade + 2) % 12) + 1;
        break;
      case 2:
        result = ((grade + 4) % 12) + 1;
        break;
      case 3:
        result = ((grade + 5) % 12) + 1;
        break;
      case 4:
        result = ((grade + 6) % 12) + 1;
        break;
      case 5:
        result = ((grade + 9) % 12) + 1;
        break;
    }

    return result;
  }

  /**
   * Given a chord, a height, a duration, and an octave, returns a valid note from the blues scale for the chord.
   * @param c The chord.
   * @param height The height representing the note in the blues scale.
   * @param duration The duration of the note.
   * @param octave The octave of the note.
   * @return The calculated note for the chord.
   */
  public static Note calculateNoteForChord(
    Chord c,
    int height,
    double duration,
    int octave
  ) {
    Note returnNote = new Note();
    returnNote.setDuration(duration);
    returnNote.setNote(Util.calculateNoteHeightForChord(c, height));
    returnNote.setOctave(octave);
    if (height % 2 == 0) {
      returnNote.setAccident('n');
    } else {
      returnNote.setAccident('b');
    }

    return returnNote;
  }

  /**
   * Generates a random octave within the specified range.
   * @param lowestOctave The lowest possible octave.
   * @param octaveQuantity The number of octaves in the range.
   * @return A random octave within the specified range.
   */
  public static int getOctaveLimitedBetween(
    int lowestOctave,
    int octaveQuantity
  ) {
    return (int) Math.rint(Math.random() * (octaveQuantity - 1)) + lowestOctave;
  }

  /**
   * Returns a number between 0 and 5 representing a note with respect to the base,
   * reducing the probability of selecting a blues note.
   * @return The randomized pitch not abusing the blues note.
   */
  public static int randomizePitchNotAbusingBluesNote() {
    int noteToImprovisation = (int) Math.rint(Math.random() * 99); // Multiply by 100 to assign probabilities.
    if (noteToImprovisation <= 9) {
      noteToImprovisation = 3;
    } else {
      noteToImprovisation = (noteToImprovisation - 10) / 18;
      if (noteToImprovisation == 3) {
        noteToImprovisation = 5;
      }
    }
    return noteToImprovisation;
  }

  /**
   * Returns the expanded form of a note, which is the absolute representation
   * of the note within all possible heights, given the pitch and octave.
   * @param pitch The pitch of the note.
   * @param octave The octave of the note.
   * @return The expanded form of the note.
   */
  public static int getExpandedFormNote(int pitch, int octave) {
    return pitch + octave * 12;
  }

  /**
   * Returns the small form of a note in a pitch-octave pair, given the expanded note.
   * @param expandedNote The expanded note.
   * @return The pitch-octave pair in small form.
   */
  public static Pair<Integer, Integer> getSmallFormNotePitchOctave(
    int expandedNote
  ) {
    return new Pair(((expandedNote - 1) % 12) + 1, expandedNote / 12);
  }

  /**
   * Returns only the pitch number corresponding to the note given an expanded note.
   * @param expandedNote The expanded note.
   * @return The pitch number of the note.
   */
  public static int getSmallFormNotePitch(int expandedNote) {
    return ((expandedNote - 1) % 12) + 1;
  }

  /**
   * Given an expanded note value, returns the corresponding octave of the note.
   * @param expandedNote The expanded note.
   * @return The octave of the note.
   */
  public static int getSmallFormNoteOctave(int expandedNote) {
    if (expandedNote / 12 == 0) return expandedNote / 12 - 1;
    return expandedNote / 12;
  }

  /**
   * Given the grade of a chord, calculates the note that corresponds to the next root of the chord.
   * @param c The chord.
   * @return The calculated note for the chord.
   */
  public static int calcChord(Chord c) {
    int grade = 1;
    switch (c.GetGrade()) {
      case 1:
        grade = 1;
        break;
      case 2:
        grade = 3;
        break;
      case 3:
        grade = 5;
        break;
      case 4:
        grade = 6;
        break;
      case 5:
        grade = 8;
        break;
      case 6:
        grade = 10;
        break;
      case 7:
        grade = 12;
        break;
    }
    return grade;
  }

  /**
   * Returns the position of a note based on the sum of all note durations in the list.
   * @param notes The list of notes.
   * @param sum The sum of note durations.
   * @return The position of the note in the list based on the time sum.
   */
  public static int calculateNotePositionInListByTimeSum(
    List<Note> notes,
    double sum
  ) {
    int result = 0;
    if (sum == 0) return 0;
    for (int i = 0; i < notes.size() && sum > 0.1; i++) {
      Note note = notes.get(i);
      if (sum > 0.1) {
        sum = sum - note.getDuration();
        result++;
      }
    }
    return result;
  }

  /**
   * Calculates the time sum of the melody up to a given note position in the list.
   * @param notes The list of notes.
   * @param position The note position.
   * @return The calculated time sum.
   */
  public static double calculateTimeSumByPosition(
    List<Note> notes,
    int position
  ) {
    double timeSum = 0;
    for (int i = 0; i < position; i++) {
      timeSum += notes.get(i).getDuration();
    }
    return timeSum;
  }

  /**
   * Given a note and a number of semitones, adds the semitones to the note, modifying the note in the parameter.
   * @param n The note.
   * @param quantity The number of semitones.
   * @return The modified note.
   */
  public static Note addDemiTones(Note n, int quantity) {
    int noteNumber =
      Util.getExpandedFormNote(n.getNote(), n.getOctave()) + quantity;
    n.setNote(Util.getSmallFormNotePitch(noteNumber));
    n.setOctave(Util.getSmallFormNoteOctave(noteNumber));

    return n;
  }

  /**
   * Returns a note based on the evaluated chord.
   * @param c The chord.
   * @return The random note based on the chord.
   */
  public static Note getRandomNoteBasedOnChord(Chord c) {
    return null;
  }

  /**
   * Returns the number of blocks (measures) in the music.
   * @param melody The melody.
   * @return The number of blocks.
   */
  public static int getBlockQuantity(List<Note> melody) {
    int blockNumbers;
    int blockSize = ConstantsDefinition.getInstance().GetBlackFigure() * 4;
    double count = 0;
    for (int i = 0; i < melody.size(); i++) {
      count += melody.get(i).getDuration();
    }
    blockNumbers = (int) Math.round(count / blockSize);
    return blockNumbers;
  }

  /**
   * Given the melody and a block number, returns the position of the first note within the block. Counting blocks starting from 0.
   * @param melody The melody.
   * @param blockNumber The block number.
   * @return The position of the first note within the block.
   */
  public static int getBlockPositionByNumber(
    List<Note> melody,
    int blockNumber
  ) {
    int blockSize = ConstantsDefinition.getInstance().GetBlackFigure() * 4;
    double count = 0;
    int i;
    int currentBlock = 0;
    for (i = 0; i < melody.size() && currentBlock != blockNumber; i++) {
      count += melody.get(i).getDuration();
      if (
        count % blockSize == 0 || blockSize - count % blockSize <= 0.1
      ) currentBlock++;
    }

    return i;
  }

  public static double countBlocks(
    List<Note> melody,
    double startBlock,
    double endBlock
  ) {
    int blockSize = ConstantsDefinition.getInstance().GetBlackFigure() * 4;
    double count = 0;
    int startPosition = Util.calculateNotePositionInListByTimeSum(
      melody,
      startBlock
    );
    int endPosition = Util.calculateNotePositionInListByTimeSum(
      melody,
      startBlock
    );
    for (int i = startPosition; i < endPosition; i++) {
      count += melody.get(i).getDuration();
      if (count % blockSize == 0) count++;
    }

    return count;
  }

  public static double getMelodyTotalLength(List<Note> melody) {
    double total = 0;
    for (Note n : melody) {
      total += n.getDuration();
    }
    return total;
  }

  public static int calculateNoteGradeForChord(Chord c, Note n) {
    int chordBasePitch = Util.CalcChord(c);
    int pitchForMath = n.getNote() < chordBasePitch
      ? 12 + n.getNote()
      : n.getNote();
    int semiTonesDistance = pitchForMath - chordBasePitch;
    return Util.calculateGradeBySemitones(semiTonesDistance);
  }

  public static int calculateGradeBySemitones(int semitonesDistance) {
    int result = 0;
    switch (semitonesDistance) {
      case 0:
        result = 1;
        break;
      case 1:
        result = 1;
        break;
      case 2:
        result = 2;
        break;
      case 3:
        result = 2;
        break;
      case 4:
        result = 3;
        break;
      case 5:
        result = 4;
        break;
      case 6:
        result = 4;
        break;
      case 7:
        result = 5;
        break;
      case 8:
        result = 5;
        break;
      case 9:
        result = 6;
        break;
      case 10:
        result = 6;
        break;
      case 11:
        result = 7;
        break;
    }
    return result;
  }

  public static int returnChordBasePitch(Chord c, int root) {
    int result = 0;
    switch (c.GetGrade()) {
      case 1:
        result = root;
        break;
      case 2:
        result = root + 2;
        break;
      case 3:
        result = root + 4;
        break;
      case 4:
        result = root + 5;
        break;
      case 5:
        result = root + 7;
        break;
      case 6:
        result = root + 9;
        break;
      case 7:
        result = root + 11;
        break;
    }
    return result;
  }

  public static Note returnNoteAfterAddingGradesToChord(
    Chord c,
    int demitones,
    int octave,
    double duration
  ) {
    Note n = new Note();
    n.setAccident('n');
    n.setOctave(octave);
    n.setDuration(duration);
    n.setNote(Util.returnChordBasePitch(c, 1));
    n = Util.addDemiTones(n, demitones);
    return n;
  }
}
