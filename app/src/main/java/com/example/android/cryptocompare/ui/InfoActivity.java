package com.example.android.cryptocompare.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.android.cryptocompare.R;
import com.example.android.cryptocompare.model.Crypto;
import com.example.android.cryptocompare.model.ViewData;
import com.example.android.cryptocompare.utilities.LocalDatabaseQueryUtils;

import java.util.List;

public class InfoActivity extends AppCompatActivity {

    private static final String LOG_TAG = InfoActivity.class.getSimpleName();
    TableLayout tableLayout;
    View tableHeader;
    LocalDatabaseQueryUtils localDatabaseQueryUtils;
    View tableRow;
    LinearLayout headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Instatantiate layout
        tableLayout = (TableLayout) findViewById(R.id.table_layout);
        headerLayout = (LinearLayout) findViewById(R.id.header);

        //get ArrayList of all currencies in db
        localDatabaseQueryUtils = new LocalDatabaseQueryUtils(this);
        List<Crypto> cryptoList = localDatabaseQueryUtils.findAllCrypto();

        //instatiate table row item for use as column headers
        tableHeader = LayoutInflater.from(this).inflate(R.layout.info_table_header, null, false);
        TextView rowFlagHeader = (TextView) tableHeader.findViewById(R.id.row_flag);
        TextView nameHeader = (TextView) tableHeader.findViewById(R.id.row_name);
        TextView fullnameHeader = (TextView) tableHeader.findViewById(R.id.row_full_name);

        //set column header text
        rowFlagHeader.setText(getResources().getText(R.string.flag));
        nameHeader.setText(getResources().getText(R.string.name));
        fullnameHeader.setText(getResources().getText(R.string.fullname));
        tableHeader.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));/*set background color for column header*/

        //        //add header to table
        headerLayout.addView(tableHeader);

        //for each currency in currencies arraylist
        for (ViewData viewData : ViewData.values()) {

            //create a table row
            tableRow = LayoutInflater.from(this).inflate(R.layout.info_table_row, null, false);

                        //instantiate each cell for the row
            ImageView currencyFlag = (ImageView) tableRow.findViewById(R.id.img_row);
            TextView nameTv = (TextView) tableRow.findViewById(R.id.row_name);
            TextView fullnameTv = (TextView) tableRow.findViewById(R.id.row_full_name);

                        //Set cells values
                    currencyFlag.setImageResource(viewData.getCurrencyFlag());
                    nameTv.setText(viewData.name());
                    fullnameTv.setText(viewData.getLocalCurrencyName());
                    Log.i(LOG_TAG.toUpperCase(), "=====> " + viewData.getLocalCurrencyName());

            //add row to table
            tableLayout.addView(tableRow);

        }

    }
}
