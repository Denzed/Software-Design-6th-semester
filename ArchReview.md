# Comments

## What's good

* Some things in logic(e.g ast tree) looks like overhead in this task, but it's was easy to implement cd, ls commands.
Because, I don't need to think about all this difficult stuff in there.
* All things are well documented, so it's easy to start work with this shell. At least, on top level.

## What's is controversial

* Despite of existing image of architecture, it's not very helping to understand what is happening here. For me it's was easy, because I looked into code and was aware of the same task, but for someone else it could be more difficult.
Maybe it's easier for me just look into the code.
* During implementing PR, I noticed strange thing. Class Repl knows everything about environment and always construct this object, when executing some command.
I thought, that it's common to modify just the environment and all things related to environment -- they're encapsulated in environment.
This becomes weird, when there is a need to update user directory in cd command: I have to update directory in environment, then, retrieve this object in repl, because it's construct new environment next time.