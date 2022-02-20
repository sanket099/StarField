package com.sankets.processing;

import java.util.ArrayList;

import processing.core.PApplet;

public class AndroidParticles extends PApplet {

    ArrayList<Particle> particles ;

    public void settings() {
        fullScreen();

    }

    public void setup() {
        background(255);
        particles = new ArrayList<Particle>();
    }

    public void mouseReleased() {
        Particle part = new Particle(mouseX - width/2, mouseY - height/2);
        particles.add(part);
    }

    public void draw() {
        background(255);
        pushMatrix();
        translate(width/2,height/2);
        ellipse(0,0,50,50);
        if(particles.size()>1){
            for (int i = 0; i < particles.size(); i++) {
                particles.get(i).update();
                particles.get(i).show();
            }
        }
        popMatrix();
    }




    public class Particle  {

        public int x;
        public int y;
        public ArrayList<int[]> history = new ArrayList<int[]>();

        public Particle (int x, int y) {
            this.x = x;
            this.y = y;

            this.history.add(new int[] {x, y});
            println(this.x);
        }



        public void update() {
            this.x += random(-10, 10);
            this.y += random(-10, 10);
            this.history.add(new int[] {this.x, this.y});

            if (this.history.size() > 100) {
                this.history.remove(0);
            }


        }

        public void show() {

            stroke(0);
            fill(0, 150);
            ellipse(this.x, this.y, 12, 12);
            noFill();

            beginShape();

            for (int i = 0; i < this.history.size(); i++) {
                int[] pos = this.history.get(i);
                vertex(pos[0], pos[1]);
            }

            endShape();
        }
    }
}