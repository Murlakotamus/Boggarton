package com.foxcatgames.boggarton.game;

import static com.foxcatgames.boggarton.Const.CYCLE;
import static com.foxcatgames.boggarton.Const.SHIFT;

import java.util.Map;

import com.foxcatgames.boggarton.Sound;
import com.foxcatgames.boggarton.game.utils.ICommand;
import com.foxcatgames.boggarton.game.utils.OuterCommand;
import com.foxcatgames.boggarton.game.utils.Pair;

public class GameAutomation {

    private final Map<String, Integer> sounds;
    protected final OuterCommand command = new OuterCommand();

    protected boolean turnFinished;

    public boolean processStage(final StageItem stage) {
        switch (stage) {
        case NEXT:
            turnFinished = false;
        case APPEAR:
        case FALL:
            executeSoundCommand();
            break;
        case SET:
            if (!turnFinished) {
                executeCommand();
                Thread.yield();
                return false;
            }
            break;
        default:
        }
        return true;
    }

    public GameAutomation(final Map<String, Integer> sounds) {
        this.sounds = sounds;
    }

    public void executeSoundCommand() {
        if (!(sounds(SHIFT) || sounds(CYCLE)))
            executeCommand();
    }

    private boolean sounds(final String soundSource) {
        return Sound.isPlaying(sounds.get(soundSource));
    }

    public void executeCommand() {
        synchronized (command) {
            if (command.getCommand() != null)
                command.execute();
            command.notify();
        }
    }

    public void sendCommand(final ICommand cmd) throws InterruptedException {
        synchronized (command) {
            while (command.getCommand() != null)
                command.wait();
            command.setCommand(cmd);
            command.notify();
        }
    }

    public void setGameOver(final IAutomatedGame game, final Pair<?, ?> gamestateBuffer) {
        synchronized (gamestateBuffer) {
            game.setSimpleGameOver(game);
            gamestateBuffer.notify();
            executeCommand();
        }
    }

    public void finishTurn() {
        turnFinished = true;
    }
}
