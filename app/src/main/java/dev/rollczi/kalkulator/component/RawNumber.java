package dev.rollczi.kalkulator.component;

import android.util.Pair;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import panda.std.Result;

public class RawNumber implements DigitsAddableComponent {

    private final Pair<List<Integer>, List<Integer>> pairDigits = new Pair<>(new ArrayList<>(), new ArrayList<>());
    private boolean right = false;

    @Override
    public RawNumber appendDigit(int digit) {
        this.functionOnList(list -> list.add(digit));
        return this;
    }

    @Override
    public void removeLastElement() {
        if (functionOnList(List::isEmpty)) {
            if (pairDigits.second.isEmpty()) {
                right = false;
            }

            return;
        }

        functionOnList(list -> list.remove(list.size() - 1));
    }

    private  <T> T functionOnList(Function<List<Integer>, T> listConsumer) {
        return listConsumer.apply(right ? pairDigits.second : pairDigits.first);
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    @Override
    public boolean isRemoved() {
        return this.pairDigits.first.isEmpty() && this.pairDigits.second.isEmpty();
    }

    @Override
    public String render() {
        double value = this.getDouble();

        if (value - Math.floor(value) == 0.0) {
            return (int) value + (right ? "." : "");
        }

        return String.valueOf(value);
    }

    @Override
    public BigDecimal getBigDecimal() {
        BigDecimal out = new BigDecimal(0);

        for (int i = 0, size = pairDigits.first.size(); i < size; i++) {
            double addZeros = Math.max(Math.pow(10, size - i - 1), 1);

            out = out.add(BigDecimal.valueOf(pairDigits.first.get(i) * addZeros));
        }

        for (int i = 0, size = pairDigits.second.size(); i < size; i++) {
            BigDecimal removeZero = BigDecimal.valueOf(Math.pow(10, i + 1));
            BigDecimal value = BigDecimal.valueOf(pairDigits.second.get(i));

            out = out.add(value.divide(removeZero, MathContext.DECIMAL64));
        }

        return out;
    }

    public FinalNumber toFinalNumber() {
        return new FinalNumber(this.getBigDecimal().doubleValue());
    }

    public static Result<RawNumber, String> of(double value) {
        RawNumber rawNumber = new RawNumber();

        for (char c : String.valueOf(value).toCharArray()) {
            if (c == '.') {
                rawNumber.setRight(true);
                continue;
            }

            int numericValue = Character.getNumericValue(c);

            if (numericValue == - 1) {
                return Result.error("Błąd!");
            }

            rawNumber.appendDigit(numericValue);
        }

        return Result.ok(rawNumber);
    }

}
