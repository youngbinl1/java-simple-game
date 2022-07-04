import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Sailor {
    public double sailorX;
    public double sailorY;
    static final int NONE = -1;
    static final int LEFT = 1;
    static final int RIGHT = 0;
    static int leftRight = NONE;
    private final int DMG_POINT = 25;
    public final int MAXHEALTH = 100;
    public int currHealth = MAXHEALTH;

    final Image SAILORRIGHT = new Image("res/sailorRight.png");
    final Image SAILORLEFT = new Image("res/sailorLeft.png");

    public Point sailorPoint = new Point(sailorX, sailorY);
    public Rectangle sailorRect = new Rectangle(SAILORRIGHT.getBoundingBoxAt(sailorPoint));

    public void sailorUpdate(int leftOrRight) {
        Point sPoint = new Point(sailorX, sailorY);

        if (leftRight == LEFT) {
            this.sailorRect = new Rectangle(SAILORLEFT.getBoundingBoxAt(sPoint));
        }
        if (leftRight == RIGHT) {
            this.sailorRect = new Rectangle(SAILORRIGHT.getBoundingBoxAt(sPoint));
        }
    }
    }
