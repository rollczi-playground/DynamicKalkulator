package dev.rollczi.kalkulator.component;

public abstract class RemovableAbstract implements Removable {

    private boolean removed = false;

    @Override
    public void removeLastElement() {
        this.removed = true;
    }

    @Override
    public boolean isRemoved() {
        return removed;
    }

}
