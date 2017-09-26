package stackup.game;

abstract public class AbstractForecast implements IForecast {
    
    final protected int debth; // dept
    final protected int lenght; // length of figure
    final protected IFigure[] figures;    

    public AbstractForecast(int debth, int lenght) {
        this.debth = debth;
        this.lenght = lenght;
        figures = initFigures();
    }

    abstract protected IFigure[] initFigures();
    
    @Override
    public int getDepth() {
        return debth; 
    }

    @Override
    public int getLenght() {
        return lenght; 
    }

    @Override
    public IFigure getForecast() {
        return getForecast(0);
    }

    @Override
    public IFigure getForecast(final int i) {
        return figures[debth - i - 1];
    }
}
