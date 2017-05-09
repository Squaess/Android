package com.example.bartosz.l5_z1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Bartosz on 08.05.2017
 */

public class ExampleView extends View {
    private final int WIDTH = 350;
    private final int HEIGHT = 100;
    private final int PADDING = 10;
    private final int SPEED = 3;
    Handler h;
    Paint font;
    private float x;
    private float y;
    Runnable[] r = new Runnable[1];

    public ExampleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        x = Resources.getSystem().getDisplayMetrics().widthPixels/2;
        y = Resources.getSystem().getDisplayMetrics().heightPixels-PADDING-HEIGHT;
        font = new Paint();
        font.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
        font.setTextSize(40);
        font.setARGB(255, 0, 0, 255);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawRect( x-(WIDTH/2), y, x+(WIDTH/2), y+HEIGHT, font);
    }

    public void invalidate(){
        Random rand = new Random();
        int r = rand.nextInt();
        font.setColor(r);
        super.invalidate();
    }

    public void invalidate(int i) {
        if(i<0){
            x-=SPEED;
            super.invalidate();
        }else {
            x+=SPEED;
            super.invalidate();
        }
    }


    public boolean onTouchEvent(MotionEvent event) {

        final float new_x = event.getX();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                if(h != null && r[0] != null ) {
                    h.removeCallbacks(r[0]);
                }

                h = new Handler(Looper.getMainLooper());
                if(new_x > x) {
                    while (new_x > x) {
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                r[0] = this;
                                invalidate(1);
                            }
                        });
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else if(new_x < x){
                    while (new_x < x) {
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                r[0] = this;
                                invalidate(-1);
                            }
                        });
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
        t.start();
        return super.onTouchEvent(event);
    }

}
