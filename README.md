# Conway's Game of Life Simulation
This is a simulation of Conway's Game of Life. It cover the original ruleset created by John Conway, a custom ruleset created by me, and allows for the user to create their own ruleset.
## Built With
Java with Ant  
NetBeans IDE 11.2
## How to use the program
When you run the program, you will see the main UI. Here is everything you can do with it:
### Clicking on the main board
Clicking on the board will cause the cell you click on to change state. A dead cell will become alive and a living cell will become dead.
### Start Button
This will cause the simulation to become active.
### Stop Button
This will cause the simulation to stop being active.
### Step Button and Text Field
Pressing the step button with a number in the text field will cause the simulation to fast-forward that many generations.
### Random Button
This will reset the simulation by changing the state of every cell randomly. Each cell has a 25% chance of being alive and a 75% chance of being dead.
### Clear Button
This will reset the simulation by changing the state of every cell to dead.
### Settings Button
This button takes you to a separate panel where you can modify the cell size, width and height of the board, and the ruleset being played. You may choose between the original ruleset, a custom ruleset created by me, or you may create your own rules.
#### How to create a ruleset
There are two text fields used to create a ruleset: one for when a cell is alive and another for when a cell is dead. Input any of the numbers 0 through 8 that you want into each text field (no separation is necessary) to set the number of neighbours a cell needs to have in each state in order to survive or be born. For example, inputting "23" into the Living text field and "3" into the dead text tield will give you the original Conway rules.
## Author
Evan Coupal
## Acknowledgements
John Conway - creator of Conway's Game of Life
