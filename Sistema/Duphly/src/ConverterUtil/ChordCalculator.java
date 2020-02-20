/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConverterUtil;

import DataDefinition.Chord;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Gaston
 */
public class ChordCalculator {

    CalculatorUtil cu;

    public static int[] pitchArray(Chord c, int baseRoot) {
        ArrayList<Integer> sol;
        int chordFirst = ChordCalculator.calculateChordFirst(c, baseRoot);
        if (c.isMinor()) {
            sol = calculateMinor(chordFirst);
        } else if (c.isDiminishedMode()) {
            return getIntArray(calculateDiminishedMode(chordFirst).toArray());
        } else if (c.isEmptyMode()) {
            return getIntArray(calculateEmptyMode(chordFirst).toArray());
        } else if (c.isSevenPlusFive()) {
            return getIntArray(calculateSevenPlusFive(chordFirst).toArray());
        } else {
            sol = calculateMajor(chordFirst);
        }

        if (c.GetSeptima()) {
            sol = ChordCalculator.addSeventh(sol, chordFirst);
        } else if (c.isSeventhComa()) {
            sol = ChordCalculator.addSeventhComa(sol, chordFirst);
        } else if (c.isMSeventh()) {
            sol = ChordCalculator.addMSeventh(sol, chordFirst);
        } else if (c.isSixth()) {
            sol = ChordCalculator.addSixth(sol, chordFirst);
        } else if (c.isNinth()) {
            sol = ChordCalculator.addNineth(sol, chordFirst, c.isbTone());
        } else if (c.isTenth()) {
            sol = ChordCalculator.addTenth(sol, chordFirst, c.isbTone());
        } else if (c.isThirtinth()) {
            sol = ChordCalculator.addThirteenth(sol, chordFirst);
        }

        return getIntArray(sol.toArray());
    }

    private static int[] getIntArray(Object[] array) {
        int[] sol = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            sol[i] = (int) array[i];
        }
        return sol;
    }

    private static ArrayList<Integer> calculateSevenPlusFive(int chordFirst) { // augmented seventh

        ArrayList<Integer> pitchList = new ArrayList<>();
        pitchList.add(CalculatorUtil.first(chordFirst));
        pitchList.add(CalculatorUtil.majorThird(chordFirst));
        pitchList.add(CalculatorUtil.augmentedFifth(chordFirst));
        pitchList.add(CalculatorUtil.minorSeventh(chordFirst));
        return pitchList;

    }

    private static ArrayList<Integer> calculateMinor(int chordFirst) {
        ArrayList<Integer> pitchList = new ArrayList<Integer>();
        pitchList.add(CalculatorUtil.first(chordFirst));
        pitchList.add(CalculatorUtil.minorThird(chordFirst));
        pitchList.add(CalculatorUtil.perfectFifth(chordFirst));
        pitchList.add(CalculatorUtil.perfectOctave(chordFirst));
        return pitchList;
    }

    private static ArrayList<Integer> calculateMajor(int chordFirst) {
        ArrayList<Integer> pitchList = new ArrayList<>();
        pitchList.add(CalculatorUtil.first(chordFirst));
        pitchList.add(CalculatorUtil.majorThird(chordFirst));
        pitchList.add(CalculatorUtil.perfectFifth(chordFirst));
        pitchList.add(CalculatorUtil.perfectOctave(chordFirst));
        return pitchList;
    }

    private static ArrayList<Integer> calculateDiminishedMode(int chordFirst) { //diminished seventh
        ArrayList<Integer> pitchList = new ArrayList<>();
        pitchList.add(CalculatorUtil.first(chordFirst));
        pitchList.add(CalculatorUtil.minorThird(chordFirst));
        pitchList.add(CalculatorUtil.diminishedFifth(chordFirst));
        pitchList.add(CalculatorUtil.diminishedSeventh(chordFirst));
        return pitchList;
    }

    private static ArrayList<Integer> calculateEmptyMode(int chordFirst) { //half diminished seventh
        ArrayList<Integer> pitchList = new ArrayList<>();
        pitchList.add(CalculatorUtil.first(chordFirst));
        pitchList.add(CalculatorUtil.minorThird(chordFirst));
        pitchList.add(CalculatorUtil.diminishedFifth(chordFirst));
        pitchList.add(CalculatorUtil.minorSeventh(chordFirst));
        return pitchList;

    }

    private static ArrayList<Integer> addSeventhComa(ArrayList<Integer> ac, int root) {
        ac.add(0, CalculatorUtil.minorSeventh(CalculatorUtil.diminishOneOctave(root)));
        return ac;
    }

    private static ArrayList<Integer> addMSeventh(ArrayList<Integer> ac, int root) {
        ac.add(0, CalculatorUtil.majorSeventh(CalculatorUtil.diminishOneOctave(root)));
        return ac;
    }

    private static ArrayList<Integer> addNineth(ArrayList<Integer> ac, int root, boolean isbTone) {
        if(!isbTone) ac.add(CalculatorUtil.majorSecond(CalculatorUtil.perfectOctave(root)));
        else ac.add(CalculatorUtil.minorSecond(CalculatorUtil.perfectOctave(root)));
        return ac;
    }

    private static ArrayList<Integer> addTenth(ArrayList<Integer> ac, int root, boolean isbTone) {
        if(!isbTone)ac.add(CalculatorUtil.majorThird(CalculatorUtil.perfectOctave(root)));
        else ac.add(CalculatorUtil.minorThird(CalculatorUtil.perfectOctave(root)));
        return ac;
    }

    private static ArrayList<Integer> addThirteenth(ArrayList<Integer> ac, int root) {
        ac.add(CalculatorUtil.perfectFifth(CalculatorUtil.perfectOctave(root)));
        return ac;
    }

    private static ArrayList<Integer> addSeventh(ArrayList<Integer> ac, int root) {
        for (int i = 0; i < ac.size(); i++) {
            if (ac.get(i) == CalculatorUtil.perfectOctave(root)) {
                if (ac.get(i - 2) == CalculatorUtil.minorThird(root)) {
                    ac.set(i, CalculatorUtil.minorSeventh(root));  // minor SEventh
                } else {
                    ac.set(i, CalculatorUtil.majorSeventh(root));    // major seventh
                }
            }
        }
        return ac;
    }

    private static ArrayList<Integer> addSixth(ArrayList<Integer> ac, int root) {
        ac.add(3, CalculatorUtil.majorSixth(root));
        return ac;
    }

    public static int calculateChordFirst(Chord c, int root) {
        int calculatedFirst = 0;
        switch (c.GetGrade()) {
            case 1:
                calculatedFirst = root;
                break;
            case 2:
                calculatedFirst = root + 2;
                break;
            case 3:
                calculatedFirst = root + 4;
                break;
            case 4:
                calculatedFirst = root + 5;
                break;
            case 5:
                calculatedFirst = root + 7;
                break;
            case 6:
                calculatedFirst = root + 9;
                break;
            case 7:
                calculatedFirst = root + 11;
                break;

        }

        return c.isFlat() ? calculatedFirst - 1 : c.isSharp() ? calculatedFirst + 1 : calculatedFirst;

    }

}
