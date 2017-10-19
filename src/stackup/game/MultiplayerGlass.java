package stackup.game;

import org.lwjgl.util.vector.Vector2f;

import stackup.Const;
import stackup.engine.Layer;
import stackup.entity.Brick;
import stackup.game.utils.Utils;

public class MultiplayerGlass extends SimpleGlass {

    private static final int STRATEGY = 0;
    private final Layer layer;
    private final int difficulty;
    private int count = 0;

    public MultiplayerGlass(final Layer layer, final Vector2f position, final int width,
            final int height, final int difficulty) {
        super(layer, position, width, height);
        this.layer = layer;
        this.difficulty = difficulty;
    }

    public void executeYuck() {
        for (int i = 0; i < state.getWidth(); i++)
            for (int j = 0; j < state.getHeight(); j++) {
                if (j > 0)
                    state.setBrick(i, j - 1, state.getBrick(i, j));
                removeBrick(i, j);
            }

        for (int i = 0; i < state.getWidth(); i++) {
            int brick;
            switch (STRATEGY) {
            case 1:
                brick = getRandomBrick();
                break;
            case 2:
                brick = getQuazyRandomBrick();
                break;
            default:
                brick = getBrick();
            }
            state.setBrick(i, state.getHeight() - 1, new Brick(brick, layer));
        }

        for (int i = 0; i < state.getWidth(); i++)
            for (int j = 0; j < state.getHeight(); j++)
                if (state.getBrick(i, j) != null)
                    addBrick(i, j);

        int delta = difficulty - 4;
        count += delta;
    }

    private int getBrick() {
        int result = count++;
        if (result >= difficulty)
            result = result % difficulty;

        return result + Const.CURRENT_SET * 10 + 1;
    }

    private int getRandomBrick() {
        return Utils.randomBrick(difficulty);
    }

    private int getQuazyRandomBrick() {
        return Utils.randomBrick(difficulty);
    }
}
