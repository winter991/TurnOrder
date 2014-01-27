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
 * Created by Jason on 1/25/14.
 */
public class AdapterPlayer extends ArrayAdapter<Player> {
    private Activity activity;
    private ArrayList<Player> players;
    private static LayoutInflater inflater = null;
    private  int layoutID;

    public AdapterPlayer(Activity context,  int textViewResourceId, List<Player> objects) {
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
        String playercard = new StringBuilder().append(players.get(position).getM_name()).append("-").append(getCard(players.get(position).getM_card())).toString();
        holder.label.setText(playercard);
        return v;
    }
    public static class Display
    {
        public TextView label;
    }
   // There's probably  a better way to do this.
    public String getCard(int i)
    {
        String strCard = "";
        switch (i)
        {
            case 1:
            case 2:
                strCard = "Joker";
                break;
            case 3: strCard = " Ace of Spades"; break;
            case 4: strCard = " Ace of Hearts"; break;
            case 5: strCard = " Ace of Diamonds"; break;
            case 6: strCard = " Ace of Clubs"; break;
            case 7: strCard = " King of Spades"; break;
            case 8: strCard = " King of Hearts"; break;
            case 9: strCard = " King of Diamonds"; break;
            case 10: strCard = " King of Clubs"; break;
            case 11: strCard = " Queen of Spades"; break;
            case 12: strCard = " Queen of Hearts"; break;
            case 13: strCard = " Queen of Diamonds"; break;
            case 14: strCard = " Queen of Clubs"; break;
            case 15: strCard = " Jack of Spades"; break;
            case 16: strCard = " Jack of Hearts"; break;
            case 17: strCard = " Jack of Diamonds"; break;
            case 18: strCard = " Jack of Clubs"; break;
            case 19: strCard = " Ten of Spades"; break;
            case 20: strCard = " Ten of Hearts"; break;
            case 21: strCard = " Ten of Diamonds"; break;
            case 22: strCard = " Ten of Clubs"; break;
            case 23: strCard = " Nine of Spades"; break;
            case 24: strCard = " Nine of Hearts"; break;
            case 25: strCard = "Nine of Diamonds"; break;
            case 26: strCard = "Nine of Clubs"; break;
            case 27: strCard = "Eight of Spades"; break;
            case 28: strCard = "Eight of Hearts"; break;
            case 29: strCard = "Eight of Diamonds"; break;
            case 30: strCard = "Eight of Clubs"; break;
            case 31: strCard = "Seven of Spades"; break;
            case 32: strCard = "Seven of Hearts"; break;
            case 33: strCard = "Seven of Diamonds"; break;
            case 34: strCard = "Seven of Clubs"; break;
            case 35: strCard = "Six of Spades"; break;
            case 36: strCard = "Six of Hearts"; break;
            case 37: strCard = "Six of Diamonds"; break;
            case 38: strCard = "Six of Clubs"; break;
            case 39: strCard = "Five of Spades"; break;
            case 40: strCard = "Five of Hearts"; break;
            case 41: strCard = "Five of Diamonds"; break;
            case 42: strCard = "Five of Clubs"; break;
            case 43: strCard = "Four of Spades"; break;
            case 44: strCard = "Four of Hearts"; break;
            case 45: strCard = "Four of Diamonds"; break;
            case 46: strCard = "Four of Clubs"; break;
            case 47: strCard = "Three of Spades"; break;
            case 48: strCard = "Three of Hearts"; break;
            case 49: strCard = "Three of Diamonds"; break;
            case 50: strCard = "Three of Clubs"; break;
            case 51: strCard = "Two of Spades"; break;
            case 52: strCard = "Two of Hearts"; break;
            case 53: strCard = "Two of Diamonds"; break;
            case 54: strCard = "Two of Clubs"; break;
            default: strCard = "";// if the player was added without a card just leave it blank. They should show up at the end of the list.
                // this can happen if they deal then add a new player.

        }
        return strCard;
    }
}

