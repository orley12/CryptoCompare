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

    private static final String LOG_TAG = CryptoConvert.class.getSimpleName() ;
    /*Here we declare an int to hold the bundle Extra for cryptoCurrency*/
    String cryptoCurrencyName;

    /*Here we declare an int to hold the bundle Extra for baseCurrency*/
    String localCurrencyName;

    /*Here we declare a double to hold the bundle Extra for priceCurrency*/
    double currencyValue;

    /*Here we declare an EditText View to hold the value for cryptoCurrency*/
    TextInputLayout cryptoTextInputLayout;

    /*Here we declare an EditText View to hold the value for cryptoCurrency*/
    TextInputLayout localTextInputLayout;

    /*Here we declare an ImageView to hold the flag for cryptoCurrency*/
    ImageView cryptoImageView;

    /*Here we declare an ImageView to hold the flag for baseCurrency*/
    ImageView localImageView;

    private TextWatcher cryptoTw, worldTw;

    boolean cryptoKeypressed;

    boolean localKeypressed;

    Context mContext;

    private EditText cryptocurrText;

    private EditText localcurrText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crypto_covert);

        mContext = this;

        /*instantiate widgets*/
        cryptocurrText = new EditText(mContext);
        localcurrText = new EditText(mContext);
        cryptoImageView = (ImageView) findViewById(R.id.crypto_image_view);
        localImageView = (ImageView) findViewById(R.id.local_image_view);



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
        cryptoTextInputLayout= (TextInputLayout) findViewById(R.id.crypto_text_input);
        localTextInputLayout= (TextInputLayout) findViewById(R.id.local_price_input);



        //get bundle
        Bundle bundle = getIntent().getExtras();


        //if bundle is not null
        if (bundle != null) {

            // get bundle values
            cryptoCurrencyName = bundle.getString("cryptoCurrency");
            localCurrencyName = bundle.getString("localCurrency");
            currencyValue = bundle.getDouble("currencyValue");
            Log.i(LOG_TAG,"======>" + cryptoCurrencyName+localCurrencyName+currencyValue + "".toUpperCase());

            //set edittexts hint
            cryptocurrText.setHint(cryptoCurrencyName);
            localcurrText.setHint(localCurrencyName);
            int i = 0;
            for (ViewData viewData : ViewData.values()){
                if (viewData.name().equals(localCurrencyName)){
                    localImageView.setImageResource(viewData.getCurrencyFlag());
                    break;
                }
            }

            for (ViewData viewData : ViewData.values()){
                if (viewData.name().equals(cryptoCurrencyName)){
                    cryptoImageView.setImageResource(viewData.getCurrencyFlag());
                    break;
                }
            }

        }

        //add edittexts to layouts
        cryptoTextInputLayout.addView(cryptocurrText);
        localTextInputLayout.addView(localcurrText);

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


        //set onKeyListener to worldcurrText
        localcurrText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    localKeypressed = true;
                } else {
                    localKeypressed = false;
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

                        /*remove worldcurrText textWatcher before setting text to it to avoid infinite loop caused
                             by both textChangeListeners of both worldcurrText and cryptocurrText*/
                        localcurrText.removeTextChangedListener(worldTw);


                        //set worldcurrText text value
                        localcurrText.setText(formatted);


                        //add textWatcher
                        localcurrText.addTextChangedListener(worldTw);

                    }
                } catch (NumberFormatException ex) {
                    Log.e("ERROR", ex.getMessage());
                    localcurrText.removeTextChangedListener(worldTw);
                    localcurrText.setText(null);
                    localcurrText.addTextChangedListener(worldTw);
                }


            }
        };


        worldTw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (localKeypressed) {
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


        localcurrText.addTextChangedListener(worldTw);


    }
}

