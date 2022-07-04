import bagel.*;
import bagel.Image;
import bagel.Window;
import java.math.*;
import bagel.util.Point;

import java.awt.*;
import java.io.*;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2022
 *
 * Please filling your name below
 * @author Youngbin Lee 1214260
 */
public class ShadowPirate extends AbstractGame {
    // static because these are the background
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "ShadowPirate";

    // Images
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    //private final Image SAILORLEFT = new Image("res/sailorLeft.png");
    //private final Image SAILORRIGHT = new Image("res/sailorRight.png");
    private final Image BLOCK = new Image("res/block.png");

    // draw messages
    private Messages message = new Messages();

    // switch
    private static boolean draw = false;

    // set sailor data
    private static int firstSailor = 0;
    private Sailor sailor = new Sailor();

    // set block data
    private static int block_num = 0;
    private Blocks blocks = new Blocks();

    // set playground boundaries
    private static final int LEFT_BOUND = 0;
    private static final int RIGHT_BOUND = 1024;
    private static final int UPPER_BOUND = 60;
    private static final int LOWER_BOUND = 670;


    // draw blocks on the screen
    private void drawBlocks() {
        if (draw) {
            for (int i = 0; i < block_num; i++) {
                BLOCK.draw(this.blocks.blockX[i], this.blocks.blockY[i]);
            }
        }
    }

    // draw a sailor on the screen
    private void drawSailor() {
        if (this.sailor.leftRight == sailor.LEFT) {
            sailor.SAILORLEFT.draw(this.sailor.sailorX, this.sailor.sailorY);
        }
        if ((draw) && (this.sailor.leftRight == sailor.NONE)) {
            sailor.SAILORRIGHT.draw(this.sailor.sailorX, this.sailor.sailorY);
        }
        if (this.sailor.leftRight == sailor.RIGHT) {
            sailor.SAILORRIGHT.draw(this.sailor.sailorX, this.sailor.sailorY);
        }
    }

    // start Showing Blocks because the space key has been pressed
    public void startShowingBlocks() {
        draw = true;
    }


    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.run();
    }

    /**
     * Method used to read file and create objects
     */
    // read csv file and store csv data onto the sailor and block
    private void readCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String text;
            String[] csv;

            while (((text = br.readLine()) != null) && (block_num < 50)) {
                csv = text.split(",");
                if (firstSailor == 0) {
                    this.sailor.sailorX = Double.parseDouble(csv[1]);
                    this.sailor.sailorY = Double.parseDouble(csv[2]);
                    firstSailor++;
                }
                else {
                    this.blocks.blockX[block_num] = Double.parseDouble(csv[1]);
                    this.blocks.blockY[block_num] = Double.parseDouble(csv[2]);
                    this.blocks.blockPoint[block_num]
                            = this.blocks.getBlockPoint(this.blocks.blockX[block_num], this.blocks.blockY[block_num]);
                    this.blocks.blockRecs[block_num] = BLOCK.getBoundingBoxAt(this.blocks.blockPoint[block_num]);
                    block_num++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // calculate sailor's health percentage
    public int calcHealth() {
        double percentage;
        percentage = (Double.valueOf(this.sailor.currHealth) / Double.valueOf(this.sailor.MAXHEALTH)) * 100;
        return (int) Math.round(percentage);
    }

    // check collision and deduct damage point from sailor's health point
    public boolean collision() {
        boolean collide = false;
        //this.sailor.sailorRect = SAILORRIGHT.getBoundingBoxAt(this.sailor.sailorPoint);
        for (int i=0; i < block_num; i++) {
            if (this.sailor.sailorRect.intersects(this.blocks.blockRecs[i])) {
                this.sailor.currHealth -= this.blocks.DMG_POINT;
                collide = true;
                System.out.print("Block inflicts 10 damage points on Sailor. ");
                System.out.printf("Sailor's current health: %d/%d\n", this.sailor.currHealth, this.sailor.MAXHEALTH);
            }
        }
        return collide;
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        final int STEP_SIZE = 20;

        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        if (Messages.gameEnd != sailor.NONE) {
            draw = false;
            this.sailor.leftRight = sailor.NONE;
            this.message.gameEnding(Messages.gameEnd);
        }
        else {
            this.message.startRendering();
            this.message.healthPercent(calcHealth());

            if (input.wasPressed((Keys.SPACE))) {
                this.message.stopRendering();
                readCSV("res/level0.csv");
                startShowingBlocks();
                this.sailor.leftRight = sailor.RIGHT;
            }

            if (input.wasPressed(Keys.LEFT)) {
                this.sailor.sailorX -= STEP_SIZE;
                this.sailor.leftRight = sailor.LEFT;
                sailor.sailorUpdate(this.sailor.leftRight);
                if (collision()) {
                    this.sailor.sailorX += STEP_SIZE;
                }
            }
            if (input.wasPressed(Keys.RIGHT)) {
                this.sailor.sailorX += STEP_SIZE;
                this.sailor.leftRight = sailor.RIGHT;
                sailor.sailorUpdate(this.sailor.leftRight);
                if (collision()) {
                    this.sailor.sailorX -= STEP_SIZE;
                }
            }
            if (input.wasPressed(Keys.UP)) {
                this.sailor.sailorY -= STEP_SIZE;
                sailor.sailorUpdate(this.sailor.leftRight);
                if (collision()) {
                    this.sailor.sailorY += STEP_SIZE;
                }
            }
            if (input.wasPressed(Keys.DOWN)) {
                this.sailor.sailorY += STEP_SIZE;
                sailor.sailorUpdate(this.sailor.leftRight);
                if (collision()) {
                    this.sailor.sailorY -= STEP_SIZE;
                }
            }

            if (input.wasPressed(Keys.ESCAPE)) {
                Window.close();
            }

            drawBlocks();
            drawSailor();

            // check if the sailor is over boundaries
            if ((draw) && ((this.sailor.sailorX < LEFT_BOUND) || (this.sailor.sailorX > RIGHT_BOUND) ||
                    (this.sailor.sailorY >= LOWER_BOUND) || (this.sailor.sailorY <= UPPER_BOUND))) {
                Messages.gameEnd = Messages.OUT;
            }

            // sailor is dead -> game over
            if (this.sailor.currHealth <= 0) {
                Messages.gameEnd = Messages.OUT;
            }

            // sailor win!
            if ((this.sailor.sailorX >= 990) && (this.sailor.sailorY >= 630)) {
                Messages.gameEnd = Messages.WINNING;
            }
        }
    }
}
