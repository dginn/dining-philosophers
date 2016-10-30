# dining-philosophers
A simple solution to the Dining Philosophers problem for concurrent applications for COSC-4430 at UTPB.

![Table with 5 chopsticks and plates](http://d2vlcm61l7u1fs.cloudfront.net/media%2Fdc9%2Fdc997db3-60bd-423d-b39e-bb0c507cb53c%2FphpqTXBeh.png "Table with 5 chopsticks and plates")

# The Problem
There is a dining room containing a circular table with five chairs. At each chair is a plate, and between each plate is a single chopstick. In the middle of the table is a bowl of rice. Near the room are five philosophers who spend most of their time thinking, but who occasionally get hungry and need to eat so they can think some more.

In order to eat, a philosopher must sit at the table, pick up the two chopsticks to the left and right of a plate, and then serve and eat the rice on the plate.

A philosopher may think indefinitely, but every philosopher who eats will eventually finish. Philosophers may pickup and putdown their chopsticks in either order, or nondeterministically, but these are atomic actions, and two philosophers cannot use a single chopstick at the same time.

The goal of the problem is to design a protocol to satisfy the liveness condition: any philosopher who tries to eat, eventually does.

# Our Protocol

# Implementation
