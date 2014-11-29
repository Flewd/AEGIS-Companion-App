package com.zephyrworkshop.AegisProbabilityCalculator.TeamViewer;

import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.app.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zephyrworkshop.AegisProbabilityCalculator.CardViewer.CardViewer;
import com.zephyrworkshop.AegisProbabilityCalculator.R;
import android.widget.TextView;

/**
 * Created by mikeflood on 11/26/14.
 */
public class TeamViewerListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] names;
    private final String[] types;
    private final String[] energys;
    private final String[] movements;
    private final String[] integritys;

    public TeamViewerListAdapter(Context context, String[] _name, String[] _type,String[] _energy , String[] _movement, String[] _integrity) {
        super(context, R.layout.team_viewer_list_item, _name);

        this.context = context;
        this.names = _name;
        this.types = _type;
        this.energys = _energy;
        this.movements = _movement;
        this.integritys = _integrity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listItem = inflater.inflate(R.layout.team_viewer_list_item, parent, false);

         TextView textView = (TextView) listItem.findViewById(R.id.nameTV);
        textView.setText(names[position] + " " + types[position] + " " + energys[position] + " " + movements[position] + " " + integritys[position]);

        //CheckBox checkBox = (CheckBox) listItem.findViewById(R.id.addToTeamCB);
        //checkBox.setText(values[position]);

        //  textView.setText(values[position]);
        // change the icon for Windows and iPhone
        //String s = values[position];

        return listItem;
    }

    public String getNameFromPosition(int _position){return names[_position];}
    public String getTypeFromPosition(int _position){return types[_position];}
    public String getEnergyFromPosition(int _position){return energys[_position]; }
    public String getMovementFromPosition(int _position){return movements[_position];}
    public String getIntegrityFromPosition(int _position){return integritys[_position];}
}
