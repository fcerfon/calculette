package com.example.formation1.calculator;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private enum Operations {
        PLUS('+'),
        MINUS('-'),
        DIVIDE('/'),
        MULTIPLY('*'),
        EQUALS('='),
        NONE('\0');

        private final char asChar;

        public char asChar() {
            return asChar;
        }

        public static Operations getOperations(char c) {
            switch (c) {
                case '+' :
                    return Operations.PLUS;
                case '-' :
                    return Operations.MINUS;
                case '/' :
                    return Operations.DIVIDE;
                case '*' :
                    return Operations.MULTIPLY;
                case '=' :
                    return Operations.EQUALS;
                default:
                    return Operations.NONE;
            }
        }

        Operations(char asChar) {
            this.asChar = asChar;
        }
    }

    private Button[] digits;
    private Button[] operands;
    private TextView text;
    private float lastValue;
    private Operations lastOperand;
    boolean textIsToDelete;

    private final String RESULT_TEXT_KEY = "calcText";
    private final String RESULT_LAST_VALUE_KEY = "lastValue";
    private final String RESULT_LAST_OPERAND_KEY = "lastOperand";
    private final String RESULT_TEXTTODELETE_KEY = "textIsToDelete";

    private void initialize() {

        digits[0] = findViewById(R.id.button_digit0);
        digits[1] = findViewById(R.id.button_digit1);
        digits[2] = findViewById(R.id.button_digit2);
        digits[3] = findViewById(R.id.button_digit3);
        digits[4] = findViewById(R.id.button_digit4);
        digits[5] = findViewById(R.id.button_digit5);
        digits[6] = findViewById(R.id.button_digit6);
        digits[7] = findViewById(R.id.button_digit7);
        digits[8] = findViewById(R.id.button_digit8);
        digits[9] = findViewById(R.id.button_digit9);
        digits[10] = findViewById(R.id.button_point);

        operands[0] = findViewById(R.id.button_plus);
        operands[1] = findViewById(R.id.button_sub);
        operands[2] = findViewById(R.id.button_mult);
        operands[3] = findViewById(R.id.button_divide);
        operands[4] = findViewById(R.id.button_equal);

        text = findViewById(R.id.textview);
    }

    private void checkIFAllZeros() {
        String str = text.getText().toString();
        float result = 1;
        try {
            result = Float.parseFloat(str);
        } catch (Exception e) {}
        if (result == 0) {
            text.setText("");
        }
    }

    private void onDigitClicked(Button b) {
        String digitStr = b.getText().toString();
        if (textIsToDelete) {
            text.setText("");
            textIsToDelete = false;
        }
        checkIFAllZeros();
        text.append(digitStr);
    }

    private float makeOperation(float currentNumber) {
        Operations operand = lastOperand;

        switch (operand) {
            case PLUS:
                return lastValue + currentNumber;
            case MINUS:
                return lastValue - currentNumber;
            case MULTIPLY:
                return lastValue * currentNumber;
            case DIVIDE:
                return lastValue / currentNumber;
            default :
                return lastValue;
        }
    }

    private void saveCurrentValue() {
        String str = text.getText().toString();
        try {
            float currentNumber = Float.parseFloat(str);
            if (lastOperand != Operations.NONE) {
                lastValue = makeOperation(currentNumber);
            }
            else {
                lastValue = Float.parseFloat(str);
            }
        } catch (Exception e) {}
    }

    private void showResult() {
        String str = text.getText().toString();
        try {
            float currentNumber = Float.parseFloat(str);
            if (lastOperand != Operations.NONE) {
                lastValue = makeOperation(currentNumber);
            }
            else {
                lastValue = Float.parseFloat(str);
            }
        } catch (Exception e) {}
        text.setText(String.valueOf(lastValue));

        lastValue = 0;
        lastOperand = Operations.NONE;
        textIsToDelete = true;
    }

    private void onOperandClicked(Button b) {
        Operations operand = Operations.getOperations(b.getText().toString().charAt(0));
        if (operand == Operations.EQUALS) {
            showResult();
        }
        else {
            saveCurrentValue();
            lastOperand = operand;
            textIsToDelete = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        digits = new Button[11];
        operands = new Button[5];
        lastValue = 0.0f;
        textIsToDelete = false;
        lastOperand = Operations.NONE;

        setContentView(R.layout.activity_main);
        initialize();

        if (savedInstanceState != null) {
            text.setText(savedInstanceState.getString(RESULT_TEXT_KEY));
            lastValue = savedInstanceState.getFloat(RESULT_LAST_VALUE_KEY);
            char lOperand = savedInstanceState.getChar(RESULT_LAST_OPERAND_KEY);
            lastOperand = Operations.getOperations(lOperand);
            textIsToDelete = savedInstanceState.getBoolean(RESULT_TEXTTODELETE_KEY);
        }

        for (Button digit : digits) {
            digit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onDigitClicked( (Button)v );
                }
            });
        }

        for (Button operand : operands) {
            operand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOperandClicked( (Button)v );
                }
            });
        }

        Button infoButton = findViewById(R.id.button_infos);
        infoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RESULT_TEXT_KEY, text.getText().toString());
        outState.putFloat(RESULT_LAST_VALUE_KEY, lastValue);
        outState.putChar(RESULT_LAST_OPERAND_KEY, lastOperand.asChar());
        outState.putBoolean(RESULT_TEXTTODELETE_KEY, textIsToDelete);
    }
}
