package com.example.bartosz.l4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

public class MainActivity extends AppCompatActivity {
    //tutaj beda sie znajdpwac nazwy plikow ktore mam czytac
    public static final String FILENAME = "data.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SparkButton button = (SparkButton) findViewById(R.id.spark_button);
        button.setEventListener(new SparkEventListener() {

            @Override
            public void onEvent(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                Log.i("SHOULD_START_NEW_INTENT", "new intent");
                Intent intent = new Intent(getApplicationContext(), CreateTaskActivity.class);
                startActivityForResult(intent,MyListViewAdapter.REQ_CODE);
            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });
    }

    protected void onActivityResult(int RequestCode, int resultCode, Intent intent) {
        super.onActivityResult(RequestCode, resultCode, intent);
        recreate();
    }

}
