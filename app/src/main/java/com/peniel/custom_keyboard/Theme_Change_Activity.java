package com.peniel.custom_keyboard;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.List;

public class Theme_Change_Activity extends PreferenceActivity
{


    KeyboardView blue_keyboard,red_keyboard,white_keyboard;
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
     /*   for(Header header:target){
            if(header.titleRes==R.string.pref_text_color){
                int header1_icon=R.drawable.paint_palette;
                header.iconRes=header1_icon;
                break;
            }
            if(header.titleRes==R.string.pref_text_size){
                int header2_icon=R.drawable.magnifying_glass_finder;
                header.iconRes=header2_icon;
                break;
            }
        }*/
    }


    public void myOnClick2(View v) {
        switch (v.getId()) {
            case R.id.button:

        }
    }



   /* @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("text_color_preference")){
            Preference connectionPref=findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key,"red"));
        }
    }*/



    public static class Prefs1fragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PreferenceManager.setDefaultValues(getActivity(), R.xml.pref1, false);
            addPreferencesFromResource(R.xml.pref1);
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_text_color)));

        }

        private void bindPreferenceSummaryToValue(Preference preference){
            preference.setOnPreferenceChangeListener(this);
            onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();




            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list (since they have separate labels/values).
                final ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                }


            }
            else {
                // For other preferences, set the summary to the value's simple string representation.
                preference.setSummary(stringValue);
            }


            return true;
        }



    }

    public static class Prefs2fragment extends PreferenceFragment implements  Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PreferenceManager.setDefaultValues(getActivity(), R.xml.pref2, false);
            addPreferencesFromResource(R.xml.pref2);
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_text_size)));

        }

        private void bindPreferenceSummaryToValue(Preference preference){
            preference.setOnPreferenceChangeListener(this);
            onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();




            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list (since they have separate labels/values).
                final ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                }


            }
            else {
                // For other preferences, set the summary to the value's simple string representation.
                preference.setSummary(stringValue);
            }


            return true;
        }

    }



}