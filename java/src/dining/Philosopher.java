package dining;

public class Philosopher extends Thread {
	private int id = 0;
	private Chopstick[] chopsticks;
	//new comment
	// Constructs a Philosopher Thread with specified id
	// We pass the constructor an array of the Chopstick
	// objects on the table so that it can pick them up
	Philosopher(int id, Chopstick[] chopsticks) {
		this.id = id;
		this.chopsticks = chopsticks;
	}
	
	// This function should control the philosopher's
	// random switching between eating and thinking
	// endlessly
	public void run() {
		
	}
	
	// This function should have the philosopher attempt to eat
	// and then wait for available chopsticks if there are not
	// enough currently unlocked
	// Be sure to put the chopsticks back down when done eating
	public void eat() {
		
	}
	
	// This function should have the philosopher think for an 
	// indefinite amount of time
	public void think() {
		
	}
}
