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
        calculatorService.setRefreshScreen(textView::setText);
        textLayout.setLayoutParams(layoutMax);
        textLayout.addView(textView, layoutMax);
        mainLayout.addView(textLayout, -1);
        textView.setTextSize(50F);

        buttonsManager
                .add("DEL", button -> calculatorService.del())
                .add("7", button -> calculatorService.digit(7))
                .add("8", button -> calculatorService.digit(8))
                .add("9", button -> calculatorService.digit(9))
                .add("/", button -> calculatorService.operator(Operator.DIVIDE))
                .add("4", button -> calculatorService.digit(4))
                .add("5", button -> calculatorService.digit(5))
                .add("6", button -> calculatorService.digit(6))
                .add("*", button -> calculatorService.operator(Operator.MULTIPLY))
                .add("1", button -> calculatorService.digit(1))
                .add("2", button -> calculatorService.digit(2))
                .add("3", button -> calculatorService.digit(3))
                .add("-", button -> calculatorService.operator(Operator.SUBTRACT))
                .add("0", button -> calculatorService.digit(0))
                .add(".", button -> calculatorService.dot())
                .add("=", button -> calculatorService.sum())
                .add("+", button -> calculatorService.operator(Operator.ADD))
                .add("CE", button -> calculatorService.clear())
                .add("+/-", button -> {} )
                .add("%", button -> calculatorService.percent())
                .add("(", button -> calculatorService.openBrackets())
                .add(")", button -> calculatorService.closeBrackets())
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