package com.example.bartosz.rest_api;

import android.app.Activity;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.util.Random;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private SparkButton imageButton;
    private ProgressBar progressBar;
    private  ProgressBar chuckProgress;
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
                //for(int i = 0; i<10; i++) {
                //    loadImage();
                //}
                progressBar.setVisibility(View.VISIBLE);
                Ion.with(MainActivity.this).load("http://thecatapi.com/api/images/get?format=xml&size=med&results_per_page=12")
                        .asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            JSONObject json = XML.toJSONObject(result);
                            processXMLData(json);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
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
                chuckProgress.setVisibility(View.VISIBLE);
                fetchData();
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progress);
        chuckProgress = (ProgressBar) findViewById(R.id.chuckProgress);
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
            chuckProgress.setVisibility(View.INVISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void processXMLData(JSONObject json) throws JSONException {
        JSONObject response = json.getJSONObject("response");
        JSONArray array = response.getJSONObject("data").getJSONObject("images").getJSONArray("image");
        Log.i(TAG,"FROM XML to JSON "+ array);
        String[] urls = new String[array.length()];


        double width = Resources.getSystem().getDisplayMetrics().widthPixels;
        width = (width-(6*5))/3;

        for (int i = 0; i<array.length(); i++){
            JSONObject o = array.getJSONObject(i);
            urls[i] = o.getString("url");
        }
        for (String s : urls){
            ImageView imageView = new ImageView(this);
            Picasso.with(this).load(s).placeholder(R.drawable.twitter).error(R.mipmap.ic_error).centerCrop().resize((int) width, (int) width).into(imageView);
            imagesLayout.addView(imageView);
        }
        progressBar.setVisibility(View.INVISIBLE);
    }
}
