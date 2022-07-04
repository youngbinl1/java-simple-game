import bagel.DrawOptions;
import bagel.Font;
import bagel.Window;

public class Messages {

    private static final int FONTSIZE = 55;
    private static final int PERCENT_SIZE = 30;
    private static final int NONE = -1;
    private static boolean initial = true;
    private DrawOptions colour = new DrawOptions();

    // set the messages
    private final Font font = new Font("res/wheaton.otf", FONTSIZE);
    private final Font percentFont = new Font("res/wheaton.otf", PERCENT_SIZE);
    private static final String START = "PRESS SPACE TO START";
    private static final String INSTRUCTION = "USE ARROW KEYS TO FIND LADDER";
    private static final String WIN = "CONGRATULATIONS!";
    private static final String END = "GAME OVER";

    // determine the ending type
    public static final int WINNING = 1;
    public static final int OUT = 2;
    public static int gameEnd = NONE;

    // draw the game instruction messages on screen
    public void startRendering() {
        if (initial) {
            font.drawString(START, (Window.getWidth() - font.getWidth(START))/ 2.0, 402);
            font.drawString(INSTRUCTION, (Window.getWidth() - font.getWidth(INSTRUCTION))/ 2.0, 402 + 70);
        }
    }

    // stop showing the initial game instruction messages on screen
    public void stopRendering() {
        initial = false;
    }

    // draw the ending message on the screen
    public void gameEnding(int endingType) {
        if (endingType == OUT) {
            font.drawString(END, (Window.getWidth() - font.getWidth(END)) / 2.0, 402);
        }
        if (endingType == WINNING) {
            font.drawString(WIN, (Window.getWidth() - font.getWidth(WIN)) / 2.0, 402);
        }
    }

    // show health points
    public void healthPercent(int percent) {
        String percentMsg;
        percentMsg = Integer.toString(percent).concat("%");
        if (!initial) {
            if (percent > 65) {
                percentFont.drawString(percentMsg, 10, 25, colour.setBlendColour(0, 0.8, 0.2));
            }
            if ((percent < 65) && (percent > 35)) {
                percentFont.drawString(percentMsg, 10, 25, colour.setBlendColour(0.9, 0.6, 0));
            }
            if (percent < 35) {
                percentFont.drawString(percentMsg, 10, 25, colour.setBlendColour(1, 0, 0));
            }
        }
    }

}
