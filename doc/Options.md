# Options

> Draft

Options can either be defined as `global options` or `specific options`.

## Types of options

Options can be 'boolean' (set or not set), or they can have an argument.

## Notation

Options can have a long name, or a short name. 

## Option clustering

ToDo 

## Built-in options

Built-in options are: `version`, `help` and `man`. All three built-in options
are `breaking options`, which means that the parsing process is stopped when
such a option is found. Moreover, all three built-in options are connected to
a generic CommandExecutor, that is triggered automatically.
