package com.foxcatgames.boggarton.players.virtual.solver;

import static com.foxcatgames.boggarton.Const.WIDTH;

import com.foxcatgames.boggarton.game.AbstractGame;
import com.foxcatgames.boggarton.game.IForecast;
import com.foxcatgames.boggarton.game.IGlass;
import com.foxcatgames.boggarton.game.IGlassState;
import com.foxcatgames.boggarton.game.VirtualForecast;
import com.foxcatgames.boggarton.game.VirtualGlass;
import com.foxcatgames.boggarton.game.utils.Pair;
import com.foxcatgames.boggarton.scenes.AbstractScene;

public class Solver {

    private static final int DEFAULT_SIZE = 48;
    private static final String D = "D";
    private static final String DN = "DN";

    private final int SIZE = WIDTH - AbstractScene.size + 1;
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
    private boolean isInit;
    private int depth;
    private int maxDepth;
    private int score;

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

    public Solver(final AbstractGame game, final boolean moveDown) {
        this.game = game;
        this.moveDown = moveDown;

        SHIFTS_LEFT = new String[SIZE];
        for (int i = 0; i < SIZE; i++)
            SHIFTS_LEFT[i] = getRepeat(i, 'L');

        SHIFTS_RIGHT = new String[SIZE];
        for (int i = 0; i < SIZE; i++)
            SHIFTS_RIGHT[i] = getRepeat(i, 'R');

        CYCLES = new String[AbstractScene.size];
        for (int i = 0; i < AbstractScene.size; i++)
            CYCLES[i] = getRepeat(i, 'C');

        final int size = WIDTH - AbstractScene.size;

        MOVES_TO_LEFT = new Vector[size + 1];
        for (int i = 0; i <= size; i++)
            MOVES_TO_LEFT[i] = new Vector(false, i);

        MOVES_TO_RIGHT = new Vector[size];
        for (int i = 0; i < size; i++)
            MOVES_TO_RIGHT[i] = new Vector(true, i + 1);

        isInit = false;
    }

    public void initSolver(final int dept) {
        Pair<IGlassState, IForecast> pair;
        try {
            solution = new Solution();
            pair = game.getBuffer();
            initGlass = new VirtualGlass(pair.getFirst(), moveDown);
            forecast = new VirtualForecast(pair.getSecond());
            maxDepth = Math.min(forecast.getDepth(), dept);
            score = initGlass.getGlassState().getScore();
            isInit = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Solution getSolution() {
        if (!isInit)
            return null;

        findSolutionRecursively(initGlass, new StringBuilder(DEFAULT_SIZE));
        isInit = false;
        game.clearBuffer();
        return solution;
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

    private void findSolutionRecursively(final IGlass glass, final StringBuilder result) {
        if (game.isGameOver())
            return;

        final Vector shift = getSpace(glass);
        final int avail = glass.getFigure().getNumber() - 1;
        for (int j = 0; j <= shift.getSpace(); j++)
            for (int i = 0; i <= avail; i++) {

                final IGlass virtualGlass = new VirtualGlass(glass.getGlassState(), moveDown);
                final StringBuilder currResult = new StringBuilder(result);
                currResult.append(cycle(virtualGlass, i));
                currResult.append(move(virtualGlass, shift.getDirection(), j));
                currResult.append(drop(virtualGlass));

                if (!virtualGlass.getFigure().isFallen())
                    findSolutionRecursively(virtualGlass, currResult);
                else {
                    virtualGlass.processGlass();

                    if (!virtualGlass.isGameOver() && depth < maxDepth) {
                        virtualGlass.newFigure(forecast.getForecast(depth++));
                        findSolutionRecursively(virtualGlass, currResult);
                        depth--;
                    }

                    if (depth == maxDepth) {
                        final int scoreDifference = virtualGlass.getGlassState().getScore() - score;
                        if (!virtualGlass.isGameOver())
                            if (Solution.getPrice(virtualGlass.getFullness(),
                                    scoreDifference) > solution.getPrice())
                                solution = new Solution(currResult.toString(), scoreDifference,
                                        virtualGlass.getFullness());
                    }
                }
            }
    }
}