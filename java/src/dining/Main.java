package dining;
 
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
 
/*This Java implementation uses a token system. 
 *If a philosopher's number is on the token, they pick up their left and right forks.
 *Passing the token to their immediate neighbor would be pointless, 
 *so they increment the token by 2, passing it to the philosopher after their neighbor. 
 *The +2 works well for odd numbers of philosophers.
 * With wait down at 1 millisecond I get about 1.5M eats/sec running 5 philosophers, 
 * down to about 0.5M eats/sec running 25. The single token generates good availability for 80% of 5 forks, 
 * but a much lower availability % of 25 forks. 
 */
enum PhilosopherState { Grab , Eats , Thnk }

public class test {
    static final int philosopherCount = 5; //  5 philosophers
    static final int runSeconds = 10; //runtime 
    //ArrayList used to hold chopstick availability 
    static ArrayList<Chopstick> chopsticks = new ArrayList<Chopstick>();
    //ArrayList used to hold Philosopher order
    static ArrayList<Philosopher> philosophers = new ArrayList<Philosopher>();
 
    public static void main(String[] args) {
    	System.out.println("  1    2    3    4    5  ");
        for (int i = 0 ; i < philosopherCount ; i++) chopsticks.add(new Chopstick());
        for (int i = 0 ; i < philosopherCount ; i++)
            philosophers.add(new Philosopher());
        for (Philosopher p : philosophers) new Thread(p).start();
        //calculates frequency/runtime
        long endTime = System.currentTimeMillis() + (runSeconds * 1000);
 
        do {                                                    //  print status
            StringBuilder div = new StringBuilder("|");
 
            for (Philosopher p : philosophers) {
                div.append(p.state.toString());
                div.append("|");            // string used to print out with 
            }                              //  ArrayList on console 
 
            div.append("     |");
 
            for (Chopstick c : chopsticks) {
                int holder = c.holder.get();
                //shows who's holding the chopsticks
                div.append(holder==-1?"   ":String.format("P%02d",holder));
                div.append("|");
            }
 
            System.out.println(div.toString());
            try {Thread.sleep(1000);} catch (Exception ex) {}
        } while (System.currentTimeMillis() < endTime);
 
        for (Philosopher p : philosophers) p.end.set(true);
        for (Philosopher p : philosophers)
            System.out.printf("Philosopher %2d: ate %,d times, %,d/sec\n",
                p.id+1, p.timesEaten, p.timesEaten/runSeconds);
    }
