/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataDefinition;

/**
 *
 * @author gasto_000
 */
public class PairRuleMoment {
    int MomentOrPositionToApply;
    int rule;

    public PairRuleMoment(int MomentOrPositionToApply, int rule) {
        this.MomentOrPositionToApply = MomentOrPositionToApply;
        this.rule = rule;
    }

    public int getMomentOrPositionToApply() {
        return MomentOrPositionToApply;
    }

    public int getRule() {
        return rule;
    }

    public void setMomentOrPositionToApply(int MomentOrPositionToApply) {
        this.MomentOrPositionToApply = MomentOrPositionToApply;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }
    
    
    
}
