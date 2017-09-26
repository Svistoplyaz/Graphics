package me.svistoplyas.lab1;

import javax.swing.*;
import java.io.File;
import java.util.Scanner;

/**
 * Created by Alexandr on 24.09.2017.
 */
public class Main {
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
}
