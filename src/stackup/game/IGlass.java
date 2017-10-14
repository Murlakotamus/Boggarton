package stackup.game;

public interface IGlass {

    GlassState getGlassState();
    void setFigure(int x, int y, boolean setChanges);
    int newFigure(IFigure f);

    int getSpaceLeft();
    int getSpaceRight();

    boolean canMoveLeft();
    boolean canMoveRight();

    void moveLeft();
    void moveRight();
    void rotate();

    void setChanges(int i, int x, int y);
    boolean moveDown();
    boolean isGameOver();
    boolean findChainsToKill();
    void killChains();
    void removeBrick(int i, int j);
    void processGlass();
    boolean removeHoles();
    void compressList();

    int getReactionLenght();
    void addReaction();
    void cleanReactions();
    void dropChanges();
    boolean hasChanges();
    int getFullness();

    IFigure getFigure();
}
