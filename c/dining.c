#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>
#include <unistd.h>
#include <semaphore.h>

#define NUM_PHILOSOPHERS 5
#define WAIT_TIME_MAX 3
#define WAIT_TIME_MIN 1

#define philosopher pthread_t
#define chopstick pthread_mutex_t

philosopher philosophers[NUM_PHILOSOPHERS];
chopstick chopsticks[NUM_PHILOSOPHERS];

// Think for a random amount of time between 1 and 3 seconds
void think(int id){
	// Think for a random amount of time
	printf("Philosopher %u is thinking...\n", id);
	sleep(rand() % WAIT_TIME_MAX + WAIT_TIME_MIN);
}


// Eat for a random amount of time between 1 and 3 seconds
void eat(int id){
	// Array to store our chopstick id's
	int c[2];
	int index;
	
	// For now lets assign both hands to one chopstick
	c[0] = id;
	c[1] = id;
	
	// Randomly choose which hand will grab the other chopstick
	c[rand() % 2] = (id + 1) % NUM_PHILOSOPHERS;

	// Attempt to grab chopsticks and do so until we have both of them
	for(index = 0; index < 2; index++){
		pthread_mutex_lock(&chopsticks[c[index]]);
		//printf("Philosopher %u has chopstick %u...\n", id, c[index]);
		//sleep(1);
	}
	
	// Print that we have both chopsticks
	printf("Philosopher %u has chopstick %u and %u.\n", id, c[0], c[1]);	
	
	// Eat
	sleep(rand() % WAIT_TIME_MAX + WAIT_TIME_MIN);

	// Release both chopsticks
	for(index = 0; index < 2; index++){
		pthread_mutex_unlock(&chopsticks[c[index]]);
	}
}


// The function all philosophers will run
void * run(void * arguments){
	// Grab arguments
	int argument = *((int *) arguments);
	
	// In an endless cycle think and then eat.
	while(1){
		think(argument);
		eat(argument);
	}
}


int main(int argc, char * argv[]){
	// Set up the seed for random numbers
	srand(time(NULL));
	
	// The array our arguments will be stored in
	// Just an array for philosopher id's
	int arguments[NUM_PHILOSOPHERS];
	int index;
	int result;

	// Initialize the Mutex
	for(index = 0; index < NUM_PHILOSOPHERS; index++){
		pthread_mutex_init(&chopsticks[index], NULL);
	}
	
	// Start each thread with the function and pass parameters to it
	for(index = 0; index < NUM_PHILOSOPHERS; index++){
		arguments[index] = index;
		result = pthread_create(&philosophers[index], NULL, run, &arguments[index]);		
	}

	// Join all threads
	for(index = 0; index < NUM_PHILOSOPHERS; index++){
		result = pthread_join(philosophers[index], NULL);
	}

	return 0;	
}
