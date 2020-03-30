import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstTest {
    @Test
    public void testDistance() {
        Point p1 = new Point(10,10);
        Point p2 = new Point(15,15);
        Assert.assertEquals(p1.distance(p2), 7.0710678118654755);
    }

    @Test
    public void testValuePoint() {
        Point p1 = new Point(10,10);
        Point p2 = new Point(15,15);
        Assert.assertEquals(p1.x = 10, 10);
        Assert.assertEquals(p1.y = 10, 10);
        Assert.assertEquals(p2.x = 15, 15);
        Assert.assertEquals(p2.y = 15, 15);
    }
}
