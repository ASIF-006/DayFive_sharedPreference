package com.example.ussl_22.dayfive_sharedpreference;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText message;
    SeekBar seekBar;
    float fontSize;
    String fontColor,text ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = (EditText) findViewById(R.id.edit_text);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);


        SharedPreferences preferences = MainActivity.this.
                getSharedPreferences(getString(R.string.PREF_FILE),MODE_PRIVATE);
        text = preferences.getString(getString(R.string.TEXT_INFO),"");
        message.setText(text);

        fontColor = preferences.getString(getString(R.string.FONT_COLOR),"");
        if(fontColor.equals("RED")){
            message.setTextColor(Color.parseColor("#FC0116"));
        }
        else if(fontColor.equals("BLUE")){
            message.setTextColor(Color.parseColor("#081085"));
        }
        else{
            message.setTextColor(Color.parseColor("#03ff20"));
        }

        fontSize = preferences.getFloat(getString(R.string.FONT_SIZE), 25);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX,fontSize);

        if(fontSize == 25){
            seekBar.setProgress(0);
        }
        else{
            seekBar.setProgress((int)fontSize);
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                message.setTextSize(TypedValue.COMPLEX_UNIT_PX,progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                fontSize = message.getTextSize();
            }
        });
    }

    public void changeColor(View view) {
        switch (view.getId()){
            case R.id.btn_red:
                message.setTextColor(Color.parseColor("#FC0116"));
                fontColor = "RED";
                break;
            case R.id.btn_blue:
                message.setTextColor(Color.parseColor("#081085"));
                fontColor = "BLUE";
                break;
            case R.id.btn_green:
                message.setTextColor(Color.parseColor("#03ff20"));
                fontColor = "GREEN";
                break;
        }
    }

    public void saveSetting(View view) {
        SharedPreferences preferences = MainActivity.this.
                getSharedPreferences(getString(R.string.PREF_FILE),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(getString(R.string.FONT_SIZE),fontSize);
        editor.putString(getString(R.string.FONT_COLOR),fontColor);
        editor.putString(getString(R.string.TEXT_INFO),message.getText().toString());
        editor.commit();
        Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();
    }

    public void clearSetting(View view) {
        SharedPreferences preferences = MainActivity.this.
                getSharedPreferences(getString(R.string.PREF_FILE),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        //nulling on runtime
        message.setText(null);
        seekBar.setProgress(0);
        Toast.makeText(getApplicationContext(),"data cleared",Toast.LENGTH_SHORT).show();
    }
}
