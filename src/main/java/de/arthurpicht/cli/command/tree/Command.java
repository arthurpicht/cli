package de.arthurpicht.cli.command.tree;

import java.util.Set;

public abstract class Command {

    private CommandTerminator commandTerminator;

    public Command() {
        this.commandTerminator = null;
    }

    public abstract boolean isOpen();

    public abstract String getCommandString();

    /**
     * Checks if specified command matches command definition exactly.
     *
     * @param commandString
     * @return
     */
    public abstract boolean matches(String commandString);

    /**
     * Checks if specified command is a matching abbreviation for the command definition.
     *
     * @param commandString
     * @return
     */
    public abstract Set<String> getMatchingCandidates(String commandString);

    public abstract String asString();

    public void terminate(CommandTerminator commandTerminator) {
        this.commandTerminator = commandTerminator;
    }

    public boolean isTerminated() {
        return this.commandTerminator != null;
    }

    public CommandTerminator getCommandTerminator() {
        return this.commandTerminator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return this.toString().equals(command.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}


