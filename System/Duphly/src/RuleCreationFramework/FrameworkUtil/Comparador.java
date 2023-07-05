package RuleCreationFramework.FrameworkUtil;

public class Comparador {

  int comparador;

  public Comparador(int comparador) {
    this.comparador = comparador;
  }

  public void Mayor() {
    comparador = 1;
  }

  public void Menor() {
    comparador = -1;
  }

  public void Igual() {
    comparador = 0;
  }

  public boolean esMayor() {
    return comparador == 1;
  }

  public boolean esMenor() {
    return comparador == -1;
  }

  public boolean esIgual() {
    return comparador == 0;
  }
}
