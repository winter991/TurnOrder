package com.TurnOrder;/*
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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jason on 1/25/14.
 */public class Player implements Parcelable
{

    public Player(String n)
    {
        m_name=n;

    }
    // Members

    private String m_name;
    int m_wounds=0;
    private boolean m_quick=false;
    private boolean m_lvlHeaded=false;
    private boolean m_ImplvlHeaded=false;
    private int m_bennies=3;// default to 3 bennies
    private int m_spells=15;// default to 15 spell casts
    private int m_card ;// card repersented as a number 1-54

    //parcel stuff
    public Player( Parcel in)
    {
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in)
    {
        String[] data = new String[8];
        m_name = in.readString();
        m_wounds= in.readInt();
        m_bennies = in.readInt();
        m_spells = in.readInt();
        m_quick = in.readInt() ==1 ? true: false; //the value in the parcel is stored as an int
        m_lvlHeaded = in.readInt() == 1 ? true: false; //the value in the parcel is stored as an int
        m_ImplvlHeaded = in.readInt() == 1 ? true: false; //the value in the parcel is stored as an int
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(m_name);
        out.writeInt(m_wounds);
        out.writeInt(m_bennies);
        out.writeInt(m_spells);
        out.writeInt(m_quick == true ? 1: 0);//convert the value to an int to be stored. Why doesn't Parcel have a read/write boolean?
        out.writeInt(m_lvlHeaded == true ? 1: 0);
        out.writeInt(m_ImplvlHeaded == true ? 1: 0);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    //Accessor  methods
    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public int getM_wounds() {
        return m_wounds;
    }

    public void setM_wounds(int m_wounds) {
        this.m_wounds = m_wounds;
    }

    public boolean isM_quick() {
        return m_quick;
    }

    public void setM_quick(boolean m_quick) {
        this.m_quick = m_quick;
    }

    public boolean isM_lvlHeaded() {
        return m_lvlHeaded;
    }

    public void setM_lvlHeaded(boolean m_lvlHeaded) {
        this.m_lvlHeaded = m_lvlHeaded;
    }

    public boolean isM_ImplvlHeaded() {
        return m_ImplvlHeaded;
    }

    public void setM_ImplvlHeaded(boolean m_ImplvlHeaded) {
        this.m_ImplvlHeaded = m_ImplvlHeaded;
    }

    public int getM_bennies() {
        return m_bennies;
    }

    public void setM_bennies(int m_bennies) {
        this.m_bennies = m_bennies;
    }

    public int getM_spells() {
        return m_spells;
    }

    public void setM_spells(int m_spells) {
        this.m_spells = m_spells;
    }


    public int getM_card() {
        return m_card;
    }

    public void setM_card(int m_card) {
        this.m_card = m_card;
    }
}
