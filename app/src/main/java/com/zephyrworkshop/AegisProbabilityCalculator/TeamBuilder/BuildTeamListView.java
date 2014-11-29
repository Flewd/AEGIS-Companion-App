package com.zephyrworkshop.AegisProbabilityCalculator.TeamBuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.CheckBox;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.zephyrworkshop.AegisProbabilityCalculator.R;
import com.zephyrworkshop.AegisProbabilityCalculator.activity_main_menu;

public class BuildTeamListView extends Activity {

    ListView unitsLV;

    CheckboxListAdapter adapter;
    // ArrayAdapter<CheckBoxItem> adapter;
    List<ParseObject> cardList;
    String[] nameList;
    boolean[] checkedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_team_list_view);

        unitsLV = (ListView) findViewById(R.id.unitsLV);

        unitsLV.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                boolean[] toggleResult = adapter.toggleCheckBox(position);

                Button saveButton = (Button) findViewById(R.id.saveTeamBtn);
                saveButton.setEnabled(toggleResult[1]);

                if(toggleResult[0] == true)
                {
                    CheckBox checkBox =  (CheckBox) arg1.findViewById(R.id.addToTeamCB);
                    checkBox.setChecked(!checkBox.isChecked());
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "You may only add 5 units to a team" , Toast.LENGTH_LONG).show();
                }
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("RobotCard");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    cardList = parseObjects;
                    nameList = new String[parseObjects.size()];
                    checkedList = new boolean[parseObjects.size()];

                    for (int i = 0; i < nameList.length; i++) {
                        nameList[i] = parseObjects.get(i).get("name").toString();
                        checkedList[i] = false;
                    }

                    setupAdapter();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }

            }
        });
    }

    void setupAdapter()
    {
        adapter  = new CheckboxListAdapter(this, nameList,checkedList);
        unitsLV.setAdapter(adapter);
    }

    String teamName;
    ParseObject SavedTeam;
    public void saveTeam(View view)
    {
        EditText teamNameET = (EditText) findViewById(R.id.teamNameET);
        teamName = teamNameET.getText().toString();

        SavedTeam = new ParseObject("SavedTeam");

        if(teamName.equals("") || teamName == null)
        {
            teamName = "AEGIS Team";
        }

        //check if this team name already exists
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SavedTeam");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {

                    checkForUniqueName(parseObjects);
                    finalizeSave();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }

            }
        });
    }

    void checkForUniqueName(List<ParseObject> objects)
    {
        boolean uniqueName = false;
        int uniqueNum = 1;

        while(uniqueName == false) {
            uniqueName = true;

            for (int i = 0; i < objects.size(); i++)
            {
                if(objects.get(i).get("TeamName").toString().equals(teamName))
                {
                    uniqueName = false;
                }
            }

            if(uniqueName == false)
            {
                if(uniqueNum == 1) {
                    teamName = teamName + " " + Integer.toString(uniqueNum);
                }
                else{
                    teamName = teamName.substring(0,teamName.length() - 2) + " " + Integer.toString(uniqueNum);
                }

                uniqueNum++;
            }
        }
        SavedTeam.put("TeamName", teamName);
    }

    void finalizeSave()
    {
        boolean[] AllCheckboxValues = adapter.GetAllCheckedValues();
        String[] AllNames = adapter.GetAllNames();
        int unitNum = 0;

        for(int i = 0; i < AllCheckboxValues.length;i++)
        {
            if (AllCheckboxValues[i] == true)
            {
                SavedTeam.put("unit" + Integer.toString(unitNum), AllNames[i] );
                unitNum++;
            }
        }

        SavedTeam.pinInBackground();
        Toast.makeText(getApplicationContext(), "" + teamName + " saved", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this,  activity_main_menu.class);
        startActivity(intent);
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
