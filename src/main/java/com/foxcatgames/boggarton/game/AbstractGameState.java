package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.game.StageItem.START;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.foxcatgames.boggarton.scenes.AbstractScene;

abstract public class AbstractGameState {

    protected int x, y;
    protected IGlass glass;
    protected IForecast forecast;

    protected static final float APPEAR_PAUSE = 0.3f;
    protected static final float DISAPPEAR_PAUSE = 1f;
    protected static final float SET_PAUSE = 0.1f;
    protected static final float YUCK_PAUSE = 0.5f;

    protected static final int DROPPING_SPEED = 300000;
    protected static final int CRASH_SPEED = 150000;
    protected static final int MOVING_SPEED = 5000;
    protected static final int CHARGE_SPEED = 300000;
    volatile protected int currentSpeed = MOVING_SPEED;

    protected float startTime = getTime();
    protected float previousTime = startTime;

    protected int yucksForEnemies;
    protected boolean glassProcessed;
    protected boolean reactionDetected = false;
    protected boolean killedBricks;

    protected String name = "default";
    protected StageItem stage = START;

    private FileOutputStream fos = null;
    private BufferedWriter bw = null;

    float getTime() {
        return AbstractScene.TIMER.getTime();
    }

    public void restoreSpeed() {
        currentSpeed = MOVING_SPEED;
    }

    public void setMaxSpeed() {
        currentSpeed = DROPPING_SPEED;
    }

    public boolean isGameOver() {
        return glass.isGameOver();
    }

    public boolean isGameOn() {
        return !glass.isGameOver();
    }

    public void rotateFigure() {
        glass.rotate();
        log("C");
    }

    public void moveLeft() {
        glass.moveLeft();
        log("L");
    }

    public void moveRight() {
        glass.moveRight();
        log("R");
    }

    public void dropFigure() {
        log("D");
    }

    public void waitNextFigure() {
        log("N\n");
    }

    protected void logFigure(IFigure figure) {
        if (figure != null)
            log("FIGURE: " + figure);
    }

    public void logYuck(String yuck) {
        log("YUCK:   " + yuck + "\n");
    }

    public IGlass getGlass() {
        return glass;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public IForecast getForecast() {
        return forecast;
    }

    public boolean hasReaction() {
        return reactionDetected;
    }

    private void log(String str) {
        if (bw != null)
            try {
                bw.write(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void initLogger(String comment) {
        String pattern1 = "yyyy-MM-dd";
        String pattern2 = "HH-mm-ss";

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);

        Date moment = new Date();
        String date = simpleDateFormat1.format(moment);
        String time = simpleDateFormat2.format(moment);

        File file = new File("History" + File.separator + name + File.separator + date + File.separator + time + ".txt");
        file.getParentFile().mkdirs();
        try {
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            log(comment + "\n\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeLogger() {
        try {
            log("\nGame over!");
            bw.flush();
            bw.close();
            fos.flush();
            fos.close();
            bw = null;
            fos = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
