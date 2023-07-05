package RuleCreationFramework.BlockFilter;

import DataDefinition.Note;
import ImprovisationRules.Util;
import java.util.List;
import javafx.util.Pair;

public class BlockPositionInMusic {

  boolean isPercentaje;
  boolean aleatorio;
  boolean isRelativePosition;
  double specificNumber;
  boolean greaterThan;
  boolean lesserThan;
  boolean start;
  boolean middle;
  boolean end;
  int relativePosition;

  /**
   *
   * @param isPercentaje
   * @param specificNumber
   */
  public BlockPositionInMusic(boolean isPercentaje, double specificNumber) {
    this.isPercentaje = isPercentaje;
    this.specificNumber = specificNumber;
  }

  public BlockPositionInMusic(boolean aleatorio) {
    this.aleatorio = aleatorio;
  }

  public BlockPositionInMusic(int relativePosition, boolean greaterThan) {
    this.relativePosition = relativePosition;
    this.greaterThan = greaterThan;
    this.lesserThan = !greaterThan;
  }

  public BlockPositionInMusic(boolean start, boolean middle, boolean end) {
    this.start = start;
    this.middle = middle;
    this.end = end;
  }

  public Pair<Integer, Integer> GetPosition(
    List<Note> melody,
    int blockQuantity
  ) {
    Pair<Integer, Integer> position = null;
    if (this.isPercentaje) position =
      this.GetPositionByPercentaje(melody, blockQuantity);
    if (this.aleatorio) position =
      this.GetRandomPosition(melody, blockQuantity);
    if (this.start || this.middle || this.end) position =
      this.GetPositionByRelativePosition(melody, blockQuantity);
    if (this.greaterThan || this.lesserThan) position =
      this.GetPositionByNumber(melody, blockQuantity);
    return position;
  }

  private Pair<Integer, Integer> GetPositionByPercentaje(
    List<Note> melody,
    int blockQuantity
  ) {
    int totalBlocks = Util.GetBlockQuantity(melody);

    int percentajePosition = (int) Math.floor(
      totalBlocks * this.specificNumber / 100
    );
    int partitionPositionStart = -1;
    int partitionPositionEnd = -1;
    partitionPositionStart =
      percentajePosition - blockQuantity / 2 >= 0
        ? percentajePosition - blockQuantity / 2
        : percentajePosition;
    partitionPositionEnd =
      percentajePosition + blockQuantity / 2 >= totalBlocks
        ? percentajePosition + blockQuantity / 2
        : percentajePosition;
    if (
      partitionPositionStart == percentajePosition &&
      partitionPositionEnd == percentajePosition
    ) {
      partitionPositionStart = 0;
      partitionPositionEnd = totalBlocks;
    }
    Integer start = Util.getBlockPositionByNumber(
      melody,
      partitionPositionStart
    );
    Integer end = Util.getBlockPositionByNumber(melody, partitionPositionEnd);
    Pair<Integer, Integer> solution = new Pair<>(start, end);
    return solution;
  }

  private Pair<Integer, Integer> GetPositionByNumber(
    List<Note> melody,
    int blockQuantity
  ) {
    int totalBlocks = Util.GetBlockQuantity(melody);
    int partitionPositionStart = -1;
    int partitionPositionEnd = -1;
    if (this.greaterThan == true) {
      partitionPositionStart = this.relativePosition;
      partitionPositionEnd =
        this.relativePosition + blockQuantity > totalBlocks
          ? totalBlocks
          : this.relativePosition + blockQuantity;
    }

    Integer start = Util.getBlockPositionByNumber(
      melody,
      partitionPositionStart
    );
    Integer end = Util.getBlockPositionByNumber(melody, partitionPositionEnd);
    Pair<Integer, Integer> solution = new Pair<>(start, end);
    return solution;
  }

  private Pair<Integer, Integer> GetPositionByRelativePosition(
    List<Note> melody,
    int blockQuantity
  ) {
    if (this.middle == true) this.specificNumber = 0.50;
    if (this.start == true) this.specificNumber = 0;
    if (this.end == true) this.specificNumber = 0.80;
    return this.GetPositionByPercentaje(melody, blockQuantity);
  }

  private Pair<Integer, Integer> GetRandomPosition(
    List<Note> melody,
    int blockQuantity
  ) {
    this.specificNumber = Math.random();
    return this.GetPositionByPercentaje(melody, blockQuantity);
  }
}
