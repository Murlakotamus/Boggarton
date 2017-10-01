package stackup.game;

import java.util.HashSet;
import java.util.Iterator;

import stackup.game.utils.Changes;
import stackup.game.utils.Coords;

abstract public class AbstractGlass implements IGlass {

    volatile protected boolean gameOver;

    final protected HashSet<Coords> newBricks = new HashSet<>();
    final protected Changes changes = new Changes(true);
    final protected GlassState state = new GlassState();

    protected void addBrick(final int i, final int j) {
        if (checkCoords(i, j))
            newBricks.add(new Coords(i, j));

        if (checkCoords(i - 1, j - 1))
            newBricks.add(new Coords(i - 1, j - 1));

        if (checkCoords(i - 1, j))
            newBricks.add(new Coords(i - 1, j));

        if (checkCoords(i - 1, j + 1))
            newBricks.add(new Coords(i - 1, j + 1));

        if (checkCoords(i, j - 1))
            newBricks.add(new Coords(i, j - 1));

        if (checkCoords(i + 1, j + 1))
            newBricks.add(new Coords(i + 1, j + 1));

        if (checkCoords(i + 1, j))
            newBricks.add(new Coords(i + 1, j));

        if (checkCoords(i + 1, j - 1))
            newBricks.add(new Coords(i + 1, j - 1));
    }

    private boolean checkCoords(final int i, final int j) {
        return !(i < 0 || i >= state.getWidth() || j < 0 || j >= state.getHeight()
                || (i <= 0 && j <= 0) || (i >= state.getWidth() - 1 && j <= 0)
                || (i <= 0 && j >= state.getHeight() - 1) || (i >= state.getWidth() - 1 && j >= state
                .getHeight() - 1));
    }

    @Override
    public boolean findChainsToKill() {
        final int oldScore = state.getScore();
        compressList();
        final Iterator<Coords> it = newBricks.iterator();
        while (it.hasNext()) {
            final Coords c = it.next();
            final int i = c.i;
            final int j = c.j;

            state.findHorizontals(i, j);
            state.findVerticals(i, j);
            state.findMainDiags(i, j);
            state.findAntiDiags(i, j);

            it.remove();
        }
        return oldScore < state.getScore();
    }

    @Override
    public void compressList() {
        final Iterator<Coords> it = newBricks.iterator();
        while (it.hasNext()) {
            final Coords c = it.next();
            if (state.getBrick(c.i, c.j) == null)
                it.remove();
        }
    }

    @Override
    public int getFullness() {
        return state.getFullness();
    }

    @Override
    public void processGlass() {
        while (findChainsToKill()) {
            killChains();
            while (removeHoles())
                ;
            compressList();
            addReaction();
        }
        cleanReactions();
    }

    @Override
    public void killChains() {
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = 0; j < state.getHeight(); j++)
                if (state.getBrick(i, j) != null && state.getBrick(i, j).isKill())
                    removeBrick(i, j);
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public int getReactionLenght() {
        return state.getReactionLenght();
    }

    @Override
    public void addReaction() {
        state.addReaction();
    }

    @Override
    public void cleanReactions() {
        state.cleanReactions();
    }

    @Override
    public boolean hasChanges() {
        return changes.isFlag();
    }

    @Override
    public GlassState getGlassState() {
        return state;
    }

    @Override
    public IFigure getFigure() {
        return state.getFigure();
    }
    
    @Override
    public boolean canMoveLeft(){
        return state.canMoveLeft();
    }
    
    @Override
    public boolean canMoveRight(){
        return state.canMoveRight();
    }
    
    @Override
    public int getSpaceLeft(){
        return state.getSpaceLeft();
    }
    
    @Override
    public int getSpaceRight(){
        return state.getSpaceRight();
    }
}
