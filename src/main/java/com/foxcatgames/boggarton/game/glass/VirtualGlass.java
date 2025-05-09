package com.foxcatgames.boggarton.game.glass;

import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.VirtualBrick;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;
import com.foxcatgames.boggarton.game.figure.VirtualFigure;

public class VirtualGlass extends AbstractGlass<VirtualBrick, VirtualFigure> {

    private final boolean moveDown;
    private boolean changes;

    public int changesCounter;

    public <B extends IBrick, F extends AbstractFigure<B>> VirtualGlass(final GlassState<B, F> glassState, final boolean moveDown) {
        super(glassState.getWidth(), glassState.getHeight(), 0);
        this.moveDown = moveDown;

        state.setBricks(new VirtualBrick[width()][height()]);

        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++)
                if (glassState.getBrick(i, j) != null)
                    state.setBrick(i, j, new VirtualBrick(glassState.getBrick(i, j).getType()));

        state.setFigure(new VirtualFigure(glassState.getFigure()));
        state.setNextPosition(glassState.getNextPosition());
        state.setScore(glassState.getScore());
        state.setI(glassState.getI());
        state.setJ(glassState.getJ());
    }

    public VirtualGlass(final int width, final int height) {
        super(width, height, 0);
        moveDown = true;
        state.setBricks(new VirtualBrick[width][height]);
    }

    public void setFigure(final int x, final int y, final boolean setChanges) {
        setFigure(x, y);
        changes = setChanges;
    }

    public void setFigure(final int x, final int y) {
        state.setI(x);
        state.setJ(y);
    }

    @Override
    public int newFigure(final VirtualFigure figure) {
        state.setFigure(new VirtualFigure(figure));

        // figures will appear from left and right side by rotation
        setFigure(state.getNextPosition(), 0, true);
        if (state.getNextPosition() == 0)
            state.setNextPosition(state.getWidth() - figure.getLenght());
        else
            state.setNextPosition(0);
        count++;
        setChanges(true);
        return 0;
    }

    @Override
    public boolean moveLeft() {
        boolean result = canMoveLeft();
        if (result)
            setFigure(state.getI() - 1, state.getJ(), true);
        return result;
    }

    @Override
    public boolean moveRight() {
        boolean result = canMoveRight();
        if (result)
            setFigure(state.getI() + 1, state.getJ(), true);
        return result;
    }

    @Override
    public void rotate() {
        state.getFigure().rotate();
    }

    public boolean moveDown() {
        if (moveDown)
            return moveDownTrue();

        return moveDownEffective();
    }

    @Override
    public boolean removeHoles() {
        boolean holesFoundOverall = false;
        for (int i = 0; i < width(); i++) {
            boolean holesFound;
            do {
                holesFound = false;
                for (int j = height() - 2; j >= 0; j--)
                    if (brick(i, j) != null && brick(i, j + 1) == null) {
                        state.setBrick(i, j + 1, brick(i, j));
                        state.setBrick(i, j, null);
                        holesFound = true;
                        holesFoundOverall = true;
                    }
            } while (holesFound);
        }
        return holesFoundOverall;
    }

    public void setChanges(final int num, final int i, final int j) {
        state.setBrick(i, j, figure().getBrick(num));
        figure().setNull(num);
    }

    @Override
    public void removeBrick(final int i, final int j) {
        if (brick(i, j) != null)
            state.setBrick(i, j, null);
    }

    private boolean moveDownTrue() {
        boolean currentChanges = false;
        for (int i = 0; i < figure().getLenght(); i++)
            if (figure().getBrick(i) != null)
                if (state.getJ() + 1 == height() || brick(state.getI() + i, state.getJ() + 1) != null) {
                    currentChanges = true;
                    setChanges(i, state.getI() + i, state.getJ());
                }

        if (currentChanges) {
            changesCounter++;
            changes = true;
        }

        if (figure().isFallen())
            return false;

        setFigure(state.getI(), state.getJ() + 1);
        return true;
    }

    private boolean moveDownEffective() {
        boolean currentChanges = false;
        for (int i = 0; i < figure().getLenght(); i++)
            if (figure().getBrick(i) != null)
                if (state.getJ() + 1 == height() || brick(state.getI() + i, state.getJ() + 1) != null) {
                    currentChanges = true;
                    setChanges(i, state.getI() + i, state.getJ());
                }

        if (currentChanges)
            changesCounter++;

        if (figure().isFallen()) {
            setChanges(true);
            return false;
        }

        setFigure(state.getI(), state.getJ() + 1);
        return true;
    }

    public void setChanges(final boolean flag) {
        changes = flag;
    }

    public boolean hasChanges() {
        return changes;
    }

    public void dropChanges() {
        changes = false;
    }
}
