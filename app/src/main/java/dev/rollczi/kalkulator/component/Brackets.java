package dev.rollczi.kalkulator.component;

public class Brackets extends Algorithm implements DigitsAddableComponent {

    private boolean closed = false;

    @Override
    public void closeBrackets() {
        this.digitComponentOption
                .is(Brackets.class)
                .peek(Algorithm::closeBrackets)
                .onEmpty(() -> this.setClosed(true));
    }

    @Override
    public Algorithm appendDigit(int digit) {
        if (closed) {
            return this;
        }

        return super.appendDigit(digit);
    }

    @Override
    public void setRight(boolean right) {
        digitComponentOption.peek(component -> component.setRight(true));
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }
}
