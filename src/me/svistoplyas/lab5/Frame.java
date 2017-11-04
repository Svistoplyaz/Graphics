package me.svistoplyas.lab5;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * Created by Alexander on 10.10.2017.
 */
public class Frame extends JFrame{
    Painting pnt = new Painting();

    Frame(){
        this.add(pnt);
        this.setLocation(20,20);
        this.pack();

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if(code == KeyEvent.VK_A){
                    pnt.addBrightnessFilter(5);
                }else if(code == KeyEvent.VK_D){
                    pnt.addBrightnessFilter(-5);
                }else if(code == KeyEvent.VK_Q){
                    pnt.addContrastFilter(5);
                }else if(code == KeyEvent.VK_E){
                    pnt.addContrastFilter(-5);
                }else if(code == KeyEvent.VK_S){
                    pnt.dropCoordinates();
                }

                paint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });



        pnt.setVisible(true);
        pnt.repaint();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void paint(){
        pnt.repaint();
    }
}
