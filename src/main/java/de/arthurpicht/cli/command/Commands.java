package de.arthurpicht.cli.command;

import de.arthurpicht.utils.core.collection.Sets;

import java.util.HashSet;
import java.util.Set;

public class Commands {

    private Set<Command> rootCommands;

    private Command curCommand;

    public Commands() {
        this.rootCommands = new HashSet<>();
        this.curCommand = null;
    }

    private Commands(Set<Command> rootCommands, Command curCommand) {
        this.rootCommands = rootCommands;
        this.curCommand = curCommand;
    }

    public Commands root() {
        return new Commands(this.rootCommands, null);
    }

    public boolean hasCurrentCommand() {
        return this.curCommand != null;
    }

    public Command getCurrentCommand() {
        if (this.curCommand == null) throw new NullPointerException();
        return this.curCommand;
    }

    public Commands add(String commandString) {
        CommandsPrecondition.checkPreconditionsSpecCommand(this, Sets.newHashSet(commandString));

        Command command = new OneCommand(this.curCommand, commandString);
        addCommand(command);

        return new Commands(this.rootCommands, command);
    }

    public Commands addOneOf(String... commands) {
        CommandsPrecondition.checkPreconditionsSpecCommand(this, Sets.newHashSet(commands));

        Command command = new OneOfManyCommand(this.curCommand, commands);
        addCommand(command);

        return new Commands(this.rootCommands, command);
    }

    public Commands addOpen() {
        CommandsPrecondition.checkPreconditionOpenCommand(this);

        Command command = new OpenCommand(this.curCommand);
        addCommand(command);

        return new Commands(this.rootCommands, command);
    }

    public Commands addManyOpen(int i) {
        throw new RuntimeException("NIY");
    }

    public Set<Command> getRootCommands() {
        return this.rootCommands;
    }

    public boolean isEmpty() {
        return this.rootCommands.isEmpty();
    }

    private void addCommand(Command command) {
        if (this.curCommand == null) {
            this.rootCommands.add(command);
        } else {
            this.curCommand.addNext(command);
        }

    }

    public void showStatus() {
        System.out.println("rootCommands size: " + this.rootCommands.size());
        System.out.println("curCommand: " + (this.curCommand == null ? "root" : this.curCommand.toString()));
    }

}
