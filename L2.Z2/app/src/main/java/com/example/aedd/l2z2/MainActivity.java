package com.example.aedd.l2z2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String[] words;
    EditText guess;
    TextView wykorzystane;
    Button  button;
    Button nast;
    TextView actual;
    int current;
    String curWord;
    int ktory = 0;
    ImageView wisielec;
    int ilezgadl;

    private Integer[] mThumbIds = {
            R.drawable.first, R.drawable.secound,
            R.drawable.third, R.drawable.fourth,
            R.drawable.fifth
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nast = (Button)findViewById(R.id.next);
        words = getResources().getStringArray(R.array.dictionary);
        guess = (EditText) findViewById(R.id.guessTextField);
        button  = (Button) findViewById(R.id.check);
        actual = (TextView) findViewById(R.id.zagadka);
        wykorzystane = (TextView) findViewById(R.id.wykorzystane);
        wisielec = (ImageView) findViewById(R.id.wisielec);

        routine();

    }

    public void routine() {
        Random r = new Random();
        current = r.nextInt(words.length);

        int length = words[current].length();
        String toSet = "";
        for(int i = 0; i <length; i++) {
            toSet += "_ ";
        }
        actual.setText(toSet);
        curWord = toSet;
        wykorzystane.setText("");

    }
    public void check(View view) {
        boolean znalazl = false;
        char tmp  = guess.getText().charAt(0);
        wykorzystane.append(" "+Character.toString(tmp));
        for (int i = 0; i< words[current].length(); i++) {
            char c = words[current].charAt(i);
            if(tmp == c) {
                znalazl = true;
                ilezgadl++;
                char[] arr = curWord.toCharArray();
                arr[i*2] = c;
                curWord = String.valueOf(arr);
            }
        }
        actual.setText(curWord);

        guess.setText("");
        if(!znalazl) {

            wisielec.setImageResource(mThumbIds[ktory]);
            ktory++;
            if(ktory>=5) {
                koniecgry();
            }
        }
        if(ilezgadl==words[current].length()) {
            koniecgry();
        }
    }

    public void koniecgry() {
        button.setEnabled(false);
        nast.setEnabled(true);
        actual.setText(words[current]);
    }

    public void next(View view) {
        ktory = 0;
        ilezgadl = 0;
        wisielec.setImageResource(R.drawable.blank);
        routine();
        nast.setEnabled(false);
        button.setEnabled(true);
    }
}
