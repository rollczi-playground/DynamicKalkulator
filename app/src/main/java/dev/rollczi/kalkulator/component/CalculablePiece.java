package dev.rollczi.kalkulator.component;

import dev.rollczi.kalkulator.Operator;

public class CalculablePiece implements Calculable {

    private final Component number;
    private Operator operator;

    public CalculablePiece(Component number, Operator operator) {
        this.number = number;
        this.operator = operator;
    }

    @Override
    public Component calcWith(Component b) {
        return operator.calculate(number, b);
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
