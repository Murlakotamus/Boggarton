package stackup.game;

abstract public class AbstractVirtualGlass extends AbstractGlass {

    public AbstractVirtualGlass(final IGlassState glass) {
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
    public void newFigure(final IFigure figure) {
        state.setFigure(new VirtualFigure(figure));

        // figures will appear from left and right side by rotation
        setFigure(state.getNextPosition(), 0, true);
        if (state.getNextPosition() == 0)
            state.setNextPosition(state.getWidth() - figure.getLenght());
        else
            state.setNextPosition(0);
    }

    @Override
    public void rotate() {
        state.getFigure().rotate();
    }

    @Override
    public void moveLeft() {
        if (canMoveLeft()) {
            state.setI(state.getI() - 1);
            setFigure(state.getI(), state.getJ(), true);
        }
    }

    @Override
    public void moveRight() {
        if (canMoveRight()) {
            state.setI(state.getI() + 1);
            setFigure(state.getI(), state.getJ(), true);
        }
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
}
