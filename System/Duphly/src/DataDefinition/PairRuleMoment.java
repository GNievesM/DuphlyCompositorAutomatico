package DataDefinition;

public class PairRuleMoment {
    int momentOrPositionToApply;
    int rule;

    public PairRuleMoment(int momentOrPositionToApply, int rule) {
        this.momentOrPositionToApply = momentOrPositionToApply;
        this.rule = rule;
    }

    public int getMomentOrPositionToApply() {
        return momentOrPositionToApply;
    }

    public int getRule() {
        return rule;
    }

    public void setMomentOrPositionToApply(int momentOrPositionToApply) {
        this.momentOrPositionToApply = momentOrPositionToApply;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }
}
