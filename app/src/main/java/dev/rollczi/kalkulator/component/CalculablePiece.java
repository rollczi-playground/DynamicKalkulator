package dev.rollczi.kalkulator.component;

import dev.rollczi.kalkulator.Operator;

public class CalculablePiece implements Calculable {

    private final DigitsAddableComponent number;
    private Operator operator;

    public CalculablePiece(DigitsAddableComponent number, Operator operator) {
        this.number = number;
        this.operator = operator;
    }

    @Override
    public Component calcWith(Component b) {
        return operator.calculate(number, b).orElseGet((error) -> new FinalNumber(Double.NaN));
    }

    @Override
    public DigitsAddableComponent getComponent() {
        return number;
    }

    @Override
    public Operator getOperator() {
        return operator;
    }

    @Override
    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
