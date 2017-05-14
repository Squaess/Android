package com.example.bartosz.l5_z1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

/**
 * Created by Bartosz on 08.05.2017
 */

public class ExampleView extends View {
    int counter = 4;

    Deck deck;
    Ball ball;

    ArrayList<Deck> blocks;
    private final int PADDING = 10;

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
        if (counter > 0) {
            if (blocks.isEmpty()) {
                this.setBackgroundColor(Color.GREEN);
            } else {
                canvas.drawCircle(ball.getX(), ball.getY(), Ball.r, ball.getPaint());

                for (Deck d : blocks) {
                    canvas.drawRect(d.getX() - (d.WIDTH / 2), d.getY() - (d.HEIGHT / 2), d.getX() + (d.WIDTH / 2), d.getY() + (d.HEIGHT / 2), d.getPaint());
                }
            }
        }else {
                this.setBackgroundColor(Color.RED);
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

        super.invalidate();
    }

    private boolean checkCollision() {

        ArrayList<Point> points = ball.getArray();

        for(Deck d: blocks) {
            for (Point p : points) {
                if (d.contains(p.x,p.y)) {
//                    ball.change_direction(d.getX(), d.getY());
                    changeBallDir(d);
                    blocks.remove(d);
                    return true;
                }
            }
//            if ( d.contains(ball.getX(), ball.getY()) ) {
//                ball.change_direction(d.getX(), d.getY());
//                // tutaj jeszcze usunac obiekt z listy;
//                blocks.remove(d);
//                return true;
//            }
        }
        for (Point p : points ) {
            if (deck.contains(p.x, p.y)) {
                ball.change_direction(deck.getX(), deck.getY());
//                changeBallDir(deck);
                return true;
            }
        }
        float width = Resources.getSystem().getDisplayMetrics().widthPixels;
        float height = Resources.getSystem().getDisplayMetrics().heightPixels;
        for (Point p : points) {
            if (p.x <= 0 ) {
                if (ball.angle < 180){

                    ball.changeAngleAndMove(- (2 * (ball.angle - 90)));
                }
                else{
                    ball.changeAngleAndMove(2 * (270 - ball.angle));
                }
                return true;
            } else if (p.x >= width) {
                if (ball.angle < 90) {

                    ball.changeAngleAndMove(2 * (90 - ball.angle));
                } else {

                    ball.changeAngleAndMove(- (2 * (ball.angle - 270)));
                }
                return true;
            } else if (p.y <= 0) {

                if (ball.angle < 90) {
                    ball.changeAngleAndMove(180 + 2 * (90 - ball.angle));
                } else {

                    ball.changeAngleAndMove(2 * (180 - ball.angle));
                }
                return true;
            } else if (p.y >= height) {
                ball.setX(width/2);
                ball.setY(height-deck.HEIGHT - PADDING);
                ball.setRandPaint();
                ball.changeAngleAndMove(-180);
                counter--;
                return true;
            }
        }
        return false;
    }

    private void changeBallDir(Deck block) {
        if ( ball.getX() > block.getX()+(block.WIDTH/2 ) && ball.getY() > ( block.getY() - (block.HEIGHT/2)) && ball.getY() < ( block.getY() + (block.HEIGHT/2) ) )  {
            //na prawo od klocka odejmuje 90 z angle
            // lub dodaje w zaleznosci od angle
            if (ball.angle > 90 && ball.angle < 180) {
                //zmien angle i oblicz move na ball -90

                ball.changeAngleAndMove(-( 2 * (ball.angle-90)));
            } else {
                // zmien angle +90 i oblicz movy

                ball.changeAngleAndMove(2 * (270-ball.angle));
            }
        } else if (ball.getX() < block.getX() - (block.WIDTH/2) && ball.getY() > ( block.getY() - (block.HEIGHT/2)) && ball.getY() < ( block.getY() + (block.HEIGHT/2))) {
            // na lewo od klocka +90 do angle
            // lub -90 w zaleznosci od angle

            if (ball.angle > 0 && ball.angle < 90) {
                //zmien angle +90 i movy

                ball.changeAngleAndMove(2 * (90 - ball.angle));
            } else {
                //zmien angle -90 i movy
                ball.changeAngleAndMove(- (2 * (ball.angle - 270)));
            }
        } else if ( ball.getY() > block.getY() + (block.HEIGHT/2) && ball.getX() > (block.getX()-(block.WIDTH/2)) && ball.getX() < (block.getX() + (block.WIDTH/2))) {
            //na dol od klocka

            if (ball.angle > 0 && ball.angle < 90 ) {
                //angle + 270

                ball.changeAngleAndMove(180 + 2 * (90 - ball.angle));
            } else {
                //angle +90

                ball.changeAngleAndMove(2 * (180 - ball.angle));
            }

        } else if (ball.getY() < block.getY() - (block.HEIGHT/2) && ball.getX() > (block.getX()-(block.WIDTH/2)) && ball.getX() < (block.getX() + (block.WIDTH/2)) ) {

            if (ball.angle > 270 && ball.angle < 360) {
                // angle -270
                ball.changeAngleAndMove(-180 - (2 * ball.angle-270));
            } else {

                //angle -90

                ball.changeAngleAndMove(- (2 * (ball.angle - 180)));
            }
        } else  {
            if (ball.angle > 180 && ball.angle < 360) {
                ball.changeAngleAndMove(-180);
            } else ball.changeAngleAndMove(180);
        }
    }

    public void invalidate(int i) {
//        if(i<0){
//            deck.setX(deck.getX()-SPEED);
//            super.invalidate();
//        }else {
//            deck.setX(deck.getX()+SPEED);
//            super.invalidate();
//        }

        if (i > 0 ) deck.setX(deck.getX()+deck.SPEED);
        else deck.setX(deck.getX() - deck.SPEED);
    }


    public boolean onTouchEvent(MotionEvent event) {

        final float new_x = event.getX();

//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                h = new Handler(Looper.getMainLooper());
//                if (new_x > deck.getX()) {
//                    while (new_x > deck.getX()) {
//                        h.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                invalidate(1);
//                            }
//                        });
//
//                        try {
//                            Thread.sleep(20);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } else if (new_x < deck.getX()) {
//                    while (new_x < deck.getX()) {
//                        h.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                invalidate(-1);
//                            }
//                        });
//
//                        try {
//                            Thread.sleep(20);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//            }
//        });
//        t.start();


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                h = new Handler(Looper.getMainLooper());
                if (new_x > ( Resources.getSystem().getDisplayMetrics().widthPixels/2)) {
                    for (int i = 0; i < 50; i++) {
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                invalidate(1);
                            }
                        }, 4);
                        try {
                            Thread.sleep(4);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                } else {
                    for (int i = 0; i < 50; i++) {
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                invalidate(-1);
                            }
                        }, 4);
                        try {
                            Thread.sleep(4);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread.start();
        return super.onTouchEvent(event);
    }

}
