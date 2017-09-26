package me.svistoplyas.lab1;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Alexandr on 24.09.2017.
 */
public class NewJFrame extends JFrame{
    JPanel holst;

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

        this.add(holst = new JPanel());
        this.add(xl = new JButton("Поворот против часовой вокруг Х"));
        this.add(xr = new JButton("Поворот по часовой вокруг Х"));
        this.add(yl = new JButton("Поворот против часовой вокруг Y"));
        this.add(yr = new JButton("Поворот по часовой вокруг Y"));
        this.add(zl = new JButton("Поворот против часовой вокруг Z"));
        this.add(zr = new JButton("Поворот по часовой вокруг Z"));
    }

    void init(){
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


}
