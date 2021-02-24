package de.arthurpicht.cli.command.integration;

import de.arthurpicht.cli.Cli;
import de.arthurpicht.cli.CliBuilder;
import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliResult;
import de.arthurpicht.cli.command.CommandSequence;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.exceptions.IllegalCommandException;
import de.arthurpicht.cli.command.exceptions.InsufficientNrOfCommandsException;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandOverlap {

    private Cli createCli() {

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

        return new CliBuilder().withGlobalOptions(globalOptions).withCommands(commands).build("test");
    }

    @Test
    public void longSequence() throws UnrecognizedArgumentException {

        Cli cli = createCli();
        String[] args = {"A", "B", "C", "D"};
        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertTrue(cliResult.getOptionParserResultGlobal().isEmpty());
        assertEquals(Arrays.asList(args), cliCall.getCommandList());
        assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(cliResult.getParameterParserResult().isEmpty());
    }

    @Test
    public void shortSequence() throws UnrecognizedArgumentException {

        Cli cli = createCli();
        String[] args = {"A", "B"};
        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertTrue(cliResult.getOptionParserResultGlobal().isEmpty());
        assertEquals(Arrays.asList(args), cliCall.getCommandList());
        assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(cliResult.getParameterParserResult().isEmpty());
    }

    @Test
    public void shortSequenceWithSpecificParameters() throws UnrecognizedArgumentException {

        Cli cli = createCli();
        String[] args = {"A", "B", "-b", "value"};
        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertTrue(cliResult.getOptionParserResultGlobal().isEmpty());
        assertEquals(List.of("A", "B"), cliCall.getCommandList());
        assertEquals(1, cliResult.getOptionParserResultSpecific().getSize());
        assertTrue(cliResult.getOptionParserResultSpecific().hasOption("b"));
        assertEquals("value", cliResult.getOptionParserResultSpecific().getValue("b"));
        assertTrue(cliResult.getParameterParserResult().isEmpty());
    }

    @Test
    public void shortSequenceWithDoubleDash() throws UnrecognizedArgumentException {

        Cli cli = createCli();
        String[] args = {"A", "B", "--", "-b", "value"};
        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertTrue(cliResult.getOptionParserResultGlobal().isEmpty());
        assertEquals(List.of("A", "B"), cliCall.getCommandList());
        assertEquals(1, cliResult.getOptionParserResultSpecific().getSize());
        assertTrue(cliResult.getOptionParserResultSpecific().hasOption("b"));
        assertEquals("value", cliResult.getOptionParserResultSpecific().getValue("b"));
        assertTrue(cliResult.getParameterParserResult().isEmpty());
    }

    @Test
    public void insufficientNumberOfCommands_neg() throws UnrecognizedArgumentException {

        Cli cli = createCli();
        String[] args = {"A"};

        try {
            cli.parse(args);
            fail(InsufficientNrOfCommandsException.class.getName() + " expected");
        } catch (InsufficientNrOfCommandsException e) {
            assertEquals("Insufficient number of commands. Next command is one of: [ B ].", e.getMessage());
        }
    }

    @Test
    public void insufficientNumberOfCommandsWithOption_neg() throws UnrecognizedArgumentException {

        Cli cli = createCli();
        String[] args = {"A", "-b", "value"};

        try {
            cli.parse(args);
            fail(InsufficientNrOfCommandsException.class.getName() + " expected");
        } catch (InsufficientNrOfCommandsException e) {
            assertEquals("Insufficient number of commands. Next command is one of: [ B ].", e.getMessage());
        }
    }

    @Test
    public void insufficientNumberOfCommandsWithDoubleDash_neg() throws UnrecognizedArgumentException {

        Cli cli = createCli();
        String[] args = {"A", "--", "-b", "value"};

        try {
            cli.parse(args);
            fail(IllegalCommandException.class.getName() + " expected");
        } catch (IllegalCommandException e) {
            assertEquals("Double dash \"--\" not allowed here. Possible commands are: [ B ].", e.getMessage());
        }
    }

}
