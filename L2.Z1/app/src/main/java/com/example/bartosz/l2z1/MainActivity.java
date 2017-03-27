package com.example.bartosz.l2z1;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.bartosz.l2z1.MainActivity.buttonState;
import static com.example.bartosz.l2z1.MainActivity.state;


public class MainActivity extends AppCompatActivity {

    public static final int N = 4;
    static final int[] buttonState = new int[N*N];
    int[] currButtonState = new int[N*N];
    /**
     * Size of square board
     */

    static boolean state = true;

    /**
     *  main layout N times linear layout
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            currButtonState = savedInstanceState.getIntArray("ButtonState");
        } else {
            currButtonState = null;
        }

        setContentView(R.layout.activity_main);
        /*
         * Taking linear layout from XML activity_main and adding N rows and N*N buttons
         */
        LinearLayout column = (LinearLayout) findViewById(R.id.oneLinearColumn);

        /*
         * Linear layout params for every row
         * and button params
         */

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout.LayoutParams bparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1);

        /*
         * creating N linear layouts for buttons rows and adding buttons to them
         */
        int counter = 0;
        for (int i = 0; i < N; i++) {
            LinearLayout ll = new LinearLayout(this);
            ll.setLayoutParams(params);
            ll.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            ll.setDividerDrawable(getDrawable(R.drawable.empty_col_divider));

            for(int j = 0 ;  j < N; j++) {

                final Button b = new Button(this);
                b.setId(Integer.parseInt(Integer.toString(j)+Integer.toString(i)));
                b.setLayoutParams(bparams);
                if(currButtonState == null) {
                    b.setBackgroundResource(R.drawable.blank);
                    buttonState[counter] = -1;
                    b.setEnabled(true);
                } else {
                    if (currButtonState[counter] == 1) {
                        b.setBackgroundResource(R.drawable.kolko);
                        b.setEnabled(false);
                    } else if (currButtonState[counter] == 0) {
                        b.setBackgroundResource(R.drawable.krzyzyk);
                        b.setEnabled(false);
                    } else {
                        b.setBackgroundResource(R.drawable.blank);
                        b.setEnabled(true);
                    }
                }
                MyOnClickListener listen = new MyOnClickListener(counter, b);
                b.setOnClickListener(listen);
                ll.addView(b);
                counter++;
            }
            column.addView(ll);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putIntArray("ButtonState",buttonState);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void resetClick(View view) {
        Log.d("CREATING","Hello");
        state = true;
        int counter = 0;
        for (int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                Button b = (Button)findViewById(Integer.parseInt(Integer.toString(j)+Integer.toString(i)));
                b.setEnabled(true);
                b.setBackgroundResource(R.drawable.blank);
                buttonState[counter] = -1;
                counter++;
            }
        }
    }
}


class MyOnClickListener implements View.OnClickListener {

    private int counter;

    private Button b;
    MyOnClickListener(int n, Button but) {
        this.counter = n;
        this.b = but;
    }
    @Override
    public void onClick(View v) {
        if(state) {
            b.setBackgroundResource(R.drawable.kolko);
            b.setEnabled(false);
            buttonState[counter] = 1;
            state = false;
        } else {
            b.setBackgroundResource(R.drawable.krzyzyk);
            b.setEnabled(false);
            buttonState[counter] = 0;
            state = true;
        }
    }
}