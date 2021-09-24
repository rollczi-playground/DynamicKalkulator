package dev.rollczi.kalkulator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CalculatorService {

    private final List<Number> numbers = new ArrayList<>();

    public void digit(int digit) {
        onLastOrCreate(number -> number.append(digit));
    }

    public void operator(Operator operator) {

        if (!numbers.isEmpty() && numbers.get(numbers.size() - 1).hasMathOperator()) {
            numbers.add(new Number());
        }

        onLastOrCreate(number -> number.setEndOperator(operator));
    }

    private void onLastOrCreate(Consumer<Number> numberConsumer) {
        if (numbers.isEmpty()) {
            Number number = new Number();

            numbers.add(number);
            numberConsumer.accept(number);
        }

        numberConsumer.accept(numbers.get(numbers.size() - 1));
    }



}
