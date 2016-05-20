package com.peniel.custom_keyboard;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.List;

public class Theme_Change_Activity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        if(hasHeaders()){
            Button button=new Button(this);
            button.setText("Some Action");
            setListFooter(button);
        }*/
    }


    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_header, target);
    }


    public void myOnClick2(View v) {
                switch (v.getId()) {
                    case R.id.button:

                }
            }

    public static class Prefs1fragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PreferenceManager.setDefaultValues(getActivity(), R.xml.pref1, false);
            addPreferencesFromResource(R.xml.pref1);
        }


    }

    public static class Prefs2fragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref1);
        }
    }



}
