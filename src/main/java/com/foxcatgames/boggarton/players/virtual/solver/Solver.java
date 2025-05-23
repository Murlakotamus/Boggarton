package com.foxcatgames.boggarton.players.virtual.solver;

import static com.foxcatgames.boggarton.Const.DOWN;
import static com.foxcatgames.boggarton.Const.LEFT;
import static com.foxcatgames.boggarton.Const.NEXT;
import static com.foxcatgames.boggarton.Const.RIGHT;
import static com.foxcatgames.boggarton.Const.UP;
import static com.foxcatgames.boggarton.Const.WIDTH;

import java.util.HashSet;

import com.foxcatgames.boggarton.Logger;
import com.foxcatgames.boggarton.game.IAutomatedGame;
import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.VirtualBrick;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;
import com.foxcatgames.boggarton.game.figure.VirtualFigure;
import com.foxcatgames.boggarton.game.forecast.AbstractForecast;
import com.foxcatgames.boggarton.game.forecast.VirtualForecast;
import com.foxcatgames.boggarton.game.glass.AbstractGlass;
import com.foxcatgames.boggarton.game.glass.GlassState;
import com.foxcatgames.boggarton.game.glass.VirtualGlass;
import com.foxcatgames.boggarton.game.utils.Pair;

public class Solver<B extends IBrick, F extends AbstractFigure<B>, G extends AbstractGlass<B, F>, P extends AbstractForecast<B, F>> {

    private static final int DEFAULT_SIZE = 16; // CCLLLDCRRRDLLLDN (16)

    private final int spaceAvail;
    private final String[] cycles;
    private final String[] shiftsLeft;
    private final String[] shiftsRight;
    private final Vector[] movesToLeft;
    private final Vector[] movesToRight;

    private final IAutomatedGame<B, F, G, P> game;
    private final IEater eater;
    private final int maxDepth;
    private final boolean moveDown;

    private Solution solution;
    private int depth;

    private final HashSet<Integer> set = new HashSet<>();

    private String getRepeat(final int count, final char baseShift) {
        final StringBuilder sb = new StringBuilder(spaceAvail);
        for (int i = 0; i < count; i++)
            sb.append(baseShift);

        return sb.toString();
    }

    public Solver(final IAutomatedGame<B, F, G, P> game, final int maxDepth, final boolean moveDown, final int figureSize, final IEater eater) {
        this.game = game;
        this.maxDepth = maxDepth;
        this.moveDown = moveDown;
        this.eater = eater;
        spaceAvail = WIDTH - figureSize + 1;

        shiftsLeft = new String[spaceAvail];
        for (int i = 0; i < spaceAvail; i++)
            shiftsLeft[i] = getRepeat(i, LEFT);

        shiftsRight = new String[spaceAvail];
        for (int i = 0; i < spaceAvail; i++)
            shiftsRight[i] = getRepeat(i, RIGHT);

        cycles = new String[figureSize];
        for (int i = 0; i < figureSize; i++)
            cycles[i] = getRepeat(i, UP);

        final int size = WIDTH - figureSize;

        movesToLeft = new Vector[size + 1];
        for (int i = 0; i <= size; i++)
            movesToLeft[i] = new Vector(false, i);

        movesToRight = new Vector[size];
        for (int i = 0; i < size; i++)
            movesToRight[i] = new Vector(true, i + 1);
    }

    public Solution getSolution() {
        try {
            final Pair<GlassState<B, F>, P> pair = game.getBuffer();
            final VirtualGlass initGlass = new VirtualGlass(pair.getFirst(), moveDown);
            final VirtualForecast initForecast = new VirtualForecast(pair.getSecond());
            final int initScore = initGlass.getGlassState().getScore();
            game.clearBuffer();
            solution = new Solution(initScore);
            findSolutionRecursively(initGlass, initForecast, 0, new StringBuilder(DEFAULT_SIZE * (maxDepth + 1)));

            if (eater.getPrice(solution) <= eater.getPrice(new Solution(initScore)))
                findDropRecursively(initGlass, initForecast, new StringBuilder(DEFAULT_SIZE));

            return solution;
        } catch (final InterruptedException e) {
            Logger.printStackTrace(e);
            return solution;
        }
    }

    private Vector getSpace(final VirtualGlass glass) {
        final int lSpace = glass.getSpaceLeft();
        final int rSpace = glass.getSpaceRight();
        if (lSpace >= rSpace)
            return movesToLeft[lSpace];

        return movesToRight[rSpace - 1];
    }

    private String cycle(final VirtualGlass glass, final int i) {
        for (int r = 0; r < i; r++)
            glass.rotate();

        return cycles[i];
    }

    private String move(final VirtualGlass glass, final boolean direction, final int j) {
        if (direction) {
            for (int s = 0; s < j; s++)
                glass.moveRight();
            return shiftsRight[j];
        }

        for (int s = 0; s < j; s++)
            glass.moveLeft();

        return shiftsLeft[j];
    }

    private static String drop(final VirtualGlass glass) {
        glass.dropChanges();
        boolean isFallen;
        glass.changesCounter = 0;
        do {
            isFallen = glass.moveDown();
        } while (!glass.hasChanges());

        StringBuilder down = new StringBuilder(glass.changesCounter + 1);
        for (int i = 0; i < glass.changesCounter; i++)
            down.append(DOWN);

        return isFallen ? down.toString() : down.append(NEXT).toString();
    }

    private int getFigureSetSize(final VirtualFigure figure) {
        set.clear();
        for (int i = 0; i < figure.getLenght(); i++) {
            final VirtualBrick brick = figure.getBrick(i);
            if (brick != null)
                set.add(brick.getType());
        }
        return set.size();
    }

    private void findSolutionRecursively(final VirtualGlass glass, final VirtualForecast initForecast, int reactions, final StringBuilder result)
            throws InterruptedException {
        if (game.isGameOver())
            return;

        final Vector shift = getSpace(glass);
        final int avail = glass.figure().getNumber() - 1;
        int figureResidueSetSize = 0;
        if (avail > 0)
            figureResidueSetSize = getFigureSetSize(glass.figure());
        final int cycles = figureResidueSetSize == 1 ? 0 : avail;
        for (int j = 0; j <= shift.getSpace(); j++)
            for (int i = 0; i <= cycles; i++) {
                final VirtualGlass virtualGlass = new VirtualGlass(glass.getGlassState(), moveDown);
                final StringBuilder currResult = new StringBuilder(result);
                currResult.append(move(virtualGlass, shift.getDirection(), j));
                currResult.append(cycle(virtualGlass, i));
                currResult.append(drop(virtualGlass));

                if (!virtualGlass.figure().isFallen())
                    findSolutionRecursively(virtualGlass, initForecast, reactions, currResult);
                else {
                    reactions += virtualGlass.processGlass();

                    if (depth < maxDepth && virtualGlass.getFullness() > 0) {
                        virtualGlass.newFigure(initForecast.getForecast(depth++));
                        findSolutionRecursively(virtualGlass, initForecast, reactions, currResult);
                        depth--;
                    }

                    if (depth == maxDepth) {
                        if (!virtualGlass.isGameOver() && virtualGlass.getFullness() > 0)
                            solution = eater.chooseBest(solution,
                                    new Solution(currResult, virtualGlass.getGlassState().getScore(), virtualGlass.getFullness()));
                    }
                }
            }
    }

    private void findDropRecursively(final VirtualGlass glass, final VirtualForecast initForecast, final StringBuilder result) throws InterruptedException {
        final int avail = glass.figure().getNumber() - 1;
        for (int i = 0; i <= avail; i++) {

            final VirtualGlass virtualGlass = new VirtualGlass(glass.getGlassState(), false);
            final StringBuilder currResult = new StringBuilder(result);
            currResult.append(drop(virtualGlass));

            if (!virtualGlass.figure().isFallen())
                findDropRecursively(virtualGlass, initForecast, currResult);
            else {
                virtualGlass.processGlass();

                if (depth < maxDepth && virtualGlass.getFullness() >= 0) {
                    virtualGlass.newFigure(initForecast.getForecast(depth++));
                    findDropRecursively(virtualGlass, initForecast, currResult);
                    depth--;
                }

                if (depth == maxDepth) {
                    solution = new Solution(currResult, 0, 0);
                    return;
                }
            }
        }
    }
}
