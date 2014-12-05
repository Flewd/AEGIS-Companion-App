package com.zephyrworkshop.AegisProbabilityCalculator.DiceCalculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.zephyrworkshop.AegisProbabilityCalculator.R;


public class MainActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.zephyrworkshop.AegisProbabilityCalculator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void sendMessage(View view) {

        EditText numDiceET = (EditText) findViewById(R.id.numDiceET);
        String numDice = numDiceET.getText().toString();

        EditText accuracyET = (EditText) findViewById(R.id.accuracyET);
        String accuracy = accuracyET.getText().toString();

        EditText damageET = (EditText) findViewById(R.id.damageET);
        String damage = damageET.getText().toString();

        EditText critValueET = (EditText) findViewById(R.id.critValueET);
        String critValue = critValueET.getText().toString();

        if(numDice.length() == 0){
            numDice = "1";
        }
        if(accuracy.length() == 0) {
            accuracy = "1";
        }
        if(damage.length() == 0) {
            damage = "1";
        }
        if(critValue.length() == 0) {
            critValue = "0";
        }

        Intent intent = new Intent(this, DisplayMessageActivity.class);

        intent.putExtra("numDice",numDice );
        intent.putExtra("accuracy",accuracy );
        intent.putExtra("damage",damage );
        intent.putExtra("critValue",critValue );

        CheckBox christmasTreeCB = (CheckBox) findViewById(R.id.ChristmasTreeCB);
        intent.putExtra("christmasTree", christmasTreeCB.isChecked());

        startActivity(intent);
    }
}
