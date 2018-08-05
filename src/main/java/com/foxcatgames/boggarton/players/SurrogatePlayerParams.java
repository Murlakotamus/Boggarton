package com.foxcatgames.boggarton.players;

public class SurrogatePlayerParams {

    final private Integer prognosisDebth;
    final private Integer setSize;
    final private Integer figureSize;
    private Integer score;
    private Integer count;

    final private String playerName;
    final private String yuckName;
    final private String randomName;
    final private String priceName;

    private SurrogatePlayerParams(final Integer prognosisDebth, final Integer setSize, final Integer figureSize, final Integer score, final Integer count,
            final String playerName, final String yuckName, String randomName, String priceName) {

        this.prognosisDebth = prognosisDebth;
        this.setSize = setSize;
        this.figureSize = figureSize;
        this.score = score;
        this.count = count;

        this.playerName = playerName;
        this.yuckName = yuckName;
        this.randomName = randomName;
        this.priceName = priceName;
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
        private String priceName = "";

        public Builder setPrognosisDebth(final Integer prognosisDebth) {
            this.prognosisDebth = prognosisDebth;
            return this;
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

        public Builder setCount(final Integer count) {
            this.count = count;
            return this;
        }

        public Builder setPlayerName(final String playerName) {
            this.playerName = playerName;
            return this;
        }

        public Builder setYuckName(final String yuckName) {
            this.yuckName = yuckName;
            return this;
        }

        public Builder setRandomName(final String randomName) {
            this.randomName = randomName;
            return this;
        }

        public Builder setPriceName(final String priceName) {
            this.priceName = priceName;
            return this;
        }

        public SurrogatePlayerParams build() {
            return new SurrogatePlayerParams(prognosisDebth, setSize, figureSize, score, count, playerName, yuckName, randomName, priceName);
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
}
