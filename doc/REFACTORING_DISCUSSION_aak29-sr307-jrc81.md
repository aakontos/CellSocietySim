####We accidentally forgot to create a single branch to commit our refactoring before we started, so we can't create a merge request with all of our changes without reverting what we have already refactored. Every merge to master after merge request !62 entitled "Adding two files for sonarqube" are what we refactored for the exercise.

Overall Changes Made
====================
Jenny added in around 10 helper methods to make the creation of the game easier to read and to get rid of duplicated code, and a lot of keys to the resource file for the placement of the buttons so that instead of magic values, they were specified elsewhere. 

Samson added in around 20 methods to help cut down on duplicated code and to simplify some of the math done on figuring out game rules. As well these changes made the game logic more apparent by making improving overall clarity of code. He also edited some magic values in the game mode classes so that the code would be far more flexible.

Alexi edited a lot of his Classes that did not have private constructors, as well as getting rid of some 'magic' values that would have made the code far less flexible.

How We Decided What to Refactor
================================
For duplicated code, we did not have that much, and for what we did need to update we made helper methods to shorten the longer duplicated ones.

For the checklist refactoring, we changed many of the magic values that were truly 'magic' values, but some of the ones that popped up we needed to keep so we left them as they were. We did our best to update most of the flexibility errors as well to keep the code easier to read and easier to implement. We also went through and changed almost all of the errors that asked us to comply with the Java Code Convention.

For general refactoring, a lot of the "code smells" section was telling us our indentation was off even though we used the automatic indentation in our IDEs. We ignored those, but did our best to improve overall readability.
