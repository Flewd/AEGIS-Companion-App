package com.zephyrworkshop.AegisProbabilityCalculator.TeamViewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.zephyrworkshop.AegisProbabilityCalculator.R;

import java.util.List;


public class TeamViewerMenu extends Activity {

    ListView teamLV;

    ArrayAdapter<String> adapter;
    List<ParseObject> teamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_viewer_menu);
        teamLV = (ListView) findViewById(R.id.teamViewerLV);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("SavedTeam");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    teamList = parseObjects;
                    populateListView();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        teamLV.setAdapter(adapter);
    }

    private void populateListView()
    {
        for(int i = 0; i < teamList.size() ;i++ ) {
            adapter.add(teamList.get(i).get("TeamName").toString());
        }

        teamLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switchToCardViewer(teamList.get(position));
            }
        });
    }

    public void switchToCardViewer(ParseObject robotObj)
    {
        Intent intent = new Intent(this, TeamViewer.class);

        intent.putExtra("TeamName",robotObj.get("TeamName").toString());
        intent.putExtra("unit0", robotObj.get("unit0").toString());
        intent.putExtra("unit1", robotObj.get("unit1").toString());
        intent.putExtra("unit2", robotObj.get("unit2").toString());
        intent.putExtra("unit3", robotObj.get("unit3").toString());
        intent.putExtra("unit4", robotObj.get("unit4").toString());

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_team_viewer_menu, menu);
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
