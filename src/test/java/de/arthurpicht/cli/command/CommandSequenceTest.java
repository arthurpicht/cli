package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.exceptions.CommandSpecException;
import de.arthurpicht.cli.parameter.ParametersOne;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandSequenceTest {

    @Test
    public void noCommand_neg() {
        try {
            new CommandSequenceBuilder().build();
            fail(CommandSpecException.class.getSimpleName() + " expected");
        } catch (CommandSpecException e) {
            //din
        }
    }

    @Test
    public void singleCommand() {
        CommandSequence commandSequence = new CommandSequenceBuilder().addCommand("A").build();
        assertEquals("A", commandSequence.asString());
        assertEquals("A", commandSequence.getFirstCommand().getCommandString());
        assertEquals("A", commandSequence.getLastCommand().getCommandString());
        assertTrue(commandSequence.getLastCommand().isTerminated());
    }

    @Test
    public void twoCommands() {
        CommandSequence commandSequence = new CommandSequenceBuilder().addCommand("A").addCommand("B").build();
        assertEquals("A B", commandSequence.asString());
        assertEquals("A", commandSequence.getFirstCommand().getCommandString());
        assertEquals("B", commandSequence.getLastCommand().getCommandString());
        assertTrue(commandSequence.getLastCommand().isTerminated());

    }

    @Test
    public void commands() {
        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("A", "B", "C").build();
        assertEquals("A B C", commandSequence.asString());
        assertEquals("A", commandSequence.getFirstCommand().getCommandString());
        assertEquals("C", commandSequence.getLastCommand().getCommandString());
        assertTrue(commandSequence.getLastCommand().isTerminated());
    }

    @Test
    public void singleCommandAndOpenCommand() {
        CommandSequence commandSequence = new CommandSequenceBuilder().addCommand("A").addOpen().build();
        assertEquals("A *", commandSequence.asString());
        assertEquals("A", commandSequence.getFirstCommand().getCommandString());
        assertEquals("*", commandSequence.getLastCommand().toString());
        assertTrue(commandSequence.getLastCommand().isTerminated());
    }

    @Test
    public void restrictionThroughOpenCommand1_neg() {
        try {
            new CommandSequenceBuilder().addCommand("A").addOpen().addCommand("B").build();
            fail(CommandSpecException.class.getSimpleName() + " expected.");
        } catch (CommandSpecException e) {
            // din
        }
    }

    @Test
    public void restrictionThroughOpenCommand2_neg() {
        try {
            new CommandSequenceBuilder().addCommand("A").addOpen().addOpen().build();
            fail(CommandSpecException.class.getSimpleName() + " expected.");
        } catch (CommandSpecException e) {
            // din
        }
    }

    @Test
    public void illegalCombinationOfOpenCommandAndParameter() {
        try {
            new CommandSequenceBuilder().addCommand("A").addOpen().withParameters(new ParametersOne()).build();
            fail(CommandSpecException.class.getSimpleName() + " expected.");
        } catch (CommandSpecException e) {
            // din
        }
    }

}