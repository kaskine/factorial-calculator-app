package com.example.kaskine.factorialcalculator;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int iInputValue;
    private double dOutputValue;
    private boolean bOutputCalculated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();

        final EditText inputField = (EditText) findViewById(R.id.inputField);
        final TextView outputField = (TextView) findViewById(R.id.outputField);
        final Button submitButton = (Button) findViewById(R.id.submitButton);

        if (savedInstanceState != null) {
            iInputValue = savedInstanceState.getInt(res.getString(R.string.iInputValueStoreID));
            dOutputValue = savedInstanceState.getDouble(res.getString(R.string.dOutputValueStoreID));
            bOutputCalculated = savedInstanceState.getBoolean(res.getString(R.string.bOutputCalculatedStoreID));

            if (bOutputCalculated) {
                setOutputFieldText(outputField);
            }
        }
        else {
            iInputValue = 0;
            dOutputValue = 0;
            bOutputCalculated = false;
        }

        inputField.setHint(R.string.user_hint);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String sInputText = inputField.getText().toString();

                if (sInputText.isEmpty() || Integer.parseInt(sInputText) == 0) {
                    dOutputValue = 0;
                    bOutputCalculated = false;

                    makeInputErrorToast();
                    outputField.setText(new String());
                }
                else {
                    iInputValue = Integer.parseInt(sInputText);
                    dOutputValue = calculateFactorial((double) iInputValue);
                    bOutputCalculated = true;
                    setOutputFieldText(outputField);
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        Resources res = getResources();

        outState.putInt(res.getString(R.string.iInputValueStoreID), iInputValue);
        outState.putDouble(res.getString(R.string.dOutputValueStoreID), dOutputValue);
        outState.putBoolean(res.getString(R.string.bOutputCalculatedStoreID), bOutputCalculated);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Recursive factorial calculation algorithm
     *
     * @param iInput - A value to use in calculating the value of the factorial.
     *
     * @return Returns 1 for base case, returns 0 for an input of 0 in order to prevent an error in the case that
     * the user enters a 0.
     */
    public double calculateFactorial(double iInput) {

        return (iInput == 0 || iInput == 1) ? iInput : iInput * calculateFactorial(iInput - 1);
    }

    /**
     * Sets the text for the output field.
     *
     * @param outputField - The output field where the output is to be set.
     *                    Formats large values so that they display using scientific notation instead of trailing zeros.
     */
    public void setOutputFieldText(TextView outputField) {

        java.text.DecimalFormat df = new java.text.DecimalFormat(getResources().getString(R.string.decimal_format));
        String output;

        if (iInputValue >= 11) {
            output = iInputValue + getResources().getString(R.string.output_prefix) + Double.toString(dOutputValue);
            outputField.setText(output);
        }
        else {
            output = iInputValue + getResources().getString(R.string.output_prefix) + df.format(dOutputValue);
            outputField.setText(output);
        }

    }

    /**
     * Creates and shows a Toast for an input which throws an error.
     */
    public void makeInputErrorToast() {

        Toast.makeText(this,
                       getResources().getString(R.string.input_error_text),
                       Toast.LENGTH_SHORT).show();
    }
}
