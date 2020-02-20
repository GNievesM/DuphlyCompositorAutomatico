/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RuleCreationFramework.FrameworkUtil;

/**
 *
 * @author Gaston
 */
public class Comparador {
    // clase con el fin de determinar unicamente si busco que sea mayor menor o igual el comparador.
    int comparador;

    public Comparador(int comparador) {
        this.comparador = comparador;
    }
    
    public void Mayor(){
        comparador =1;
    }
    public void Menor(){
        comparador = -1;
    }
    public void Igual(){
        comparador = 0;
    }
    public boolean esMayor(){
        return comparador ==1;
    }
    public boolean esMenor(){
        return comparador ==-1;
    }
    public boolean esIgual(){
        return comparador==0;
    }
}
