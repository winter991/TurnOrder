
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
import android.app.FragmentManager;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import android.view.Menu;


import java.util.ArrayList;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        FragmentAddPlayer.OnPlayerAddedListener,FragmentPlay.OnPlayerClicked, FragmentSplash.OnContinue{



    //Members
    private Bundle mMainFragmentArgs;
    private  FragmentManager fragmentManager;
    ArrayList<Player> playerList;
    private Menu titlemenue;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;


    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            playerList = savedInstanceState.getParcelableArrayList("PlayerList");
        }

        fragmentManager = getFragmentManager();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        playerList=new ArrayList<Player>();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        changeNavItem(position,null);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.HomeSection);
                break;
            case 2:
                mTitle = getString(R.string.AddPlayerSection);
                break;
            case 3:
                mTitle = getString(R.string.ViewPlayerSection);
            case 4:
                mTitle = getString(R.string.PlaySection);
            case 5:
                mTitle = getString(R.string.Notice);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.main, menu);
            titlemenue = menu;
           // if(mNavigationDrawerFragment !=null)

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    public void AddPlayer(Player p) {
        Integer i=0;
        playerList.add(p);
    }

    @Override
    public void clickPlayer(Player p) {
        changeNavItem(2, p); // FragmentView Player's index -1
    }
    public void saveMainFragmentState(Bundle args) {
        mMainFragmentArgs = args;
    }

    public Bundle getSavedMainFragmentState() {
        return mMainFragmentArgs;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("PlayerList", playerList);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        playerList=savedInstanceState.getParcelableArrayList("PlayerList");
    }

    private void changeNavItem(int position,Player p)
    {
        Fragment fragment = null;
        switch(position+1)
        {
            case 1:
                fragment=new FragmentHome();
                setTitleMenu(titlemenue, "Home");

                break;
            case 2:
                fragment=new FragmentAddPlayer();
                setTitleMenu(titlemenue, "Add Player");
                break;
            case 3:
                fragment=new FragmentViewPlayer(p,playerList);
                setTitleMenu(titlemenue, "View Player");
                break;
            case 4:
                fragment=new FragmentPlay(playerList);
                setTitleMenu(titlemenue, "Play");
                break;
            case 5:
                fragment=new FragmentSplash();
                setTitleMenu(titlemenue, "Savage Worlds Licence");
        }

        FragmentTransaction fragmentTransaction =   fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment); 
        fragmentTransaction.addToBackStack( null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void setTitleMenu(Menu m, String title)
    {
        mTitle =title;
    }


    @Override
    public void goHome() {
        changeNavItem(0,null);
    }
}
