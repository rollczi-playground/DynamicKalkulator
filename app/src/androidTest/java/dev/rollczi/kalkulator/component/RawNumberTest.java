package dev.rollczi.kalkulator.component;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import dev.rollczi.kalkulator.Operator;

@RunWith(AndroidJUnit4.class)
public class RawNumberTest extends TestCase {

    @Test
    public void checkMeagre() {
        RawNumber rawNumber = new RawNumber();

        rawNumber.appendDigit(3);
        rawNumber.appendDigit(4);
        rawNumber.appendDigit(5);
        rawNumber.setRight(true);
        rawNumber.appendDigit(9);
        rawNumber.appendDigit(8);
        rawNumber.appendDigit(7);

        assertEquals(345.987D, rawNumber.getDouble());
    }

    @Test
    public void checkFloatingPointInRawNumber() {
        RawNumber rawNumber = new RawNumber();

        rawNumber.setRight(true);
        rawNumber.appendDigit(1);
        rawNumber.appendDigit(2);

        assertEquals(0.12D, rawNumber.getDouble());
    }

    @Test
    public void checkFloatingPoint() {
        RawNumber a = new RawNumber();

        a.setRight(true);
        a.appendDigit(1);

        RawNumber b = new RawNumber();

        b.setRight(true);
        b.appendDigit(2);

        CalculablePiece calculableA = new CalculablePiece(a, Operator.ADD);
        Component component = calculableA.calcWith(b);

        assertEquals(0.3D, component.getDouble());
    }

}