# An implementation that solves the Dining
# Philosopher's problem using the Resource Hierarchy
# Solution proposed by Djikstra
import threading
import os
import time
from random import randint

# Number of philosophers as well as chopsticks
gNumPhilosophers = 5

# Lists of the philosophers(threads) and the chopsticks(locks)
gPhilosophers = []
gChopsticks = []

class Philosopher(threading.Thread):
	def __init__(self, id):
		threading.Thread.__init__(self)
		# Identifier used for printing the philosopher number
		self.id = id

	def run(self):
		# Assign left and right chopstick
		# Table visualization:
		leftChopstick = self.id
		rightChopstick = (self.id + 1) % gNumPhilosophers
		chopsticks = ChopstickPair(leftChopstick, rightChopstick)

		# Alternate between eating and 
		# thinking an arbitrary amount of time
		while True:
			# Think for some amount of time
			# between 0 and 15 seconds
			thinkingTime = randint(0, 15)
			time.sleep(thinkingTime)
			print "Philosopher {} finished thinking after {} second(s).".format(
				self.id, thinkingTime)

			# Attempt to eat
			# Keep track of how long the Philosopher waits
			beginWaitTime = time.time()
			chopsticks.pickUp()
			endWaitTime = time.time()
			
			totalWaitTime = endWaitTime - beginWaitTime

			# Eat for a short amount of time
			eatingTime = randint(0, 10)
			time.sleep(eatingTime)
			print "Philosopher {} ate for {} seconds. (Waited: {})".format(
				self.id, eatingTime, totalWaitTime)
			chopsticks.putDown()

			#repeat

# A class that defines a pair of chopsticks, requiring the index of the left
# chopstick to be less than the index of the right. This prevents deadlock.
class ChopstickPair:
	def __init__(self, leftChopstickIdx, rightChopstickIdx):
		# Order chopsticks by index to prevent deadlock
		if leftChopstickIdx > rightChopstickIdx:
			leftChopstickIdx, rightChopstickIdx = rightChopstickIdx, leftChopstickIdx
		self.firstChopstick = gChopsticks[leftChopstickIdx]
		self.secondChopstick = gChopsticks[rightChopstickIdx]

	def pickUp(self):
		# Acquire both chopstick locks by starting with lower idx
		self.firstChopstick.acquire()
		self.secondChopstick.acquire()

	def putDown(self):
		# Release the locks in any order
		self.firstChopstick.release()
		self.secondChopstick.release()


if __name__ == "__main__":
	# Initialize philosophers and chopsticks
	for i in range(gNumPhilosophers):
		gPhilosophers.append(Philosopher(i))
		gChopsticks.append(threading.Lock())

	# Start all philosophers thinking/eating cycle
	for philosopher in gPhilosophers:
		philosopher.start()

	# Allow CTRL + C to exit
	try:
		while True: time.sleep(0.1)
	except(KeyboardInterrupt, SystemExit):
		os._exit(0)
