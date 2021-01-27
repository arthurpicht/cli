package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.tree.Command;

public class CommandSequenceIterator {

    private final CommandSequence commandSequence;
    private int index = -1;

    public CommandSequenceIterator(CommandSequence commandSequence) {
        this.commandSequence = commandSequence;
    }

    public CommandSequenceIterator(CommandSequence commandSequence, int startIndex) {
        if (startIndex >= commandSequence.getCommandList().size() || startIndex < -1)
            throw new IllegalArgumentException("Method parameter startIndex is out of bounds: "
                    + startIndex + ". commandSequence.size: " + commandSequence.getCommandList().size());
        this.commandSequence = commandSequence;
        this.index = startIndex;
    }

    public boolean hasNext() {
        return this.commandSequence.getCommandList().size() > this.index + 1;
    }

    public Command getNext() {
        this.index++;
        return this.commandSequence.getCommandList().get(this.index);
    }

}
