# Commands

> DRAFT

## Command structure

Commands are defined in a tree like structure by specifying a set of command
sequences. The number and length of sequences is unlimited.

For example: Specifying the two sequences *A B X* and *A B Y* results
in a situation, where *A* must be followed by *B*, which must be followed
either by *X* or *Y*.

## Command types

TODO

## Default command

Defining a default command makes the usage of commands optional. If no command
is specified by the user, the default command is bound.

Moreover, it allows for defining command line interfaces with no commands.
In that case, a default command must be specified whereas the definition of
further *commands* can be omitted.

In contrast to regular commands, a default command can not have specific options.
This is because in that case, global an specific commands can not be distinguished
syntactically.

## Command overlap

Defining command sequences which overlap each other is possible. For example:

> A B C D and A B

Both "A B" and "A B C D" are legal command sequences.

### Double dash

A double dash "--" ca be used to explicitly terminate the command sequence in cases
where an ambiguous situation would arise, e.g.:

    $ myCommand A B -- value

By applying a double dash, the argument 'value' will not be interpreted as
a command but as a parameter.

## Command executors

Implementations of the *CommandExecutor* interface can be bound to each
terminating command. It can automatically be called by *CommandLineInterface* 
in case a command sequence is specified by the end user which terminates with
the respective command.
