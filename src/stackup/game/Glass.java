package stackup.game;

import static stackup.Const.FONT_WIDTH;
import static stackup.Const.FORECAST;
import static stackup.Const.LIGHT_FONT;
import static stackup.Const.BOX;
import static stackup.entity.Frame.BORDER;

import org.lwjgl.util.vector.Vector2f;

import stackup.engine.Layer;
import stackup.entity.Brick;
import stackup.entity.Frame;
import stackup.entity.Text;
import stackup.game.utils.Utils;

public class Glass extends AbstractGlass {

    static public final int SCREEN_OFFSET = 105;

    private final Text showScore;
    private final Text showCount;
    private final Frame frame;
    private final Layer layer;
    private final int setSize;

    private boolean gamePaused;
    private int count; // fallen figures number

    /**
     * Real glass constructor
     */
    public Glass(final Layer layer, final Vector2f position, final int width, final int height, final int setSize) {
        this.layer = layer;
        this.setSize = setSize;
        frame = new Frame(layer, position, width, height);
        init(width, height);

        showScore = new Text("Score: " + state.getScore(), LIGHT_FONT, layer);
        showScore.spawn(new Vector2f(position.getX(), position.getY() - 30));

        final int numLen = ("" + count).length();
        showCount = new Text("" + count, LIGHT_FONT, layer);
        showCount.spawn(new Vector2f(position.getX() - numLen * FONT_WIDTH - 10, position.getY()
                + FORECAST * BOX + 15));
    }

    private void init(final int width, final int height) {
        state.setWidth(width);
        state.setHeight(height);
        state.setBricks(new Brick[width][height]);
        setChanges(false);
        gameOver = false;
    }

    private Brick brick(final int i, final int j) {
        return (Brick) state.getBrick(i, j);
    }

    private Figure figure() {
        return (Figure) state.getFigure();
    }

    public boolean canTakeNewFigure() {
        if (getFigure() == null)
            return true; // game is not started yet

        // if there's no space where for a next figure
        for (int i = state.getNextPosition(); i < state.getNextPosition() + getFigure().getLenght(); i++)
            if (state.getBrick(i, 0) != null)
                return false;

        return true;
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
                    brick(i, j).spawn(
                            new Vector2f(position.getX() + i * BOX + BORDER, position.getY() + j
                                    * BOX + BORDER));
        figure().respawn();
        showScore.setString("Score: " + state.getScore());

        final String count = "" + this.count;
        final int numLen = count.length();
        showCount.setString(count);
        showCount.spawn(new Vector2f(position.getX() - numLen * FONT_WIDTH - 10, position.getY()
                + FORECAST * BOX + 15));
    }

    public void executeYuck() {
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = 0; j < state.getHeight(); j++) {
                if (j > 0)
                    state.setBrick(i, j - 1, state.getBrick(i, j));
                removeBrick(i, j);
            }

        for (int i = 0; i < state.getWidth(); i++)
            state.setBrick(i, state.getHeight() - 1, new Brick(Utils.randomBrick(setSize), layer));

        for (int i = 0; i < state.getWidth(); i++)
            for (int j = 0; j < state.getHeight(); j++)
                if (state.getBrick(i, j) != null)
                    addBrick(i, j);
    }

    @Override
    public boolean removeHoles() {
        boolean result = false; // useless but we need to be honest in advance
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

    public int getY() {
        return (int) figure().getPosition().getY() - SCREEN_OFFSET;
    }

    public boolean allBricksFell() {
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = state.getHeight() - 2; j >= 0; j--)
                if (state.getBrick(i, j) != null && ((Brick) state.getBrick(i, j)).isCrashing())
                    return false;
        return true;
    }

    public void setY(final int y) {
        final Figure figure = figure();
        figure.getPosition().setY(y + SCREEN_OFFSET);
        figure.respawn();
    }

    @Override
    public void newFigure(final IFigure newFigure) {
        if (getFigure() != null)
            figure().unspawn();

        final Figure figure = (Figure) newFigure;
        state.setFigure(figure);
        state.setI(state.getNextPosition());
        state.setJ(0);

        figure.setPosition(new Vector2f(frame.getPosition().getX() + state.getI() * BOX, frame
                .getPosition().getY() + BORDER));
        // figures will appear from left and right side by rotation
        if (state.getNextPosition() == 0)
            state.setNextPosition(state.getWidth() - figure.getLenght());
        else
            state.setNextPosition(0);

        count++;
        setChanges(true);
    }

    @Override
    public void rotate() {
        if (!gamePaused)
            getFigure().rotate();
    }

    @Override
    public void moveLeft() {
        if (!gamePaused && canMoveLeft()) {
            state.setI(state.getI() - 1);
            setFigure(state.getI(), state.getJ(), true);
        }
    }

    @Override
    public void moveRight() {
        if (!gamePaused && canMoveRight()) {
            state.setI(state.getI() + 1);
            setFigure(state.getI(), state.getJ(), true);
        }
    }

    @Override
    public void setChanges(final int num, final int i, final int j) {
        state.setBrick(i, j, getFigure().getBrick(num));
        addBrick(i, j);
        getFigure().setNull(num);
        setChanges(true);
    }

    @Override
    public void setFigure(final int i, final int j, final boolean setChanges) {
        state.setI(i);
        state.setJ(j);
        final Vector2f position = frame.getPosition();
        figure().setPosition(new Vector2f(i * BOX + position.getX(), getY() + position.getY()
                + BORDER));
        setChanges(setChanges);
    }

    @Override
    public boolean moveDown() {
        boolean changes = false;
        state.setJ((int) (getY() / BOX));
        for (int i = 0; i < getFigure().getLenght(); i++)
            if (getFigure().getBrick(i) != null)
                if (state.getJ() + 1 == state.getHeight()
                        || state.getBrick(state.getI() + i, state.getJ() + 1) != null) {
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
}
