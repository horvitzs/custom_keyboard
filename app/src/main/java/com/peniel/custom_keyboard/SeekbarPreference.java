package com.peniel.custom_keyboard;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
/**
 * Created by 진호 on 2016-06-08.
 */
public class SeekbarPreference extends DialogPreference{


    public SeekbarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.seekbar_preference);
    }
/*
    @Override
    protected View onCreateDialogView(ViewGroup view) {
        LayoutInflater li=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return li.inflate(R.layout.seekbar_preference,view,false);
}*/
}
