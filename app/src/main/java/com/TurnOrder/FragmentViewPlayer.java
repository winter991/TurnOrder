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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Jason on 1/25/14.
 */
public class FragmentViewPlayer extends Fragment {

    public  FragmentViewPlayer(Player p,ArrayList<Player> playerArrayList)
    {
        this.m_Player =p;
        this.m_PlayerList = playerArrayList;
    }

    private Spinner spinner;
    private Player m_Player;
    private ArrayList<Player> m_PlayerList;
    View rootView;

    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_view_player, container, false);
        spinner  =  (Spinner) rootView.findViewById(R.id.spinner);
        btnSave  =(Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                btnSave_Click();
            }
        });


        AdapterSpinner adapterPlayer= new AdapterSpinner(getActivity(), 0,m_PlayerList);
        adapterPlayer.setDropDownViewResource(R.id.current_player);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Player p = (Player) adapterView.getItemAtPosition(i);
                if (p != null) {
                    LoadPlayer(p);
                    spinner.setSelection(i);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setAdapter(adapterPlayer );


        if(m_Player != null)
        {
            AdapterSpinner adapter =( AdapterSpinner) spinner.getAdapter();
            int pos = adapter.getPosition(m_Player);
            if (pos >=0)
            {
                spinner.setSelection(pos,true    );
            }
        }
        LoadPlayer(m_Player);


        return rootView;
    }

    private void btnSave_Click() {
        int bennies;
        int spells;
        int wounds;

        Player p = (Player) spinner.getSelectedItem();
        if (p != null)
        {
            String s = ((EditText) rootView.findViewById(R.id.txtBennies)).getText().toString();
            if(tryParseInt( s))
            {
                bennies= Integer.parseInt(((EditText) rootView.findViewById(R.id.txtBennies)).getText().toString());
            }
            else
            {
                //default to 3 bennies
                bennies=3;
            }

            if(tryParseInt( ((EditText) rootView.findViewById(R.id.txtWounds)).getText().toString()))
            {
                wounds=  Integer.parseInt(((EditText) rootView.findViewById(R.id.txtWounds)).getText().toString());
            }
            else
            {
                //default to 0 wounds
                wounds=0;
            }

            if(tryParseInt(((EditText) rootView.findViewById(R.id.txtSpells)).getText().toString()))
            {
                spells=Integer.parseInt(((EditText) rootView.findViewById(R.id.txtSpells)).getText().toString());
            }
            else
            {
                //default to 15 spell casts
                spells=0;
            }

            p.setM_bennies(bennies);
            p.setM_wounds(wounds);
            p.setM_spells(spells);
            Toast toast= Toast.makeText( getActivity(),"Player Updated",Toast.LENGTH_SHORT );
            toast.show();
        }
    }

    private void LoadPlayer(Player p)
    {

        if(p != null)
        {
            StringBuilder builder=new StringBuilder();
            boolean m_quick,m_lvlheaded,m_implvlHeaded;
            m_quick = p.isM_quick();
            m_lvlheaded = p.isM_lvlHeaded();
            m_implvlHeaded = p.isM_ImplvlHeaded();
            if(m_quick)
                builder.append("Quick \n ");
            if(m_lvlheaded)
                builder.append("Level Headed\n" );
            else if(m_implvlHeaded)
                builder.append("Improved Level Headed \n");
            if(p.getM_card() != 0 && p.getM_card()==1 || p.getM_card() ==2)
                builder.append("Joker");
            if(builder != null && builder.length()>0)
            {
                ((TextView) rootView.findViewById(R.id.txtInit)).setText(builder.toString());
            }
            else
            {
                ((TextView) rootView.findViewById(R.id.txtInit)).setText("No Initiative modifiers");
            }
            Integer spells,bennies,wounds;

            EditText t =((EditText) rootView.findViewById(R.id.txtSpells));
            spells=p.getM_spells();
            t.setText(spells.toString());
            EditText f =((EditText)rootView.findViewById(R.id.txtWounds));
            wounds = p.getM_wounds();
            f.setText(wounds.toString() );

            EditText  z= ((EditText)rootView.findViewById(R.id.txtBennies));
            bennies =p.getM_bennies();
            z.setText(bennies.toString() );

        }
    }
    private boolean tryParseInt( String s)
    {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }

    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void setupUI(View view) {

        if(view == null)
        {
            return;
        }

        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard(getActivity());
                    return false;
                }

            });
        }

        //Todo find a better way
        if (view instanceof ViewGroup) {
            int i=0;
            while(i<((ViewGroup) view).getChildCount())
            {
                View v = ((ViewGroup) view).getChildAt(i);
                setupUI(v);
                i++;
            }
        }
    }




}


