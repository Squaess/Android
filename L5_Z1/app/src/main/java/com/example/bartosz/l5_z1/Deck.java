package com.example.bartosz.l5_z1;

import android.graphics.Paint;

import java.util.Random;

/**
 * Created by aedd on 5/13/17
 */

class Deck {

    int WIDTH = 400;
    int HEIGHT = 80;
    private float x;
    private float y;
    Paint paint;

    Deck(float x, float y) {
        this.x = x;
        this.y = y-(HEIGHT/2);
        paint = new Paint();
    }

    void setX(float x) {
        this.x = x;
    }

    float getX() {
        return x;
    }

    void setY(float y) {
        this.y = y;
    }

    float getY() {
        return y;
    }

    void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    void setRandPaint(){
        Random r = new Random();
        int a = r.nextInt(256);
        int er  = r.nextInt(256);
        int g = r.nextInt(256);
        int b = r.nextInt(256);

        this.paint.setARGB(a, er, g, b);
    }

    Paint getPaint() {
        return this.paint;
    }

    boolean contains(double x, double y) {
        return x < (this.x + (WIDTH / 2)) && x > (this.x - (WIDTH / 2)) && y > (this.y - (HEIGHT / 2)) && y < (this.y + (HEIGHT / 2));
    }
}
