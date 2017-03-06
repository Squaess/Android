package com.example.bartosz.lab1;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
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
    private boolean[] czyByl;
    private int toRand;
    private String word;
    private String guess;
    private int row;
    private int col;
    private int correct=0;
    private int failed=0;
    private Button check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check = (Button)findViewById(R.id.b2);
        toRand = dictionary.length;
        czyByl = new boolean[toRand];
        roll();
    }

    public void roll() {
        Random rand =  new Random();
        int prev = row;
        row = rand.nextInt(toRand);
        int val = 0;
        for(int i = 0; i < toRand; i++) {
            if(czyByl[i]==true) val++;
        }
        if(val==toRand) {
            Toast.makeText(this, "END!!", Toast.LENGTH_SHORT).show();
            RelativeLayout l = (RelativeLayout)findViewById(R.id.main);
            l.setBackgroundColor(Color.GREEN);
        } else {
            while (prev == row || czyByl[row] == true) {
                row = (row + 1) % toRand;
            }
            col = rand.nextInt(2);
            word = dictionary[row][col];
            TextView tv = (TextView) findViewById(R.id.word);
            tv.setText(word);
        }
        check.setEnabled(true);
        TextView tv1 = (TextView) findViewById(R.id.cb);
        tv1.setText("Correct: " + correct);
        TextView tv2 = (TextView) findViewById(R.id.fb);
        tv2.setText("Failed: " + failed);

        EditText et = (EditText) findViewById(R.id.guess);
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
            czyByl[row]=true;
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
        check.setEnabled(false);
    }

    public void fail() {
        TextView tv1 = (TextView)findViewById(R.id.cb);
        tv1.setText("Correct: "+ correct);
        TextView tv2 = (TextView)findViewById(R.id.fb);
        tv2.setText("Failed: "+ failed);
    }

    public void small(View view) {
        Toast.makeText(this, "Small", Toast.LENGTH_LONG).show();
        setSize((float)12.0);
    }

    public void medium(View view) {
        Toast.makeText(this, "Medium", Toast.LENGTH_LONG).show();
        setSize((float)16);
    }

    public void large(View view) {
        Toast.makeText(this, "Large", Toast.LENGTH_LONG).show();
        setSize((float)24);
    }

    private void setSize(float n) {
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

        heading.setTextSize(n);
        word.setTextSize(n);
        gues.setTextSize(n);
        sm.setTextSize(n);
        me.setTextSize(n);
        lg.setTextSize(n);
        an.setTextSize(n);
        ne.setTextSize(n);
        ch.setTextSize(n);
        cb.setTextSize(n);
        fb.setTextSize(n);
        bot.setTextSize(n);
    }

}
