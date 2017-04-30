package com.example.bartosz.l4;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

public class CreateTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        SparkButton b = (SparkButton) findViewById(R.id.done_button);
        b.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                EditText et = (EditText) findViewById(R.id.title);
                final String newFileName = et.getText().toString();

                if(Objects.equals(newFileName, "")) {
                    Toast.makeText(getApplicationContext(), "No Title", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText text = (EditText) findViewById(R.id.tekst);
                final String txt = text.getText().toString();

                try {

                    Scanner input = new Scanner(openFileInput(MainActivity.FILENAME));
                    PrintStream output = new PrintStream(openFileOutput("temp", Context.MODE_PRIVATE));

                    while(input.hasNextLine()) {
                        output.println(input.nextLine());
                        Log.i("blah","cos jest");
                    }
                    output.println(newFileName);
                    output.close();
                    input.close();
                    deleteFile(MainActivity.FILENAME);

                    PrintStream out = new PrintStream(openFileOutput(MainActivity.FILENAME, Context.MODE_PRIVATE));
                    Scanner in = new Scanner(openFileInput("temp"));
                    while(in.hasNextLine()) {
                        Log.d("bbbbbb","bylo cos");
                        out.println(in.nextLine());
                    }
                    out.close();
                    in.close();
                    deleteFile("tmp");

                    output = new PrintStream(openFileOutput(newFileName, Context.MODE_PRIVATE));
                    output.println(txt);
                    output.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                setResult(MyListViewAdapter.REQ_CODE,new Intent());
                finish();
            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

    }
}
