package com.foxcatgames.boggarton.game;

public enum Stage {

    YUCK_PAUSE(null),
    COMPRESS(null),
    PROCESS(COMPRESS),
    CRASH(PROCESS),
    SET(PROCESS),
    FALL(SET),
    APPEAR(FALL),
    NEXT(APPEAR),
    START(NEXT),
    YUCK(YUCK_PAUSE);

    private final Stage nextStage;

    private Stage(final Stage nextStage) {
        this.nextStage = nextStage;
    }

    public Stage getNextStage(final boolean reactionDetected) {
        if (this == COMPRESS)
            if (reactionDetected)
                return Stage.CRASH;
            else
                return Stage.NEXT;
        return nextStage;
    }

    public Stage getNextStage(final boolean reactionDetected, final boolean hasYucks) {
        switch (this) {
        case COMPRESS:
            if (reactionDetected)
                return Stage.CRASH;
            else
                if (hasYucks)
                    return Stage.YUCK;
                else
                    return Stage.NEXT;
        case YUCK_PAUSE:
            if (hasYucks)
                return Stage.YUCK;
            else
                return Stage.PROCESS;
        default:
            return nextStage;
        }
    }
}
