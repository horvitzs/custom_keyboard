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
public class Custom_Keyboard extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener{


    private StringBuilder mComposing = new StringBuilder();

    //private KeyboardView kv;
    private KeyboardView kv;
    private Keyboard keyboard;
    private Keyboard keyboard_shift;

    private Keyboard qwerty_keyboard;
    private Keyboard qwerty_shift_keyboard;
    private Keyboard hangul_keyboard;
    private Keyboard hangul_shift_keyboard;
    private Keyboard symbol_keyboard;
    private Keyboard mCurrent_keyboard;

    private Keyboard EmojiKeyboard_a0;
    private Keyboard EmojiKeyboard_a1;
    private Keyboard EmojiKeyboard_a2;
    private Keyboard EmojiKeyboard_a3;
    private Keyboard EmojiKeyboard_a4;
    private Keyboard EmojiKeyboard_a5;


    private boolean caps=false;
    private boolean globe=false;
    private boolean now_english=true;
    private boolean isEmoji=false;

    private int last_keyboard = 0;
    HangulAutomata mHangulAutomata = new HangulAutomata();

    @Override
    public void onCreate() {


        super.onCreate();
    }


    public void updateColor(){
        //SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getActivity());

    }
    @Override
    public void onInitializeInterface() {
        keyboard=new Keyboard(this,R.xml.qwerty);
        keyboard_shift=new Keyboard(this, R.xml. qwerty_shift);

        hangul_keyboard=new Keyboard(this,R.xml.hangul);
        hangul_shift_keyboard = new Keyboard(this,R.xml.hangul_shift);
        qwerty_keyboard=new Keyboard(this,R.xml.qwerty);
        qwerty_shift_keyboard=new Keyboard(this,R.xml.qwerty_shift);

        symbol_keyboard = new Keyboard(this, R.xml.symbol);

        EmojiKeyboard_a0=new Keyboard(this,R.xml.emoji_a0);
        EmojiKeyboard_a1=new Keyboard(this,R.xml.emoji_a1);
        EmojiKeyboard_a2=new Keyboard(this,R.xml.emoji_a2);
    }

    @Override
    public View onCreateInputView() {

        // SharedPreferences color_pref=getSharedPreferences("text_color_preference", Integer.parseInt("red"));
        //  int txt_color=color_pref.getInt("")


        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            ic.commitText(mComposing, 1);
        }
        mComposing.setLength(0);
        mHangulAutomata.reset();

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
                keyboard        = new Keyboard(this, R.xml.qwerty);
                keyboard_shift = new Keyboard(this, R.xml.qwerty_shift);
                break;
            case "small":
                keyboard        = new Keyboard(this, R.xml.qwerty_small);
                keyboard_shift = new Keyboard(this, R.xml.qwerty_shift_small);
                break;
            case "large":
                keyboard        = new Keyboard(this, R.xml.qwerty_large);
                keyboard_shift = new Keyboard(this, R.xml.qwerty_shift_large);
                break;
            default:
                keyboard = qwerty_keyboard;
                keyboard_shift = qwerty_shift_keyboard;
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
        System.out.println(primaryCode);
        InputConnection ic=getCurrentInputConnection();
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE:
                if(mComposing.length() > 0){
                    mHangulAutomata.reset();
                    mComposing.setLength(0);
                    ic.setComposingText(mComposing, 0);
                } else {
                    try{
                        CharSequence t = ic.getTextBeforeCursor(1, 0);
                        System.out.println("0x"+Integer.toHexString(t.charAt(0)));
                        if(0xd7a3 < t.charAt(0) && t.charAt(0) < 0xE000){
                            ic.deleteSurroundingText(2,0);
                        } else {
                            ic.deleteSurroundingText(1,0);
                        }
                    } catch (RuntimeException e) {
                        System.out.println(e);
                    }

                }
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                break;
            case Keyboard.KEYCODE_MODE_CHANGE:
                System.out.println(now_english);
                if(!now_english){
                    kv.setKeyboard(keyboard);
                    now_english=true;
                }
                else{
                    kv.setKeyboard(hangul_keyboard);
                    now_english=false;
                }
                break;
            case 188:
                isEmoji = true;
                now_english = false;
                kv.setKeyboard(EmojiKeyboard_a0);
                changeEmojiKeyboard(new Keyboard[]{
                        EmojiKeyboard_a0, EmojiKeyboard_a1, EmojiKeyboard_a2
                });
                break;

            case -3:
                kv.closing();
                break;

            case -11:
                isEmoji = false;
                kv.setKeyboard(keyboard);
                now_english=true;
                break;

            case -120:
                kv.setKeyboard(keyboard_shift);
                break;

            case -121:
                kv.setKeyboard(keyboard);
                break;

            case -122:
                kv.setKeyboard(hangul_shift_keyboard);
                break;

            case -123:
                kv.setKeyboard(hangul_keyboard);
                break;

            case -124:
                kv.setKeyboard(symbol_keyboard);
                break;

            case -125:
                if(now_english) {
                    kv.setKeyboard(keyboard);
                }
                else {
                    kv.setKeyboard(hangul_keyboard);
                }
                break;

            default:
                if(now_english){
                    mHangulAutomata.reset();
                    ic.commitText(new String(Character.toChars(primaryCode)), 1);
                }

                else if(isEmoji){
                    mHangulAutomata.reset();
                    ic.commitText(new String(Character.toChars(primaryCode)), 1);
                }

                else {
                    String ret = mHangulAutomata.appendCharacter(primaryCode);

                    if(mHangulAutomata.currentState == 0){
                        ic.commitText(ret, ret.length());
                    }
                    else if(ret.length() >= 1){
                        ic.commitText(ret.substring(0, ret.length()-1), 1);
                        mComposing.setLength(0);
                        mComposing.append(ret.charAt(ret.length() - 1));
                        ic.setComposingText(mComposing, 1);
                    }
                    else {
                        mComposing.setLength(0);
                        mComposing.append(ret.charAt(ret.length() - 1));
                        ic.setComposingText(mComposing, 1);
                    }
                }

                if(kv.getKeyboard() == hangul_shift_keyboard){
                    kv.setKeyboard(hangul_keyboard);
                }

              /*  String emoji_string;
                emoji_string = String.valueOf(ic.commitText(String.valueOf(code),1));
                ic.commitText(AndroidEmoji.ensure(emoji_string,getApplicationContext()),1);*/


              /*  String emoji_string = String.valueOf(code);
                emoji_string= Emoji.replaceInText(emoji_string);
                ic.commitText(emoji_string,1);*/
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
