package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.exceptions.CommandSpecException;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.utils.core.collection.Sets;

import java.util.HashSet;
import java.util.Set;

public class Commands {

    private final Set<Command> rootCommands;
    private final Set<Command> curCommands;

    public Commands() {
        this.rootCommands = new HashSet<>();
        this.curCommands = new HashSet<>();
    }

    private Commands(Set<Command> rootCommands, Set<Command> curCommands) {
        this.rootCommands = rootCommands;
        this.curCommands = curCommands;
    }

    public Commands root() {
        return new Commands(this.rootCommands, new HashSet<>());
    }

    public boolean isEmpty() {
        return this.rootCommands.isEmpty();
    }

    public Set<Command> getRootCommands() {
        return this.rootCommands;
    }

    public boolean hasCurrentCommands() {
        return !this.curCommands.isEmpty();
    }

    public Set<Command> getCurrentCommands() {
        if (!hasCurrentCommands()) throw new RuntimeException("No current commands.");
        return this.curCommands;
    }

    public Commands trace(String commandString) {
        if (this.isEmpty())
            throw new CommandSpecException("Commands is empty.");
        if (this.curCommands.size() > 1)
            throw new CommandSpecException("Ambiguous trace operation. Commands comprises more than one.");
        if (this.curCommands.size() == 0) {
            for (Command command : this.rootCommands) {
                if (command.asString().equals(commandString)) {
                    return new Commands(this.rootCommands, Sets.newHashSet(command));
                }
            }
        } else {
            Command command = Sets.getSomeElement(this.curCommands);
            for (Command nextCommand : command.getNext()) {
                if (nextCommand.asString().equals(commandString)) {
                    return new Commands(this.rootCommands, Sets.newHashSet(nextCommand));
                }
            }
        }
        throw new CommandSpecException("No such command to trace: " + commandString);
    }

    public Commands add(String commandString) {
        CommandsPrecondition.checkPreconditionsSpecCommand(this, Sets.newHashSet(commandString));

        Set<Command> futureCurrents = new HashSet<>();

        if (!hasCurrentCommands()) {
            Command command = new OneCommand(null, commandString);
            this.rootCommands.add(command);
            futureCurrents.add(command);
        } else {
            for (Command curCommand : this.curCommands) {
                Command command = new OneCommand(curCommand, commandString);
                curCommand.addNext(command);
                futureCurrents.add(command);
            }
        }

        return new Commands(this.rootCommands, futureCurrents);
    }

    public Commands addOneOf(String... commandStrings) {
        CommandsPrecondition.checkPreconditionsSpecCommand(this, Sets.newHashSet(commandStrings));

        Set<Command> futureCurrents = new HashSet<>();

        if (!hasCurrentCommands()) {
            for (String commandString : commandStrings) {
                Command command = new OneCommand(null, commandString);
                this.rootCommands.add(command);
                futureCurrents.add(command);
            }
        } else {
            for (Command curCommand : this.curCommands) {
                for (String commandString : commandStrings) {
                    Command command = new OneCommand(curCommand, commandString);
                    curCommand.addNext(command);
                    futureCurrents.add(command);
                }
            }
        }

        return new Commands(this.rootCommands, futureCurrents);
   }

    public Commands addOpen() {
        CommandsPrecondition.checkPreconditionOpenCommand(this);

        Set<Command> futureCurrents = new HashSet<>();

        if (!hasCurrentCommands()) {
            Command command = new OpenCommand(null);
            this.rootCommands.add(command);
            futureCurrents.add(command);
        } else {
            for (Command curCommand : this.curCommands) {
                Command command = new OpenCommand(curCommand);
                curCommand.addNext(command);
                futureCurrents.add(command);
            }
        }

        return new Commands(this.rootCommands, futureCurrents);

    }

    public Commands addManyOpen(int i) {
        throw new RuntimeException("NIY");
    }

    public Commands withSpecificOptions(Options specificOptions) {

        if (this.isEmpty()) throw new CommandSpecException("No commands defined yet.");

        for (Command command : this.curCommands) {
            command.setSpecificOptions(specificOptions);
        }

        return new Commands(this.rootCommands, this.curCommands);
    }

    public static boolean hasDefinitions(Commands commands) {
        return (commands != null && !commands.isEmpty());
    }

}
