package de.arthurpicht.cli.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandMatcherTest {

    @Test
    void hasMatchingCommand_plausibility_positive_1() {

        Commands commands = new Commands().add("A");

        CommandMatcher commandMatcher = new CommandMatcher(commands.getRootCommands(), "A", true);

        assertTrue(commandMatcher.hasMatchingCommand());
        assertNotNull(commandMatcher.getMatchingCommand());
        assertEquals("A", commandMatcher.getMatchingCommand().getCommandName());
        assertEquals("A", commandMatcher.getMatchingCommand().getArg());

        assertFalse(commandMatcher.hasCandidates());
        assertEquals(0, commandMatcher.getMatchingCandidates().size());
    }

    @Test
    void hasMatchingCommand_plausibility_negative_1() {

        Commands commands = new Commands().add("A");

        CommandMatcher commandMatcher = new CommandMatcher(commands.getRootCommands(), "B", true);

        assertFalse(commandMatcher.hasMatchingCommand());
        assertNull(commandMatcher.getMatchingCommand());
        assertFalse(commandMatcher.hasCandidates());
        assertEquals(0, commandMatcher.getMatchingCandidates().size());
    }

    @Test
    void abbreviation_plausibility_positive_1() {

        Commands commands = new Commands().add("ABC");

        CommandMatcher commandMatcher = new CommandMatcher(commands.getRootCommands(), "A", true);

        assertTrue(commandMatcher.hasMatchingCommand());
        assertNotNull(commandMatcher.getMatchingCommand());
        assertEquals("ABC", commandMatcher.getMatchingCommand().getCommandName());
        assertEquals("A", commandMatcher.getMatchingCommand().getArg());

        assertFalse(commandMatcher.hasCandidates());
        assertEquals(0, commandMatcher.getMatchingCandidates().size());
    }

    @Test
    void abbreviation_plausibility_positive_2() {

        Commands commands = new Commands().add("ABC").root().add("AXY");

        CommandMatcher commandMatcher = new CommandMatcher(commands.getRootCommands(), "A", true);

        assertFalse(commandMatcher.hasMatchingCommand());
        assertNull(commandMatcher.getMatchingCommand());

        assertTrue(commandMatcher.hasCandidates());
        assertEquals(2, commandMatcher.getMatchingCandidates().size());

        assertTrue(commandMatcher.getMatchingCandidates().contains(new RecognizedCommand(new OneCommand(null, "ABC"), "ABC", "A")));
        assertTrue(commandMatcher.getMatchingCandidates().contains(new RecognizedCommand(new OneCommand(null, "AXY"), "AXY", "A")));

    }



}