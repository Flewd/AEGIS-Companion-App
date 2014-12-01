package com.zephyrworkshop.AegisProbabilityCalculator.CardViewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.zephyrworkshop.AegisProbabilityCalculator.R;


public class CardViewerMenu extends Activity {

    ListView cardsLV;

    ArrayAdapter<String> adapter;
    List<ParseObject> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_viewer_menu);

        sortByName();
    }

    private void populateListView()
    {
        for(int i = 0; i < cardList.size() ;i++ ) {
            adapter.add(cardList.get(i).get("name").toString());
        }


        cardsLV.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switchToCardViewer(cardList.get(position));
            }
        });


    }

    public void switchToCardViewer(ParseObject robotObj)
    {
        Intent intent = new Intent(this, CardViewer.class);
        intent.putExtra("cardName", robotObj.get("name").toString() );
        intent.putExtra("type", robotObj.get("type").toString());
        intent.putExtra("energy", robotObj.get("energy").toString());
        intent.putExtra("movement", robotObj.get("movement").toString());
        intent.putExtra("integrity", robotObj.get("integrity").toString());
        startActivity(intent);
    }

    public void sortByName()
    {
        cardsLV = (ListView) findViewById(R.id.cardsLV);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("RobotCard");
        query.fromLocalDatastore();
        query.addAscendingOrder("name");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    cardList = parseObjects;
                    populateListView();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }

            }
        });

        cardsLV.setAdapter(adapter);

    }

    public void sortByName(View view)
    {
        cardsLV = (ListView) findViewById(R.id.cardsLV);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("RobotCard");
        query.fromLocalDatastore();
        query.addAscendingOrder("name");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    cardList = parseObjects;
                    populateListView();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }

            }
        });

        cardsLV.setAdapter(adapter);

    }
    public void sortByType(View view)
    {
        cardsLV = (ListView) findViewById(R.id.cardsLV);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("RobotCard");
        query.fromLocalDatastore();
        query.addAscendingOrder("type");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    cardList = parseObjects;
                    populateListView();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }

            }
        });

        cardsLV.setAdapter(adapter);

    }
    public void sortByEnergy(View view)
    {
        cardsLV = (ListView) findViewById(R.id.cardsLV);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("RobotCard");
        query.fromLocalDatastore();
        query.addAscendingOrder("energy");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    cardList = parseObjects;
                    populateListView();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }

            }
        });

        cardsLV.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_viewer_menu, menu);
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
