package com.foxcatgames.boggarton.game.glass;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.LIGHT_FONT;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.Frame;
import com.foxcatgames.boggarton.entity.Text;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;
import com.foxcatgames.boggarton.game.figure.IFigure;

public class SimpleGlass extends AbstractGlass {

    static public final int SCREEN_OFFSET_Y = 165;

    private final Text showScore;
    private final Text showCount;
    private final Frame frame;

    private boolean gamePaused;
    private int count; // figures counter

    private int dropSound;

    public SimpleGlass(final Layer layer, final Vector2f position, final int width, final int height, final int... sounds) {
        super(width, height);

        dropSound = sounds[0];

        state.setBricks(new Brick[width][height]);
        frame = new Frame(layer, position, width, height, true, false);

        showScore = new Text("Score: " + state.getScore(), LIGHT_FONT, layer);
        showScore.spawn(new Vector2f(position.getX(), position.getY() - 30));

        showCount = new Text("Figures: " + count, LIGHT_FONT, layer);
        showCount.spawn(new Vector2f(position.getX(), position.getY() + height * BOX + 15));
    }

    public SimpleGlass(final Layer layer, final Vector2f position, final int width, final int height, final int[][] glass, final int... sounds) {
        this(layer, position, width, height, sounds);

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                if (glass[i][j] > 0)
                    state.setBrick(i, j, new Brick(glass[i][j] + Const.CURRENT_SET * 10, layer));

        respawn();
    }

    private Brick brick(final int i, final int j) {
        return (Brick) state.getBrick(i, j);
    }

    private AbstractVisualFigure figure() {
        return (AbstractVisualFigure) state.getFigure();
    }

    public void startAnimation() {
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = 0; j < state.getHeight(); j++)
                if (state.getBrick(i, j) != null && state.getBrick(i, j).isKill())
                    brick(i, j).startLoopedAnimation();
    }

    public void pauseOn() {
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = 0; j < state.getHeight(); j++)
                if (state.getBrick(i, j) != null)
                    brick(i, j).unspawn();
        figure().unspawn();
        gamePaused = true;
    }

    public void pauseOff() {
        respawn();
        gamePaused = false;
    }

    public void respawn() {
        final Vector2f position = frame.getPosition();
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = 0; j < state.getHeight(); j++)
                if (state.getBrick(i, j) != null)
                    brick(i, j).spawn(new Vector2f(position.getX() + i * BOX + BORDER, position.getY() + j * BOX + BORDER));
        if (figure() != null)
            figure().respawn();
        showScore.setString("Score: " + state.getScore());

        showCount.setString("Figures: " + count);
        showCount.spawn(new Vector2f(position.getX(), position.getY() + state.height * BOX + 15));
    }

    @Override
    public boolean removeHoles() {
        boolean result = false;
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = state.getHeight() - 2; j >= 0; j--)
                if (state.getBrick(i, j) != null && state.getBrick(i, j + 1) == null) {
                    for (int k = j; k >= 0; k--) {
                        final Brick brick = ((Brick) state.getBrick(i, k));
                        if (brick != null) {
                            brick.setCrashing(true);
                            addBrick(i, k);
                            result = true;
                        }
                    }
                    break;
                }
        return result;
    }

    public int getX() {
        return (int) figure().getPosition().getX();
    }

    public int getY() {
        return (int) figure().getPosition().getY() - SCREEN_OFFSET_Y;
    }

    public void setX(final int x) {
        final AbstractVisualFigure figure = figure();
        figure.getPosition().setX(x);
        figure.respawn();
    }

    public void setY(final int y) {
        final AbstractVisualFigure figure = figure();
        figure.getPosition().setY(y + SCREEN_OFFSET_Y);
        figure.respawn();
    }

    public boolean allBricksFell() {
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = state.getHeight() - 2; j >= 0; j--)
                if (state.getBrick(i, j) != null && ((Brick) state.getBrick(i, j)).isCrashing())
                    return false;
        return true;
    }

    @Override
    public int newFigure(final IFigure newFigure) {
        if (getFigure() != null)
            figure().unspawn();

        final IFigure figure = newFigure;
        state.setFigure(figure);
        state.setI(state.getNextPosition());
        state.setJ(0);

        // figures will appear from left and right side by rotation
        int currentPosition = state.getNextPosition();
        if (state.getNextPosition() == 0)
            state.setNextPosition(state.getWidth() - figure.getLenght());
        else
            state.setNextPosition(0);

        count++;
        setChanges(true);
        return currentPosition;
    }

    @Override
    public void rotate() {
        if (!gamePaused)
            getFigure().rotate();
    }

    @Override
    public void moveLeft() {
        if (!gamePaused && canMoveLeft())
            setFigure(state.getI() - 1, state.getJ(), true);
    }

    @Override
    public void moveRight() {
        if (!gamePaused && canMoveRight())
            setFigure(state.getI() + 1, state.getJ(), true);
    }

    @Override
    public void setChanges(final int num, final int i, final int j) {
        state.setBrick(i, j, getFigure().getBrick(num));
        addBrick(i, j);

        getFigure().setNull(num, dropSound, false);
        setChanges(true);
    }

    @Override
    public void setFigure(final int i, final int j, final boolean setChanges) {
        state.setI(i);
        state.setJ(j);
        final Vector2f position = frame.getPosition();
        figure().setPosition(new Vector2f(i * BOX + position.getX(), getY() + position.getY() + BORDER));
        setChanges(setChanges);
    }

    @Override
    public boolean moveDown() {
        boolean changes = false;
        state.setJ((int) (getY() / BOX));
        for (int i = 0; i < getFigure().getLenght(); i++)
            if (getFigure().getBrick(i) != null)
                if (state.getJ() + 1 == state.getHeight() || state.getBrick(state.getI() + i, state.getJ() + 1) != null) {
                    changes = true;
                    setChanges(i, state.getI() + i, state.getJ());
                }
        if (getFigure().isFallen())
            return false;

        setFigure(state.getI(), state.getJ(), changes);
        return true;
    }

    @Override
    public void removeBrick(final int i, final int j) {
        if (state.getBrick(i, j) != null) {
            brick(i, j).unspawn();
            state.setBrick(i, j, null);
        }
    }

    public void setGameOver() {
        gameOver = true;
        for (int j = 0; j < state.getHeight(); j++)
            for (int i = 0; i < state.getWidth(); i++)
                if (state.getBrick(i, j) != null && brick(i, j).isAnimated())
                    brick(i, j).stopAnimation();
        setChanges(true);
    }

    public void waitChanges() {
        synchronized (changes) {
            try {
                while (!changes.isFlag() && !gameOver)
                    changes.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            changes.notify();
        }
    }

    private void setChanges(final boolean flag) {
        synchronized (changes) {
            changes.setFlag(flag);
            changes.notify();
        }
    }

    @Override
    public void dropChanges() {
        setChanges(false);
    }

    public Frame getFrame() {
        return frame;
    }

    public int getCount() {
        return count;
    }
}
