package dev.rollczi.kalkulator.component;

public interface DigitsAddableComponent extends Component, Removable{

    DigitsAddableComponent appendDigit(int digit);

    void setRight(boolean right);

}
