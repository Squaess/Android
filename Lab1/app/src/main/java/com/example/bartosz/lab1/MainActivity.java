package com.example.bartosz.lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[][] dictionary = {
            {"door","drzwi"},
            {"awesome","niesamowite"},
            {"conflict","konflikt"},
            {"apple","jabłko"},
            {"pen","długopis"},
            {"hair","włosy"},
            {"pain","ból"},
            {"please","proszę"},
            {"yes","tak"}
    };
    private int toRand;
    private String word;
    private String guess;
    private int row;
    private int col;
    private int correct=0;
    private int failed=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toRand = dictionary.length;
        roll();
    }

    public void roll() {
        Random rand =  new Random();
        int prev = row;
        row = rand.nextInt(toRand);
        if(prev == row) {
            row = (row+1)%toRand;
        }
        TextView tv1 = (TextView)findViewById(R.id.cb);
        tv1.setText("Correct: "+ correct);
        TextView tv2 = (TextView)findViewById(R.id.fb);
        tv2.setText("Failed: "+ failed);
        col = rand.nextInt(2);
        word = dictionary[row][col];
        TextView tv = (TextView)findViewById(R.id.word);
        tv.setText(word);
        EditText et = (EditText)findViewById(R.id.guess);
        et.setText("");
    }

    public void nextButton(View view) {
        roll();
    }

    public void checkButton(View view) {
        EditText et = (EditText)findViewById(R.id.guess);
        guess = et.getText().toString();
        if(guess.equals(dictionary[row][(col+1)%2])) {
            Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
            correct++;
            roll();
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
            failed++;
            fail();
        }
    }

    public void answButton(View view) {
        EditText et = (EditText)findViewById(R.id.guess);
        et.setText(dictionary[row][(col+1)%2]);
    }

    public void fail() {
        TextView tv1 = (TextView)findViewById(R.id.cb);
        tv1.setText("Correct: "+ correct);
        TextView tv2 = (TextView)findViewById(R.id.fb);
        tv2.setText("Failed: "+ failed);
    }

    public void small() {
        TextView heading = (TextView)findViewById(R.id.heading);
        TextView word = (TextView)findViewById(R.id.word);
        EditText gues = (EditText)findViewById(R.id.guess);
        Button sm = (Button)findViewById(R.id.sm);
        Button me = (Button)findViewById(R.id.me);
        Button lg = (Button)findViewById(R.id.lg);

        Button an = (Button)findViewById(R.id.answ);
        Button ne = (Button)findViewById(R.id.b1);
        Button ch = (Button)findViewById(R.id.b2);

        TextView cb = (TextView)findViewById(R.id.cb);
        TextView fb = (TextView)findViewById(R.id.fb);

        TextView bot = (TextView)findViewById(R.id.bottom);

        setSize();
    }

    private void setSize() {}

}
