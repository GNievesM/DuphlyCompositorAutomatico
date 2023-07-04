/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataDefinition;

import ImprovisationManagement.GenericImprovisationRule;

/**
 *
 * @author Gaston
 */
public class TupleNameTypeImpRule {
    String description;
    String type;
    boolean generate;
    GenericImprovisationRule gir;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isGenerate() {
        return generate;
    }

    public void setGenerate(boolean generate) {
        this.generate = generate;
    }

    public GenericImprovisationRule getGir() {
        return gir;
    }

    public void setGir(GenericImprovisationRule gir) {
        this.gir = gir;
    }

    public TupleNameTypeImpRule(String description, String type, boolean generate, GenericImprovisationRule gir) {
        this.description = description;
        this.type = type;
        this.generate = generate;
        this.gir = gir;
    }
    
    
    
}
