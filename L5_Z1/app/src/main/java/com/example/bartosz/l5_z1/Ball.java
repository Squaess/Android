package com.example.bartosz.l5_z1;

import android.graphics.Paint;
import android.util.Log;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by aedd on 5/13/17
 */

class Ball {

    private Paint paint;
    private float x;
    private float y;
    final static float r = 50;
    private final static float SPEED = 3;
    double angle;
    private double move_x;
    private double move_y;

    Ball (float x, float y) {
        paint = new Paint();
        this.x = x;
        this.y = y;
        this.angle = 90;
        this.move_x = 0;
        this.move_y = SPEED;
    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    void setY(float y) {
        this.y = y;
    }

    void setX(float x){ this.x = x;}

    void moveBall() {
        // check() collision and bounds
        y-=move_y;
        x+= move_x;
    }

    void changeAngleAndMove(double difference) {
        angle = angle + difference;

        move_y = Math.sin(Math.toRadians(angle)) * SPEED;
        move_x = Math.cos(Math.toRadians(angle)) * SPEED;
    }

    void change_direction(float obj_x, float obj_y) {

        float diff_x = Math.abs(x - obj_x);
        float diff_y = Math.abs(y - obj_y);

        float tang = diff_y / diff_x;

        angle = Math.toDegrees(Math.atan(tang));

        move_y = Math.sin(Math.toRadians(angle)) * SPEED;
        move_x = Math.cos(Math.toRadians(angle)) * SPEED;

        if (x < obj_x){
            double diff = 2 * (90 - angle);
            angle +=  diff;


            move_y = Math.sin(Math.toRadians(angle)) * SPEED;
            move_x = Math.cos(Math.toRadians(angle)) * SPEED;
        }

        // co z zerami jeśli diff_x bedzie zerem to sie wyjebie

        //cztery mozliwosci odbicia
        // gora lew
        // lewo dol
        // gora prawo
        // dol prawo


//        // gora lewo
//        if (angle > 90 && angle < 180) {
//            angle-=90;
//
//        } else if (angle > 180 && angle < 270) {            //lewo dol
//            angle += 90;
//        } else if (angle > 270 && angle < 360) {
////
//        }
//
//        move_y = Math.sin(Math.toRadians(angle)) * SPEED;
//        move_x = Math.cos(Math.toRadians(angle)) * SPEED;
//

        // ------------------------------------
//        if(diff_x == 0) {
//            move_x = 0;
//            if (angle <= 180) {
//                move_y = -SPEED;
//                angle = 270;
//            } else {
//                move_y = SPEED;
//                angle = 90;
//            }
//        } else if (diff_y == 0) {
//            move_y = 0;
//            if(angle > 90 && angle < 270) {
//                move_x = SPEED;
//                angle = 0;
//            } else {
//                move_x = -SPEED;
//                angle = 180;
//            }
//        } else {
//
//            float tang = diff_y / diff_x;
//
//            angle = Math.toDegrees(Math.atan(tang));
//
//            move_y = Math.sin(Math.toRadians(angle)) * SPEED;
//            move_x = Math.cos(Math.toRadians(angle)) * SPEED;
//        }

        Log.d("angle", Double.toString(angle));
    }

    Paint getPaint() {
        return this.paint;
    }

    ArrayList<Point> getArray(){
        ArrayList<Point> ret = new ArrayList<>();
        ret.add(new Point(x, y-r));
        ret.add(new Point(x-r, y));
        ret.add(new Point(x, y+r));
        ret.add(new Point(x+r, y));
        float n_x = x-3;
        while( n_x > x-r) {
            double n_y =Math.sqrt( Math.pow(r,2) - Math.pow(x - n_x,2) );
            ret.add(new Point(n_x, y - n_y));
            ret.add(new Point(n_x, y + n_y));
            n_x -= 3;
        }
        n_x = x+3;
        while(n_x < x+r) {
            double n_y = Math.sqrt( Math.pow(r, 2) - Math.pow(n_x - x, 2) );
            ret.add(new Point(n_x, y - n_y));
            ret.add(new Point(n_x, y + n_y));
            n_x += 3;
        }

        return ret;
    }

    void setRandPaint(){
        Random r = new Random();
        int a = r.nextInt(256);
        int er  = r.nextInt(256);
        int g = r.nextInt(256);
        int b = r.nextInt(256);

        this.paint.setARGB(a, er, g, b);
    }
}
