package stackup.game;

public class VirtualFigure extends AbstractFigure {

    public VirtualFigure(final IFigure figure) {
        super(figure.getLenght());

        for (int j = 0; j < lenght; j++)
            if (figure.getBrick(j) != null)
                bricks[j] = new VirtualBrick(figure.getBrick(j).getType());

        number = figure.getNumber();
    }
}
