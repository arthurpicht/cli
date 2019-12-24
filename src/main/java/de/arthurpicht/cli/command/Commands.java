package de.arthurpicht.cli.command;

import de.arthurpicht.utils.core.collection.Sets;
import de.arthurpicht.utils.core.strings.Strings;

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

    public Command getCurrentCommand() {
        if (this.curCommand == null) throw new NullPointerException();
        return this.curCommand;
    }

    public Commands add(String commandString) {

        checkPreconditions(Sets.newHashSet(commandString));
        Command command = new OneCommand(this.curCommand, commandString);
        addCommand(command);

        return new Commands(this.rootCommands, command);
    }

    public Commands addOneOf(String... commands) {

        checkPreconditions(Sets.newHashSet(commands));
        Command command = new OneOfManyCommand(this.curCommand, commands);
        addCommand(command);

        return new Commands(this.rootCommands, command);
    }

    public Commands addOpen() {

        throw new RuntimeException("NIY");

//        return this;
    }

    public Commands addManyOpen(int i) {

        throw new RuntimeException("NIY");

//        return this;
    }

    public Set<Command> getRootCommands() {
        return this.rootCommands;
    }

    private void checkPreconditions(Set<String> commandString) {

        Set<Command> commandList;
        if (this.curCommand != null) {
            commandList = this.curCommand.getNext();
        } else {
            commandList = this.rootCommands;
        }

        Set<String> allCommands = CommandsHelper.getAllCommands(commandList);
        Set<String> intersection = Sets.intersection(allCommands, commandString);

        if (intersection.size() > 0) {
            throw new CommandSpecException("The following commands are already defined: " + Strings.listing(intersection, ", ", "[", "]"));
        }



//        for (Command nextCommand : nextCommandList) {
//            List<String> nextCommandStringList = nextCommand.getCommands();
//
//
//            List<String> commandIntersection = Helper.intersection();
//        }
    }

    private void addCommand(Command command) {
        if (this.curCommand == null) {
            this.rootCommands.add(command);
        } else {
            this.curCommand.addNext(command);
        }

//        if (this.rootCommands.isEmpty()) {
//            this.rootCommands.add(command);
//        } else if (this.curCommand != null) {
//            this.curCommand.addNext(command);
//        } else {
//            throw new IllegalStateException();
//        }

//        this.curCommand = command;
    }

    public void showStatus() {
        System.out.println("rootCommands size: " + this.rootCommands.size());
        System.out.println("curCommand: " + (this.curCommand == null ? "root" : this.curCommand.toString()));
    }

}
