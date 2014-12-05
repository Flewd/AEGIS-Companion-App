package com.zephyrworkshop.AegisProbabilityCalculator;

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
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseException;
import com.parse.DeleteCallback;
import com.parse.ParseQuery;
import com.zephyrworkshop.AegisProbabilityCalculator.AbilityIndex.AbilityIndexMenu;
import com.zephyrworkshop.AegisProbabilityCalculator.CardViewer.CardViewerMenu;
import com.zephyrworkshop.AegisProbabilityCalculator.DiceCalculator.MainActivity;
import com.zephyrworkshop.AegisProbabilityCalculator.TeamBuilder.BuildTeamListView;
import com.zephyrworkshop.AegisProbabilityCalculator.TeamViewer.TeamViewerMenu;
import android.widget.Toast;

import java.util.List;


public class activity_main_menu extends Activity {

    static boolean ParseEnabled = false;

    ListView navigationLV;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        if(ParseEnabled == false) {
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "vxpq5jb5p161R9HxCnY83mzsayotrfCveb1lajui", "78OLnoxpqucG6zFya4brfYGWzqmhxUPEP5ziaF5P");
            //         pullToLocalDataStore("RobotCard", false);
            //         pullToLocalDataStore("AbilityIndex", false);
            ParseEnabled = true;
        }

 //       ParseObject.unpinAllInBackground();   //use to clear local data storage

            navigationLV = (ListView) findViewById(R.id.navigationLV);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

            adapter.add("Dice Calculator");
            adapter.add("Card Viewer");
            adapter.add("Ability Index");
            adapter.add("Build Team");
            adapter.add("Team Viewer");
            adapter.add("Sync local data with server");
      //      adapter.add("Erase local data");

            navigationLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position)
                {
                    case 0: goToCalculator(); break;
                    case 1: goToCardViewerMenu(); break;
                    case 2: goToAbilityIndex(); break;
                    case 3: goToBuildTeamListView(); break;
                    case 4: goToTeamViewer(); break;
                    case 5: refreshLocalData(); break;
                    case 6: clearLocalData(); break;
                }

            }
        });
            navigationLV.setAdapter(adapter);

    }

    public void pullToLocalDataStore(final String ROBOT_CARD_LABEL, final boolean finalPull) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(ROBOT_CARD_LABEL);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> robotList, ParseException e) {
                if (e != null) {
                    // There was an error or the network wasn't available.
                    return;
                }

                else {
                    // Release any objects previously pinned for this query.
                    ParseObject.unpinAllInBackground(ROBOT_CARD_LABEL, robotList, new DeleteCallback() {
                        public void done(ParseException e) {
                            if (e != null) {
                                // There was some error.
                                return;
                            } else {
                                // Add the latest results for this query to the cache.
                                ParseObject.pinAllInBackground(ROBOT_CARD_LABEL, robotList);

                                if (finalPull == true) {
                                    Toast.makeText(getApplicationContext(), "" + "Local data is up to date", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main_menu, menu);
        return true;
    }

    public void goToCalculator() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void goToCardViewerMenu() {
        Intent intent = new Intent(this, CardViewerMenu.class);
        startActivity(intent);
    }
    public void goToBuildTeamListView() {
        Intent intent = new Intent(this, BuildTeamListView.class);
        startActivity(intent);
    }
    public void goToTeamViewer() {
        Intent intent = new Intent(this, TeamViewerMenu.class);
        startActivity(intent);
    }
    public void goToAbilityIndex() {
        Intent intent = new Intent(this, AbilityIndexMenu.class);
        startActivity(intent);
    }
    public void refreshLocalData() {
        pullToLocalDataStore("RobotCard", false);
        pullToLocalDataStore("AbilityIndex", true);
    }
    public void clearLocalData()
    {
        ParseObject.unpinAllInBackground();
        Toast.makeText(getApplicationContext(), "" + "Local data erased. sync with server", Toast.LENGTH_LONG).show();
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
