package com.peniel.custom_keyboard;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

/**
 * Created by 진호 on 2016-06-03.
 */
public class CustomKeyboardVIew extends KeyboardView{


    static final int KEYCODE_EMOJI_1=188;

    public CustomKeyboardVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomKeyboardVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
