package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.CliResult.Status;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.HelpOption;
import de.arthurpicht.cli.option.Option;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParametersMin;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ParsingBrokenTest {

    private Cli createCli() {

        Options globalOptions = new Options()
                .add(new HelpOption())
                .add(new OptionBuilder().withShortName('v').withLongName("version").withDescription("Show version and exit.").build("VERSION"))
                .add(new OptionBuilder().withLongName("stacktrace").withDescription("Show stacktrace on error occurence.").build("STACKTRACE"))
                .add(new OptionBuilder().withLongName("loglevel").withArgumentName("loglevel").withDescription("Log level.").build("LOGLEVEL"));

        Commands commands = new Commands();
        commands.add(
                new CommandSequenceBuilder()
                        .addCommands("COMMAND_A")
                        .withSpecificOptions(
                                new Options()
                                        .add(new HelpOption())
                                        .add(new Option("A", 'A', "almost-all", false, "", "do not list implied . and .."))
                        )
                        .withParameters(new ParametersMin(1, "file", "Files to be processed."))
                        .build()
        );

        return new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build("test");

    }

    @Test
    public void breakOnHelpAsGlobalOptionOnly() throws UnrecognizedArgumentException {

        Cli cli = createCli();
        String[] args = new String[]{"-h"};

        CliResult cliResult = cli.parse(args).getCliResult();

        assertEquals(Status.BROKEN, cliResult.getStatus());
        assertTrue(cliResult.hasGlobalOptions());
        assertTrue(cliResult.getOptionParserResultGlobal().hasOption(HelpOption.ID));
        assertEquals(0, cliResult.getCommandParserResult().getCommandStringList().size());
        assertFalse(cliResult.hasSpecificOptions());
        assertFalse(cliResult.hasParameters());
    }

    @Test
    public void breakOnHelpAsGlobalOption() throws UnrecognizedArgumentException {

        Cli cli = createCli();
        String[] args = new String[]{"-h", "something"};

        CliResult cliResult = cli.parse(args).getCliResult();

        assertEquals(Status.BROKEN, cliResult.getStatus());
        assertTrue(cliResult.hasGlobalOptions());
        assertTrue(cliResult.getOptionParserResultGlobal().hasOption(HelpOption.ID));
        assertEquals(0, cliResult.getCommandParserResult().getCommandStringList().size());
        assertFalse(cliResult.hasSpecificOptions());
        assertFalse(cliResult.hasParameters());
    }



    @Test
    public void breakOnHelpAsSpecificOptionWithParameter() throws UnrecognizedArgumentException {

        Cli cli = createCli();
        String[] args = new String[]{"COMMAND_A", "-h", "something"};

        CliResult cliResult = cli.parse(args).getCliResult();

        assertEquals(Status.BROKEN, cliResult.getStatus());
        assertFalse(cliResult.hasGlobalOptions());
        assertEquals(Collections.singletonList("COMMAND_A"), cliResult.getCommandParserResult().getCommandStringList());
        assertTrue(cliResult.hasSpecificOptions());
        assertTrue(cliResult.getOptionParserResultSpecific().hasOption(HelpOption.ID));
        assertFalse(cliResult.hasParameters());
    }

    @Test
    public void breakOnHelpAsSpecificOptionWithoutParameter() throws UnrecognizedArgumentException {

        Cli cli = createCli();
        String[] args = new String[]{"COMMAND_A", "-h"};

        CliResult cliResult = cli.parse(args).getCliResult();

        assertEquals(Status.BROKEN, cliResult.getStatus());
        assertFalse(cliResult.hasGlobalOptions());
        assertEquals(Collections.singletonList("COMMAND_A"), cliResult.getCommandParserResult().getCommandStringList());
        assertTrue(cliResult.hasSpecificOptions());
        assertTrue(cliResult.getOptionParserResultSpecific().hasOption(HelpOption.ID));
        assertFalse(cliResult.hasParameters());
    }


}