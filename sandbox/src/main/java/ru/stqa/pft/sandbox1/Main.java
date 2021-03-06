package ru.stqa.pft.sandbox1;

import static java.lang.Math.pow;
import static ru.stqa.pft.sandbox1.Point.*;

/**
 * Created by Olga on 22.02.2016.
 */
public class Main {
    public static void main(String[] args) {

        Point p1 = new Point(12, -13);
        Point p2 = new Point(-5, 7);

        System.out.println("Точка A[" + p1.x + ", " + p1.y + ")");
        System.out.println("Точка B[" + p2.x + ", " + p2.y + ")");

        double d = distance(p1, p2);
        System.out.printf("Расстояние AB = %.2g\n", d);

        Point p3 = new Point(34, 11);
        Point p4 = new Point(21, -44);

        System.out.println("Точка C[" + p3.x + ", " + p3.y + ")");
        System.out.println("Точка D[" + p4.x + ", " + p4.y + ")");
        System.out.printf("Расстояние CD = %.2g\n", p3.distance(p4));


    }

    public static double distance(Point p1, Point p2) {
        return ((Math.sqrt(pow((p2.x - p1.x), 2) + pow((p2.y - p1.y), 2))));
    }


}