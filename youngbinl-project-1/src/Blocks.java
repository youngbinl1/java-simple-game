import bagel.util.Point;
import bagel.util.Rectangle;

public class Blocks {
    final int MAX = 50;
    public double[] blockY = new double[MAX];
    public double[] blockX = new double[MAX];
    public Point[] blockPoint = new Point[MAX];

    public Rectangle[] blockRecs = new Rectangle[MAX];

    public final int DMG_POINT = 10;

    public Point getBlockPoint(double x, double y) {
        Point p = new Point(x, y);
        return p;
    }

}
