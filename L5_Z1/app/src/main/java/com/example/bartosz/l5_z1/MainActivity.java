package com.example.bartosz.l5_z1;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    PowerManager.WakeLock lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // going full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // screen does not go to sleep
        PowerManager pwr = (PowerManager) getSystemService(POWER_SERVICE);
        lock = pwr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "my lock");

        final ExampleView view = (ExampleView) findViewById(R.id.mv);

        // thread change color
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
                    }, 12);
                    try {
                        Thread.sleep(12);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    @Override
    protected void onResume() {
        lock.acquire();
        super.onResume();
    }

    @Override
    protected void onPause() {
        lock.release();
        super.onPause();
    }
}
