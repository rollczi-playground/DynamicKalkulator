package dev.rollczi.kalkulator;

import java.util.function.Consumer;

import dev.rollczi.kalkulator.component.Algorithm;
import panda.std.Result;

public class CalculatorService {

    private Algorithm algorithm = new Algorithm();
    private Consumer<String> refreshScreen = text -> {};

    public void setRefreshScreen(Consumer<String> refreshScreen) {
        this.refreshScreen = refreshScreen;
    }

    public void openBrackets() {
        algorithm.openBrackets();
        refresh();
    }

    public void closeBrackets() {
        algorithm.closeBrackets();
        refresh();
    }

    public void digit(int digit) {
        algorithm.appendDigit(digit);
        refresh();
    }

    public void operator(Operator operator) {
        algorithm.operator(operator);
        refresh();
    }

    public void percent() {
        algorithm.percent();
        refresh();
    }

    public void del() {
        algorithm.removeLastElement();
        refresh();
    }

    public void clear() {
        algorithm = new Algorithm();
        refresh();
    }

    public void sum() {
        Result<Double, String> result = algorithm.calculateResult()
                .peek(value -> refreshScreen.accept(String.valueOf(value)))
                .onError(error -> refreshScreen.accept(error));

        if (result.isErr()) {
            algorithm = new Algorithm();
        }
    }

    public double doubleValue() {
        return algorithm.calculateResult().orElseGet(error -> Double.NaN);
    }

    public void refresh() {
        refreshScreen.accept(algorithm.render());
    }

    public void dot() {
        algorithm.setRight(true);
        refresh();
    }
}
