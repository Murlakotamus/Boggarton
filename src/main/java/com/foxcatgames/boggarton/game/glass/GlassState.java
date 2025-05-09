package com.foxcatgames.boggarton.game.glass;

import static com.foxcatgames.boggarton.Const.EMPTY;

import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.figure.AbstractFigure;

public class GlassState<B extends IBrick, F extends AbstractFigure<B>> {

    static private final int VERTICAL = 1;
    static private final int HORIZONTAL = 2;
    static private final int DIAGONAL = 3;

    protected int reaction;
    protected int nextPosition;
    protected int score;
    protected int i, j; // first figure brick location (glass cell)
    protected int width, height; // glass size in bricks

    protected B[][] bricks;
    protected F figure;

    public GlassState(int nextPosition) {
        this.nextPosition = nextPosition;
    }

    public void findHorizontals(final int i, final int j) {
        if (bricks[i][j] == null || bricks[i][j].getType() == EMPTY || bricks[i - 1][j] == null || bricks[i + 1][j] == null)
            return;

        if (bricks[i - 1][j].getType() == bricks[i][j].getType() && bricks[i][j].getType() == bricks[i + 1][j].getType()) {
            bricks[i - 1][j].setKill();
            bricks[i][j].setKill();
            bricks[i + 1][j].setKill();
            score += HORIZONTAL * (reaction + 1);
        }
    }

    public void findVerticals(final int i, final int j) {
        if (bricks[i][j] == null || bricks[i][j].getType() == EMPTY || bricks[i][j + 1] == null || bricks[i][j - 1] == null)
            return;

        if (bricks[i][j - 1].getType() == bricks[i][j].getType() && bricks[i][j].getType() == bricks[i][j + 1].getType()) {
            bricks[i][j - 1].setKill();
            bricks[i][j].setKill();
            bricks[i][j + 1].setKill();
            score += VERTICAL * (reaction + 1);
        }
    }

    public void findMainDiags(final int i, final int j) {
        if (bricks[i][j] == null || bricks[i][j].getType() == EMPTY || bricks[i - 1][j - 1] == null || bricks[i + 1][j + 1] == null)
            return;

        if (bricks[i - 1][j - 1].getType() == bricks[i][j].getType() && bricks[i][j].getType() == bricks[i + 1][j + 1].getType()) {
            bricks[i + 1][j + 1].setKill();
            bricks[i][j].setKill();
            bricks[i - 1][j - 1].setKill();
            score += DIAGONAL * (reaction + 1);
        }
    }

    public void findAntiDiags(final int i, final int j) {
        if (bricks[i][j] == null || bricks[i][j].getType() == EMPTY || bricks[i + 1][j - 1] == null || bricks[i - 1][j + 1] == null)
            return;

        if (bricks[i + 1][j - 1].getType() == bricks[i][j].getType() && bricks[i][j].getType() == bricks[i - 1][j + 1].getType()) {
            bricks[i + 1][j - 1].setKill();
            bricks[i][j].setKill();
            bricks[i - 1][j + 1].setKill();
            score += DIAGONAL * (reaction + 1);
        }
    }

    public boolean canTakeNewFigure(final int targetPosition) {
        if (figure == null)
            return true; // game's not started yet

        for (int i = 0; i < targetPosition + figure.getLenght(); i++)
            if (bricks[i][0] != null)
                return false;

        return true;
    }

    public boolean canMoveLeft() {
        if (i <= 0)
            return false;

        for (int num = 0; num < figure.getLenght(); num++)
            if (figure.getBrick(num) != null)
                if (bricks[i + num - 1][j] != null)
                    return false;

        return true;
    }

    public boolean canMoveRight() {
        if (i >= width - figure.getLenght())
            return false;

        for (int num = 0; num < figure.getLenght(); num++)
            if (figure.getBrick(num) != null)
                if (bricks[i + num + 1][j] != null)
                    return false;
        return true;
    }

    public int getSpaceLeft() {
        int result = 0;
        if (canMoveLeft()) {
            final int firstBrick = figure.getFirstBrickNum();
            int xPos = i + firstBrick - 1;
            while (xPos >= 0 && bricks[xPos][j] == null)
                xPos = i + firstBrick - 1 - (++result);
        }
        return result;
    }

    public int getSpaceRight() {
        int result = 0;
        if (canMoveRight()) {
            final int lastBrick = figure.getLastBrickNum();
            int xPos = i + lastBrick + 1;
            while (xPos < width && bricks[xPos][j] == null)
                xPos = i + lastBrick + 1 + (++result);
        }
        return result;
    }

    public int getFullness() {
        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++)
                if (bricks[i][j] != null)
                    return j;
        return height; // empty, the best state
    }

    public int getEmptyHeight(final int i) {
        for (int j = 0; j < height; j++)
            if (bricks[i][j] != null)
                return j - 1;
        return height - 1;
    }

    public void setNextPosition(final int nextPosition) {
        this.nextPosition = nextPosition;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public void setI(final int i) {
        this.i = i;
    }

    public void setJ(final int j) {
        this.j = j;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public void setBricks(final B[][] bricks) {
        this.bricks = bricks;
    }

    public void setFigure(final F figure) {
        this.figure = figure;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getScore() {
        return score;
    }

    public F getFigure() {
        return figure;
    }

    public B getBrick(final int i, final int j) {
        return bricks[i][j];
    }

    public void setBrick(final int i, final int j, final B brick) {
        bricks[i][j] = brick;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNextPosition() {
        return nextPosition;
    }

    public int getReactionLenght() {
        return reaction;
    }

    public void addReaction() {
        reaction++;
    }

    public int cleanReactions() {
        try {
            return reaction;
        } finally {
            reaction = 0;
        }
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();
        sb.append("==============================================\n");
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++)
                if (bricks[i][j] == null)
                    sb.append(" ");
                else
                    sb.append((char) (bricks[i][j].getType() - 10 + 64));
            sb.append("\n");
        }
        sb.append("----------------------------------------------\n");
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++)
                if (bricks[i][j] == null)
                    sb.append("0, ");
                else
                    sb.append(bricks[i][j].getId()).append(", ");
            sb.append("\n");
        }
        sb.append("==============================================\n");

        return sb.toString();
    }
}
