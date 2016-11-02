#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>
#include <unistd.h>

#define NUM_PHILOSOPHERS 5
#define WAIT_TIME_MAX 3
#define WAIT_TIME_MIN 1

struct philosophers {
	unsigned int id;
	pthread_t thread;
	unsigned int left_stick;
	unsigned int right_stick;
};

void pickup_chopsticks(int philosopher_id){
	
}

void release_chopsticks(int philosopher_id){

}

void * run(void * arguments){
	sleep(rand() % (WAIT_TIME_MAX - WAIT_TIME_MIN) + WAIT_TIME_MIN);
	int argument = *((int *) arguments);
	printf("Philosopher #%u\n", argument);
	
	// Pick the lowest chopstick available
	// Pickup chopstick. Wait for available chopstick for other hand.
	// eat
	// release chopstick
}

struct philosophers philosophers[NUM_PHILOSOPHERS];
bool forks[NUM_PHILOSOPHERS];

int main(int argc, char * argv[]){

	srand(time(NULL));

	int arguments[NUM_PHILOSOPHERS];
	int index;
	int result;

	for(index = 0; index < NUM_PHILOSOPHERS; index++){
		arguments[index] = index;
		result = pthread_create(&philosophers[index].thread, NULL, run, &arguments[index]);		
	}

	for(index = 0; index < NUM_PHILOSOPHERS; index++){
		result = pthread_join(philosophers[index].thread, NULL);
	}
	return 0;	
}
