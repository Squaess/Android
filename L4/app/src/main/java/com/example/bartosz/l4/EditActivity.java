package com.example.bartosz.l4;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        final TextView tv = (TextView) findViewById(R.id.tytul);
        EditText et = (EditText) findViewById(R.id.editText);
        String title = intent.getStringExtra("TITLE");
        tv.setText(title);

        try {
            Scanner in = new Scanner(openFileInput(title));
            while(in.hasNextLine()) {
                et.append(in.nextLine() + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        SparkButton b = (SparkButton) findViewById(R.id.edit_done);
        b.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                String filename = tv.getText().toString();
                EditText et = (EditText) findViewById(R.id.editText);
                try {
                    PrintStream output = new PrintStream(openFileOutput(filename, Context.MODE_PRIVATE));
                    output.println(et.getText());
                    output.close();
                    setResult(MyListViewAdapter.REQ_CODE,new Intent());
                    finish();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });
    }
}
