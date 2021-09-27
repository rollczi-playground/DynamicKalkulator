package dev.rollczi.kalkulator.component;

import java.math.BigDecimal;

import dev.rollczi.kalkulator.Operator;
import panda.std.Pair;
import panda.std.Result;

public class Operation implements Component {

    private final Pair<DigitsAddableComponent, DigitsAddableComponent> pair;
    private final Operator operator;

    public Operation(Pair<DigitsAddableComponent, DigitsAddableComponent> pair, Operator operator) {
        this.pair = pair;
        this.operator = operator;
    }

    public Result<Component, String> calculate() {
        return operator.calculate(pair.getFirst(), pair.getSecond());
    }

    public Pair<DigitsAddableComponent, DigitsAddableComponent> getPair() {
        return pair;
    }

    @Override
    public String render() {
        return pair.getFirst().render() + " " + operator.getIcon() + " " + pair.getSecond().render();
    }

    @Override
    public BigDecimal getBigDecimal() {
        return this.calculate().orElseGet((error) -> new FinalNumber(Double.NaN)).getBigDecimal();
    }
    
}
