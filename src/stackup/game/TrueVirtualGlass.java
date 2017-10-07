package stackup.game;

public class TrueVirtualGlass extends AbstractVirtualGlass {

    public TrueVirtualGlass(final IGlassState glass) {
        super(glass);
    }

    @Override
    public boolean moveDown() {
        boolean changes = false;
        for (int i = 0; i < state.getFigure().getLenght(); i++)
            if (state.getFigure().getBrick(i) != null)
                if (state.getJ() + 1 == state.getHeight()
                        || state.getBrick(state.getI() + i, state.getJ() + 1) != null) {
                    changes = true;
                    setChanges(i, state.getI() + i, state.getJ());
                }
        if (state.getFigure().isFallen())
            return false;

        setFigure(state.getI(), state.getJ() + 1, changes);
        return true;
    }
}
