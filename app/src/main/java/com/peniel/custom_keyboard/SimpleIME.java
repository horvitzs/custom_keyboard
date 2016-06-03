package com.peniel.custom_keyboard;
import android.content.SharedPreferences;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

/**
 * Created by 진호 on 2016-05-10.
 */
public class SimpleIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener{


    //private KeyboardView kv;
    private KeyboardView kv;
    private Keyboard keyboard;

    private Keyboard qwerty_keyboard;
    private Keyboard hangul_keyboard;
    private Keyboard mCurrent_keyboard;

    private Keyboard EmojiKeyboard_a0;
    private Keyboard EmojiKeyboard_a1;
    private Keyboard EmojiKeyboard_a2;
    private Keyboard EmojiKeyboard_a3;
    private Keyboard EmojiKeyboard_a4;
    private Keyboard EmojiKeyboard_a5;


    private boolean caps=false;
    private boolean globe=false;
    private boolean now_english=false;
    private boolean isEmoji=false;

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

        EmojiKeyboard_a0=new Keyboard(this,R.xml.emoji_a0);
        EmojiKeyboard_a1=new Keyboard(this,R.xml.emoji_a1);
        EmojiKeyboard_a2=new Keyboard(this,R.xml.emoji_a2);
    }

    @Override
    public View onCreateInputView() {

       // SharedPreferences color_pref=getSharedPreferences("text_color_preference", Integer.parseInt("red"));
      //  int txt_color=color_pref.getInt("")

        SharedPreferences color_prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String keyboard_text_color=color_prefs.getString("text_color_preference", "RED");

        SharedPreferences size_prefs=PreferenceManager.getDefaultSharedPreferences(this);
        String keyboard_text_size=size_prefs.getString("text_size_preference","Medium");

        if(keyboard_text_color.equals("red"))
            kv=(KeyboardView)getLayoutInflater().inflate(R.layout.keyboard,null);
        else if(keyboard_text_color.equals("white"))
            kv=(KeyboardView)getLayoutInflater().inflate(R.layout.white_keyboard,null);
        else if(keyboard_text_color.equals("blue"))
            kv=(KeyboardView)getLayoutInflater().inflate(R.layout.blue_keyboard,null);
        else
            kv=(KeyboardView)getLayoutInflater().inflate(R.layout.keyboard,null);


        switch (keyboard_text_size) {
            case "medium":
                keyboard = new Keyboard(this, R.xml.qwerty);
                break;
            case "small":
                keyboard = new Keyboard(this, R.xml.qwerty_small);
                break;
            case "large":
                keyboard = new Keyboard(this, R.xml.qwerty_large);
                break;
            default:
                keyboard = new Keyboard(this, R.xml.qwerty);
                break;
        }

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
        if(kv.getKeyboard()==EmojiKeyboard_a0||kv.getKeyboard()==EmojiKeyboard_a1||kv.getKeyboard()==EmojiKeyboard_a2){
            kv.setPreviewEnabled(false);
        }

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
            case 188:
              //  isEmoji=!isEmoji;
                kv.setKeyboard(EmojiKeyboard_a0);
                changeEmojiKeyboard(new Keyboard[]{
                        EmojiKeyboard_a0, EmojiKeyboard_a1, EmojiKeyboard_a2

                });
                break;
            default:
                char code=(char)primaryCode;
                if(Character.isLetter(code)&&caps){
                    code=Character.toUpperCase(code);
                }

              /*  String emoji_string;
                emoji_string = String.valueOf(ic.commitText(String.valueOf(code),1));
                ic.commitText(AndroidEmoji.ensure(emoji_string,getApplicationContext()),1);*/


              /*  String emoji_string = String.valueOf(code);
                emoji_string= Emoji.replaceInText(emoji_string);
                ic.commitText(emoji_string,1);*/

               ic.commitText(String.valueOf(code),1);
        }

    }


    public void changeEmojiKeyboard(Keyboard[] keyboards){
        int j=0;
        for (int i=0; i<keyboards.length; i++){
            if(keyboards[i]==kv.getKeyboard()){
                j=i;
                break;
            }
        }
        if(j+1>=keyboards.length){
            kv.setKeyboard(keyboards[0]);
        }
        else{
            kv.setKeyboard(keyboards[j+1]);
        }
    }

    public void changeEmojiKeyboardReverse(Keyboard[] emojiKeyboard) {
        int j = emojiKeyboard.length - 1;
        for(int i=emojiKeyboard.length - 1; i>=0; i--) {
            if (emojiKeyboard[i] == this.kv.getKeyboard()) {
                j = i;
                break;
            }
        }

        if (j - 1 < 0) {
            this.kv.setKeyboard(emojiKeyboard[emojiKeyboard.length - 1]);
        }else{
            this.kv.setKeyboard(emojiKeyboard[j - 1]);
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {
        changeEmojiKeyboard(new Keyboard[] {
            EmojiKeyboard_a0,EmojiKeyboard_a1,EmojiKeyboard_a2
        });
    }

    @Override
    public void swipeRight() {
        changeEmojiKeyboardReverse(new Keyboard[]{
                EmojiKeyboard_a0, EmojiKeyboard_a1, EmojiKeyboard_a2
        });

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
