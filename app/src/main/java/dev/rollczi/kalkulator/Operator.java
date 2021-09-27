package dev.rollczi.kalkulator;

import java.math.BigDecimal;
import java.math.MathContext;

import dev.rollczi.kalkulator.component.Component;
import dev.rollczi.kalkulator.component.FinalNumber;
import panda.std.Result;
import panda.std.function.TriFunction;

public enum Operator {
    ADD     ('+', 1, BigDecimal::add),
    SUBTRACT('-', 1, BigDecimal::subtract),
    MULTIPLY('*', 2, BigDecimal::multiply),
    DIVIDE  ('/', 2, BigDecimal::divide);

    private final char icon;
    private final int priority;
    private final TriFunction<BigDecimal, BigDecimal, MathContext, BigDecimal> function;

    Operator(char icon, int priority, TriFunction<BigDecimal, BigDecimal, MathContext, BigDecimal> function) {
        this.icon = icon;
        this.priority = priority;
        this.function = function;
    }

    public Result<Component, String> calculate(Component a, Component b) {
        if (this == DIVIDE && b.getDouble() == 0.0) {
            return Result.error("Nie można dzielić przez zero!");
        }

        double value = function.apply(a.getBigDecimal(), b.getBigDecimal(), MathContext.DECIMAL64).doubleValue();
        return Result.ok(new FinalNumber(value));
    }

    public char getIcon() {
        return icon;
    }

    public int getPriority() {
        return priority;
    }
}
