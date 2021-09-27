package dev.rollczi.kalkulator.component;

import java.math.BigDecimal;

public interface Component {

    String render();

    BigDecimal getBigDecimal();

    default double getDouble() {
        return this.getBigDecimal().doubleValue();
    }

}
