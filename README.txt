CS4341 - Artificial Intelligence - WPI - Project 1

Akshay Thejaswi
William Hartman

---------

Experiment description:

We tested the performance of different versions of the heuristic evaluation method against
each other.

We found that our program was content to stack pieces vertically, which could easily be blocked.
We noticed however, that this method did sometimes successfully lead to wins. In order to decide
what behavior was better we tested three options: scoring all chains (vertical, horizontal, and diagonal)
quadratically, scoring only vertical and horizontal chains (i.e. no points we awarded for vertical chains),
and scoring horizontal and diagonal chains quadratically while vertical chains were scored linearly.

We found that the method scoring the different types of chains differently was best, as it balances
the benefits and drawbacks of stacking pieces vertically. This version of the heuristic won the
majority of the time. There was some variation due to the depth reached, but the data was relatively
clear.

---------

Referee Documentation:

Running the ai should be very simple.
The jar file '' is executable, and can be simply used with the referee as follows:

    Assuming the '' file is at the root of your C:\ drive. Substitute the path for the path to the '' file.
    java -jar RefereeForDebug.jar "java -jar C:\" "command for 2nd ai" 6 7 4 10 10

The player name will be 10 random numbers (this was chosen to avoid any possible name conflicts)

---------

Building the Project:
We used gradle (http://gradle.org/) to build our project. It is possible to build without it, but these instructions
assume gradle is installed.

1) Navigate to the source directory
2) with gradle installed, run 'gradle dist'. This build a .jar file, which will be placed in build/libs/
3) if you wish to run the AI with our manual debug player, run 'gradle runref'

---------
