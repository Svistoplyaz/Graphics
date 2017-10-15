package me.svistoplyas.lab2;

import me.svistoplyas.lab1.Point;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import static me.svistoplyas.lab1.NewJFrame.matrixRx;

/**
 * Created by Alexander on 10.10.2017.
 */
public class Frame extends JFrame{
    Painting pnt;

    Frame(){
        pnt = new Painting();
        this.setLocation(20,20);
        this.setSize(800,600);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if(code == KeyEvent.VK_A){

                }else if(code == KeyEvent.VK_D){

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    public void add(){
        Random r = new Random();

        int[][] xy = new int[4][2];
        xy[0][0] = r.nextInt()%600;
        xy[0][1] = r.nextInt()%500;

        xy[1][0] = xy[0][0] + r.nextInt()%200;
        xy[1][1] = xy[0][1] +
    }

    public void clear(){

    }
}
