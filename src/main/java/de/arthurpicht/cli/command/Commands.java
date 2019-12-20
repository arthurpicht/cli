package de.arthurpicht.cli.command;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Commands {

    private Set<Command> rootCommands;

    private Command curCommand;

    public Commands() {
        this.rootCommands = new HashSet<>();
        this.curCommand = null;
    }

    public Commands root() {
        this.curCommand = null;
        return this;
    }

    public Commands add(String commandString) {

        Command command = new OneCommand(this.curCommand, commandString);
        addCommand(command);

        return this;
    }

    public Commands addOneOf(String... commands) {

        Command command = new OneOfManyCommand(this.curCommand, commands);
        addCommand(command);

        return this;
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
        Set<String> intersection = Helper.intersection(allCommands, commandString);
        if (intersection.size() > 0) throw new CommandSpecException("");



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

        this.curCommand = command;
    }

}
