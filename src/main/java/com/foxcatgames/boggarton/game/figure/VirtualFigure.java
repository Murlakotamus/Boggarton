package com.foxcatgames.boggarton.game.figure;

import com.foxcatgames.boggarton.game.IBrick;
import com.foxcatgames.boggarton.game.VirtualBrick;
import com.foxcatgames.boggarton.game.utils.Utils;

public class VirtualFigure extends AbstractFigure<VirtualBrick> {

    public <B extends IBrick, F extends IFigure<B>> VirtualFigure(final F figure) {
        super(new VirtualBrick[figure.getLenght()]);

        for (int j = 0; j < figureSize; j++)
            if (figure.getBrick(j) != null)
                bricks[j] = new VirtualBrick(figure.getBrick(j).getType());

        number = figure.getNumber();
    }

    public VirtualFigure(final int size, final int difficulty, final int[] randomType) {
        super(new VirtualBrick[size]);

        for (int j = 0; j < size; j++) {
            final int value = Utils.getBrick(difficulty, randomType);
            bricks[j] = new VirtualBrick(value, true);
        }
    }
}
