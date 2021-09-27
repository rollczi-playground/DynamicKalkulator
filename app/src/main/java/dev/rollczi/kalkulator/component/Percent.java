package dev.rollczi.kalkulator.component;

import java.math.BigDecimal;
import java.util.function.Supplier;

import dev.rollczi.kalkulator.Operator;
import panda.std.Pair;

public class Percent extends RemovablePseudoDigitsAddable implements Removable, Component {

    private final static Supplier<RawNumber> HUNDRED = () -> new RawNumber()
            .appendDigit(1)
            .appendDigit(0)
            .appendDigit(0);

    private final Operation operation;

    public Percent(DigitsAddableComponent component) {
        this.operation = new Operation(Pair.of(component, HUNDRED.get()), Operator.DIVIDE);
    }

    @Override
    public String render() {
        return operation.getPair().getFirst().render() + "%";
    }

    @Override
    public BigDecimal getBigDecimal() {
        return operation.getBigDecimal();
    }

}
