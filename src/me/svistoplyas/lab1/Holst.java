package me.svistoplyas.lab1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Alexandr on 02.10.2017.
 */
public class Holst extends JPanel{
    public double[][] points;
    public int[][] edges;
    public boolean draw = false;
    public int proection = 4;
    public double[][] axes;

    Holst(int len, int[][] _edges){
        points = new double[len][2];
        axes = new double[3][2];
        edges = _edges;
    }

    @Override
    public void paint(Graphics g){
        if(!draw) {
            return;
        }

//        for (double[] cur : points) {
//            g.fillOval((int) cur[0] + 400, (int) cur[1] + 300, 6, 6);
//        }

        try {
            g.setColor(Color.MAGENTA);
            g.drawLine(20, 20, 120, 20);
            g.drawImage(ImageIO.read(new File("x.png")), 140, 15, 10, 10, null);
            g.setColor(Color.BLUE);
            g.drawLine(20, 40, 120, 40);
            g.drawImage(ImageIO.read(new File("y.png")), 140, 35, 10, 10, null);
            g.setColor(Color.RED);
            g.drawLine(20, 60, 120, 60);
            g.drawImage(ImageIO.read(new File("z.png")), 140, 55, 10, 10, null);
        }catch (Exception e){

        }


        switch (proection){
            case 0:
                g.setColor(Color.BLUE);
                g.drawLine(400,300,10000,300);
                g.setColor(Color.RED);
                g.drawLine(400,300,400,-1);
                break;

            case 1:
                g.setColor(Color.MAGENTA);
                g.drawLine(400,300,10000,300);
                g.setColor(Color.RED);
                g.drawLine(400,300,400,-1);
                break;

            case 2:
                g.setColor(Color.MAGENTA);
                g.drawLine(400,300,10000,300);
                g.setColor(Color.BLUE);
                g.drawLine(400,300,400,-1);
                break;

            case 3:
                g.setColor(Color.MAGENTA);
                g.drawLine(400,300,(int)axes[0][0]+400,(int)axes[0][1]+300);
                g.setColor(Color.BLUE);
                g.drawLine(400,300,(int)axes[1][0]+400,(int)axes[1][1]+300);
                g.setColor(Color.RED);
                g.drawLine(400,300,(int)axes[2][0]+400,(int)axes[2][1]+300);
                break;

            case 4:
                g.setColor(Color.MAGENTA);
                g.drawLine(400,300,(int)axes[0][0]+400,(int)axes[0][1]+300);
                g.setColor(Color.BLUE);
                g.drawLine(400,300,(int)axes[1][0]+400,(int)axes[1][1]+300);
                g.setColor(Color.RED);
                g.drawLine(400,300,(int)axes[2][0]+400,(int)axes[2][1]+300);
                break;
        }

        g.setColor(Color.BLACK);
        for(int[] cur : edges){
            g.drawLine((int) (points[cur[0]][0]*100) + 400, (int) (points[cur[0]][1]*100) + 300,(int) (points[cur[1]][0]*100) + 400, (int) (points[cur[1]][1]*100) + 300);
        }

    }
}
