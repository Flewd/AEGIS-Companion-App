package com.zephyrworkshop.AegisProbabilityCalculator.AbilityIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.zephyrworkshop.AegisProbabilityCalculator.R;

public class AbilityIndexMenu extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability_index_menu);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.AbilityELV);



        ParseQuery<ParseObject> query = ParseQuery.getQuery("AbilityIndex");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {

                    // preparing list data
                    prepareListData(parseObjects);

                    // setting list adapter
                    expListView.setAdapter(listAdapter);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void prepareListData(List<ParseObject> abilityList) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        for(int i = 0; i < abilityList.size(); i++) {
            String abbreviation = abilityList.get(i).get("abbreviation").toString();
            if(abbreviation == null || abbreviation.equals("")) {
                listDataHeader.add(abilityList.get(i).get("name").toString());
            }else{
                listDataHeader.add(abilityList.get(i).get("name").toString() + "  (" + abbreviation + ")");
            }

            List<String> temp = new ArrayList<String>();
            temp.add(abilityList.get(i).get("description").toString());
            listDataChild.put(listDataHeader.get(i), temp);
        }

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ability_index_menu, menu);
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
