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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static android.view.DragEvent.ACTION_DRAG_STARTED;

/**
 * Created by Bartosz on 08.05.2017
 */

public class ExampleView extends View {
    int counter = 0;

    Deck deck;
    Ball ball;

    ArrayList<Deck> blocks;
    private final int PADDING = 10;
    private final int SPEED = 10;

    Handler h;

    public ExampleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        float x = Resources.getSystem().getDisplayMetrics().widthPixels/2;
        float y = Resources.getSystem().getDisplayMetrics().heightPixels-PADDING;
        deck = new Deck(x, y);

        ball = new Ball(x,y-deck.HEIGHT-Ball.r);
        ball.setRandPaint();

//        font = new Paint();
//        font.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
//        font.setTextSize(40);
//        font.setARGB(255, 0, 0, 255);
//
//        b = new Paint();
//        b.setARGB(255,0,0,255);

        blocks = new ArrayList<>();
        int width = Resources.getSystem().getDisplayMetrics().widthPixels/5;
        int height = width/4;
        for (int j = 1; j < 5; j ++) {
            for (int i = 0; i < 5; i++) {
                Deck block = new Deck((i * width) + width / 2, j * height);

                block.setHEIGHT(height);

                block.setY(j * height);

                block.setWIDTH(width);
                block.setRandPaint();
                blocks.add(block);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawRect( deck.getX()-(deck.WIDTH/2), deck.getY() - (deck.HEIGHT/2), deck.getX()+(deck.WIDTH/2), deck.getY()+ (deck.HEIGHT/2), deck.getPaint());
        canvas.drawCircle(ball.getX(), ball.getY(), Ball.r, ball.getPaint());
        for (Deck d : blocks) {
            canvas.drawRect(d.getX() - (d.WIDTH/2), d.getY() - (d.HEIGHT/2), d.getX()+(d.WIDTH/2), d.getY()+(d.HEIGHT/2), d.getPaint());
        }
    }

    public void invalidate(){
    //    Random rand = new Random();
    //    int r = rand.nextInt();
    //    font.setColor(r);

    //    r = rand.nextInt();
    //    b.setColor(r);
//        if (counter > 180) ball.change_direction(ball.getX()-10, ball.getY()+ Ball.r);
        //sprawdz czy spotkalismy jakis obiekt
        // jesli nie to move ball else change direction
        checkCollision();
        ball.moveBall();

        counter++;
        super.invalidate();
    }

    private boolean checkCollision() {

        ArrayList<Point> points = ball.getArray();

        for(Deck d: blocks) {
            for (Point p : points) {
                if (d.contains(p.x,p.y)) {
                    ball.change_direction(d.getX(), d.getY());
                    return true;
                }
            }
            if ( d.contains(ball.getX(), ball.getY()) ) {
                ball.change_direction(d.getX(), d.getY());
                // tutaj jeszcze usunac obiekt z listy;
                blocks.remove(d);
                return true;
            }
        }
        for (Point p : points ) {
            if (deck.contains(p.x, p.y)) {
                ball.change_direction(deck.getX(), deck.getY());
                return true;
            }
        }
        return false;
    }

    private void changeBallDir(float obj_x, float obj_y, float x, float y) {
        if ( x > obj_x && y < obj_x )
    }

    public void invalidate(int i) {
        if(i<0){
            deck.setX(deck.getX()-SPEED);
            super.invalidate();
        }else {
            deck.setX(deck.getX()+SPEED);
            super.invalidate();
        }
    }


    public boolean onTouchEvent(MotionEvent event) {

        final float new_x = event.getX();

        if(new_x > deck.getX()-(deck.WIDTH/2) && new_x < deck.getX() + (deck.WIDTH/2)) {

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    Toast.makeText(getContext(),"ACTION_DOWN", Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_UP:
                    Toast.makeText(getContext(),"ACTION_UP", Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    Toast.makeText(getContext(),"ACTION_POINTER_DOWN", Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    Toast.makeText(getContext(),"ACTION_POINTER_UP", Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Toast.makeText(getContext(),"ACTION_MOVE", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                h = new Handler(Looper.getMainLooper());
                if (new_x > deck.getX()) {
                    while (new_x > deck.getX()) {
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                invalidate(1);
                            }
                        });

                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (new_x < deck.getX()) {
                    while (new_x < deck.getX()) {
                        h.post(new Runnable() {
                            @Override
                            public void run() {
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
