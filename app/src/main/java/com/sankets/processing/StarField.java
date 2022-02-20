package com.sankets.processing;


import processing.core.PApplet;

public class StarField extends PApplet {

    Star[] stars = new Star[800];

    // I create a variable "speed", it'll be useful to control the speed of stars.
    float speed;

    int width = 600;
    int height = 600;

    StarField(int wid, int hei){
        width = wid;
        height = hei;
    }

    public void settings() {

        size(width, height);

    }
    public void setup() {
        //size(600, 600);
        // I fill the array with a for loop;
        // running 800 times, it creates a new star using the Star() class.
        for (int i = 0; i < stars.length; i++)
        {
            stars[i] = new Star();
            System.out.println(stars[i]);
        }
    }

    public void draw() {
        // i link the value of the speed variable to the mouse position.
        speed = map(mouseX, 0, width, 0, 50);

        background(0);
        // I shift the entire composition,
        // moving its center from the top left corner to the center of the canvas.
        translate(width/2, height/2);
        // I draw each star, running the "update" method to update its position and
        // the "show" method to show it on the canvas.
        for (int i = 0; i < stars.length; i++) {
            stars[i].update();
            stars[i].show();
        }
    }

    public class Star  {
        // I create variables to specify the x and y of each star.
        float x;
        float y;
        // I create "z", a variable I'll use in a formula to modify the stars position.
        float z;

        // I create an other variable to store the previous value of the z variable.
        // (the value of the z variable at the previous frame).
        float pz;

        public Star() {
            // I place values in the variables
            x = random(-width/2, width/2);
            // note: height and width are the same: the canvas is a square.
            y = random(-height/2, height/2);
            // note: the z value can't exceed the width/2 (and height/2) value,
            // beacuse I'll use "z" as divisor of the "x" and "y",
            // whose values are also between "0" and "width/2".
            z = random(width/2);
            // I set the previous position of "z" in the same position of "z",
            // which it's like to say that the stars are not moving during the first frame.
            pz = z;
        }

        public void update() {
            // In the formula to set the new stars coordinates
            // I'll divide a value for the "z" value and the outcome will be
            // the new x-coordinate and y-coordinate of the star.
            // Which means if I decrease the value of "z" (which is a divisor),
            // the outcome will be bigger.
            // Wich means the more the speed value is bigger, the more the "z" decrease,
            // and the more the x and y coordinates increase.
            // Note: the "z" value is the first value I updated for the new frame.
            z = z - 10;
            // when the "z" value equals to 1, I'm sure the star have passed the
            // borders of the canvas( probably it's already far away from the borders),
            // so i can place it on more time in the canvas, with new x, y and z values.
            // Note: in this way I also avoid a potential division by 0.
            if (z < 1) {
                z = width/2;
                x = random(-width/2, width/2);
                y = random(-height/2, height/2);
                pz = z;
            }
        }

        public void show() {
            fill(255);
            noStroke();

            // with theese "map", I get the new star positions
            // the division x / z get a number between 0 and a very high number,
            // we map this number (proportionally to a range of 0 - 1), inside a range of 0 - width/2.
            // In this way we are sure the new coordinates "sx" and "sy" move faster at each frame
            // and which they finish their travel outside of the canvas (they finish when "z" is less than a).

            float sx = map(x / z, 0, 1, 0, width/2);
            float sy = map(y / z, 0, 1, 0, height/2);;

            // I use the z value to increase the star size between a range from 0 to 16.
            float r = map(z, 0, width/2, 16, 0);
            ellipse(sx, sy, r, r);

            // Here i use the "pz" valute to get the previous position of the stars,
            // so I can draw a line from the previous position to the new (current) one.
            float px = map(x / pz, 0, 1, 0, width/2);
            float py = map(y / pz, 0, 1, 0, height/2);

            // Placing here this line of code, I'm sure the "pz" value are updated after the
            // coordinates are already calculated; in this way the "pz" value is always equals
            // to the "z" value of the previous frame.
            pz = z;

            stroke(255);
            line(px, py, sx, sy);

        }
    }
}
