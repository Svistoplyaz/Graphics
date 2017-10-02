package me.svistoplyas.lab1;

/**
 * Created by Alexandr on 24.09.2017.
 */
public class Point {
//    double x;
//    double y;
//    double z;
//    double v;
    public double[] mat = new double[4];

    Point(int _x, int _y, int _z){
        mat[0] = _x;
        mat[1] = _y;
        mat[2] = _z;
        mat[3] = 100;
    }
}
