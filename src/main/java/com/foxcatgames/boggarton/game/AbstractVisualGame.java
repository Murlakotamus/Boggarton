package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.game.glass.AbstractVisualGlass.SCREEN_OFFSET_Y;

import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.Sound;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.Text;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractVisualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractVisualGlass;
import com.foxcatgames.boggarton.game.glass.GlassState;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.scenes.AbstractScene;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;

abstract public class AbstractVisualGame<B extends Brick, F extends AbstractVisualFigure<B>, G extends AbstractVisualGlass<B, F>, P extends AbstractVisualForecast<B, F>>
        extends AbstractGame<B, F, G, P> {

    private static final float APPEAR_PAUSE = 1f;
    private static final float DISAPPEAR_PAUSE = 1f;
    private static final float SET_PAUSE = 0.1f;
    protected static final float YUCK_PAUSE = 0.5f;

    private static final int DROPPING_SPEED = 500000;
    private static final int CRASH_SPEED = 150000;
    private static final int MOVING_SPEED = 3000;
    private static final int CHARGE_SPEED = 300000;
    private int currentSpeed = MOVING_SPEED;

    protected float startTime = getTime();
    protected float previousTime = startTime;

    private final int x, y;
    private final Text diffScore;
    protected final Map<String, Integer> sounds;

    public AbstractVisualGame(final Layer layer, final int x, final int y, final int width, final int height, final int prognosis, final int figureSize,
            final int setSize, final RandomTypes randomType, final Map<String, Integer> sounds) {

        this.x = x;
        this.y = y;
        this.sounds = sounds;

        diffScore = new Text("", Const.DARK_FONT, layer);
    }

    abstract protected void nextStage();

    public void processStage() {
        switch (stage) {
        case NEXT:
            if (needNewFigure) {
                logFigure(nextFigure());
                logMoves();
            } else
                charge();
            break;
        case APPEAR:
            executeCommand();
            if (!dropPressed)
                stagePause(APPEAR_PAUSE);
            else
                nextStage();
            break;
        case FALL:
            executeCommand();
            fall();
            break;
        case SET:
            stagePause(SET_PAUSE);
            break;
        case CRASH:
            crashDown();
            break;
        case PROCESS:
            processGlass();
            break;
        case COMPRESS:
            compress();
            break;
        default:
        }
    }

    protected F nextFigure() {
        dropPressed = false;
        F figure = null;
        if (needNewFigure) {
            figure = forecast.getForecast();
            targetPosition = glass.newFigure(figure);
        }
        if (!glass.getGlassState().canTakeNewFigure(targetPosition)) {
            setGameOver();
            return null;
        }
        needNewFigure = false;
        resumeScore();
        oldGlassState = glass.getGlassState().toString();
        // Sound.play(sounds.get(Const.NEW));
        return figure;
    }

    protected void resumeScore() {
        final int diff = getGlass().getGlassState().getScore() - lastScore;
        if (diff > 0) {
            diffScore.setString("+" + diff);
            diffScore.spawn(new Vector2f(x + BOX * 8, y - BOX * 2));
            logScore(diff);
            if (diff > 100) {
                logGlass(oldGlassState);
                logGlass(glass.getGlassState().toString());
            }
            Sound.play(sounds.get(Const.SCORE));
        } else {
            diffScore.unspawn();
        }
        lastScore = getGlass().getGlassState().getScore();
    }

    private boolean enoughSleep(final float sleep) {
        return startTime + sleep < getTime();
    }

    protected void stagePause(final float pause) {
        if (enoughSleep(pause))
            nextStage();
    }

    protected void charge(final List<Pair<Integer, Integer>> pairs, ICommand satisfyCondition) {
        final F figure = glass.figure();
        final Vector2f figurePosition = figure.getPosition();
        final float currentTime = getTime();
        final float spentTime = (currentTime - previousTime) / 1000f;
        final int currX = Math.round(figurePosition.getX());
        final int newX = currX + Math.round(spentTime * CHARGE_SPEED);
        final Vector2f framePosition = glass.getFrame().getPosition();

        if (newX >= framePosition.getX() + targetPosition * BOX) {
            figure.setPosition(new Vector2f(framePosition.getX() + targetPosition * BOX, figurePosition.getY()));
            forecast.setNext(pairs);
            glass.respawn();
            fillBuffer();
            needNewFigure = true;
            if (pairs != null)
                satisfyCondition.execute();
            nextStage();
            return;
        }
        figure.setPosition(new Vector2f(newX, figurePosition.getY()));
        previousTime = currentTime;
    }

    protected void charge() {
        charge(null, null);
    }

    private void fall() {
        final G glass = this.glass;
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

    private void crashDown() {
        if (glass.allBricksFell()) {
            nextStage();
            return;
        }

        boolean fell = false;
        final float currentTime = getTime();
        final float spentTime = (currentTime - previousTime) / 1000f;
        final GlassState<B, F> state = glass.getGlassState();
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = state.getHeight() - 2; j >= 0; j--) {

                final B brick = state.getBrick(i, j);
                if (brick == null || !brick.isCrashing())
                    continue;

                final int currY = (int) brick.position.getY() - SCREEN_OFFSET_Y;
                final int newY = currY + Math.round(spentTime * CRASH_SPEED);
                if (newY == currY)
                    return;

                fell = fell | crashDownBrick(brick, i, j, currY, newY);
            }
        previousTime = currentTime;
        if (fell)
            Sound.playDrop(sounds.get(Const.DROP));
    }

    private boolean crashDownBrick(final B brick, final int i, final int j, final int currY, final int newY) {
        boolean fell = false;
        final int oldCell = currY / BOX;
        final int newCell = newY / BOX;
        final int diffCell = newCell - oldCell;
        final Vector2f position = brick.position;
        if (diffCell == 0)
            position.setY(newY + SCREEN_OFFSET_Y);
        else {
            final GlassState<B, F> state = glass.getGlassState();
            for (int k = 1; k <= diffCell; k++) {
                position.setY((oldCell + k) * BOX + SCREEN_OFFSET_Y);
                state.setBrick(i, j + k, brick);
                state.setBrick(i, j + k - 1, null);
                final int z = j + k + 1;
                if ((z == state.getHeight()) || state.getBrick(i, z) != null && !state.getBrick(i, z).isCrashing()) {
                    fell = true;
                    brick.setCrashing(false);
                }
            }
        }
        return fell;
    }

    private void processGlass() {
        if (!glassProcessed) {
            killedBricks = glass.findChainsToKill();
            if (killedBricks) {
                glass.startAnimation();
                Sound.playDrop(sounds.get(Const.DISAPPEAR));
            }
            glassProcessed = true;
        }
        if (!killedBricks)
            nextStage();
        if (enoughSleep(DISAPPEAR_PAUSE)) {
            glass.killChains();
            glass.respawn();
            nextStage();
        }
    }

    protected float getTime() {
        return AbstractScene.TIMER.getTime();
    }

    public void restoreSpeed() {
        currentSpeed = MOVING_SPEED;
    }

    public void setMaxSpeed() {
        currentSpeed = DROPPING_SPEED;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
