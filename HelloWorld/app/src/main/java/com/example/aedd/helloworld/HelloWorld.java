package com.example.aedd.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class HelloWorld extends AppCompatActivity {

    private int r1;
    private int r2;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);
        score = 0;
        roll();
    }

    private void roll() {
        TextView pt = (TextView)findViewById(R.id.punkty);
        pt.setText("Punkty: "+ Integer.toString(score));
        Random rand = new Random();
        r1 = rand.nextInt(10);
        r2 = rand.nextInt(10);
        Button b1 = (Button)findViewById(R.id.b1);
        b1.setText(Integer.toString(r1));
        Button b2 = (Button)findViewById(R.id.b2);
        b2.setText(Integer.toString(r2));
    }

    public void leftButton(View view) {
        if(r1 <= r2) {
            score++;
            Toast.makeText(this, "Dobrze", Toast.LENGTH_SHORT).show();
        } else {
            score--;
            Toast.makeText(this, "Źle!", Toast.LENGTH_SHORT).show();
        }
        roll();
    }

    public void rightButton(View view) {
        if(r1 >= r2) {
            score++;
            Toast.makeText(this, "Dobrze!", Toast.LENGTH_SHORT).show();
        } else {
            score--;
            Toast.makeText(this, "Źle!", Toast.LENGTH_SHORT).show();
        }
        roll();
    }
}
