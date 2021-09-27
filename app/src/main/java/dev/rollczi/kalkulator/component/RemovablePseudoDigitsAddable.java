package dev.rollczi.kalkulator.component;

public abstract class RemovablePseudoDigitsAddable extends RemovableAbstract implements DigitsAddableComponent {

    @Override
    public DigitsAddableComponent appendDigit(int digit) {
        return this;
    }

    @Override
    public void setRight(boolean right) {}

}
