package me.svistoplyas.lab2;

import javafx.scene.image.PixelWriter;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Alexander on 10.10.2017.
 */
public class Painting extends JPanel{
    double[][] zBuf;
    int[][] framBuf;
    int n;
    int m;
    ArrayList<Color> colors;
//    BufferedImage im = new BufferedImage();

    Painting(int _n , int _m){
        n = _n;
        m = _m;

        clearBoard();
    }

    public void clearBoard(){
        zBuf = new double[n][m];
        for(int i = 0 ; i < n; i++){
            for(int j = 0; j < m; j++)
                zBuf[i][j] = Integer.MIN_VALUE;
        }

        framBuf  = new int[n][m];
        for(int i = 0 ; i < n; i++){
            for(int j = 0; j < m; j++)
                framBuf[i][j] = 0;
        }

        colors = new ArrayList<>();
        colors.add(Color.WHITE);
    }

    public void addFigure(int[] equation, int[][] edges, int firY, int lastY, int[][] nodes){
        Random r = new Random();

        Color col = new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
        int cur = colors.size();
        colors.add(col);

        int begx, endx;

        double z;

        for(int y = firY; y < lastY; y++){
            begx = Integer.MAX_VALUE;
            endx = Integer.MIN_VALUE;

            for(int i = 0; i < 3; i++)
               if(y<=nodes[i][1] && y>=nodes[(i+1)%3][1] || y>=nodes[i][1] && y<=nodes[(i+1)%3][1]) {
                   begx = Math.min(begx, solveWithY(edges[i], y));
                   endx = Math.max(endx, solveWithY(edges[i], y));
               }


            z = -(equation[0]*begx + equation[1]*y + equation[3])*1.0/equation[2];

            for(int x = begx; x <=endx; x++){
                z -= equation[0]*1.0/equation[2];
                if(z > zBuf[x][y]) {
                    framBuf[x][y] = cur;
                    zBuf[x][y] = z;
                }
            }

        }
    }

    private int solveWithY(int[] edge, int y){
        return (-edge[1]*y-edge[2])/edge[0];
    }

    @Override
    public void paint(Graphics g){
//        Graphics2D g2 = (Graphics2D)g;
//        RenderingHints rh = new RenderingHints(
//                RenderingHints.KEY_TEXT_ANTIALIASING,
//                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//        g2.setRenderingHints(rh);

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                g.setColor(colors.get(framBuf[i][j]));
                g.drawLine(i,j,i,j);
            }
        }
    }



}
