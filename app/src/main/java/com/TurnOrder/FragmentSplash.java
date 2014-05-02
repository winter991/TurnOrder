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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Jason on 5/1/2014.
 */
public class FragmentSplash extends Fragment {

    OnContinue mCallback;
    Button btnContinue;
    // Container Activity must implement this interface
    public interface OnContinue {
        public void goHome ();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.splash, container, false);
        setHasOptionsMenu(true);
        btnContinue  =(Button) rootView.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                btnContinue_Click();
            }
        });
        return rootView;
    }

    private void btnContinue_Click() {
        mCallback.goHome();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnContinue) activity;

        } catch (Exception e) {


            throw new ClassCastException(activity.toString()
                    + " must implement OnPlayerAddedListener and the OnFormClear listener");
        }
    }
}
