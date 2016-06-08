package com.peniel.custom_keyboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  getFragmentManager().beginTransaction()
                .replace(android.R.id.content,new setting_fragment()).commit();*/
        setContentView(R.layout.activity_main);

        Handler handler=new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                startActivity(new Intent(MainActivity.this,Theme_Change_Activity.class));
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0,3000);


        /*Keyboard myKeyboard=new Keyboard(this,R.xml.m_keyboard);
        KeyboardView myKeyboardView=(KeyboardView)findViewById(R.id.keyboard_view);
        myKeyboardView.setKeyboard(myKeyboard);
        myKeyboardView.setPreviewEnabled(false);*/


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}


