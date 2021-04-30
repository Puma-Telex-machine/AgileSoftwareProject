import model.grid.Grid;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GridTest {

    Grid<Character> grid = new Grid<>();

    @Test
    public void set() {
        Point a = new Point(0, 0);
        Point b = new Point(3, 3);

        Assert.assertTrue(grid.set(a, 'A'));
        Assert.assertFalse(grid.set(a, 'B'));
        Assert.assertTrue(grid.set(b, 'B'));
    }

    @Test
    public void get() {
        Point a = new Point(0, 0);

        grid.set(a, 'A');
        Assert.assertEquals('A', (char) grid.get(a));
    }

    @Test
    public void remove() {
        Point a = new Point(0, 0);

        grid.set(a, 'A');
        Assert.assertNotNull(grid.get(a));

        grid.remove(a);
        Assert.assertNull(grid.get(a));
    }

    @Test
    public void removeMany() {
        Point a = new Point(0, 0);
        Point b = new Point(3, 3);

        grid.set(a, 'a');
        grid.set(b, 'B');

        grid.remove(new ArrayList<>(Arrays.asList(a, b)));
        Assert.assertNull(grid.get(a));
        Assert.assertNull(grid.get(b));
    }

    @Test
    public void move() {
        Point a = new Point(0, 0);
        Point b = new Point(3, 3);
        Point c = new Point(6, 6);

        grid.set(a, 'a');

        grid.set(c, 'c');
        //Assert.assertFalse(grid.move(a, c));    // Can't move to occupied (Implementation has changed)
        //Assert.assertTrue(grid.move(a, b));     // Can move to empty
        Assert.assertNull(grid.get(a));
        Assert.assertNotNull(grid.get(b));
    }

    @Test
    public void moveMany() {
        Point a = new Point(0, 0);
        Point b = new Point(1, 0);
        Point c = new Point(2, 0);
        Point d = new Point(3, 0);

        Point shiftRight = new Point(1, 0);


        grid.set(a, 'a');
        grid.set(b, 'b');
        grid.set(c, 'c');
        // Note that point d is not set

        // Moving a and b right is not possible because of c
        Assert.assertFalse(grid.shift(new ArrayList<>(Arrays.asList(a, b)), shiftRight));
        Assert.assertNotNull(grid.get(a));
        Assert.assertNull(grid.get(d));

        // Moving a, b and c is possible because nothing is in the way
        Assert.assertTrue(grid.shift(new ArrayList<>(Arrays.asList(a, b, c)), shiftRight));
        Assert.assertNull(grid.get(a));
        Assert.assertNotNull(grid.get(d));
    }
}
