package com.example.android.cryptocompare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cryptocompare.data.CryptoContract;

import java.math.BigDecimal;
import java.util.Locale;

public class CryptoConvert extends AppCompatActivity {

    int cryptoExtra;
    int baseExtra;
    double priceExtra;
    Context context = this;
    EditText cryptoEditText;
    EditText baseEditText;
    ImageView cryptoImageView;
    ImageView baseImageView;
    private TextWatcher cryptoTw, worldTw;

    boolean cryptoKeypressed;
    boolean worldKeypressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crypto_covert);

        cryptoEditText = (EditText) findViewById(R.id.crypto_price_textview);
        baseEditText = (EditText) findViewById(R.id.base_price_textview);
        cryptoImageView = (ImageView) findViewById(R.id.crypto_image_view);
         baseImageView = (ImageView) findViewById(R.id.base_image_view);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cryptoExtra = extras.getInt("cryptoCurrency");
            baseExtra = extras.getInt("baseCurrency");
            priceExtra = extras.getDouble("priceCurrency");
            Log.e("LOG_TAG", "the price currency is" + priceExtra);
        }

        cryptoEditText.setOnKeyListener(new View.OnKeyListener() {
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



        baseEditText.setOnKeyListener(new View.OnKeyListener() {
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



        cryptoTw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (cryptoKeypressed) {
                    String str = s.toString();
                    if (count == 1) {
                        cryptoEditText.setText(null);

                    }
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {



                try {

                    if (cryptoEditText.getText().toString() != "") {

                        double number = Double.parseDouble(cryptoEditText.getText().toString().replace("," , ""));

                        BigDecimal converted = BigDecimal.valueOf(number * priceExtra);

                        String formatted = String.format(Locale.US,"%,.2f", converted) ;

                        baseEditText.removeTextChangedListener(worldTw);


                        baseEditText.setText(formatted);


                        baseEditText.addTextChangedListener(worldTw);

                    }
                } catch (NumberFormatException ex) {
                    Log.e("ERROR", ex.getMessage());
                    baseEditText.removeTextChangedListener(worldTw);
                    baseEditText.setText(null);
                    baseEditText.addTextChangedListener(worldTw);
                }


            }
        };


        worldTw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (worldKeypressed) {
                    String str = s.toString();
                    if (count == 1) {
                        baseEditText.setText(null);

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {

                    if (baseEditText.getText().toString() != "" ) {

                        double number = Double.parseDouble(baseEditText.getText().toString().replace(",", ""));




                        BigDecimal converted = BigDecimal.valueOf(number / priceExtra);

                        cryptoEditText.removeTextChangedListener(cryptoTw);

                        String formatted = String.format(Locale.US,"%,.5f", converted) ;


                        cryptoEditText.setText(formatted);


                        cryptoEditText.addTextChangedListener(cryptoTw);

                    }
                } catch (NumberFormatException ex) {
                    Log.e("ERROR", ex.getMessage());
                    cryptoEditText.removeTextChangedListener(cryptoTw);
                    cryptoEditText.setText("");
                    cryptoEditText.addTextChangedListener(cryptoTw);
                }


            }
        };



        cryptoEditText.addTextChangedListener(cryptoTw);


        baseEditText.addTextChangedListener(worldTw);

        switch (baseExtra){

            case CryptoContract.CryptoEntry.BASE_CURRENCY_NAIRA:
                baseImageView.setImageResource(R.drawable.ngn);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_USD:
                baseImageView.setImageResource(R.drawable.usd);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_RUPEE:
                baseImageView.setImageResource(R.drawable.inr);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_SINGAPORE:
                baseImageView.setImageResource(R.drawable.sgd);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_TAIWAN:
                baseImageView.setImageResource(R.drawable.twd);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_AUD:
                baseImageView.setImageResource(R.drawable.aud);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_EURO:
                baseImageView.setImageResource(R.drawable.eur);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_GBP:
                baseImageView.setImageResource(R.drawable.gbp);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_RAND:
                baseImageView.setImageResource(R.drawable.zar);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_MEXICO:
                baseImageView.setImageResource(R.drawable.mxn);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_ISREAL:
                baseImageView.setImageResource(R.drawable.ils);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_NZD:
                baseImageView.setImageResource(R.drawable.nzd);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_SWEDEN:
                baseImageView.setImageResource(R.drawable.sek);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_NORWAY:
                baseImageView.setImageResource(R.drawable.nok);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_FRANC:
                baseImageView.setImageResource(R.drawable.chf);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_SAUDI:
                baseImageView.setImageResource(R.drawable.sar);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_DUBAI:
                baseImageView.setImageResource(R.drawable.aed);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_PHILIPPIANS:
                baseImageView.setImageResource(R.drawable.php);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_GUATEMALA:
                baseImageView.setImageResource(R.drawable.gtq);
                break;

            case CryptoContract.CryptoEntry.BASE_CURRENCY_GHANA:
                baseImageView.setImageResource(R.drawable.ghs);
                break;

            default:
                break;
        }

        switch(cryptoExtra){
            case CryptoContract.CryptoEntry.CRYPTO_CURRENCY_BTC:
                cryptoImageView.setImageResource(R.drawable.btc);
                break;

            case CryptoContract.CryptoEntry.CRYPTO_CURRENCY_ETH:
                cryptoImageView.setImageResource(R.drawable.etc);
                break;

            default:
                break;

        }
    }
}

