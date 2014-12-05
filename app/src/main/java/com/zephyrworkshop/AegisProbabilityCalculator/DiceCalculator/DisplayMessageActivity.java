package com.zephyrworkshop.AegisProbabilityCalculator.DiceCalculator;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.text.LoginFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;

import com.zephyrworkshop.AegisProbabilityCalculator.R;

import java.text.DecimalFormat;
import java.util.Random;

public class DisplayMessageActivity extends Activity {

    int totalCrit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        //Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();

        Boolean christmasTree = extras.getBoolean("christmasTree");
        int numDice = Integer.parseInt( extras.getString("numDice"));

        TextView textView = (TextView) findViewById(R.id.textView);
   //     textView.setText(Integer.toString(numDice));

        int accuracy = Integer.parseInt(extras.getString("accuracy"));
        int damage = Integer.parseInt(extras.getString("damage"));
        int critValue = Integer.parseInt(extras.getString("critValue"));

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        DecimalFormat df = new DecimalFormat(("#0.00"));

            if (christmasTree == true) {
                //textView.setText("Chance of all " + numDice + " dice being above " + accuracy);
                textView.setText("Dice count: " + numDice +
                                "\nAccuracy: " + accuracy +
                                "\nDamage: " + damage);
                double totalPossibleOutcomes = Math.pow(6, numDice);
                double probability = (Math.pow(7 - accuracy, numDice) / totalPossibleOutcomes);

                String finalProb = df.format(probability * 100);

                textView2.setText(finalProb + "%" + " chance to deal " + damage + " damage");
            } else {
                textView.setText("Dice count: " + numDice +
                                "\nAccuracy: " + accuracy +
                                "\nDamage: " + damage +
                                "\nAverage of 500 rolls: " + simulateDiceRolls(damage,numDice,accuracy,critValue) +
                                "\nAverage crit damage: " + totalCrit/500);

                ListView resultsLV = (ListView) findViewById(R.id.resultsLV);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

                for (int i = 0; i < numDice; i++) {
                    double totalPossibleOutcomes = Math.pow(6, i + 1);
                    double probability = (Math.pow(7 - accuracy, i + 1) / totalPossibleOutcomes);

                    String finalProb = df.format(probability * 100);

                    adapter.add(finalProb + "%" + " chance to deal " + ((i + 1) * damage) + " damage");
                }

                resultsLV.setAdapter(adapter);
                //      textView2.setText("% " + results[0] + results[1] + results[2]);
            }
    }


    private int simulateDiceRolls(int damagePerHit,int dice, int accuracy, int critValue) {

        int maximum = 6;
        int minimum = 1;
        int range = maximum - minimum + 1;
        Random rn = new Random();

        int totalHits = 0;
        int critDamage = 0;
        for(int i = 0; i < 500; i++) {
            for(int d = 0; d <= dice;d++)
            {
                int randomNum =  rn.nextInt(range) + minimum;
                if(randomNum >= accuracy)
                {
                    totalHits++;
                }

                if(randomNum == accuracy)
                {
                    critDamage += 1 * critValue;
                }
            }
        }
        totalCrit = critDamage;
        return ((totalHits * damagePerHit) + critDamage) /500;
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_message, menu);
        return true;
    }
*/
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
