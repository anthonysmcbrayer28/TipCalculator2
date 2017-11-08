package com.jrsdevelopers.tipcalculator;

import android.app.Activity;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.R.attr.editable;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends Activity {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final  NumberFormat percentformat = NumberFormat.getCurrencyInstance();

    private double billAmount = 0.0;
    private double percent = 0.15;
    private TextView amountTextView;
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.precentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0));
        totalTextView.setText(currencyFormat.format(0));
        // set amountEditText's TextWatcher
        EditText amountEditText = (EditText)findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeckBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

    }

    private void calculate(){
        percentTextView.setText(percentformat.format(percent));
        // caculate the tip and total
        double tip = billAmount * percent;
        double total = billAmount + tip;

        // display tip and total formatted as currency
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
    }
    private final SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percent = progress / 100.0;
            calculate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    // listener object for the EditText's text changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                billAmount = Double.parseDouble(s.toString())/100.0;
                amountTextView.setText(currencyFormat.format(billAmount));
            }
            catch (NumberFormatException e){
                amountTextView.setText("");
                billAmount = 0.0;
            }
            calculate();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
