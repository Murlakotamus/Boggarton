package stackup.players.virtual.solver;

import static stackup.Const.LENGHT;
import static stackup.Const.WIDTH;
import stackup.game.AbstractGame;
import stackup.game.IForecast;
import stackup.game.IGlass;
import stackup.game.IGlassState;
import stackup.game.VirtualForecast;
import stackup.game.VirtualGlass;
import stackup.game.utils.Pair;

public class Solver {

    private static final int DEFAULT_SIZE = 48;
    private static final String D = "D";
    private static final String DE = "DE";

    private static final int SIZE = WIDTH - LENGHT + 1;
    private static final String ROTATES[];
    private static final String SHIFTS_LEFT[];
    private static final String SHIFTS_RIGHT[];

    private final AbstractGame game;
    private IGlass initGlass;
    private IForecast forecast;
    private Solution solution;
    private boolean isInit;
    private int depth;
    private int maxDepth;
    private int score;

    static {
        SHIFTS_LEFT = new String[SIZE];
        for (int i = 0; i < SIZE; i++)
            SHIFTS_LEFT[i] = getRepeat(i, 'A');

        SHIFTS_RIGHT = new String[SIZE];
        for (int i = 0; i < SIZE; i++)
            SHIFTS_RIGHT[i] = getRepeat(i, 'B');

        ROTATES = new String[LENGHT];
        for (int i = 0; i < LENGHT; i++)
            ROTATES[i] = getRepeat(i, 'C');
    }

    private static String getRepeat(final int count, final char baseShift) {
        final StringBuilder sb = new StringBuilder(SIZE);
        for (int i = 0; i < count; i++)
            sb.append(baseShift);

        return sb.toString();
    }

    public Solver(final AbstractGame game) {
        this.game = game;
        isInit = false;
    }

    public void initSolver(final int dept) {
        Pair<IGlassState, IForecast> pair;
        try {
            solution = new Solution();
            pair = game.getBuffer();
            initGlass = new VirtualGlass(pair.getFirst());
            forecast = new VirtualForecast(pair.getSecond());
            maxDepth = Math.min(forecast.getDepth(), dept);
            score = initGlass.getGlassState().getScore();
            isInit = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getMoves() {
        if (!isInit)
            return "";

        findSolutionRecursively(initGlass, new StringBuilder(DEFAULT_SIZE));
        isInit = false;
        game.clearBuffer();
        return solution.getTurns();
    }

    private Vector getSpace(final IGlass glass) {
        final int lSpace = glass.getSpaceLeft();
        final int rSpace = glass.getSpaceRight();
        if (lSpace >= rSpace)
            return Vector.MOVES_TO_LEFT[lSpace];

        return Vector.MOVES_TO_RIGHT[rSpace - 1];
    }

    private String doRotate(final IGlass glass, final int i) {
        for (int r = 0; r < i; r++)
            glass.rotate();

        return ROTATES[i];
    }

    private String doShift(final IGlass glass, final boolean direction, final int j) {
        if (direction) {
            for (int s = 0; s < j; s++)
                glass.moveRight();
            return SHIFTS_RIGHT[j];
        }

        for (int s = 0; s < j; s++)
            glass.moveLeft();

        return SHIFTS_LEFT[j];
    }

    // due to invalid implementation of moveDownVirtual always returns D.
    private String doFall(final IGlass glass) {
        glass.dropChanges();
        boolean isFallen = false;
        do {
            isFallen = glass.moveDown();
        } while (!glass.hasChanges());

        return isFallen ? D : DE;
    }

    private void findSolutionRecursively(final IGlass glass, final StringBuilder result) {
        if (game.isGameOver())
            return;

        final Vector shift = getSpace(glass);
        final int avail = glass.getFigure().getNumber() - 1;
        for (int j = 0; j <= shift.getSpace(); j++)
            for (int i = 0; i <= avail; i++) {

                final IGlass virtualGlass = new VirtualGlass(glass.getGlassState());
                final StringBuilder currResult = new StringBuilder(result);
                currResult.append(doRotate(virtualGlass, i));
                currResult.append(doShift(virtualGlass, shift.getDirection(), j));
                currResult.append(doFall(virtualGlass));

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
                        final int scoreDifference = virtualGlass.getGlassState().getScore()- score;
                        if (!virtualGlass.isGameOver())
                            if (Solution.getPrice(virtualGlass.getFullness(), scoreDifference) > solution.getPrice())
                                solution = new Solution(currResult.toString(), scoreDifference, virtualGlass.getFullness());
                    }
                }
            }
        }
}
