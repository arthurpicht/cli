package de.arthurpicht.cli.command.tree;

import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommandTreeIteratorTest {

    @Test
    public void test() {

        Commands commands = new Commands();

        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "D", "E").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "X", "Y").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "X", "Z").build());
        commands.add(new CommandSequenceBuilder().addCommands("AA", "X", "Z").build());

        CommandTreeIterator commandTreeIterator = new CommandTreeIterator(commands.getCommandTree());

        assertTrue(commandTreeIterator.hasNext());
        Set<Command> commandCandidates = commandTreeIterator.getCommandCandidates();

        assertEquals(2, commandCandidates.size());
        assertTrue(commandCandidates.contains(new OneCommand("A")));
        assertTrue(commandCandidates.contains(new OneCommand("AA")));

        commandTreeIterator.stepForward(new OneCommand("A"));
        assertTrue(commandTreeIterator.hasNext());
        commandCandidates = commandTreeIterator.getCommandCandidates();
        assertTrue(commandCandidates.contains(new OneCommand("B")));
        assertTrue(commandCandidates.contains(new OneCommand("D")));
        assertTrue(commandCandidates.contains(new OneCommand("X")));

        commandTreeIterator.stepForward(new OneCommand("X"));
        assertTrue(commandTreeIterator.hasNext());
        commandCandidates = commandTreeIterator.getCommandCandidates();
        assertTrue(commandCandidates.contains(new OneCommand("Y")));
        assertTrue(commandCandidates.contains(new OneCommand("Z")));

        commandTreeIterator.stepForward(new OneCommand("Z"));
        assertFalse(commandTreeIterator.hasNext());
    }

}