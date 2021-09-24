package dev.rollczi.kalkulator.component;

public class FinalNumber implements Component {

    private final double value;

    public FinalNumber(double value) {
        this.value = value;
    }

    @Override
    public double mergeValuesValue() {
        return value;
    }

}
