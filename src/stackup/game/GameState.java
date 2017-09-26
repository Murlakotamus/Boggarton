package stackup.game;

import static stackup.game.Stage.START;
import stackup.scenes.AbstractScene;

abstract public class GameState {

    protected int x, y;
    protected IGlass glass;
    protected IForecast forecast;

    protected static final float APPEAR_PAUSE = 0.2f;
    protected static final float DISAPPEAR_PAUSE = 1f;
    protected static final float SET_PAUSE = 0.1f;
    protected static final float YUCK_PAUSE = 0.5f;

    protected static final int DROPPING_SPEED = 300000;
    protected static final int CRASH_SPEED = 150000;
    protected static final int MOVING_SPEED = 5000;    
    volatile protected int currentSpeed = MOVING_SPEED;
    

    protected float startTime = getTime();
    protected float previousTime = startTime;

    protected int enemiesYuck;
    protected boolean glassProcessed;
    protected boolean reactionDetected = false;
    protected boolean killedBricks;
    
    protected String name = "default";
    protected Stage stage = START;
    
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
    }

    public void moveLeft() {
        glass.moveLeft();
    }

    public void moveRight() {
        glass.moveRight();
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

    public Stage getStages() {
        return stage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    
}
