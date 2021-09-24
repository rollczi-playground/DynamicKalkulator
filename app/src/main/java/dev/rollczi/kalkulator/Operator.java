package dev.rollczi.kalkulator;

import java.util.function.BiFunction;

import dev.rollczi.kalkulator.component.Component;
import dev.rollczi.kalkulator.component.FinalNumber;

public enum Operator {
    ADD     (1, Double::sum),
    SUBTRACT(1, (a, b) -> a - b),
    MULTIPLY(2, (a, b) -> a * b),
    DIVIDE  (2, (a, b) -> a / b);

    private final int priority;
    private final BiFunction<Double, Double, Number> function;

    Operator(int priority, BiFunction<Double, Double, Number> function) {
        this.priority = priority;
        this.function = function;
    }

    public Component calculate(Component a, Component b) {
        return new FinalNumber(function.apply(a.mergeValuesValue(), b.mergeValuesValue()).doubleValue());
    }

    public int getPriority() {
        return priority;
    }
}
