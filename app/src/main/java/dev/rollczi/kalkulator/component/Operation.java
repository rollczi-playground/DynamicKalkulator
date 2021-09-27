package dev.rollczi.kalkulator.component;

import java.math.BigDecimal;

import dev.rollczi.kalkulator.Operator;
import panda.std.Pair;

public class Operation implements Component {

    private final Pair<Component, Component> pair;
    private final Operator operator;

    public Operation(Pair<Component, Component> pair, Operator operator) {
        this.pair = pair;
        this.operator = operator;
    }

    public Component calc() {
        return operator.calculate(pair.getFirst(), pair.getSecond());
    }

    @Override
    public String render() {
        return pair.getFirst().render() + " " + operator.getIcon() + " " + pair.getSecond().render();
    }

    @Override
    public BigDecimal getBigDecimal() {
        return this.calc().getBigDecimal();
    }
    
}
