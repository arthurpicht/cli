package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.tree.Command;
import de.arthurpicht.cli.command.tree.OneCommand;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommandMatcherTest {

    @Test
    void hasMatchingCommand_plausibility_positive_1() {

        Set<Command> commandCandidates = new HashSet<>();
        commandCandidates.add(new OneCommand("A"));

        CommandMatcher commandMatcher = new CommandMatcher(commandCandidates, "A", true);

        assertTrue(commandMatcher.hasMatchingCommand());
        assertNotNull(commandMatcher.getMatchingCommand());
        assertEquals("A", commandMatcher.getMatchingCommand().getCommandName());
        assertEquals("A", commandMatcher.getMatchingCommand().getArg());

        assertFalse(commandMatcher.hasCandidates());
        assertEquals(0, commandMatcher.getMatchingCandidates().size());
    }

    @Test
    void hasMatchingCommand_plausibility_negative_1() {

        Set<Command> commandCandidates = new HashSet<>();
        commandCandidates.add(new OneCommand("A"));

        CommandMatcher commandMatcher = new CommandMatcher(commandCandidates, "B", true);

        assertFalse(commandMatcher.hasMatchingCommand());
        assertNull(commandMatcher.getMatchingCommand());
        assertFalse(commandMatcher.hasCandidates());
        assertEquals(0, commandMatcher.getMatchingCandidates().size());
    }

    @Test
    void abbreviation_plausibility_positive_1() {

        Set<Command> commandCandidates = new HashSet<>();
        commandCandidates.add(new OneCommand("ABC"));

        CommandMatcher commandMatcher = new CommandMatcher(commandCandidates, "A", true);

        assertTrue(commandMatcher.hasMatchingCommand());
        assertNotNull(commandMatcher.getMatchingCommand());
        assertEquals("ABC", commandMatcher.getMatchingCommand().getCommandName());
        assertEquals("A", commandMatcher.getMatchingCommand().getArg());

        assertFalse(commandMatcher.hasCandidates());
        assertEquals(0, commandMatcher.getMatchingCandidates().size());
    }

    @Test
    void abbreviation_plausibility_positive_2() {

        Set<Command> commandCandidates = new HashSet<>();
        commandCandidates.add(new OneCommand("ABC"));
        commandCandidates.add(new OneCommand("AXY"));

        CommandMatcher commandMatcher = new CommandMatcher(commandCandidates, "A", true);

        assertFalse(commandMatcher.hasMatchingCommand());
        assertNull(commandMatcher.getMatchingCommand());

        assertTrue(commandMatcher.hasCandidates());
        assertEquals(2, commandMatcher.getMatchingCandidates().size());

        assertTrue(commandMatcher.getMatchingCandidates().contains(new RecognizedCommand(new OneCommand("ABC"), "ABC", "A")));
        assertTrue(commandMatcher.getMatchingCandidates().contains(new RecognizedCommand(new OneCommand("AXY"), "AXY", "A")));
    }

}