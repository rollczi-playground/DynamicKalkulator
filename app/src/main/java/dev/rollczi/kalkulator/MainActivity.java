package dev.rollczi.kalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private final ButtonsManager buttonsManager = new ButtonsManager();
    private final CalculatorService calculatorService = new CalculatorService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout mainLayout = findViewById(R.id.layout);


        LayoutParams layoutMax = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutMax.leftMargin += 50;
        layoutMax.rightMargin += 50;

        LinearLayout textLayout = new LinearLayout(this);
        TextView textView = new TextView(this);
        textView.setText("0");
        textLayout.setLayoutParams(layoutMax);
        textLayout.addView(textView, layoutMax);
        mainLayout.addView(textLayout, -1);

        buttonsManager
                .add("DEL", button -> {} )
                .add("7", button -> textView.setText("7"))
                .add("8", button -> {})
                .add("9", button -> {})
                .add("/", button -> {})
                .add("4", button -> {})
                .add("5", button -> {})
                .add("6", button -> {})
                .add("*", button -> {})
                .add("1", button -> {})
                .add("2", button -> {})
                .add("3", button -> {} )
                .add("-", button -> {} )
                .add("0", button -> {} )
                .add(".", button -> {} )
                .add("=", button -> {} )
                .add("+", button -> {} )
                .add("CE", button -> {} )
                .add("+/-", button -> {} )
                .add("%", button -> {} )
                ;

        Iterator<ButtonData> iterator = buttonsManager.getButtons().values().iterator();
        int row = 0;
        while (iterator.hasNext()) {
            LinearLayout subLayout = new LinearLayout(this);

            subLayout.setLayoutParams(layoutMax);
            subLayout.setGravity(Gravity.CENTER);

            int column = 0;
            while (iterator.hasNext() && column < 4) {
                ButtonData buttonData = iterator.next();
                LayoutParams paramsBut = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.8f);
                Button button = new Button(this);

                paramsBut.topMargin += row * 140;
                button.setText(buttonData.getKey());
                button.setOnClickListener(v -> buttonData.getButtonConsumer().accept((Button) v));
                subLayout.addView(button, paramsBut);
                column++;
            }

            mainLayout.addView(subLayout, row);
            row++;
        }
    }
}