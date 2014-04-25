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
import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


/**
 * Created by Jason on 1/25/14.
 */
public class FragmentPlay extends Fragment {
    private ArrayList<Player> m_playerList;
    private ArrayList<Integer> deck;
    private  boolean reshuffle=true;
    private Button btnDeal;
    private Button btnShuffle;
    private ListView lv;

    View rootView;
    OnPlayerClicked mCallback;

    public FragmentPlay(ArrayList<Player> p)
    {
        this.m_playerList=p;
        this.deck=new ArrayList<Integer>() ;
    }
    public interface OnPlayerClicked {
        public void clickPlayer (Player p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        Bundle args = ((MainActivity) getActivity()).getSavedMainFragmentState();
        rootView = inflater.inflate(R.layout.fragment_play, container, false);
        btnDeal =(Button) rootView.findViewById(R.id.BtnDeal);
        btnDeal.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                btnDeal_Click();
            }
        });
        btnShuffle =(Button) rootView.findViewById(R.id.btnShuffle);
        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                btnShuffle_Click();
            }
        });
        lv= (ListView) rootView.findViewById(R.id.Turn);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Player p = (Player) adapterView.getItemAtPosition(i);

                mCallback.clickPlayer(p);
            }
        });

        registerForContextMenu(lv);
        restoreData(args);
        return rootView;
    }
    private void restoreData(   Bundle savedInstanceState)
    {
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            deck = savedInstanceState.getIntegerArrayList("Deck");
            if (deck == null ||deck.size()<0)
            {
                shuffle();
            }

        }
        if( deck != null && deck.size()>0)
        {
            //restore the deck
            displayTurn();
        }

    }
    /** This will be invoked when an item in the listview is long pressed */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();

        inflater.inflate(R.menu.actions , menu);
    }
    /** This will be invoked when a menu item is selected */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Player p = (Player) lv.getItemAtPosition(info.position);
        if(p == null)
        {            // break out
            return true;
        }
        switch(item.getItemId()){

            case R.id.cnt_mnu_addBennies:
                p.setM_bennies(p.getM_bennies()+1);
                Toast.makeText(getActivity().getApplicationContext(), p.getM_name()+" has gained a bennie:", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cnt_mnu_removedBennie:
                if(p.getM_bennies()==0)
                {
                    Toast.makeText(getActivity().getApplicationContext(), p.getM_name() + " is out of bennies", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    p.setM_bennies(p.getM_bennies() - 1);
                    Toast.makeText(getActivity().getApplicationContext(), p.getM_name() + " has used a bennie:", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cnt_mnu_addWounds:
                if(p.getM_wounds()==3)
                {
                    Toast.makeText(getActivity().getApplicationContext(), p.getM_name()+" is incapacitated and cannot take more wounds", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    p.setM_wounds(p.getM_wounds() + 1);
                    Toast.makeText(getActivity().getApplicationContext(), p.getM_name() + " has taken a wound:", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cnt_mnu_removedWounds:
                if(p.getM_wounds()==0)
                {
                    Toast.makeText(getActivity().getApplicationContext(), p.getM_name()+" is fully healed and cannot be healed more", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    p.setM_wounds(p.getM_wounds() - 1);
                    Toast.makeText(getActivity().getApplicationContext(), p.getM_name()+" has healed a wound.", Toast.LENGTH_SHORT).show();
                    Toast.makeText( getActivity().getApplicationContext(), "Wound Healed"  , Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.cnt_mnu_addSpell:
                p.setM_spells(p.getM_spells() + 1);
                Toast.makeText( getActivity().getApplicationContext(), "Spell Point Gained "   , Toast.LENGTH_SHORT).show();
                break;
            case R.id.cnt_mnu_removedSpells:
                if(p.getM_spells()==0)
                {
                    Toast.makeText(getActivity().getApplicationContext(), p.getM_name()+" is out of spell points", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    p.setM_spells(p.getM_spells()-1);
                    Toast.makeText(getActivity().getApplicationContext(), p.getM_name()+" has used a spell point ", Toast.LENGTH_SHORT).show();
                }

                break;

        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Bundle outState = new Bundle();
        outState.putIntegerArrayList("Deck", deck);
        this.setRetainInstance(true);
        ((MainActivity) getActivity()).saveMainFragmentState(outState);
    }

    private void btnShuffle_Click()
    {
        shuffle();
        TextView t = (TextView)rootView.findViewById(R.id.CardsInDeck);
        t.setText("Cards In Deck:"+deck.size());
    }


    private void btnDeal_Click()
    {
        if(reshuffle)
        {
            shuffle();
        }
        deal();
        displayTurn();
    }
    private void displayTurn()
    {
        // make the current player
        TextView t = (TextView)rootView.findViewById(R.id.CardsInDeck);

        AdapterPlayer p = new AdapterPlayer(getActivity(), 0,m_playerList);
        p.sort( new Comparator<Player>(){
            @Override
            public int compare(Player i, Player j){
                Integer x=i.getM_card();
                Integer y= j.getM_card();
                return x.compareTo(y);
            }
        });
        t.setText("Cards In Deck:"+deck.size());


        lv.setAdapter(p );
    }

    // Since we don't reshuffle after every hand we need to reset the deck.
    private void shuffle()
    {
        //remove everything from the deck so we can add all of the cards back
        deck.clear();
        Random random = new Random();
        for (Integer i = 1; i < 55; ++i)
        {
            deck.add(i);
        }

        for (int i = deck.size() - 1; i > 0; i--)
        {

            int r= random.nextInt(i + 1);
            int x = deck.get(i);
            deck.set(i, deck.get(r));
            deck.set(r, x);

        }
        reshuffle=!reshuffle;
    }

    private void deal()
    {
        for (Player p : m_playerList)
        {
            if(deck.size()==0)
            {
                shuffle();
            }
            if (p.isM_lvlHeaded() || p.isM_ImplvlHeaded())
            {
                if (p.isM_lvlHeaded())
                {
                    //deal 2 cards
                    p.setM_card( deck.get(0) > deck.get(1) ? deck.get(0) : deck.get(1));
                    deck.remove(0);
                    deck.remove(0);
                }
                if (p.isM_ImplvlHeaded())
                {
                    //deal 3 cards
                    ArrayList<Integer> dealt = new ArrayList<Integer>();
                    dealt.add(deck.get(0));
                    dealt.add(deck.get(1));
                    dealt.add(deck.get(2));
                    deck.remove(0);
                    deck.remove(1);
                    deck.remove(2);
                    Collections.sort(dealt);
                    p.setM_card(dealt.get(0));
                }

            }
            else
            {
                p.setM_card(deck.get(0));
                deck.remove(0);
            }

            if(p.isM_quick())
            {
                if(p.getM_card()>=39)//player gets a new card
                {
                    if(deck.size() > 0)
                    {
                        p.setM_card(deck.get(0));
                        deck.remove(0);
                    }
                    else
                    {
                        shuffle();
                        p.setM_card(deck.get(0));
                        deck.remove(0);
                    }
                }
            }
            if (p.getM_card() == 1 || p.getM_card() == 2)
            {

                reshuffle = true;// reshuffle on jokers
                //  btnUp.Visible = true;
                //   btnDown.Visible = true;
            }
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnPlayerClicked) activity;

        } catch (Exception e) {


            throw new ClassCastException(activity.toString()
                    + " must implement OnPlayerClicked and the OnFormClear listener");
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList("Deck", deck);
        ((MainActivity) getActivity()).saveMainFragmentState(outState);

    }

}
