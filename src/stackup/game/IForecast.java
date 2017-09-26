package stackup.game;

public interface IForecast {

    int getDepth();
    int getLenght();

    IFigure getForecast();
    IFigure getForecast(int i);
}
