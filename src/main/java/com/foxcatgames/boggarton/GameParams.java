package com.foxcatgames.boggarton;

public class GameParams {

    final private Integer prognosisDebth;
    final private Integer setSize;
    final private Integer figureSize;
    final private Integer score;
    final private Integer count;

    final private String playerName;
    final private String yuckName;
    final private String randomName;
    final private String priceName;

    final private Boolean virtual;

    private GameParams(final Integer prognosisDebth, final Integer setSize, final Integer figureSize, final Integer score, final Integer count,
            final String playerName, final String yuckName, String randomName, String priceName, final Boolean virtual) {

        this.prognosisDebth = prognosisDebth;
        this.setSize = setSize;
        this.figureSize = figureSize;
        this.score = score;
        this.count = count;

        this.playerName = playerName;
        this.yuckName = yuckName;
        this.randomName = randomName;
        this.priceName = priceName;

        this.virtual = virtual;
    }

    public static final class Builder {

        private Integer prognosisDebth = 0;
        private Integer setSize = 0;
        private Integer figureSize = 0;
        private Integer score = 0;
        private Integer count = 0;

        private String playerName = "";
        private String yuckName = "";
        private String randomName = "";

        private Boolean virtual = true;

        public void setPrognosisDebth(final Integer prognosisDebth) {
            this.prognosisDebth = prognosisDebth;
        }

        public Builder setSetSize(final Integer setSize) {
            this.setSize = setSize;
            return this;
        }

        public Builder setFigureSize(final Integer figureSize) {
            this.figureSize = figureSize;
            return this;
        }

        public Builder setScore(final Integer score) {
            this.score = score;
            return this;
        }

        public void setCount(final Integer count) {
            this.count = count;
        }

        public void setPlayerName(final String playerName) {
            this.playerName = playerName;
        }

        public void setYuckName(final String yuckName) {
            this.yuckName = yuckName;
        }

        public void setRandomName(final String randomName) {
            this.randomName = randomName;
        }

        public Builder setVirtual(final Boolean virtual) {
            this.virtual = virtual;
            return this;
        }

        public GameParams build() {
            String priceName = "";
            return new GameParams(prognosisDebth, setSize, figureSize, score, count, playerName, yuckName, randomName, priceName, virtual);
        }
    }

    public Integer getPrognosisDebth() {
        return prognosisDebth;
    }

    public Integer getSetSize() {
        return setSize;
    }

    public Integer getFigureSize() {
        return figureSize;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getCount() {
        return count;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getYuckName() {
        return yuckName;
    }

    public String getRandomName() {
        return randomName;
    }

    public String getPriceName() {
        return priceName;
    }

    public Boolean isVirtual() {
        return virtual;
    }

}
