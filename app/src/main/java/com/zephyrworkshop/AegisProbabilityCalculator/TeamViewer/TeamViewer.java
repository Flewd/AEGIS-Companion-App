package com.zephyrworkshop.AegisProbabilityCalculator.TeamViewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.zephyrworkshop.AegisProbabilityCalculator.CardViewer.CardViewer;
import com.zephyrworkshop.AegisProbabilityCalculator.R;
import com.zephyrworkshop.AegisProbabilityCalculator.TeamBuilder.CheckboxListAdapter;

import java.util.List;


public class TeamViewer extends Activity {

    int unitCount = 0;
    String teamName;

    ListView unitsLV;
    TeamViewerListAdapter adapter;

    Bundle extras;

    String[] names = new String[5];
    String[] types = new String[5];
    String[] energys = new String[5];
    String[] movements = new String[5];
    String[] integritys = new String[5];
    ParseFile[] unitPortraits = new ParseFile[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_viewer);

        extras = getIntent().getExtras();

        teamName = extras.getString("TeamName");
        setTeamNameText(teamName);


        unitsLV = (ListView) findViewById(R.id.unitsLV);

        unitsLV.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
               switchToCardViewer(position);

            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("RobotCard");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {

                    String name0 = extras.getString("unit0");
                    String name1 = extras.getString("unit1");
                    String name2 = extras.getString("unit2");
                    String name3 = extras.getString("unit3");
                    String name4 = extras.getString("unit4");

                    for(int i = 0; i < parseObjects.size();i++)
                    {
                        String currentName = parseObjects.get(i).get("name").toString();

                       if( currentName.equals(name0) || currentName.equals(name1) || currentName.equals(name2) || currentName.equals(name3) || currentName.equals(name4)) {
                           names[unitCount] = parseObjects.get(i).get("name").toString();
                           types[unitCount] = parseObjects.get(i).get("type").toString();
                           energys[unitCount] = parseObjects.get(i).get("energy").toString();
                           movements[unitCount] = parseObjects.get(i).get("movement").toString();
                           integritys[unitCount] = parseObjects.get(i).get("integrity").toString();
                           unitPortraits[unitCount] = parseObjects.get(i).getParseFile("portraitIMG");
                           unitCount++;
                       }
                    }

                    setupAdapter(names,types,energys,movements,integritys,unitPortraits);


                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

    }

    void switchToCardViewer(int _position)
    {
        Intent intent = new Intent(this, CardViewer.class);
        intent.putExtra("cardName", adapter.getNameFromPosition(_position) );
        intent.putExtra("type", adapter.getTypeFromPosition(_position));
        intent.putExtra("energy", adapter.getEnergyFromPosition(_position));
        intent.putExtra("movement",adapter.getMovementFromPosition(_position));
        intent.putExtra("integrity", adapter.getIntegrityFromPosition(_position));
        startActivity(intent);
    }

    void setupAdapter(String[] names, String[] types, String[] energys, String[] movements, String[] integritys, ParseFile[] unitPortraits)
    {
        adapter  = new TeamViewerListAdapter(this, names,types, energys,movements,integritys,unitPortraits);
        unitsLV.setAdapter(adapter);
    }

    void setTeamNameText(String name)
    {
        TextView teamName = (TextView) findViewById(R.id.TeamNameTV);
        teamName.setText(name);
    }

    void setText(ParseObject unit)
    {
        TextView unitTV = (TextView) findViewById(R.id.Unit0TV);

        switch (unitCount)
        {
            case 0:  unitTV = (TextView) findViewById(R.id.Unit0TV); break;
            case 1:  unitTV = (TextView) findViewById(R.id.Unit1TV); break;
            case 2:  unitTV = (TextView) findViewById(R.id.Unit2TV); break;
            case 3:  unitTV = (TextView) findViewById(R.id.Unit3TV); break;
            case 4:  unitTV = (TextView) findViewById(R.id.Unit4TV); break;
        }

        unitTV.setText(unit.get("name").toString() + " " + unit.get("type").toString() + " " + unit.get("energy").toString() + " " + unit.get("movement").toString() + " " + unit.get("integrity").toString());
        unitCount++;
    }
    public void confirmDeleteTeam(View view)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SavedTeam");
        query.whereEqualTo("TeamName", teamName);
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    ParseObject.unpinAllInBackground(parseObjects);
                    Toast.makeText(getApplicationContext(), "" + parseObjects.get(0).get("TeamName") + " deleted", Toast.LENGTH_LONG).show();
                    switchBackToTeamViewer();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    void switchBackToTeamViewer()
    {
        Intent intent = new Intent(this, TeamViewerMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void cancelDelete(View view)
    {
        Button deleteBtn = (Button) findViewById(R.id.deleteTeamBtn);
        Button confirmBtn = (Button) findViewById(R.id.deleteConfirmBtn);
        Button cancelBtn = (Button) findViewById(R.id.cancelDeleteBtn);

        deleteBtn.setEnabled(true);
        deleteBtn.setVisibility(View.VISIBLE);

        confirmBtn.setEnabled(false);
        confirmBtn.setVisibility(View.INVISIBLE);

        cancelBtn.setEnabled(false);
        cancelBtn.setVisibility(View.INVISIBLE);
    }

    public void deleteTeam(View view)
    {
        Button deleteBtn = (Button) findViewById(R.id.deleteTeamBtn);
        Button confirmBtn = (Button) findViewById(R.id.deleteConfirmBtn);
        Button cancelBtn = (Button) findViewById(R.id.cancelDeleteBtn);

        cancelBtn.setEnabled(true);
        cancelBtn.setVisibility(View.VISIBLE);

        confirmBtn.setEnabled(true);
        confirmBtn.setVisibility(View.VISIBLE);

        deleteBtn.setEnabled(false);
        deleteBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_team_viewer, menu);
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
