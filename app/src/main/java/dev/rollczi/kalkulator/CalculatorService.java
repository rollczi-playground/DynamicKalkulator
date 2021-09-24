package dev.rollczi.kalkulator;

import dev.rollczi.kalkulator.component.Algorithm;

public class CalculatorService {

    private Algorithm algorithm = new Algorithm();

    public void openBrackets() {
        algorithm.openBrackets();
    }

    public void closeBrackets() {
        algorithm.closeBrackets();
    }

    public void digit(int digit) {
        algorithm.appendDigit(digit);
    }

    public void operator(Operator operator) {
        algorithm.operator(operator);
    }

    public void clear() {
        algorithm = new Algorithm();
    }

    public double calc() {
        return algorithm.mergeValuesValue();
    }

}
