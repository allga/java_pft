package ru.stqa.pft.sandbox1;

import static java.lang.Math.*;

/**
 * Created by Olga on 22.02.2016.
 */
public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point p1){
        return((Math.sqrt(pow((this.x - p1.x), 2) + pow((this.y - p1.y), 2))));
    }


}
