package com.foxcatgames.boggarton.game;

public enum StageItem {

    YUCK_PAUSE(null), COMPRESS(null), PROCESS(COMPRESS), CRASH(PROCESS), SET(PROCESS), FALL(SET), APPEAR(FALL), NEXT(APPEAR), START(NEXT), YUCK(YUCK_PAUSE);

    private final StageItem nextStage;

    private StageItem(final StageItem nextStage) {
        this.nextStage = nextStage;
    }

    public StageItem getNextStage(final boolean reactionDetected) {
        if (this == COMPRESS)
            if (reactionDetected)
                return StageItem.CRASH;
            else
                return StageItem.NEXT;
        return nextStage;
    }

    public StageItem getNextStage(final boolean reactionDetected, final boolean hasYucks) {
        switch (this) {
        case COMPRESS:
            if (reactionDetected)
                return StageItem.CRASH;
            else if (hasYucks)
                return StageItem.YUCK;
            else
                return StageItem.NEXT;
        case YUCK_PAUSE:
            if (hasYucks)
                return StageItem.YUCK;
            else
                return StageItem.PROCESS;
        default:
            return nextStage;
        }
    }
}
