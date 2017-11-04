package me.svistoplyas.lab5;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Alexander on 02.11.2017.
 */
public class Histogram extends JPanel{
    int[] hist = new int[256];
    Painting paint;

    Histogram(Painting pnt){
        paint = pnt;
    }

    @Override
    public void paint(Graphics g){

    }
}
