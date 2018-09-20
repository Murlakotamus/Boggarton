package com.foxcatgames.boggarton.game.glass;

import static com.foxcatgames.boggarton.Const.EMPTY;

import com.foxcatgames.boggarton.entity.Brick;
import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.figure.IFigure;

public class GlassState implements IGlassState {

    static private final int VERTICAL = 1;
    static private final int HORIZONTAL = 2;
    static private final int DIAGONAL = 3;

    protected int reaction = 0;
    protected int nextPosition;
    protected int score;
    protected int i, j; // first figure brick location (glass cell)
    protected int width, height; // glass size in bricks

    protected IBrick[][] bricks;
    protected IFigure figure;

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

    public boolean canTakeNewFigure(int targetPosition) {
        if (figure == null)
            return true; // game's not started yet

        for (int i = 0; i < targetPosition + figure.getLenght(); i++)
            if (bricks[i][0] != null)
                return false;

        return true;
    }

    @Override
    public boolean canMoveLeft() {
        if (i <= 0)
            return false;

        for (int num = 0; num < figure.getLenght(); num++)
            if (figure.getBrick(num) != null)
                if (bricks[i + num - 1][j] != null)
                    return false;

        return true;
    }

    @Override
    public boolean canMoveRight() {
        if (i >= width - figure.getLenght())
            return false;

        for (int num = 0; num < figure.getLenght(); num++)
            if (figure.getBrick(num) != null)
                if (bricks[i + num + 1][j] != null)
                    return false;
        return true;
    }

    @Override
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

    @Override
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
        return height;
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

    public void setBricks(final IBrick[][] bricks) {
        this.bricks = bricks;
    }

    public void setFigure(final IFigure figure) {
        this.figure = figure;
    }

    @Override
    public int getI() {
        return i;
    }

    @Override
    public int getJ() {
        return j;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public IFigure getFigure() {
        return figure;
    }

    @Override
    public IBrick getBrick(final int i, final int j) {
        return bricks[i][j];
    }

    public void setBrick(final int i, final int j, final IBrick brick) {
        bricks[i][j] = brick;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getNextPosition() {
        return nextPosition;
    }

    public int getReactionLenght() {
        return reaction;
    }

    public void addReaction() {
        reaction++;
    }

    public void cleanReactions() {
        reaction = 0;
    }

    public IGlassState getGlassState() {
        return this;
    }

    @Override
    public String toString() {

        final StringBuffer sb = new StringBuffer();
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
                if (bricks[i][j] == null || !(bricks[i][j] instanceof Brick))
                    sb.append("0, ");
                else
                    sb.append(((Brick) bricks[i][j]).getId() + ", ");
            sb.append("\n");
        }
        sb.append("==============================================\n");

        return sb.toString();
    }
}
