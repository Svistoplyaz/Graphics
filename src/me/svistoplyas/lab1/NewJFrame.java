package me.svistoplyas.lab1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Alexandr on 24.09.2017.
 */
public class NewJFrame extends JFrame{
    private static double[][] proectionX = {{0,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};

    public static final int f = 4;

    Holst holst;

    JButton xl;
    JButton xr;
    JButton yl;
    JButton yr;
    JButton zl;
    JButton zr;

    ArrayList<Point> startPoints = new ArrayList<>();
    ArrayList<Edge> startEdges = new ArrayList<>();

    NewJFrame(){
        this.setLocation(20,20);
        this.setSize(800,600);

//        this.add(xl = new JButton("Поворот против часовой вокруг Х"));
//        this.add(xr = new JButton("Поворот по часовой вокруг Х"));
//        this.add(yl = new JButton("Поворот против часовой вокруг Y"));
//        this.add(yr = new JButton("Поворот по часовой вокруг Y"));
//        this.add(zl = new JButton("Поворот против часовой вокруг Z"));
//        this.add(zr = new JButton("Поворот по часовой вокруг Z"));
    }

    void init(int len){
        this.add(holst = new Holst(len));

        ortogonal();
        holst.draw = true;
//        holst.paintComponent(this.getGraphics());
        holst.setVisible(true);

        this.setVisible(true);
    }

    void addPoint(Point cur){
        startPoints.add(cur);
    }

    Point getPoint(int num){
        return startPoints.get(num);
    }

    void addEdge(Edge cur){
        startEdges.add(cur);
    }

    void ortogonal(){
        int len = startPoints.size();



        for(int i = 0; i < len; i++){
            holst.points[i] = multiply(startPoints.get(i).mat,proectionX);

        }
    }

    public static double[] multiply(double[] first, double[][] second){
        int m = second.length, n = second[0].length;
        double[] ans = new double[n];

        for(int j = 0; j < n; j++){
            for(int k = 0; k < m; k++)
                ans[j] += first[k]*second[k][j];
        }

        return ans;
    }

    public static double[][] multiply(double[][] first, double[][] second){
        int l = first.length, m = second.length, n = second[0].length;
        double[][] ans = new double[l][n];

        for(int i = 0; i < l; i++){
            for(int j = 0; j < n; j++){
                for(int k = 0; k < m; k++)
                    ans[i][j] += first[i][k]*second[k][j];
            }
        }

        return ans;
    }

    public static double[][] matrixRx(double angle){
        double[][] ans = new double[f][f];

        ans[0][0] = 1;
        ans[1][1] = Math.cos(angle);
        ans[1][2] = Math.sin(angle);
        ans[2][1] = -Math.sin(angle);
        ans[2][2] = Math.cos(angle);
        ans[3][3] = 1;

        return ans;
    }

    public static double[][] matrixRy(double angle){
        double[][] ans = new double[f][f];

        ans[0][0] = Math.cos(angle);
        ans[0][2] = -Math.sin(angle);
        ans[1][1] = 1;
        ans[2][0] = Math.sin(angle);
        ans[2][2] = Math.cos(angle);
        ans[3][3] = 1;

        return ans;
    }

    public static double[][] matrixRz(double angle){
        double[][] ans = new double[f][f];

        ans[0][0] = Math.cos(angle);
        ans[0][1] = Math.sin(angle);
        ans[1][0] = -Math.sin(angle);
        ans[1][1] = Math.cos(angle);
        ans[2][2] = 1;
        ans[3][3] = 1;

        return ans;
    }

    public static double[][] matrixMove(double a, double b, double c){
        double[][] ans = new double[f][f];

        for(int i = 0; i < f; i++){
            ans[i][i] = 1;
        }

        ans[3][0] = a;
        ans[3][1] = b;
        ans[3][2] = c;

        return ans;
    }
}
