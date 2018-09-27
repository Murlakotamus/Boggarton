package com.foxcatgames.boggarton.game.figure;

import com.foxcatgames.boggarton.game.VirtualBrick;
import com.foxcatgames.boggarton.game.utils.Utils;

public class VirtualFigure extends AbstractFigure {

    public VirtualFigure(final IFigure figure) {
        super(figure.getLenght());

        for (int j = 0; j < lenght; j++)
            if (figure.getBrick(j) != null)
                bricks[j] = new VirtualBrick(figure.getBrick(j).getType());

        number = figure.getNumber();
    }

    public VirtualFigure(final int size, final int difficulty, final int[] randomType) {
        super(size);

        number = size;
        for (int j = 0; j < size; j++) {
            int value = Utils.getBrick(difficulty, randomType);
            bricks[j] = new VirtualBrick(value, true);
        }
    }
}
