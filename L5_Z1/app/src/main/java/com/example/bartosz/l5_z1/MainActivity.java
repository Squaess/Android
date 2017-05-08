package com.example.bartosz.l5_z1;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final ExampleView view = (ExampleView) findViewById(R.id.mv);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Handler h = new Handler(Looper.getMainLooper());
                while(true) {
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.invalidate();
                        }
                    }, 2000);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }
}
