package stackup.game;

public class EffectiveVirtualGlass extends AbstractVirtualGlass {

    public EffectiveVirtualGlass(final IGlassState glass) {
        super(glass);
    }
    
    @Override
    public boolean moveDown() {
        for (int i = 0; i < state.getFigure().getLenght(); i++)
            if (state.getFigure().getBrick(i) != null)
                if (state.getJ() + 1 == state.getHeight()
                        || state.getBrick(state.getI() + i, state.getJ() + 1) != null)
                    setChanges(i, state.getI() + i, state.getJ());

        if (state.getFigure().isFallen())
            return false;

        setFigure(state.getI(), state.getJ() + 1, false);
        return true;
    }

}
