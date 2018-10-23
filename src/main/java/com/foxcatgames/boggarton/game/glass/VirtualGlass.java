package com.foxcatgames.boggarton.game.glass;

import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.VirtualBrick;
import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.game.figure.VirtualFigure;

public class VirtualGlass extends AbstractGlass<VirtualBrick, VirtualFigure> {

    private boolean moveDown;
    private boolean forSearchingSolution = true;

    public <B extends IBrick, F extends IFigure<B>> VirtualGlass(final IGlassState<B, F> glassState, final boolean moveDown) {
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
        forSearchingSolution = false;
        state.setBricks(new VirtualBrick[width][height]);
    }

    @Override
    public void setFigure(final int x, final int y, final boolean setChanges) {
        state.setI(x);
        state.setJ(y);
        changes.setFlag(setChanges);
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
    public void moveLeft() {
        if (canMoveLeft())
            setFigure(state.getI() - 1, state.getJ(), true);
    }

    @Override
    public void moveRight() {
        if (canMoveRight())
            setFigure(state.getI() + 1, state.getJ(), true);
    }

    @Override
    public void rotate() {
        state.getFigure().rotate();
    }

    @Override
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

    @Override
    public void setChanges(final int num, final int i, final int j) {
        state.setBrick(i, j, figure().getBrick(num));
        figure().setNull(num);
        changes.setFlag(true);
    }

    @Override
    public void removeBrick(final int i, final int j) {
        if (brick(i, j) != null)
            state.setBrick(i, j, null);
    }

    private boolean moveDownTrue() {
        boolean changes = false;
        for (int i = 0; i < figure().getLenght(); i++)
            if (figure().getBrick(i) != null)
                if (state.getJ() + 1 == height() || brick(state.getI() + i, state.getJ() + 1) != null) {
                    changes = true;
                    setChanges(i, state.getI() + i, state.getJ());
                }
        if (figure().isFallen())
            return false;

        setFigure(state.getI(), state.getJ() + 1, changes);
        return true;
    }

    private boolean moveDownEffective() {
        for (int i = 0; i < figure().getLenght(); i++)
            if (figure().getBrick(i) != null)
                if (state.getJ() + 1 == height() || brick(state.getI() + i, state.getJ() + 1) != null)
                    setChanges(i, state.getI() + i, state.getJ());

        if (figure().isFallen())
            return false;

        setFigure(state.getI(), state.getJ() + 1, false);
        return true;
    }

    @Override
    protected void setChanges(final boolean flag) {
        if (forSearchingSolution)
            changes.setFlag(flag);
        else
            super.setChanges(flag);
    }

    @Override
    public void waitChanges() throws InterruptedException {
        if (!forSearchingSolution)
            super.waitChanges();
    }
}
