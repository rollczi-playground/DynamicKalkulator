package dev.rollczi.kalkulator;

import android.widget.Button;

import java.util.function.Consumer;

public class ButtonData {

    private final String key;
    private final Consumer<Button> buttonConsumer;

    public ButtonData(String key, Consumer<Button> buttonConsumer) {
        this.key = key;
        this.buttonConsumer = buttonConsumer;
    }

    public String getKey() {
        return key;
    }

    public Consumer<Button> getButtonConsumer() {
        return buttonConsumer;
    }
}
