/*
 * Copyright (c) 2014. Jason Esposito
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.TurnOrder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 1/19/14.
 */
public class AdapterSpinner extends ArrayAdapter<Player> {
    private Activity activity;
    private ArrayList<Player> players;
    private static LayoutInflater inflater = null;
    private  int layoutID;

    public AdapterSpinner(Activity context,  int textViewResourceId, List<Player> objects) {
        super(context, textViewResourceId, objects);
        try
        {
            this.activity=context;
            this.players= (ArrayList<Player>) objects;
            this.layoutID=textViewResourceId;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        catch  (Exception e)
        {
            //TODO: figure out what to do if this fails.
        }
    }

    public int getCount()
    {
        return  players.size();
    }
    public Player getItem(Player p)
    {
        return p;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v=convertView;
        final Display holder;
        if(convertView == null)
        {
            v = inflater.inflate(R.layout.turnrow,null);
            holder = new Display();
            holder.label = (TextView) v.findViewById(R.id.label);
            v.setTag(holder);
        }
        else
        {
            holder = (Display) v.getTag();
        }
        holder.label.setText(players.get(position).getM_name());
        return v;
    }
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View v=convertView;
        final Display holder;
        if(convertView == null)
        {
            v = inflater.inflate(R.layout.turnrow,null);
            holder = new Display();
            holder.label = (TextView) v.findViewById(R.id.label);
            v.setTag(holder);
        }
        else
        {
            holder = (Display) v.getTag();
        }
        holder.label.setText(players.get(position).getM_name());
        return v;
    }


    public static class Display
    {
        public TextView label;
    }
}
