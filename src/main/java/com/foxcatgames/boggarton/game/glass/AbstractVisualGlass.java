package com.foxcatgames.boggarton.game.glass;

import static com.foxcatgames.boggarton.Const.BORDER;
import static com.foxcatgames.boggarton.Const.BOX;
import static com.foxcatgames.boggarton.Const.LIGHT_FONT;

import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.Sound;
import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.entity.Frame;
import com.foxcatgames.boggarton.entity.Text;
import com.foxcatgames.boggarton.game.figure.AbstractVisualFigure;

abstract public class AbstractVisualGlass<B extends Brick, F extends AbstractVisualFigure<B>> extends AbstractGlass<B, F> {

    static public final int SCREEN_OFFSET_Y = 165;

    private final Text showScore;
    private final Text showCount;
    private final Frame frame;

    private boolean gamePaused;

    final Map<String, Integer> sounds;

    public AbstractVisualGlass(final B[][] bricks, final Layer layer, final Vector2f position, final int width, final int height,
            final Map<String, Integer> sounds, final int nextPosition) {

        super(width, height, nextPosition);

        this.sounds = sounds;

        state.setBricks(bricks);
        frame = new Frame(layer, position, width, height, true, false);

        showScore = new Text("Score: " + state.getScore(), LIGHT_FONT, layer);
        showScore.spawn(new Vector2f(position.getX(), position.getY() - 30));

        showCount = new Text("Figures: " + count, LIGHT_FONT, layer);
        showCount.spawn(new Vector2f(position.getX(), position.getY() + height * BOX + 15));
    }

    public void startAnimation() {
        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++)
                if (brick(i, j) != null && brick(i, j).isKill())
                    brick(i, j).startLoopedAnimation();
    }

    public void pauseOn() {
        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++)
                if (brick(i, j) != null)
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
        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++)
                if (brick(i, j) != null)
                    brick(i, j).spawn(new Vector2f(position.getX() + i * BOX + BORDER, position.getY() + j * BOX + BORDER));
        if (figure() != null)
            figure().respawn();
        showScore.setString("Score: " + state.getScore());

        showCount.setString("Figures: " + count);
        showCount.spawn(new Vector2f(position.getX(), position.getY() + height() * BOX + 15));
    }

    @Override
    public boolean removeHoles() {
        boolean result = false;
        for (int i = 0; i < width(); i++)
            for (int j = height() - 2; j >= 0; j--)
                if (brick(i, j) != null && brick(i, j + 1) == null) {
                    for (int k = j; k >= 0; k--) {
                        final B brick = brick(i, k);
                        if (brick != null) {
                            brick.setCrashing(true);
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
        figure().getPosition().setX(x);
        figure().respawn();
    }

    public void setY(final int y) {
        figure().getPosition().setY(y + SCREEN_OFFSET_Y);
        figure().respawn();
    }

    public boolean allBricksFell() {
        for (int i = 0; i < width(); i++)
            for (int j = height() - 2; j >= 0; j--)
                if (brick(i, j) != null && brick(i, j).isCrashing())
                    return false;
        return true;
    }

    protected void raiseBricks() {
        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++) {
                if (j > 0)
                    state.setBrick(i, j - 1, brick(i, j));
                removeBrick(i, j);
            }
    }

    @Override
    public int newFigure(final F newFigure) {
        if (figure() != null)
            figure().unspawn();

        final F figure = newFigure;
        state.setFigure(figure);
        state.setI(state.getNextPosition());
        state.setJ(0);

        // figures will appear from left and right side by rotation
        final int currentPosition = state.getNextPosition();
        if (state.getNextPosition() == 0)
            state.setNextPosition(width() - figure.getLenght());
        else
            state.setNextPosition(0);

        count++;
        return currentPosition;
    }

    @Override
    public void rotate() {
        if (gamePaused || gameOver)
            return;

        Sound.play(sounds.get(Const.CYCLE));
        figure().rotate();
    }

    @Override
    public void moveLeft() {
        if (gamePaused || gameOver || !canMoveLeft())
            return;

        Sound.play(sounds.get(Const.SHIFT));
        setFigure(state.getI() - 1, state.getJ());
        setChanges(true);
    }

    @Override
    public void moveRight() {
        if (gamePaused || gameOver || !canMoveRight())
            return;

        Sound.play(sounds.get(Const.SHIFT));
        setFigure(state.getI() + 1, state.getJ());
        setChanges(true);
    }

    public void setChanges(final int num, final int i, final int j) {
        Sound.playDrop(sounds.get(Const.DROP));
        state.setBrick(i, j, figure().getBrick(num));
        figure().setNull(num);
    }

    public void setFigure(final int i, final int j) {
        state.setI(i);
        state.setJ(j);
        final Vector2f position = frame.getPosition();
        figure().setPosition(new Vector2f(i * BOX + position.getX(), getY() + position.getY() + BORDER));
    }

    public boolean moveDown() {
        boolean changes = false;
        state.setJ(getY() / BOX);
        for (int i = 0; i < figure().getLenght(); i++)
            if (figure().getBrick(i) != null)
                if (state.getJ() + 1 == height() || brick(state.getI() + i, state.getJ() + 1) != null) {
                    changes = true;
                    setChanges(i, state.getI() + i, state.getJ());
                }

        if (changes)
            setChanges(true);

        if (figure().isFallen())
            return false;

        setFigure(state.getI(), state.getJ());
        return true;
    }

    @Override
    public void removeBrick(final int i, final int j) {
        if (brick(i, j) != null) {
            brick(i, j).unspawn();
            state.setBrick(i, j, null);
        }
    }

    @Override
    public void setGameOver() {
        super.setGameOver();
        for (int j = 0; j < height(); j++)
            for (int i = 0; i < width(); i++)
                if (brick(i, j) != null && brick(i, j).isAnimated())
                    brick(i, j).stopAnimation();
    }

    public Frame getFrame() {
        return frame;
    }
}
