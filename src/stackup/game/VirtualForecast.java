package stackup.game;


public class VirtualForecast extends AbstractForecast implements IForecast {

    public VirtualForecast(final IForecast forecast) {
        super(forecast.getDepth(), forecast.getLenght());
        for (int i = 0; i < prognosis; i++)
            figures[i] = new VirtualFigure(forecast.getForecast(i));
    }

    @Override
    protected IFigure[] initFigures() {
        return new VirtualFigure[prognosis];
    }

}
