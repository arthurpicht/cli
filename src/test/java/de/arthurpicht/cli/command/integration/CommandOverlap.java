package de.arthurpicht.cli.command.integration;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.ParserResult;
import de.arthurpicht.cli.command.CommandSequence;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.exceptions.IllegalCommandException;
import de.arthurpicht.cli.command.exceptions.InsufficientNrOfCommandsException;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.cli.parameter.ParametersOne;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandOverlap {

    private CommandLineInterface getCommandLineInterface() {

        Options globalOptions = new Options()
                .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v"))
                .add(new OptionBuilder().withShortName('o').withLongName("option").hasArgument().build("option"));

        Commands commands = new Commands();

        Options optionsSpecificD = new Options()
                .add(new OptionBuilder().withShortName('d').withLongName("doption").hasArgument().build("d"));
        CommandSequence commandSequenceD = new CommandSequenceBuilder()
                .addCommands("A", "B", "C", "D")
                .withSpecificOptions(optionsSpecificD)
                .build();
        commands.add(commandSequenceD);

        Options optionsSpecificB = new Options()
                .add(new OptionBuilder().withShortName('b').withLongName("boption").hasArgument().build("b"));
        CommandSequence commandSequenceB = new CommandSequenceBuilder()
                .addCommands("A", "B")
                .withSpecificOptions(optionsSpecificB)
                .build();
        commands.add(commandSequenceB);

        return new CommandLineInterfaceBuilder().withGlobalOptions(globalOptions).withCommands(commands).build();
    }

    @Test
    public void longSequence() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();
        String[] args = {"A", "B", "C", "D"};
        ParserResult parserResult = commandLineInterface.parse(args);

        assertTrue(parserResult.getOptionParserResultGlobal().isEmpty());
        assertEquals(Arrays.asList(args), parserResult.getCommandList());
        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(parserResult.getParameterList().isEmpty());
    }

    @Test
    public void shortSequence() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();
        String[] args = {"A", "B"};
        ParserResult parserResult = commandLineInterface.parse(args);

        assertTrue(parserResult.getOptionParserResultGlobal().isEmpty());
        assertEquals(Arrays.asList(args), parserResult.getCommandList());
        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(parserResult.getParameterList().isEmpty());
    }

    @Test
    public void shortSequenceWithSpecificParameters() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();
        String[] args = {"A", "B", "-b", "value"};
        ParserResult parserResult = commandLineInterface.parse(args);

        assertTrue(parserResult.getOptionParserResultGlobal().isEmpty());
        assertEquals(List.of("A", "B"), parserResult.getCommandList());
        assertEquals(1, parserResult.getOptionParserResultSpecific().getSize());
        assertTrue(parserResult.getOptionParserResultSpecific().hasOption("b"));
        assertEquals("value", parserResult.getOptionParserResultSpecific().getValue("b"));
        assertTrue(parserResult.getParameterList().isEmpty());
    }

    @Test
    public void shortSequenceWithDoubleDash() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();
        String[] args = {"A", "B", "--", "-b", "value"};
        ParserResult parserResult = commandLineInterface.parse(args);

        assertTrue(parserResult.getOptionParserResultGlobal().isEmpty());
        assertEquals(List.of("A", "B"), parserResult.getCommandList());
        assertEquals(1, parserResult.getOptionParserResultSpecific().getSize());
        assertTrue(parserResult.getOptionParserResultSpecific().hasOption("b"));
        assertEquals("value", parserResult.getOptionParserResultSpecific().getValue("b"));
        assertTrue(parserResult.getParameterList().isEmpty());
    }

    @Test
    public void insufficientNumberOfCommands_neg() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();
        String[] args = {"A"};

        try {
            commandLineInterface.parse(args);
            fail(InsufficientNrOfCommandsException.class.getName() + " expected");
        } catch (InsufficientNrOfCommandsException e) {
            assertEquals("Insufficient number of commands. Next command is one of: [ B ].", e.getMessage());
        }
    }

    @Test
    public void insufficientNumberOfCommandsWithOption_neg() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();
        String[] args = {"A", "-b", "value"};

        try {
            commandLineInterface.parse(args);
            fail(InsufficientNrOfCommandsException.class.getName() + " expected");
        } catch (InsufficientNrOfCommandsException e) {
            assertEquals("Insufficient number of commands. Next command is one of: [ B ].", e.getMessage());
        }
    }

    @Test
    public void insufficientNumberOfCommandsWithDoubleDash_neg() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = getCommandLineInterface();
        String[] args = {"A", "--", "-b", "value"};

        try {
            commandLineInterface.parse(args);
            fail(IllegalCommandException.class.getName() + " expected");
        } catch (IllegalCommandException e) {
            assertEquals("Double dash \"--\" not allowed here. Possible commands are: [ B ].", e.getMessage());
        }
    }

}
