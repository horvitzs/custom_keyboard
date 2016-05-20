package com.peniel.custom_keyboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  getFragmentManager().beginTransaction()
                .replace(android.R.id.content,new setting_fragment()).commit();*/
        setContentView(R.layout.activity_main);

        /*Keyboard myKeyboard=new Keyboard(this,R.xml.m_keyboard);
        KeyboardView myKeyboardView=(KeyboardView)findViewById(R.id.keyboard_view);
        myKeyboardView.setKeyboard(myKeyboard);
        myKeyboardView.setPreviewEnabled(false);*/


    }


    public void myOnClick(View v) {
        switch (v.getId()){
            case R.id.keyboard_color_button:
                Intent intent1=new Intent(this, Theme_Change_Activity.class);
                startActivity(intent1);
                break;
        }
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


