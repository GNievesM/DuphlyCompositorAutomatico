/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RhythmCreator;

import DataDefinition.Chord;
import DataDefinition.Rhythm;
import java.util.List;

/**
 *
 * @author gasto_000
 */
public class RhythmFactory {

  private List<Chord> base;
  private Rhythm rhythmSequence;

  public RhythmFactory(List<Chord> base) {
    this.base = base;
  }

  public Rhythm getRhythmSequence() {
    return rhythmSequence;
  }

  public List<Chord> getBase() {
    return base;
  }

  public void setBase(List<Chord> base) {
    this.base = base;
  }

  public Rhythm CreateRhythmSequence(GenericRhythmRule grr) {
    this.rhythmSequence = grr.GenerateRhytm(base);
    return this.rhythmSequence;
  }

  public void setRhythmSequence(Rhythm rhythmSequence) {
    this.rhythmSequence = rhythmSequence;
  }
}
