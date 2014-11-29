package com.zephyrworkshop.AegisProbabilityCalculator.CardViewer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.zephyrworkshop.AegisProbabilityCalculator.R;

import java.text.ParseException;


public class CardViewer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_viewer);

        setText();
    }

    private void setText()
    {
        Bundle extras = getIntent().getExtras();

        TextView cardNameTV = (TextView) findViewById(R.id.cardNameTV);
        cardNameTV.setText(extras.getString("cardName") + " " + extras.getString("type") + " " + extras.getString("energy") + " " + extras.getString("movement") + " " + extras.getString("integrity") );

        TextView cardDetail = (TextView) findViewById(R.id.nameTV);
        cardDetail.setText(extras.getString("cardName"));

         cardDetail = (TextView) findViewById(R.id.classTV);
        cardDetail.setText(extras.getString("type"));

         cardDetail = (TextView) findViewById(R.id.movementTV);
        cardDetail.setText(extras.getString("movement"));

         cardDetail = (TextView) findViewById(R.id.energyTV);
        cardDetail.setText(extras.getString("energy"));

         cardDetail = (TextView) findViewById(R.id.integrityTV);
        cardDetail.setText(extras.getString("integrity"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
