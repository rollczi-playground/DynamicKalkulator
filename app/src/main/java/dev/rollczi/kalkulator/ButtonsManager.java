package dev.rollczi.kalkulator;

import android.widget.Button;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ButtonsManager {

    private final Map<String, ButtonData> buttons = new LinkedHashMap<>();

    public ButtonsManager add(String key, Consumer<Button> consumer) {
        buttons.put(key, new ButtonData(key, consumer));
        return this;
    }

    public Map<String, ButtonData> getButtons() {
        return Collections.unmodifiableMap(buttons);
    }
}
