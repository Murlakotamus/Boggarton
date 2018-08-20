package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.game.StageItem.START;
import static com.foxcatgames.boggarton.game.glass.SimpleGlass.SCREEN_OFFSET_Y;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.Text;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.game.forecast.IForecast;
import com.foxcatgames.boggarton.game.forecast.SimpleForecast;
import com.foxcatgames.boggarton.game.forecast.VirtualForecast;
import com.foxcatgames.boggarton.game.glass.GlassState;
import com.foxcatgames.boggarton.game.glass.IGlass;
import com.foxcatgames.boggarton.game.glass.IGlassState;
import com.foxcatgames.boggarton.game.glass.SimpleGlass;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.game.utils.OuterCommand;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.scenes.AbstractScene;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

/**
 * The Game is the Glass, Forecast and the all motion inside the Glass.
 */
abstract public class AbstractGame {

    protected int x, y;
    protected IGlass glass;
    protected IForecast forecast;

    protected static final float APPEAR_PAUSE = 1f;
    protected static final float DISAPPEAR_PAUSE = 1f;
    protected static final float SET_PAUSE = 0.1f;
    protected static final float YUCK_PAUSE = 0.5f;

    protected static final int DROPPING_SPEED = 500000;
    protected static final int CRASH_SPEED = 150000;
    protected static final int MOVING_SPEED = 3000;
    protected static final int CHARGE_SPEED = 300000;
    volatile protected int currentSpeed = MOVING_SPEED;

    protected float startTime = getTime();
    protected float previousTime = startTime;

    protected int yucksForEnemies;
    protected boolean glassProcessed;
    protected boolean reactionDetected = false;
    protected boolean killedBricks;
    protected boolean dropPressed = false;

    protected String name = "default";
    protected StageItem stage = START;
    private boolean isLoggerInit = false;

    private GameLogger gameLogger = null;

    final protected Pair<IGlassState, IForecast> buffer = new Pair<>(null, null);
    final private OuterCommand command = new OuterCommand();
    final protected Text diffScore;
    protected int targetPosition = 0;

    protected boolean needNewFigure = true;
    protected int lastScore = 0;

    public AbstractGame(final Layer layer, final int x, final int y, final int width, final int height, final int forecast, final int lenght,
            final int difficulty, final RandomTypes randomType) {

        this.x = x;
        this.y = y;
        if (forecast >= 1)
            this.forecast = new SimpleForecast(layer, new Vector2f(x, y), forecast, lenght, difficulty, randomType);
        diffScore = new Text("", Const.DARK_FONT, layer);
    }

    abstract protected void nextStage();

    abstract public void processStage();

    public IFigure nextFigure() {
        dropPressed = false;
        IFigure figure = null;
        if (needNewFigure) {
            figure = forecast.getForecast();
            targetPosition = glass.newFigure(figure);
        }
        if (!glass.getGlassState().canTakeNewFigure(targetPosition)) {
            setGameOver();
            return null;
        }

        final int diff = getGlass().getGlassState().getScore() - lastScore;
        if (diff > 0) {
            diffScore.setString("+" + diff);
            diffScore.spawn(new Vector2f(x + BOX * 8, y - BOX * 2));
        } else {
            diffScore.unspawn();
        }
        lastScore = getGlass().getGlassState().getScore();
        return figure;
    }

    private boolean enoughSleep(final float sleep) {
        return startTime + sleep < getTime();
    }

    protected void stagePause(final float pause) {
        if (enoughSleep(pause))
            nextStage();
    }

    protected void charge(final List<Pair<Integer, Integer>> pairs) {
        final AbstractVisualFigure figure = (AbstractVisualFigure) glass.getFigure();
        final Vector2f figurePosition = figure.getPosition();
        final float currentTime = getTime();
        final float spentTime = (currentTime - previousTime) / 1000f;
        final int currX = Math.round(figurePosition.getX());
        final int newX = currX + Math.round(spentTime * CHARGE_SPEED);
        final Vector2f framePosition = ((SimpleGlass) glass).getFrame().getPosition();

        if (newX >= framePosition.getX() + targetPosition * BOX) {
            figure.setPosition(new Vector2f(framePosition.getX() + targetPosition * BOX, figurePosition.getY()));
            ((SimpleForecast) forecast).setNext(pairs);
            ((SimpleGlass) glass).respawn();
            fillBuffer();
            needNewFigure = true;
            // here it need to drop condition
            nextStage();
            return;
        }
        figure.setPosition(new Vector2f(newX, figurePosition.getY()));
        previousTime = currentTime;
    }

    protected void charge() {
        charge(null);
    }

    public void fall() {
        final SimpleGlass glass = (SimpleGlass) this.glass;
        if (glass.getY() % BOX == 0 && !glass.moveDown()) {
            nextStage();
            return;
        }

        final float currentTime = getTime();
        final float spentTime = (currentTime - previousTime) / 1000f;
        final int currY = glass.getY(); // staring Y is 0
        final int newY = currY + Math.round(spentTime * currentSpeed);

        if (newY == currY)
            return;

        final int oldCell = currY / BOX;
        final int newCell = newY / BOX;
        final int diffCell = newCell - oldCell;

        if (diffCell == 0) {
            glass.setY(newY);
            glass.getGlassState().setJ((int) ((newY + BOX) / BOX));
        } else {
            for (int i = 1; i <= diffCell; i++) {
                glass.setY((oldCell + i) * BOX);
                if (!glass.moveDown()) {
                    nextStage();
                    return;
                }
            }
            glass.setY(newY);
        }
        previousTime = currentTime;
    }

    public void crashDown() {
        if (((SimpleGlass) glass).allBricksFell()) {
            nextStage();
            return;
        }

        final float currentTime = getTime();
        final float spentTime = (currentTime - previousTime) / 1000f;
        final GlassState state = glass.getGlassState();
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = state.getHeight() - 2; j >= 0; j--) {

                final Brick brick = (Brick) state.getBrick(i, j);
                if (brick == null || !brick.isCrashing())
                    continue;

                final int currY = (int) brick.position.getY() - SCREEN_OFFSET_Y;
                final int newY = currY + Math.round(spentTime * CRASH_SPEED);
                if (newY == currY)
                    return;

                crashDownBrick(brick, i, j, currY, newY);
            }
        previousTime = currentTime;
    }

    private void crashDownBrick(final Brick brick, final int i, final int j, final int currY, final int newY) {
        final int oldCell = currY / BOX;
        final int newCell = newY / BOX;
        final int diffCell = newCell - oldCell;
        final Vector2f position = brick.position;
        if (diffCell == 0)
            position.setY(newY + SCREEN_OFFSET_Y);
        else {
            final GlassState state = glass.getGlassState();
            for (int k = 1; k <= diffCell; k++) {
                position.setY((oldCell + k) * BOX + SCREEN_OFFSET_Y);
                state.setBrick(i, j + k, brick);
                state.setBrick(i, j + k - 1, null);
                final int z = j + k + 1;
                if ((z == state.getHeight()) || state.getBrick(i, z) != null && !((Brick) state.getBrick(i, z)).isCrashing())
                    brick.setCrashing(false);
            }
        }
    }

    public void processGlass() {
        if (!glassProcessed) {
            killedBricks = glass.findChainsToKill();
            if (killedBricks)
                ((SimpleGlass) glass).startAnimation();
            glassProcessed = true;
        }
        if (!killedBricks)
            nextStage();
        if (enoughSleep(DISAPPEAR_PAUSE)) {
            glass.killChains();
            ((SimpleGlass) glass).respawn();
            nextStage();
        }
    }

    protected void compress() {
        if (killedBricks) {
            glass.removeHoles();
            glass.addReaction();
            killedBricks = false;
            reactionDetected = true;
        } else {
            if (glass.getReactionLenght() > 2)
                yucksForEnemies += glass.getReactionLenght() - 2;
            glass.cleanReactions();
            reactionDetected = false;
        }
        glassProcessed = false;
        nextStage();
    }

    public void setGameOver() {
        synchronized (buffer) {
            ((SimpleGlass) glass).setGameOver();
            buffer.notify();
            executeCommand();
        }
    }

    public Pair<IGlassState, IForecast> getBuffer() throws InterruptedException {
        synchronized (buffer) {
            while (buffer.isEmpty() && isGameOn())
                buffer.wait();
            buffer.notify();
        }
        return buffer;
    }

    public void clearBuffer() {
        synchronized (buffer) {
            buffer.setFirst(null);
            buffer.setSecond(null);
            buffer.notify();
        }
    }

    public void fillBuffer() {
        synchronized (buffer) {
            buffer.setFirst(glass.getGlassState());
            buffer.setSecond(new VirtualForecast(forecast));
            buffer.notify();
        }
    }

    final Lock commandLock = new ReentrantLock();
    final Condition bufferReady = commandLock.newCondition();

    protected void executeCommand() {
        if (command.getCommand() != null) {
            commandLock.lock();
            try {
                command.execute();
                bufferReady.signal();
            } finally {
                commandLock.unlock();
            }
        }
    }

    public void sendCommand(ICommand cmd) {
        commandLock.lock();
        try {
            while (command.getCommand() != null)
                bufferReady.await();
            command.setCommand(cmd);
            bufferReady.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            commandLock.unlock();
        }
    }

    public void startGame() {
        nextStage();
    }

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
        logEvent("C");
    }

    public void moveLeft() {
        glass.moveLeft();
        logEvent("L");
    }

    public void moveRight() {
        glass.moveRight();
        logEvent("R");
    }

    public void dropFigure() {
        dropPressed = true;
        logEvent("D");
    }

    public void waitNextFigure() {
        logEvent("N\n");
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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void initLogger() {
        gameLogger = new GameLogger(name);
        isLoggerInit = gameLogger.isInit();
    }

    public void closeLogger() {
        if (isLoggerInit) {
            gameLogger.close();
            isLoggerInit = false;
        }
    }

    protected void logFigure(final IFigure figure) {
        if (figure != null)
            logEvent(Const.FIGURE + figure);
    }

    protected void logYuck(final String yuck) {
        logEvent(Const.YUCK + yuck + "\n");
    }

    private void logEvent(final String str) {
        if (isLoggerInit)
            gameLogger.logEvent(str);
    }

}
