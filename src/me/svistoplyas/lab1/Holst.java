package me.svistoplyas.lab1;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexandr on 02.10.2017.
 */
public class Holst extends JPanel{
    public double[][] points;
    public boolean draw = false;

    Holst(int len){
        points = new double[len][];
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(draw) {
            int len = points.length;
//        Graphics2D graphics2D = (Graphics2D)g;
//        graphics2D.
            for (int i = 0; i < len; i++) {
                g.fillOval((int) (points[i][1]*points[i][3])+200, (int) (points[i][2]*points[i][3])+200, 10, 10);
            }
        }

//        super.paintComponent(g);
    }
}
