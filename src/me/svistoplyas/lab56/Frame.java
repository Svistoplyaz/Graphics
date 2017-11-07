package me.svistoplyas.lab56;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Alexander on 10.10.2017.
 */
public class Frame extends JFrame{
    Painting pnt = new Painting();
    Histogram hist = new Histogram(pnt);

    Frame(){
        this.setLayout(new FlowLayout());
        this.add(pnt);
        this.add(hist);
        this.setLocation(20,20);
        this.pack();

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if(code == KeyEvent.VK_Q){
                    pnt.addBrightnessFilter(5);
                }else if(code == KeyEvent.VK_W){
                    pnt.addBinaryFilter();
                }else if(code == KeyEvent.VK_E){
                    pnt.addBrightnessFilter(-5);
                }else if(code == KeyEvent.VK_A){
                    pnt.addContrastFilter(5);
                }else if(code == KeyEvent.VK_S){
                    pnt.dropCoordinates();
                }else if(code == KeyEvent.VK_D){
                    pnt.addContrastFilter(-5);
                }else if(code == KeyEvent.VK_Z){
                    pnt.addBlackAndWhiteFilter();
                }else if(code == KeyEvent.VK_X){
                    pnt.addNegativeFilter();
                }else if(code == KeyEvent.VK_COMMA){
                    pnt.modifyBinaryFilter(-15);
                }else if(code == KeyEvent.VK_PERIOD){
                    pnt.modifyBinaryFilter(15);
                }else if(code == KeyEvent.VK_R){
                    pnt.addNoiseFilter();
                }else if(code == KeyEvent.VK_F){
                    pnt.addEvenDistribution();
                }else if(code == KeyEvent.VK_V){
                    pnt.addGaussDistribution();
                }else if(code == KeyEvent.VK_C){
                    pnt.addSharpness();
                }else if(code == KeyEvent.VK_T){
                    pnt.addBorder();
                }else if(code == KeyEvent.VK_G){
                    pnt.addWaves();
                }else if(code == KeyEvent.VK_B){
                    pnt.addGlass();
                }

                paint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });



//        pnt.setVisible(true);
//        pnt.repaint();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void paint(){
        pnt.repaint();
        hist.repaint();
    }

}
