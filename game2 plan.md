[ ] Getting better at programming - what language should I use to get better?
- Realm of Racket

[x] How do you "export" the game to a format that is playable by other people? 
- Build to an executable JAR file


PROBLEM WITH STATES- having to make enormous constructors—
is there a way to fix this issue? 
Does generalizing the state into its own class solve these problems? Sounds like a design pattern...  
- No way to do this in Java... must change the constructor for all new instances.
-- Racket can do this!

[x] What kind of programming is HTML/CSS anyway??
- markup languages— no real programming because it's just descriptions of things— no control flow etc...
[x] How do you test HTML/CSS ? Is there a way to do that? 
- impossible to test because programs don't have "errors" just unintended side-effects 

#Game2 
"Finite state machine"

What can be generalized between Mob and Player? 
Mobs don't need to contain hp etc... is it worth generalizing if there will be more code to write/irrelevant fields???

TODO:
Does the game logic need to rely on onTick? How do you make the game WAIT?
- don't use sleep
- To do nothing: Just return the same instance 
	- Hold onto ticks by adding a value to a field at every onTick...
	- Only hold onto a certain amount of ticks, display a message for the first... 10 ticks for example.
	- 

[x] Console version: make a boolean that is updated for every World 
- Change all of the constructors 

[x] Fix all new instances of FieldWorld to contain new states
[x] TreasureCoord needs to be stored across states...

[x] Add treasure collision detection
	- Use adapter function for this
[ ] Research key presses
[x] Key handling for BattleWorld

##Drawing Graphics
[ ] GRAPHICS - 

## TESTING
Test only functions that do important things/give output values

###Actor
DONE TESTING!

###FieldWorld
 - movement
 - deciding on random battles


###BattleWorld - 
Hard to test because reliance on random logic
 - Cannot access specific fields of World 

###MessageWorld - 
Difficult to test for same reasons as BattleWorld



## Command Line version??
- print lines for all actions

##WRITE TESTS
[x] toString() everything that contains data

Decide which functions to test 

Cannot test most Worlds because they rely on key inputs?	

# Later additions
[ ] Generalize FieldObject data container
[ ] Sound
[ ] Beyonce mode???

Generalize FieldWorld states?
Generalize player states?


#Turn in checklist
[ ] Manual
[ ] Source Code
[ ] Console Transcript
[ ] Essay
"You will be graded on the persuasiveness of your essay."