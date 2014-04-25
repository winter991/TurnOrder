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
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class FragmentAddPlayer extends Fragment  {

    OnPlayerAddedListener mCallback;

    public static String getName() {
        return m_name;
    }



    // Container Activity must implement this interface
    public interface OnPlayerAddedListener {
        public void AddPlayer (Player p);
    }
    // Container Activity must implement this interface
    public interface OnFormClear {
        public void btnClear_Click(View v);
    }
    View rootView;
    public FragmentAddPlayer(){}
    Button btnAdd;
    Button btnClear;
    //adding all of the edit text fields so i can dismiss them when they lose focus
    EditText etSpells;
    EditText etWounds;
    EditText etName;
    EditText etBennies;
    private static String   m_name = "AddFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_add_player, container, false);
        setupUI(rootView);
        // set

        btnAdd =(Button) rootView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                btnAdd_Click();
            }
        });
        btnClear =(Button) rootView.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                btnClear_Click();
            }
        });
        //stupid textfiled stuff

        return rootView;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnPlayerAddedListener) activity;

        } catch (Exception e) {


            throw new ClassCastException(activity.toString()
                    + " must implement OnPlayerAddedListener and the OnFormClear listener");
        }
    }

    private  void btnClear_Click()
    {
        resetFields();
    }
    public void btnAdd_Click() {
        //validation
        EditText t = (EditText) rootView.findViewById(R.id.txtPlayerName);
        String name = t.getText().toString() ;
        int bennies;
        int spells;
        int wounds;
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
        Player p = new Player(name);
        p.setM_bennies(bennies);
        p.setM_wounds(wounds);
        p.setM_spells(spells);
        p.setM_quick ( ((CheckBox) rootView.findViewById(R.id.ckQuick) ).isChecked());
        p.setM_lvlHeaded(((RadioButton) rootView.findViewById(R.id.rbLevelHeaded)).isChecked());
        p.setM_ImplvlHeaded(((RadioButton) rootView.findViewById(R.id.rbImpLvlHeaded)).isChecked());
        mCallback.AddPlayer(p);
        Toast toast= Toast.makeText( getActivity(),"com.TurnOrder.Player Added",Toast.LENGTH_SHORT );
        toast.show();
        resetFields();
    }
    private void resetFields()
    {
        ((EditText) rootView.findViewById(R.id.txtPlayerName)).setText("");
        ((EditText) rootView.findViewById(R.id.txtBennies)).setText("");
        ((EditText) rootView.findViewById(R.id.txtWounds)).setText("");
        ((EditText) rootView.findViewById(R.id.txtSpells)).setText("");
        ((EditText) rootView.findViewById(R.id.txtPlayerName)).setText("");
        ((CheckBox) rootView.findViewById(R.id.ckQuick) ).setChecked(false);
        ((RadioButton) rootView.findViewById(R.id.rbLevelHeaded)).setChecked(false);
        ((RadioButton) rootView.findViewById(R.id.rbImpLvlHeaded)).setChecked(false);
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
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }


}
