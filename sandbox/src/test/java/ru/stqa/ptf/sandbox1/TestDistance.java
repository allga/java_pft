package ru.stqa.ptf.sandbox1;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.sandbox1.Main;
import ru.stqa.pft.sandbox1.Point;

/**
 * Created by Olga on 26.02.2016.
 */
public class TestDistance {

    @Test
    public void testStaticDistance() {
        Point p1 = new Point(-15, 65);
        Point p2 = new Point(33, -44);
        double d = Math.round(Main.distance(p1, p2));

        Assert.assertEquals(d, 119.0);
    }

    @Test
    public void testPointDistance() {
        Point p3 = new Point(-15, 65);
        Point p4 = new Point(33, -44);

        Assert.assertEquals(p3.distance(p4), 119.0); // тест упадет, так как фактический результат и ожидаемый не округлены одинаково
    }

    @Test
    public void testNullDistance() {
        Point p5 = new Point(0, 0);
        Point p6 = new Point(0, 0);

        Assert.assertEquals(p5.distance(p6), 0.0);
    }



}
