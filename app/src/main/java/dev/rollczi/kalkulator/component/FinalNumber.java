package dev.rollczi.kalkulator.component;

import java.math.BigDecimal;

public class FinalNumber implements Component {

    private final double value;

    public FinalNumber(double value) {
        this.value = value;
    }

    @Override
    public String render() {
        if (value - Math.floor(value) == 0.0) {
            return String.valueOf(Integer.valueOf((int) value));
        }

        return String.valueOf(value);
    }

    @Override
    public BigDecimal getBigDecimal() {
        return BigDecimal.valueOf(value);
    }

}
