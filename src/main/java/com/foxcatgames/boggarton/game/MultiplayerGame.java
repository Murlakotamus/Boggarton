package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.BOX;

import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import com.foxcatgames.boggarton.engine.Layer;
import com.foxcatgames.boggarton.game.glass.MultiplayerGlass;
import com.foxcatgames.boggarton.scenes.types.RandomTypes;
import com.foxcatgames.boggarton.scenes.types.YuckTypes;

public class MultiplayerGame extends AbstractMultiplayerGame {

    public MultiplayerGame(final Layer layer, final int x, final int y, final int width, final int height, final int prognosis, final int figureSize,
            final int setSize, final int victories, YuckTypes yuckType, final RandomTypes randomType, final Map<String, Integer> sounds) {

        super(layer, x, y, width, height, prognosis, figureSize, setSize, victories, yuckType, randomType, sounds);
        glass = new MultiplayerGlass(layer, new Vector2f(x + figureSize * BOX + 20, y), width, height, setSize, sounds);
    }

    @Override
    protected void changeHappens() {
    }
}
