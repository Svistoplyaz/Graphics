package me.svistoplyas.lab2;

import javafx.scene.image.PixelWriter;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alexander on 10.10.2017.
 */
public class Painting extends JPanel{
    int[][] zBuf;
    int[][] framBuf;
    int n;
    int m;
    HashMap<Integer,Color> colors;
//    BufferedImage im = new BufferedImage();

    Painting(int _n , int _m){
        n = _n;
        m = _m;
        zBuf = new int[n][m];
        for(int i = 0 ; i < n; i++){
            for(int j = 0; j < m; j++)
                zBuf[i][j] = Integer.MIN_VALUE;
        }

        framBuf  = new int[n][m];
        for(int i = 0 ; i < n; i++){
            for(int j = 0; j < m; j++)
                framBuf[i][j] = 0;
        }

        colors = new HashMap<>();
        colors.put(0,Color.WHITE);
    }

    @Override
    public void paint(Graphics g){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                g.setColor(colors.get(framBuf[i][j]));
                g.drawLine(i,j,i,j);
            }
        }
    }

}
