package stackup.game;

import static stackup.Const.BOX;
import static stackup.game.SimpleGlass.SCREEN_OFFSET_Y;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.lwjgl.util.vector.Vector2f;

import stackup.Const;
import stackup.engine.Layer;
import stackup.entity.Brick;
import stackup.entity.Text;
import stackup.game.utils.Command;
import stackup.game.utils.OuterCommand;
import stackup.game.utils.Pair;

/**
 * The Game is the Glass, Forecast and the all motion inside the Glass.
 *
 * @author Michael
 */
abstract public class AbstractGame extends GameState {

    final private Pair<IGlassState, IForecast> buffer = new Pair<>(null, null);
    final private OuterCommand command = new OuterCommand();
    final private Text diffScore;
    private int targetPosition = 0;

    protected boolean needNewFigure = true;
    protected int lastScore = 0;

    public AbstractGame(final Layer layer, final int x, final int y, final int width,
            final int height, final int forecast, final int lenght, final int setSize) {
        this.x = x;
        this.y = y;
        this.forecast = new Forecast(layer, new Vector2f(x, y), forecast, lenght, setSize);
        diffScore = new Text("", Const.DARK_FONT, layer);
    }

    abstract protected void nextStage();

    abstract public void processStage();

    public void nextFigure() {
        if (needNewFigure)
            targetPosition = glass.newFigure(forecast.getForecast());

        if (!glass.getGlassState().canTakeNewFigure(targetPosition)) {
            setGameOver();
            return;
        }

        int diff = getGlass().getGlassState().getScore() - lastScore;
        if (diff > 0) {
            diffScore.setString("+" + diff);
            diffScore.spawn(new Vector2f(x + BOX * 8, y - BOX * 2));
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

    protected void charge() {
        final Figure figure = (Figure) glass.getFigure();
        final Vector2f figurePosition = figure.getPosition();
        final float currentTime = getTime();
        final float spentTime = (currentTime - previousTime) / 1000f;
        final int currX = Math.round(figurePosition.getX());
        final int newX = currX + Math.round(spentTime * CHARGE_SPEED);
        final Vector2f framePosition = ((SimpleGlass) glass).getFrame().getPosition();

        if (newX >= framePosition.getX() + targetPosition * BOX) {
            figure.setPosition(new Vector2f(framePosition.getX() + targetPosition * BOX,
                    figurePosition.getY()));
            ((Forecast) forecast).setNext();
            ((SimpleGlass) glass).respawn();
            fillBuffer();
            needNewFigure = true;
            nextStage();
            return;
        }
        figure.setPosition(new Vector2f(newX, figurePosition.getY()));
        previousTime = currentTime;
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

        if (diffCell == 0)
            glass.setY(newY);
        else {
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

    private void crashDownBrick(final Brick brick, final int i, final int j, final int currY,
            final int newY) {
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
                ((SimpleGlass) glass).addBrick(i, j + k);
                state.setBrick(i, j + k, brick);
                state.setBrick(i, j + k - 1, null);
                final int z = j + k + 1;
                if ((z == state.getHeight()) || state.getBrick(i, z) != null
                        && !((Brick) state.getBrick(i, z)).isCrashing())
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
            glass.compressList();
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

    public void sendCommand(Command cmd) {
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

    public void checkCommand() {
        commandLock.lock();
        try {
            while (command.getCommand() != null)
                bufferReady.await();
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
}
