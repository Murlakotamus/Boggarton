package stackup;

public final class Logger {
    public static final boolean IS_ACTIVE = false;
    
    private Logger(){
    }

    public static void log(final Object obj) {
        System.out.println(obj);
    }
    
    public static void err(final String string) {
        System.err.println(string);
    }
}
