package com.peniel.custom_keyboard;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by 진호 on 2016-05-10.
 */
public class SimpleIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener{


    private KeyboardView kv;
    private Keyboard keyboard;

    private Keyboard qwerty_keyboard;
    private Keyboard hangul_keyboard;
    private Keyboard mCurrent_keyboard;

    private boolean caps=false;
    private boolean globe=false;
    private boolean now_english=false;

    @Override
    public void onCreate() {


        super.onCreate();
    }


    public void updateColor(){
        //SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getActivity());

    }
    @Override
    public void onInitializeInterface() {
        mCurrent_keyboard=new Keyboard(this,R.xml.hangul);
        hangul_keyboard=new Keyboard(this,R.xml.hangul);
        qwerty_keyboard=new Keyboard(this,R.xml.qwerty);
    }

    @Override
    public View onCreateInputView() {

       // SharedPreferences color_pref=getSharedPreferences("text_color_preference", Integer.parseInt("red"));
      //  int txt_color=color_pref.getInt("")

        SharedPreferences color_prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String keyboard_text_color=color_prefs.getString("text_color_preference","RED");

        if(keyboard_text_color.equals("red"))
            kv=(KeyboardView)getLayoutInflater().inflate(R.layout.keyboard,null);
        else if(keyboard_text_color.equals("white"))
            kv=(KeyboardView)getLayoutInflater().inflate(R.layout.white_keyboard,null);
        else if(keyboard_text_color.equals("blue"))
            kv=(KeyboardView)getLayoutInflater().inflate(R.layout.blue_keyboard,null);
        else
            kv=(KeyboardView)getLayoutInflater().inflate(R.layout.keyboard,null);

        keyboard=new Keyboard(this,R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }


    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        setInputView(onCreateInputView());
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic=getCurrentInputConnection();
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps=!caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                break;
            case Keyboard.KEYCODE_MODE_CHANGE:
                if(now_english==false){
                   kv.setKeyboard(qwerty_keyboard);
                   now_english=true;
                }
                else{
                    kv.setKeyboard(hangul_keyboard);
                    now_english=false;
                }
                break;
            default:
                char code=(char)primaryCode;
                if(Character.isLetter(code)&&caps){
                    code=Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
        }

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    //read setting
    private void SetPreferredColor(){
        SharedPreferences color_prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String keyboard_text_color=color_prefs.getString("text_color_preference","red");
    }

}
