package com.example.bartosz.l2z1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    /**
     * Size of square board
     */
    public static final int N = 4;

    /**
     *  main layout N times linear layout
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "OnCreate", Toast.LENGTH_LONG).show();
        /**
         * Taking grid layout from XML acticity_main and adding N*N buttons
         */
        GridLayout boardlayout = (GridLayout) findViewById(R.id.boardlayout);
        boardlayout.setRowCount(N);
        boardlayout.setColumnCount(N);

        /**
         * setting properly N*N board
         */

//        for(int i=0; i<N*N; i++) {
//            Button b = new Button(this);
//            b.setWidth(0);
//            b.setHeight(0);
//            b.setText("0");
//
//            boardlayout.addView(b);
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "OnStart", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "OnResume", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "OnRestart", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "OnPause", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "OnStart", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "OnStart", Toast.LENGTH_LONG).show();
    }
}
