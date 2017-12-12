package me.svistoplyas.lab2;

import me.svistoplyas.lab1.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import static me.svistoplyas.lab1.NewJFrame.matrixRx;

/**
 * Created by Alexander on 10.10.2017.
 */
public class Frame extends JFrame{
    Painting pnt;

    Frame(){
        this.add(pnt = new Painting(800,600));
        this.setLocation(20,20);
        this.setSize(800,600);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if(code == KeyEvent.VK_A){
                    addFigure();
                }else if(code == KeyEvent.VK_D){
                    clear();
                }
                paint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        pnt.setVisible(true);
        pnt.repaint();



        this.setVisible(true);
    }

    public void addFigure(){
        Random r = new Random();

        int[][] xyz = new int[3][3];
        for(int i = 0; i < 3; i++){
            xyz[i][0] = r.nextInt(800);
            xyz[i][1] = r.nextInt(600);
            xyz[i][2] = r.nextInt(600);
        }
//        xyz[1][0] = 604;
//        xyz[1][1] = 273;
//        xyz[1][2] = 431;
//        xyz[0][0] = 669;
//        xyz[0][1] = 175;
//        xyz[0][2] = 345;
//        xyz[2][0] = 669;
//        xyz[2][1] = 348;
//        xyz[2][2] = 377;

        int[][] vectors = new int[2][3];
        for(int i = 0; i < 2; i++) {
            vectors[i][0] = xyz[i+1][0] - xyz[0][0];
            vectors[i][1] = xyz[i+1][1] - xyz[0][1];
            vectors[i][2] = xyz[i+1][2] - xyz[0][2];
        }

        //Уравнение плоскости
        int[] equation = new int[4];
        equation[0] = vectors[0][1]*vectors[1][2] - vectors[0][2]*vectors[1][1];
        equation[1] = vectors[0][2]*vectors[1][0] - vectors[0][0]*vectors[1][2];
        equation[2] = vectors[0][0]*vectors[1][1] - vectors[0][1]*vectors[1][0];
        equation[3] = -xyz[0][0]*equation[0] - xyz[0][1]*equation[1] - xyz[0][2]*equation[2];

        //Нижний и верхний y
        int firstY = Math.min(xyz[0][1],Math.min(xyz[1][1],xyz[2][1]));
        int lastY = Math.max(xyz[0][1],Math.max(xyz[1][1],xyz[2][1]));

        //Уравнения ребёр
        int[][] edges = new int[3][3];
        for(int i = 0; i < 3; i++) {
            edges[i][0] = xyz[(i+1)%3][1] - xyz[i][1]; //y1-y0
            edges[i][1] = -(xyz[(i+1)%3][0] - xyz[i][0]); //x0-x1
            edges[i][2] = -xyz[i][0] * (xyz[(i+1)%3][1] - xyz[i][1]) + xyz[i][1] * (xyz[(i+1)%3][0] - xyz[i][0]);
        }

        //Защита от деления на ноль
        for(int i = 0; i < 3; i++)
            if(edges[i][0] == 0) {
                addFigure();
                return;
            }

        pnt.addFigure(equation, edges, firstY, lastY, xyz);
    }

    public void clear(){
        pnt.clearBoard();
    }

    private void paint(){
        this.repaint();
    }
}
