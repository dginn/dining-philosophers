package dining;
import java.util.concurrent.atomic.AtomicInteger;

public class Chopstick {
	
	    public static final int ON_TABLE = -1;
	    static int instances = 0;
	    public int id;
	    //AtomicInteger used increment through threads
	    public AtomicInteger holder = new AtomicInteger(ON_TABLE);
	    Chopstick() { id = instances++; }
	}
