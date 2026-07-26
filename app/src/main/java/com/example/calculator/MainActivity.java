package com.example.calculator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvExpression, tvResult;
    private StringBuilder currentInput = new StringBuilder();
    private boolean resultShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvExpression = findViewById(R.id.tvExpression);
        tvResult = findViewById(R.id.tvResult);

        int[] numberIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        String[] numberVals = {"0","1","2","3","4","5","6","7","8","9"};

        for (int i = 0; i < numberIds.length; i++) {
            final String val = numberVals[i];
            findViewById(numberIds[i]).setOnClickListener(v -> onNumberClick(val));
        }

        findViewById(R.id.btnDot).setOnClickListener(v -> onNumberClick("."));

        findViewById(R.id.btnPlus).setOnClickListener(v -> onOperatorClick("+"));
        findViewById(R.id.btnMinus).setOnClickListener(v -> onOperatorClick("−"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> onOperatorClick("×"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> onOperatorClick("÷"));

        findViewById(R.id.btnClear).setOnClickListener(v -> onClearClick());
        findViewById(R.id.btnBackspace).setOnClickListener(v -> onBackspaceClick());
        findViewById(R.id.btnEquals).setOnClickListener(v -> onEqualsClick());
        findViewById(R.id.btnPercent).setOnClickListener(v -> onPercentClick());
    }

    // Called when a number or dot button is pressed
    private void onNumberClick(String val) {
        if (resultShown) {
            currentInput.setLength(0);
            tvExpression.setText("");
            resultShown = false;
        }
        if (val.equals(".")) {
            String lastNum = getLastNumberSegment();
            if (lastNum.contains(".")) return; // don't allow two dots in one number
        }
        currentInput.append(val);
        tvResult.setText(currentInput.toString());
    }

    // Called when an operator button is pressed
    private void onOperatorClick(String op) {
        if (currentInput.length() == 0) return; // don't allow operator at the start
        resultShown = false;
        char last = currentInput.charAt(currentInput.length() - 1);
        if (isOperator(last)) {
            currentInput.deleteCharAt(currentInput.length() - 1); // replace last operator
        }
        currentInput.append(op);
        tvResult.setText(currentInput.toString());
    }

    private void onPercentClick() {
        if (currentInput.length() == 0) return;
        try {
            double value = evaluateExpression(currentInput.toString());
            value = value / 100;
            currentInput.setLength(0);
            currentInput.append(formatNumber(value));
            tvResult.setText(currentInput.toString());
        } catch (Exception e) {
            tvResult.setText("Error");
        }
    }

    private void onClearClick() {
        currentInput.setLength(0);
        tvExpression.setText("");
        tvResult.setText("0");
        resultShown = false;
    }

    private void onBackspaceClick() {
        if (currentInput.length() > 0) {
            currentInput.deleteCharAt(currentInput.length() - 1);
            tvResult.setText(currentInput.length() == 0 ? "0" : currentInput.toString());
        }
    }

    private void onEqualsClick() {
        if (currentInput.length() == 0) return;
        String expr = currentInput.toString();

        // remove trailing operator if present
        char lastChar = expr.charAt(expr.length() - 1);
        if (isOperator(lastChar)) {
            expr = expr.substring(0, expr.length() - 1);
        }

        try {
            double result = evaluateExpression(expr);
            tvExpression.setText(expr);
            String formatted = formatNumber(result);
            tvResult.setText(formatted);
            currentInput.setLength(0);
            currentInput.append(formatted);
            resultShown = true;
        } catch (ArithmeticException ae) {
            tvExpression.setText(expr);
            tvResult.setText("Error");
            currentInput.setLength(0);
            resultShown = true;
        } catch (Exception e) {
            tvResult.setText("Error");
            currentInput.setLength(0);
            resultShown = true;
        }
    }

    // ---------- Helper Functions ----------

    private boolean isOperator(char c) {
        return c == '+' || c == '−' || c == '×' || c == '÷';
    }

    private String getLastNumberSegment() {
        String str = currentInput.toString();
        int idx = -1;
        for (int i = str.length() - 1; i >= 0; i--) {
            if (isOperator(str.charAt(i))) {
                idx = i;
                break;
            }
        }
        return str.substring(idx + 1);
    }

    private String formatNumber(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        } else {
            return String.valueOf(value);
        }
    }

    // Evaluates the expression following BODMAS: first × and ÷, then + and −
    private double evaluateExpression(String expr) {
        List<Double> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();

        StringBuilder num = new StringBuilder();
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (isOperator(c)) {
                numbers.add(Double.parseDouble(num.toString()));
                num.setLength(0);
                operators.add(c);
            } else {
                num.append(c);
            }
        }
        numbers.add(Double.parseDouble(num.toString()));

        // Step 1: solve × and ÷ first (left to right)
        int i = 0;
        while (i < operators.size()) {
            char op = operators.get(i);
            if (op == '×' || op == '÷') {
                double a = numbers.get(i);
                double b = numbers.get(i + 1);
                double res;
                if (op == '×') {
                    res = a * b;
                } else {
                    if (b == 0) throw new ArithmeticException("Divide by zero");
                    res = a / b;
                }
                numbers.set(i, res);
                numbers.remove(i + 1);
                operators.remove(i);
            } else {
                i++;
            }
        }

        // Step 2: solve + and − (left to right)
        double result = numbers.get(0);
        for (i = 0; i < operators.size(); i++) {
            char op = operators.get(i);
            double next = numbers.get(i + 1);
            if (op == '+') {
                result += next;
            } else if (op == '−') {
                result -= next;
            }
        }

        return result;
    }
}