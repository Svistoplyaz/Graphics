package me.svistoplyas.lab1;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

/**
 * Created by Alexandr on 24.09.2017.
 */
public class NewJFrame extends JFrame{
    private static double sqr2 = sqrt(1.0/2);
    private static double sqr3 = sqrt(1.0/3);
    private static double[][] proectionX = {{0,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};
    private static double[][] proectionY = {{1,0,0,0},{0,0,0,0},{0,0,1,0},{0,0,0,1}};
    private static double[][] proectionZ = {{1,0,0,0},{0,1,0,0},{0,0,0,0},{0,0,0,1}};
    private static double[][] isometric = {{sqr2, sqr2*sqr3,0,0},
            {0, sqr2,0,0},{sqr2,-sqr2*sqr2,0,0},{0,0,0,1}};
    private static double[][] persperctive = {{1,0,0,0},{0,1,0,0},{0,0,0,-0.5},{0,0,0,1}};

    public static final int f = 4;

    Holst holst;

    ArrayList<Point> startPoints = new ArrayList<>();

    NewJFrame(){
        this.setLocation(20,20);
        this.setSize(800,600);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if(code == KeyEvent.VK_Q){
                    for(Point cur: startPoints){
                        cur.mat = multiply(cur.mat, matrixRx(-2*Math.PI/120));
                    }
                }else if(code == KeyEvent.VK_W){
                    for(Point cur: startPoints){
                        cur.mat = multiply(cur.mat, matrixRx(2*Math.PI/120));
                    }
                }else if(code == KeyEvent.VK_A){
                    for(Point cur: startPoints){
                        cur.mat = multiply(cur.mat, matrixRy(-2*Math.PI/120));
                    }
                }else if(code == KeyEvent.VK_S){
                    for(Point cur: startPoints){
                        cur.mat = multiply(cur.mat, matrixRy(2*Math.PI/120));
                    }
                }else if(code == KeyEvent.VK_Z){
                    for(Point cur: startPoints){
                        cur.mat = multiply(cur.mat, matrixRz(-2*Math.PI/120));
                    }
                }else if(code == KeyEvent.VK_X){
                    for(Point cur: startPoints){
                        cur.mat = multiply(cur.mat, matrixRz(2*Math.PI/120));
                    }
                }else if(code == KeyEvent.VK_UP){
                    for(Point cur: startPoints){
                        cur.mat = multiply(cur.mat, matrixMove(0.001, 0, 0));
                    }
                }else if(code == KeyEvent.VK_DOWN){
                    for(Point cur: startPoints){
                        cur.mat = multiply(cur.mat, matrixMove(-0.001, 0, 0));
                    }
                }else if(code == KeyEvent.VK_LEFT){
                    for(Point cur: startPoints){
                        cur.mat = multiply(cur.mat, matrixMove(0, 0.001, 0));
                    }
                }else if(code == KeyEvent.VK_RIGHT){
                    for(Point cur: startPoints){
                        cur.mat = multiply(cur.mat, matrixMove(0, -0.001, 0));
                    }
                }else if(code == KeyEvent.VK_E){
                    for(Point cur: startPoints){
                        cur.mat = multiply(cur.mat, matrixMove(0, 0, 0.001));
                    }
                }else if(code == KeyEvent.VK_D){
                    for(Point cur: startPoints){
                        cur.mat = multiply(cur.mat, matrixMove(0, 0, -0.001));
                    }
                }else if(code == KeyEvent.VK_0){
                    holst.proection = 0;
                }else if(code == KeyEvent.VK_1){
                    holst.proection = 1;
                }else if(code == KeyEvent.VK_2){
                    holst.proection = 2;
                }else if(code == KeyEvent.VK_3){
                    holst.proection = 3;
                }else if(code == KeyEvent.VK_4){
                    holst.proection = 4;
                }

                paint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    void init(int len, int[][] edges){
        this.add(holst = new Holst(len,edges));

        projection();
        holst.draw = true;
//        holst.paintComponent(this.getGraphics());
        holst.setVisible(true);

        this.setVisible(true);
    }

    private void paint(){
        projection();

        this.repaint();
    }

    private void projection(){
        switch (holst.proection){
            case 0:
                subortogonal(proectionX, 1, 2);
                break;

            case 1:
                subortogonal(proectionY, 0, 2);
                break;

            case 2:
                subortogonal(proectionZ, 0, 1);
                break;

            case 3:
                int len = startPoints.size();

                double[][] axes = new double[3][4];

                double[] tmp;
                for(int i = 0; i < len; i++){
                    tmp = multiply(startPoints.get(i).mat,isometric);

                    holst.points[i][0] = tmp[0]*tmp[3];
                    holst.points[i][1] = tmp[1]*tmp[3];
                }

                axes[0][0] = 1000;
                axes[0][1] = 0;
                axes[0][2] = 0;
                axes[0][3] = 1;

                axes[1][0] = 0;
                axes[1][1] = -1000;
                axes[1][2] = 0;
                axes[1][3] = 1;

                axes[2][0] = 0;
                axes[2][1] = 0;
                axes[2][2] = -1000;
                axes[2][3] = 1;

                for(int i = 0; i < 3; i++){
                    tmp = multiply(axes[i],isometric);

                    holst.axes[i][0] = tmp[0]*tmp[3];
                    holst.axes[i][1] = tmp[1]*tmp[3];
                }
                break;

            case 4:
                int len1 = startPoints.size();

                double[][] axes1 = new double[3][4];

                double[] tmp1;
                for(int i = 0; i < len1; i++){
                    tmp1 = multiply(startPoints.get(i).mat,persperctive);

                    holst.points[i][0] = tmp1[0]*tmp1[3];
                    holst.points[i][1] = tmp1[1]*tmp1[3];
                }

                axes1[0][0] = 1000;
                axes1[0][1] = 0;
                axes1[0][2] = 0;
                axes1[0][3] = 1;

                axes1[1][0] = 0;
                axes1[1][1] = -1000;
                axes1[1][2] = 0;
                axes1[1][3] = 1;

                axes1[2][0] = 0;
                axes1[2][1] = 0;
                axes1[2][2] = -1000;
                axes1[2][3] = 1;

                for(int i = 0; i < 3; i++){
                    tmp1 = multiply(axes1[i],persperctive);

                    holst.axes[i][0] = tmp1[0]*tmp1[3];
                    holst.axes[i][1] = tmp1[1]*tmp1[3];
                }
                break;
        }
    }

    private void subortogonal(double[][] proe, int x, int y){
        int len = startPoints.size();

        double[] tmp;
        for(int i = 0; i < len; i++){
            tmp = multiply(startPoints.get(i).mat,proe);

            holst.points[i][0] = tmp[x]*tmp[3];
            holst.points[i][1] = tmp[y]*tmp[3];
        }
    }

    void addPoint(Point cur){
        startPoints.add(cur);
    }

    Point getPoint(int num){
        return startPoints.get(num);
    }

//    void addEdge(Edge cur){
//        startEdges.add(cur);
//    }

    private static double[] multiply(double[] first, double[][] second){
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
