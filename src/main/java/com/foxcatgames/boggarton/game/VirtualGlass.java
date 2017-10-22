package com.foxcatgames.boggarton.game;

public class VirtualGlass extends AbstractGlass {

    private boolean moveDown;

    public VirtualGlass(final IGlassState glass, final boolean moveDown) {
        init(glass.getWidth(), glass.getHeight());
        for (int i = 0; i < glass.getWidth(); i++)
            for (int j = 0; j < glass.getHeight(); j++)
                if (glass.getBrick(i, j) != null)
                    state.setBrick(i, j, new VirtualBrick(glass.getBrick(i, j).getType()));

        state.setFigure(new VirtualFigure(glass.getFigure()));
        state.setNextPosition(glass.getNextPosition());
        state.setScore(glass.getScore());
        state.setI(glass.getI());
        state.setJ(glass.getJ());

        this.moveDown = moveDown;
    }

    private void init(final int width, final int height) {
        state.setWidth(width);
        state.setHeight(height);
        state.setBricks(new VirtualBrick[width][height]);
        changes.setFlag(false);
        gameOver = false;
    }

    @Override
    public void setFigure(final int x, final int y, final boolean setChanges) {
        state.setI(x);
        state.setJ(y);
        changes.setFlag(setChanges);
    }

    @Override
    public int newFigure(final IFigure figure) {
        state.setFigure(new VirtualFigure(figure));

        // figures will appear from left and right side by rotation
        setFigure(state.getNextPosition(), 0, true);
        if (state.getNextPosition() == 0)
            state.setNextPosition(state.getWidth() - figure.getLenght());
        else
            state.setNextPosition(0);
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
        final int oldNum = newBricks.size();
        for (int i = 0; i < state.getWidth(); i++) {
            boolean holesFound;
            do {
                holesFound = false;
                for (int j = state.getHeight() - 2; j >= 0; j--)
                    if (state.getBrick(i, j) != null && state.getBrick(i, j + 1) == null) {
                        state.setBrick(i, j + 1, state.getBrick(i, j));
                        addBrick(i, j + 1);
                        state.setBrick(i, j, null);
                        holesFound = true;

                    }
            } while (holesFound);
        }
        return oldNum < newBricks.size();
    }

    @Override
    public void setChanges(final int num, final int i, final int j) {
        state.setBrick(i, j, state.getFigure().getBrick(num));
        addBrick(i, j);
        state.getFigure().setNull(num);
        changes.setFlag(true);
    }

    @Override
    public void removeBrick(final int i, final int j) {
        if (state.getBrick(i, j) != null)
            state.setBrick(i, j, null);
    }

    @Override
    public void dropChanges() {
        changes.setFlag(false);
    }
    
    private boolean moveDownTrue() {
        boolean changes = false;
        for (int i = 0; i < state.getFigure().getLenght(); i++)
            if (state.getFigure().getBrick(i) != null)
                if (state.getJ() + 1 == state.getHeight()
                        || state.getBrick(state.getI() + i, state.getJ() + 1) != null) {
                    changes = true;
                    setChanges(i, state.getI() + i, state.getJ());
                }
        if (state.getFigure().isFallen())
            return false;

        setFigure(state.getI(), state.getJ() + 1, changes);
        return true;
    } 
    
    private boolean moveDownEffective () {
        for (int i = 0; i < state.getFigure().getLenght(); i++)
            if (state.getFigure().getBrick(i) != null)
                if (state.getJ() + 1 == state.getHeight()
                        || state.getBrick(state.getI() + i, state.getJ() + 1) != null)
                    setChanges(i, state.getI() + i, state.getJ());

        if (state.getFigure().isFallen())
            return false;

        setFigure(state.getI(), state.getJ() + 1, false);
        return true;   
    }
}
