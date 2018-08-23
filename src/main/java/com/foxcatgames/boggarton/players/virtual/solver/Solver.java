package com.foxcatgames.boggarton.players.virtual.solver;

import static com.foxcatgames.boggarton.Const.WIDTH;

import java.util.HashSet;

import com.foxcatgames.boggarton.Logger;
import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.figure.IFigure;
import com.foxcatgames.boggarton.game.forecast.IForecast;
import com.foxcatgames.boggarton.game.forecast.VirtualForecast;
import com.foxcatgames.boggarton.game.glass.IGlass;
import com.foxcatgames.boggarton.game.glass.IGlassState;
import com.foxcatgames.boggarton.game.glass.VirtualGlass;
import com.foxcatgames.boggarton.game.utils.Pair;

public class Solver {

    private static final int DEFAULT_SIZE = 48;
    private static final String D = "D";
    private static final String DN = "DN";

    private final int SIZE;
    private final String[] CYCLES;
    private final String[] SHIFTS_LEFT;
    private final String[] SHIFTS_RIGHT;
    private final Vector[] MOVES_TO_LEFT;
    private final Vector[] MOVES_TO_RIGHT;

    private final AbstractGame game;
    private final boolean moveDown;
    private IGlass initGlass;
    private IForecast forecast;
    private Solution solution;
    private int depth;
    private int maxDepth;
    private int score;

    private final HashSet<Integer> set = new HashSet<>();

    public class Vector {

        private boolean direction;
        private int space;

        private Vector(final boolean direction, final int space) {
            this.direction = direction;
            this.space = space;
        }

        public int getSpace() {
            return space;
        }

        public boolean getDirection() {
            return direction;
        }
    }

    private String getRepeat(final int count, final char baseShift) {
        final StringBuilder sb = new StringBuilder(SIZE);
        for (int i = 0; i < count; i++)
            sb.append(baseShift);

        return sb.toString();
    }

    public Solver(final AbstractGame game, final boolean moveDown, final int figureSize) {
        this.game = game;
        this.moveDown = moveDown;
        SIZE = WIDTH - figureSize + 1;

        SHIFTS_LEFT = new String[SIZE];
        for (int i = 0; i < SIZE; i++)
            SHIFTS_LEFT[i] = getRepeat(i, 'L');

        SHIFTS_RIGHT = new String[SIZE];
        for (int i = 0; i < SIZE; i++)
            SHIFTS_RIGHT[i] = getRepeat(i, 'R');

        CYCLES = new String[figureSize];
        for (int i = 0; i < figureSize; i++)
            CYCLES[i] = getRepeat(i, 'C');

        final int size = WIDTH - figureSize;

        MOVES_TO_LEFT = new Vector[size + 1];
        for (int i = 0; i <= size; i++)
            MOVES_TO_LEFT[i] = new Vector(false, i);

        MOVES_TO_RIGHT = new Vector[size];
        for (int i = 0; i < size; i++)
            MOVES_TO_RIGHT[i] = new Vector(true, i + 1);
    }

    public Solution getSolution(final int dept, final IPrice price) {
        solution = new Solution();
        try {
            final Pair<IGlassState, IForecast> pair = game.getBuffer();
            initGlass = new VirtualGlass(pair.getFirst(), moveDown);
            forecast = new VirtualForecast(pair.getSecond());
            maxDepth = Math.min(forecast.getDepth(), dept);
            score = initGlass.getGlassState().getScore();
            findSolutionRecursively(initGlass, new StringBuilder(DEFAULT_SIZE), price);

            if (solution.getScore() <= 0)
                findDropRecursively(initGlass, new StringBuilder(DEFAULT_SIZE));
            game.clearBuffer();
            return solution;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return solution;
        }
    }

    private Vector getSpace(final IGlass glass) {
        final int lSpace = glass.getSpaceLeft();
        final int rSpace = glass.getSpaceRight();
        if (lSpace >= rSpace)
            return MOVES_TO_LEFT[lSpace];

        return MOVES_TO_RIGHT[rSpace - 1];
    }

    private String cycle(final IGlass glass, final int i) {
        for (int r = 0; r < i; r++)
            glass.rotate();

        return CYCLES[i];
    }

    private String move(final IGlass glass, final boolean direction, final int j) {
        if (direction) {
            for (int s = 0; s < j; s++)
                glass.moveRight();
            return SHIFTS_RIGHT[j];
        }

        for (int s = 0; s < j; s++)
            glass.moveLeft();

        return SHIFTS_LEFT[j];
    }

    private String drop(final IGlass glass) {
        glass.dropChanges();
        boolean isFallen = false;
        do {
            isFallen = glass.moveDown();
        } while (!glass.hasChanges());

        return isFallen ? D : DN;
    }

    private int getFigureSetSize(final IFigure figure) {
        set.clear();
        for (int i = 0; i < figure.getLenght(); i++) {
            IBrick brick = figure.getBrick(i);
            if (brick != null)
                set.add(brick.getType());
        }
        return set.size();
    }

    private void findSolutionRecursively(final IGlass glass, final StringBuilder result, final IPrice price) {
        if (game.isGameOver())
            return;

        final Vector shift = getSpace(glass);
        final int avail = glass.getFigure().getNumber() - 1;
        int figureResidueSetSize = 0;
        if (avail > 0)
            figureResidueSetSize = getFigureSetSize(glass.getFigure());
        final int cycles = figureResidueSetSize == 1 ? 0 : avail;
        for (int j = 0; j <= shift.getSpace(); j++)
            for (int i = 0; i <= cycles; i++) {
                final IGlass virtualGlass = new VirtualGlass(glass.getGlassState(), moveDown);
                final StringBuilder currResult = new StringBuilder(result);
                currResult.append(cycle(virtualGlass, i));
                currResult.append(move(virtualGlass, shift.getDirection(), j));
                currResult.append(drop(virtualGlass));

                if (!virtualGlass.getFigure().isFallen())
                    findSolutionRecursively(virtualGlass, currResult, price);
                else {
                    virtualGlass.processGlass();

                    if (depth < maxDepth && virtualGlass.getFullness() > 0) {
                        virtualGlass.newFigure(forecast.getForecast(depth++));
                        findSolutionRecursively(virtualGlass, currResult, price);
                        depth--;
                    }

                    if (depth == maxDepth) {
                        if (!virtualGlass.isGameOver() && virtualGlass.getFullness() > 0) {
                            final int scoreDifference = virtualGlass.getGlassState().getScore() - score;
                            if (price.getPrice(new Solution(null, scoreDifference, virtualGlass.getFullness(), 0)) > price.getPrice(solution))
                                solution = new Solution(currResult.toString(), scoreDifference, virtualGlass.getFullness(), 0);
                        }
                    }
                }
            }
    }

    private void findDropRecursively(final IGlass glass, final StringBuilder result) {
        final int avail = glass.getFigure().getNumber() - 1;
        for (int i = 0; i <= avail; i++) {

            final IGlass virtualGlass = new VirtualGlass(glass.getGlassState(), moveDown);
            final StringBuilder currResult = new StringBuilder(result);
            currResult.append(drop(virtualGlass));

            if (!virtualGlass.getFigure().isFallen())
                findDropRecursively(virtualGlass, currResult);
            else {
                virtualGlass.processGlass();

                if (depth < maxDepth && virtualGlass.getFullness() >= 0) {
                    virtualGlass.newFigure(forecast.getForecast(depth++));
                    findDropRecursively(virtualGlass, currResult);
                    depth--;
                }

                if (depth == maxDepth) {
                    solution = new Solution(currResult.toString(), 0, virtualGlass.getFullness(), 0);
                    return;
                }
            }
        }
    }
}
