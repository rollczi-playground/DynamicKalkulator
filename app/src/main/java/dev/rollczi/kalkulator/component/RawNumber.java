package dev.rollczi.kalkulator.component;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RawNumber implements DigitsAddableComponent {

    private final Pair<List<Integer>, List<Integer>> pairDigits = new Pair<>(new ArrayList<>(), new ArrayList<>());
    private boolean right = false;

    @Override
    public DigitsAddableComponent appendDigit(int digit) {
        this.functionOnList(list -> list.add(digit));
        return this;
    }

    public void dropLast() {
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
    public double mergeValuesValue() {
        int size = pairDigits.second.size();
        double out = 0;

        for (int i = 0; i < size; i++) {
            out += pairDigits.second.get(i) * Math.max(10 * (size - i), 1);
        }

        return out;
    }

    public FinalNumber toFinalNumber() {
        return new FinalNumber(this.mergeValuesValue());
    }

}
