package dev.rollczi.kalkulator.component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import dev.rollczi.kalkulator.Operator;
import panda.std.Option;
import panda.std.stream.PandaStream;

public class Algorithm implements DigitsAddableComponent {

    protected final List<Calculable> calculables = new ArrayList<>();
    protected Option<DigitsAddableComponent> digitComponentOption = Option.none();

    public void openBrackets() {
        Brackets brackets = digitComponentOption
                .is(Brackets.class)
                .peek(Algorithm::openBrackets)
                .orElseGet(new Brackets());

        digitComponentOption = Option.of(brackets);
    }

    public void closeBrackets() {
        digitComponentOption
                .is(Brackets.class)
                .peek(Algorithm::closeBrackets);
    }

    @Override
    public Algorithm appendDigit(int digit) {
        digitComponentOption = digitComponentOption
                .peek(component -> component.appendDigit(digit))
                .orElse(new RawNumber().appendDigit(0));
        return this;
    }

    public void operator(Operator operator) {
        if (calculables.isEmpty()) {
            return;
        }

        if (digitComponentOption.isEmpty()) {
            int lastIndex = calculables.size() - 1;
            calculables.get(lastIndex).setOperator(operator);
            return;
        }

        CalculablePiece piece = new CalculablePiece(digitComponentOption.get(), operator);
        calculables.add(piece);
        digitComponentOption = Option.none();
    }


    @Override
    public double mergeValuesValue() {
        if (calculables.isEmpty()) {
            return 0;
        }

        PandaStream.of(calculables)
                .sorted(Comparator.comparingInt(c -> c.getOperator().getPriority()));

        //TODO: Zrobić to coś
        return 0;
    }

}
