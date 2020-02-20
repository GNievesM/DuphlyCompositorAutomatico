/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EvolutionaryAlgorithm;

/**
 *
 * @author Gaston
 */
public interface Mutator<T> {
    
    public T mutate (T element);
}
