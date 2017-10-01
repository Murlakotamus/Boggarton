package stackup.game;


public class VirtualBrick implements IBrick {
    
    private final int type;
    private boolean kill = false;
    
    public VirtualBrick(final int type) {
        this.type = type;
    }

    @Override
    public boolean isKill() {
        return kill;
    }

    @Override
    public void setKill() {
        kill = true;
    }
    
    @Override
    public int getType() {
        return type;
    }
}
