package com.zephyrworkshop.AegisProbabilityCalculator.TeamBuilder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.zephyrworkshop.AegisProbabilityCalculator.R;

/**
 * Created by mikeflood on 11/25/14.
 */

public class CheckboxListAdapter extends ArrayAdapter<String>{

    private final Context context;
    private final String[] values;
    private final boolean[] checkBoxValues;
    int amountSelected = 0;

    public CheckboxListAdapter(Context context, String[] _values, boolean[] _checkValues) {
        super(context, R.layout.check_box_list_item, _values);

        this.context = context;
        this.values = _values;
        this.checkBoxValues = _checkValues;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listItem = inflater.inflate(R.layout.check_box_list_item, parent, false);
        // TextView textView = (TextView) listItem.findViewById(R.id.label);

        CheckBox checkBox = (CheckBox) listItem.findViewById(R.id.addToTeamCB);
        checkBox.setText(values[position]);

        //  textView.setText(values[position]);
        // change the icon for Windows and iPhone
        //String s = values[position];

        return listItem;
    }

    //returns true if the value was able to be selected
    //returns false if the users limit of selected units is reached

    //first value is whether the limit has been reached
    //second value is whether the confirm button should display
    public boolean[] toggleCheckBox(int position)
    {
        if(checkBoxValues[position] == true)
        {
            checkBoxValues[position] = false;
            amountSelected--;

            boolean[] results = {true,false};
            return results;
        }
        else if(checkBoxValues[position] == false)
        {
            if(amountSelected < 5)
            {
                checkBoxValues[position] = true;
                amountSelected++;

                if (amountSelected == 5)
                {
                    boolean[] results = {true,true};
                    return results;
                }
                else{
                    boolean[] results = {true,false};
                    return results;
                }
            }
            else
            {
                boolean[] results = {false,true};
                return results;
            }
        }
        boolean[] results = {true,true};
        return results;
    }

    public void setCheckBoxValue(int position, boolean _value)
    {
        checkBoxValues[position] = _value;
    }
    public boolean[] GetAllCheckedValues()
    {
        return checkBoxValues;
    }
    public String[] GetAllNames()
    {
        return values;
    }


}




