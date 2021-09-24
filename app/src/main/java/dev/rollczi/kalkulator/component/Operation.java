package dev.rollczi.kalkulator.component;

import dev.rollczi.kalkulator.Operator;
import panda.std.Pair;

public class Operation implements Component {

    private final Pair<RawNumber, RawNumber> pair;
    private final Operator operator;

    public Operation(Pair<RawNumber, RawNumber> pair, Operator operator) {
        this.pair = pair;
        this.operator = operator;
    }

    public Component calc() {
        return operator.calculate(pair.getFirst(), pair.getSecond());
    }

    @Override
    public double mergeValuesValue() {
        return this.calc().mergeValuesValue();
    }
    
}
