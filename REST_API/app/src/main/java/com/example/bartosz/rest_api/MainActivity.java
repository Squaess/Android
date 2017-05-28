package com.example.bartosz.rest_api;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SparkButton imageButton;
    private ProgressBar progressBar;
    private GridLayout imagesLayout;
    private SparkButton chuckButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView  = (TextView) findViewById(R.id.tv);
        imageButton = (SparkButton) findViewById(R.id.imageB);
        imageButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                for(int i = 0; i<10; i++) {
                    loadImage();
                }
            }
        });

        chuckButton = (SparkButton) findViewById(R.id.chuck);
        chuckButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                fetchData();
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progress);
        imagesLayout = (GridLayout) findViewById(R.id.imagesLayout);

    }

    private void fetchData() {
        String urlString = "http://api.icndb.com/jokes/random";
        Ion.with(this).load(urlString).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    processData(json);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }

    private void processData(JSONObject json) {
        try {
            JSONObject joky = json.getJSONObject("value");
            final String joke = joky.getString("joke");
            Log.i(TAG,"Joke is"+joke);
            textView.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(joke);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadImage() {
        double width = Resources.getSystem().getDisplayMetrics().widthPixels;
        width = (width-(6*5))/3;

        long ts = new Random().nextLong();
        ImageView imageView = new ImageView(this);
        Picasso.with(this).load("http://thecatapi.com/api/images/get?format=src&timestamp="+String.valueOf(ts)).tag("A").placeholder(R.drawable.twitter).centerCrop().resize((int) width, (int) width).into(imageView);
        imagesLayout.addView(imageView);

        Ion.with(this).load("http://thecatapi.com/api/images/get?format=xml&size=med&results_per_page=12").asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                try {
                    JSONObject json = XML.toJSONObject(result);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }
}
