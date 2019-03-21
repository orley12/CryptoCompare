package com.example.android.cryptocompare.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.android.cryptocompare.R;
import com.example.android.cryptocompare.model.ViewData;

import java.math.BigDecimal;
import java.util.Locale;

public class CryptoConvert extends AppCompatActivity {
    TextInputLayout cryptoLayout;
    TextInputLayout localLayout;

    EditText cryptocurrText;
    EditText localcurrText;

    ImageView cryptoImage;
    ImageView localImage;

    String cryptoName;
    String localcurName;
    double currencyValue = 0;

    TextWatcher cryptoTw;
    TextWatcher localTw;

    boolean cryptoKeypressed;
    boolean worldKeypressed;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crypto_covert);

        context = this;

        /*instantiate widgets*/
        cryptocurrText = new EditText(context);
        localcurrText = new EditText(context);
        cryptoImage = (ImageView) findViewById(R.id.img_conv_crypto);
        localImage = (ImageView) findViewById(R.id.img_conv_world);



        //set input type for both edittext
        cryptocurrText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        localcurrText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        //set textsize
        cryptocurrText.setTextSize(20);
        localcurrText.setTextSize(20);

        //set textColor
        cryptocurrText.setTextColor(getResources().getColor(R.color.colorAccent));
        localcurrText.setTextColor(getResources().getColor(R.color.colorAccent));


        //instantiate text input layouts
        cryptoLayout = (TextInputLayout) findViewById(R.id.edit_cryptocurr);
        localLayout = (TextInputLayout) findViewById(R.id.edit_worldcurr);



        //get bundle
        Bundle bundle = getIntent().getExtras();


        //if bundle is not null
        if (bundle != null) {

            // get bundle values
            cryptoName = bundle.getString("cryptoCurrencyName");
            localcurName = bundle.getString("localCurrencyName");
            currencyValue = bundle.getDouble("currencyValue");

            //set edittexts hint
            cryptocurrText.setHint(cryptoName);
            localcurrText.setHint(localcurName);
            for (ViewData viewData : ViewData.values()){
                if (viewData.name().equals(cryptoName)){
                    cryptoImage.setImageResource(viewData.getCurrencyFlag());
                }
                if (viewData.name().equals(localcurName)){
                    localImage.setImageResource(viewData.getCurrencyFlag());
                }
            }
        }

        //add edittexts to layouts
        cryptoLayout.addView(cryptocurrText);
        localLayout.addView(localcurrText);

        //set onKeyListener to cryptocurrText
        cryptocurrText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    cryptoKeypressed = true;
                } else {
                    cryptoKeypressed = false;
                }
                return false;
            }
        });


        //set onKeyListener to localcurrText
        localcurrText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    worldKeypressed = true;
                } else {
                    worldKeypressed = false;
                }
                return false;
            }
        });


        //Instantiate crytoTextWatch
        cryptoTw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //in order to avoid infinite loop when the edittext is cleared
                if (cryptoKeypressed) {
                    String str = s.toString();
                    if (count == 1) {
                        cryptocurrText.setText(null);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //get text from cryptocurrText, use the input for conversion
                try {

                    if (cryptocurrText.getText().toString() != "") {

                        // replace/delete the comma(thousand separator)
                        double number = Double.parseDouble(cryptocurrText.getText().toString().replace("," , ""));

                        //convert value from cryptocurrency to world currency
                        BigDecimal converted = BigDecimal.valueOf(number * currencyValue);

                        /*format world currency value with comma as thousand separator*/
                        String formatted = String.format(Locale.US,"%,.2f", converted) ;

                        /*remove localcurrText textWatcher before setting text to it to avoid infinite loop caused
                             by both textChangeListeners of both localcurrText and cryptocurrText*/
                        localcurrText.removeTextChangedListener(localTw);


                        //set localcurrText text value
                        localcurrText.setText(formatted);


                        //add textWatcher
                        localcurrText.addTextChangedListener(localTw);

                    }
                } catch (NumberFormatException ex) {
                    Log.e("ERROR", ex.getMessage());
                    localcurrText.removeTextChangedListener(localTw);
                    localcurrText.setText(null);
                    localcurrText.addTextChangedListener(localTw);
                }


            }
        };


        localTw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (worldKeypressed) {
                    String str = s.toString();
                    if (count == 1) {
                        localcurrText.setText(null);

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {

                    if (localcurrText.getText().toString() != "" ) {

                        double number = Double.parseDouble(localcurrText.getText().toString().replace(",", ""));




                        BigDecimal converted = BigDecimal.valueOf(number / currencyValue);

                        cryptocurrText.removeTextChangedListener(cryptoTw);

                        String formatted = String.format(Locale.US,"%,.5f", converted) ;


                        cryptocurrText.setText(formatted);


                        cryptocurrText.addTextChangedListener(cryptoTw);

                    }
                } catch (NumberFormatException ex) {
                    Log.e("ERROR", ex.getMessage());
                    cryptocurrText.removeTextChangedListener(cryptoTw);
                    cryptocurrText.setText("");
                    cryptocurrText.addTextChangedListener(cryptoTw);
                }


            }
        };



        cryptocurrText.addTextChangedListener(cryptoTw);


        localcurrText.addTextChangedListener(localTw);


    }
}

