package me.svistoplyas.lab1;

import javax.swing.*;
import java.io.File;
import java.util.Scanner;

/**
 * Created by Alexandr on 24.09.2017.
 */
public class Main {
    public static final int f = 4;

    public static void main(String[] args) throws Exception{
        NewJFrame frame = new NewJFrame();

        Scanner in = new Scanner(new File("in.in"));

        int n = in.nextInt();
        for(int i = 0; i < n; i++){
            frame.addPoint(new Point(in.nextInt(),in.nextInt(),in.nextInt()));
        }

        int m = in.nextInt();
        for(int i = 0; i < m; i++){
            frame.addEdge(new Edge(frame.getPoint(in.nextInt()),frame.getPoint(in.nextInt())));
        }

        frame.init();
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

//    public static double[][] matrixMirrorZ(){
//        double[][] ans = new double[f][f];
//
//        ans[0][0] = 1;
//        ans[1][1] = 1;
//        ans[2][2] = -1;
//        ans[3][3] = 1;
//
//        return ans;
//    }
//
//    public static double[][] matrixMirrorX(){
//        double[][] ans = new double[f][f];
//
//        ans[0][0] = -1;
//        ans[1][1] = 1;
//        ans[2][2] = 1;
//        ans[3][3] = 1;
//
//        return ans;
//    }
//
//    public static double[][] matrixMirrorY(){
//        double[][] ans = new double[f][f];
//
//        ans[0][0] = 1;
//        ans[1][1] = -1;
//        ans[2][2] = 1;
//        ans[3][3] = 1;
//
//        return ans;
//    }
}
