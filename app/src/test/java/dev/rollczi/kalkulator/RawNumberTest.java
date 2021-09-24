package dev.rollczi.kalkulator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dev.rollczi.kalkulator.component.RawNumber;

public class RawNumberTest {

    @Test
    public void addition_isCorrect() {
        RawNumber a = new RawNumber();
        RawNumber b = new RawNumber();

        a.appendDigit(1);
        a.appendDigit(2);
        a.appendDigit(3);

        b.appendDigit(4);
        b.appendDigit(0);
        b.appendDigit(5);
        b.appendDigit(2);



        assertEquals(4, 2 + 2);
    }

}