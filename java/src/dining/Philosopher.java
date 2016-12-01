package dining;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class Philosopher implements Runnable {
    static final int maxWaitMs = 100;   //  must be > 0
    static AtomicInteger token = new AtomicInteger(0); //token used for turns
    static int instances = 0;
    static Random rand = new Random();
    AtomicBoolean end = new AtomicBoolean(false);
    int id;
    PhilosopherState state = PhilosopherState.Grab;//state whether philosopher decides so grab
    Chopstick left;
    Chopstick right;
    int timesEaten = 0;//times eaten by each philospoher, increments every time they eat
 
    Philosopher() {
        id = instances++;
        left = Main.chopsticks.get(id);
        right = Main.chopsticks.get((id+1)%Main.philosopherCount);
    }
 
    void sleep() { try { Thread.sleep(rand.nextInt(maxWaitMs)); }
        catch (InterruptedException ex) {} }
 
    void waitForChopstick(Chopstick chopstick) {
        do {
            if (chopstick.holder.get() == Chopstick.ON_TABLE) {
            	chopstick.holder.set(id);                //  holding the chopstick
                return;
            } else {                                //  someone still holds it
                sleep();                            //  check again later
            }
        } while (true);
    }
 
    public void run() {
        do {
            if (state == PhilosopherState.Thnk) {    //  thinking
                state = PhilosopherState.Grab;       // grab chopsticks
            } else { // ==PhilosopherState.Get
                if (token.get() == id) {            //  token availability
                    waitForChopstick(left);
                    waitForChopstick(right);             
                    token.set((id+2)% Main.philosopherCount);
                    state = PhilosopherState.Eats;//changes the state to eat
                    timesEaten++;
                    sleep();                          
                    left.holder.set(Chopstick.ON_TABLE);
                    right.holder.set(Chopstick.ON_TABLE);
                    state = PhilosopherState.Thnk;  //changes the state to think
                    sleep();
                } else {                    //  token.get() != id, so not my turn
                    sleep();
                }
            }
        } while (!end.get());
    }
}
 