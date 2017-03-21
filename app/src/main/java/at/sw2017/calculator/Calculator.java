package at.sw2017.calculator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Calculator extends Activity implements View.OnClickListener {


    public enum State {
        ADD, SUB, MUL, DIV, INIT, NUM
    }

    private TextView numberView;
    private State state = State.INIT;
    private int firstNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        Button buttonDiv = (Button) findViewById(R.id.buttonDiv);
        Button buttonMul = (Button) findViewById(R.id.buttonMul);
        Button buttonSub = (Button) findViewById(R.id.buttonSub);
        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        Button buttonEqual = (Button) findViewById(R.id.buttonEqual);

        buttonAdd.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
        buttonMul.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonEqual.setOnClickListener(this);
        setUpNumberButtonListener();

    }

    @Override
    public void onClick(View v) {
        numberView = (TextView) findViewById(R.id.textView);
        Button clickedButton = (Button) v;
        switch (clickedButton.getId()) {
            case R.id.buttonAdd:
                clearNumberView();
                state = State.ADD;
                break;
            case R.id.buttonSub:
                clearNumberView();
                state = State.SUB;
                break;
            case R.id.buttonMul:
                clearNumberView();
                state = State.MUL;
                break;
            case R.id.buttonDiv:
                clearNumberView();
                state = State.DIV;
                break;
            case R.id.buttonEqual:
                calculateResult();
                state = State.INIT;
                break;
            case R.id.buttonClear:
                clearTextView();
                break;
            default:
                String recentNumber = numberView.getText().toString();
                if (state == State.INIT) {
                    recentNumber = "";
                    state = State.NUM;
                }
                recentNumber += clickedButton.getText().toString();
                System.out.println(recentNumber);
                numberView.setText(recentNumber);
        }
    }

    private void calculateResult() {
        int secondNumber = 0;
        String tempString = numberView.getText().toString();
        if(!tempString.equals("")){
            secondNumber = Integer.valueOf(tempString);
        }
        int result;
        switch(state){
            case ADD:
                result = Calculations.doAddition(firstNumber, secondNumber);
                break;
            case SUB:
                result = Calculations.doSubtraction(firstNumber, secondNumber);
                break;
            case MUL:
                result = Calculations.doMultiplication(firstNumber, secondNumber);
                break;
            case DIV:
                result = Calculations.doDivision(firstNumber, secondNumber);
                break;
            default:
                result = secondNumber;
        }
        numberView.setText(Integer.toString(result));
    }


    public void setUpNumberButtonListener()
    {
        ArrayList<Button> numberButtons = new ArrayList<>();
        for(int i =0; i <= 9; i++)
        {
            String buttonName = "button" + i;
            int id = getResources().getIdentifier(buttonName, "id", R.class.getPackage().getName());

            Button button = (Button) findViewById(id);
            button.setOnClickListener(this);

            numberButtons.add(button);

        }
    }

    private void clearTextView() {
        TextView numberView = (TextView) findViewById(R.id.textView);
        numberView.setText("0");
        firstNumber = 0;
        state = State.INIT;
    }

    private void clearNumberView() {
        TextView numberView = (TextView) findViewById(R.id.textView);
        String tempString = numberView.getText().toString();
        if(!tempString.equals("")){
            firstNumber = Integer.valueOf(tempString);
        }
        numberView.setText("");
    }

}
