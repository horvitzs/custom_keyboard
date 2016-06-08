package com.peniel.custom_keyboard;

/**
 * Created by windy on 2016-06-06.
 */
import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.Arrays;

import android.util.Log;


public class HangulAutomata
{
    public int currentState = 0;
    // 0 - No Input
    // 1 - Accept 초성
    // 2 - Accept 중성
    // 3 - Accept 종성

    private int START = 0; // 초성
    private int MIDDLE = 0; // 중성
    private int FINAL = 0;  // 종성

    final int JAEUM = 1;
    final int MOEUM = 2;

    String tempStringToReturn = "";


    public void reset()
    {
        currentState = 0;
        START = 0;
        MIDDLE = 0;
        FINAL = 0;
    }

    public String appendCharacter(int primaryCode)
    {
        switch(currentState){
            case 0: // No Input
                switch(categorize(primaryCode)){
                    case JAEUM:
                        currentState = 1;
                        START = getChosungIndex(primaryCode);
                        break;

                    default:
                        tempStringToReturn = CodeToHangul(primaryCode);
                        reset();
                }
                break;
            case 1:
                switch(categorize(primaryCode)){
                    case JAEUM:
                        tempStringToReturn = getFullWord();
                        currentState = 1;
                        START = getChosungIndex(primaryCode);
                        break;

                    case MOEUM:
                        currentState = 2;
                        MIDDLE = getJungsungIndex(primaryCode);
                        break;
                    default:
                        tempStringToReturn = getFullWord();
                        reset();
                        tempStringToReturn += CodeToHangul(primaryCode);
                }
                break;

            case 2:
                switch(categorize(primaryCode)){
                    case JAEUM:
                        currentState = 3;
                        FINAL = getJongsungIndex(primaryCode);
                        if(FINAL == 0){
                            tempStringToReturn = getFullWord();
                            reset();
                            currentState = 1;
                            START = getChosungIndex(primaryCode);
                        }
                        break;
                    case MOEUM:
                        int later = getJungsungIndex(primaryCode);

                        switch(100*MIDDLE + later){
                            case 800: // ㅘ
                                MIDDLE = 9; break;
                            case 801: // ㅙ
                                MIDDLE = 10; break;
                            case 820: // ㅚ
                                MIDDLE = 11; break;
                            case 1304: // ㅝ
                                MIDDLE = 14; break;
                            case 1305: // ㅞ
                                MIDDLE = 15; break;
                            case 1320: // ㅟ
                                MIDDLE = 16; break;
                            case 1820: // ㅢ
                                MIDDLE = 19; break;
                            default:
                                tempStringToReturn = getFullWord();
                                tempStringToReturn += CodeToHangul(primaryCode);
                                reset();
                        }
                        break;
                    default:
                        tempStringToReturn = getFullWord();
                        reset();
                        tempStringToReturn += CodeToHangul(primaryCode);
                }
                break;

            case 3:
                switch(categorize(primaryCode)){
                    case JAEUM:
                        int l = getJongsungIndex(primaryCode);
                        switch(100*FINAL + l) {
                            case 119: // ㄳ
                                FINAL = 3; break;
                            case 422: // ㄵ
                                FINAL = 5; break;
                            case 427: // ㄶ
                                FINAL = 6; break;
                            case 801: // ㄺ
                                FINAL = 9; break;
                            case 816: // ㄻ
                                FINAL = 10; break;
                            case 817: // ㄼ
                                FINAL = 11; break;
                            case 819: // ㄽ
                                FINAL = 12; break;
                            case 825: // ㄾ
                                FINAL = 13; break;
                            case 826: // ㄿ
                                FINAL = 14; break;
                            case 827: // ㅀ
                                FINAL = 15; break;
                            case 1619: // ㅄ
                                FINAL = 18; break;

                            default:
                                tempStringToReturn = getFullWord();
                                reset();
                                currentState = 1;
                                START = getChosungIndex(primaryCode);
                        }
                        break;
                    case MOEUM:
                        switch(FINAL){
                            case 0:
                                tempStringToReturn = getFullWord();
                                reset();
                                tempStringToReturn += CodeToHangul(primaryCode);
                                break;

                            case 1: JongsungProcess(0, 'ㄱ', primaryCode); break;
                            case 2: JongsungProcess(0, 'ㄲ', primaryCode); break;
                            case 3: JongsungProcess('ㄱ', 'ㅅ', primaryCode); break;
                            case 4: JongsungProcess(0, 'ㄴ', primaryCode); break;
                            case 5: JongsungProcess('ㄴ', 'ㅈ', primaryCode); break;
                            case 6: JongsungProcess('ㄴ', 'ㅎ', primaryCode); break;
                            case 7: JongsungProcess(0, 'ㄷ', primaryCode); break;
                            case 8: JongsungProcess(0, 'ㄹ', primaryCode); break;
                            case 9: JongsungProcess('ㄹ', 'ㄱ', primaryCode); break;
                            case 10: JongsungProcess('ㄹ', 'ㅁ', primaryCode); break;
                            case 11: JongsungProcess('ㄹ', 'ㅂ', primaryCode); break;
                            case 12: JongsungProcess('ㄹ', 'ㅅ', primaryCode); break;
                            case 13: JongsungProcess('ㄹ', 'ㅌ', primaryCode); break;
                            case 14: JongsungProcess('ㄹ', 'ㅍ', primaryCode); break;
                            case 15: JongsungProcess('ㄹ', 'ㅎ', primaryCode); break;
                            case 16: JongsungProcess(0, 'ㅁ', primaryCode); break;
                            case 17: JongsungProcess(0, 'ㅂ', primaryCode); break;
                            case 18: JongsungProcess('ㅂ', 'ㅅ', primaryCode); break;
                            case 19: JongsungProcess(0, 'ㅅ', primaryCode); break;
                            case 20: JongsungProcess(0, 'ㅆ', primaryCode); break;
                            case 21: JongsungProcess(0, 'ㅇ', primaryCode); break;
                            case 22: JongsungProcess(0, 'ㅈ', primaryCode); break;
                            case 23: JongsungProcess(0, 'ㅊ', primaryCode); break;
                            case 24: JongsungProcess(0, 'ㅋ', primaryCode); break;
                            case 25: JongsungProcess(0, 'ㅌ', primaryCode); break;
                            case 26: JongsungProcess(0, 'ㅍ', primaryCode); break;
                            case 27: JongsungProcess(0, 'ㅎ', primaryCode); break;
                        }

                        break;

                    default:
                        tempStringToReturn = getFullWord();
                        reset();
                        tempStringToReturn += CodeToHangul(primaryCode);
                }
        }

        String returnString = tempStringToReturn + getFullWord();
        tempStringToReturn = "";
        return returnString;
    }

    private int categorize(int input)
    {
        if('ㄱ' <= input && input <= 'ㅎ'){
            System.out.println("자음을 받음");
            return JAEUM;
        } else if('ㅏ' <= input && input <= 'ㅣ'){
            System.out.println("모음을 받음");
            return MOEUM;
        }
        return 0;
    }

    private String CodeToHangul(int input){
        char[] charPair = Character.toChars(input);
        String str = new String(charPair);
        return str;
    }

    private int getChosungIndex(int input){
        switch(input){
            case 'ㄱ': return 0;
            case 'ㄲ': return 1;
            case 'ㄴ': return 2;
            case 'ㄷ': return 3;
            case 'ㄸ': return 4;
            case 'ㄹ': return 5;
            case 'ㅁ': return 6;
            case 'ㅂ': return 7;
            case 'ㅃ': return 8;
            case 'ㅅ': return 9;
            case 'ㅆ': return 10;
            case 'ㅇ': return 11;
            case 'ㅈ': return 12;
            case 'ㅉ': return 13;
            case 'ㅊ': return 14;
            case 'ㅋ': return 15;
            case 'ㅌ': return 16;
            case 'ㅍ': return 17;
            case 'ㅎ': return 18;
            default : return -1;
        }
    }
    private String getChosungByIndex(int index){
        switch(index){
            case 0: return "ㄱ";
            case 1: return "ㄲ";
            case 2: return "ㄴ";
            case 3: return "ㄷ";
            case 4: return "ㄸ";
            case 5: return "ㄹ";
            case 6: return "ㅁ";
            case 7: return "ㅂ";
            case 8: return "ㅃ";
            case 9: return "ㅅ";
            case 10: return "ㅆ";
            case 11: return "ㅇ";
            case 12: return "ㅈ";
            case 13: return "ㅉ";
            case 14: return "ㅊ";
            case 15: return "ㅋ";
            case 16: return "ㅌ";
            case 17: return "ㅍ";
            case 18: return "ㅎ";
            default: return "";
        }
    }
    private int getJungsungIndex(int input){
        switch(input){
            case 'ㅏ': return 0;
            case 'ㅐ': return 1;
            case 'ㅑ': return 2;
            case 'ㅒ': return 3;
            case 'ㅓ': return 4;
            case 'ㅔ': return 5;
            case 'ㅕ': return 6;
            case 'ㅖ': return 7;
            case 'ㅗ': return 8;
            case 'ㅘ': return 9;
            case 'ㅙ': return 10;
            case 'ㅚ': return 11;
            case 'ㅛ': return 12;
            case 'ㅜ': return 13;
            case 'ㅝ': return 14;
            case 'ㅞ': return 15;
            case 'ㅟ': return 16;
            case 'ㅠ': return 17;
            case 'ㅡ': return 18;
            case 'ㅢ': return 19;
            case 'ㅣ': return 20;
            default : return -1;
        }
    }
    private int getJongsungIndex(int input){
        switch(input){
            case 'ㄱ': return 1;
            case 'ㄲ': return 2;
            case 'ㄴ': return 4;
            case 'ㄷ': return 7;
            case 'ㄹ': return 8;
            case 'ㅁ': return 16;
            case 'ㅂ': return 17;
            case 'ㅅ': return 19;
            case 'ㅆ': return 20;
            case 'ㅇ': return 21;
            case 'ㅈ': return 22;
            case 'ㅊ': return 23;
            case 'ㅋ': return 24;
            case 'ㅌ': return 25;
            case 'ㅍ': return 26;
            case 'ㅎ': return 27;
            default : return 0;
        }
    }

    private void JongsungProcess(int left, int send, int primaryCode){
        FINAL = getJongsungIndex(left);
        tempStringToReturn = getFullWord();
        reset();
        currentState = 2;
        START = getChosungIndex(send); MIDDLE = getJungsungIndex(primaryCode);
    }

    private String getFullWord(){
        int t = 0xAC00;

        if(currentState == 0)
            return new String("");
        else if(currentState == 1)
            return getChosungByIndex(START);
        else {
            t += START * 21 * 28;
            t += MIDDLE * 28;
            t += FINAL;

            return CodeToHangul(t);
        }
    }
}