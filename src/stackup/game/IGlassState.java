package stackup.game;

public interface IGlassState {

    int getI();
    int getJ();
    int getWidth();
    int getHeight();
    int getScore();
    int getNextPosition();
    int getFullness();

    IFigure getFigure();
    IBrick getBrick(int i, int j);
    IGlassState getGlassState();

    void setNextPosition(int nextPosition);
    void setScore(int score);
    void setFigure(IFigure figure);

    boolean canMoveLeft();
    boolean canMoveRight();
    int getSpaceLeft();
    int getSpaceRight();
}
