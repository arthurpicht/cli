package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.Cli;
import de.arthurpicht.cli.CliBuilder;
import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliResult;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParametersOne;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.*;

public class DemoParameters {

    private static final boolean OUT = false;

    @SuppressWarnings("SpellCheckingInspection")
    private Cli createCli() {

        Options globalOptions = new Options()
                .add(new OptionBuilder().withShortName('d').withLongName("debug").withDescription("debug").build("DEBUG"))
                .add(new OptionBuilder().withLongName("loglevel").hasArgument().build("LOG_LEVEL"));

        Options specificOptionsAddUser = new Options()
                .add(new OptionBuilder().withShortName('u').withLongName("username").hasArgument().build("USERNAME"))
                .add(new OptionBuilder().withShortName('p').withLongName("password").hasArgument().build("PASSWORD"));

        Options specificOptionsDeleteUser = new Options()
                .add(new OptionBuilder().withShortName('u').withLongName("username").hasArgument().build("USERNAME"))
                .add(new OptionBuilder().withLongName("doit").build("DOIT"));

        Options specificOptionsShow = new Options()
                .add(new OptionBuilder().withShortName('v').withLongName("verbose").build("VERBOSE"));

        Commands commands = new Commands()
                .add(new CommandSequenceBuilder().addCommand("add").withSpecificOptions(specificOptionsAddUser).build())
                .add(new CommandSequenceBuilder().addCommand("delete").withSpecificOptions(specificOptionsDeleteUser).build())
                .add(new CommandSequenceBuilder().addCommand("show").withSpecificOptions(specificOptionsShow).withParameters(new ParametersOne()).build());

        return new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .build("test");
    }

    @Test
    void mostSimpleCommandWithNoParameter() throws UnrecognizedArgumentException {

        Cli cli = createCli();

        String[] args = {"add"};

        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertTrue(cliResult.getOptionParserResultGlobal().isEmpty());

        List<String> commandList = cliCall.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("add", commandList.get(0));

        assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());
    }

    @Test
    void mostSimpleCommandWithParameter_neg() {

        Cli cli = createCli();

        String[] args = {"show"};

        try {
            cli.parse(args);
            fail("Parameter is missing. Exception expected.");
        } catch (UnrecognizedArgumentException e) {
            // din
            println(e.getMessage());
        }
    }

    @Test
    void mostSimpleCommandWithParameter() throws UnrecognizedArgumentException {

        Cli cli = createCli();

        String[] args = {"show", "123"};

        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertTrue(cliResult.getOptionParserResultGlobal().isEmpty());

        List<String> commandList = cliCall.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("show", commandList.get(0));

        assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());

        assertEquals(1, cliResult.getParameterParserResult().getNrOfParameters());
        assertEquals("123", cliResult.getParameterParserResult().getParameterList().get(0));
    }

    @Test
    void globalOptionAndParameter() throws UnrecognizedArgumentException {

        Cli cli = createCli();

        String[] args = {"-d", "show", "123"};

        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertFalse(cliResult.getOptionParserResultGlobal().isEmpty());
        assertTrue(cliResult.getOptionParserResultGlobal().hasOption("DEBUG"));
        assertEquals(1, cliResult.getOptionParserResultGlobal().getSize());

        List<String> commandList = cliCall.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("show", commandList.get(0));

        assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());

        assertEquals(1, cliResult.getParameterParserResult().getNrOfParameters());
        assertEquals("123", cliResult.getParameterParserResult().getParameterList().get(0));
    }

    @Test
    void globalOptionAndSpecificOptionAndParameter() throws UnrecognizedArgumentException {

        Cli cli = createCli();

        String[] args = {"-d", "show", "-v", "123"};

        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertFalse(cliResult.getOptionParserResultGlobal().isEmpty());
        assertTrue(cliResult.getOptionParserResultGlobal().hasOption("DEBUG"));
        assertEquals(1, cliResult.getOptionParserResultGlobal().getSize());

        List<String> commandList = cliCall.getCommandList();
        assertEquals(1, commandList.size());
        assertEquals("show", commandList.get(0));

        assertFalse(cliResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(cliResult.getOptionParserResultSpecific().hasOption("VERBOSE"));
        assertEquals(1, cliResult.getOptionParserResultSpecific().getSize());

        assertEquals(1, cliResult.getParameterParserResult().getNrOfParameters());
        assertEquals("123", cliResult.getParameterParserResult().getParameterList().get(0));
    }

    @Test
    void globalOptionAndSpecificOptionAndParameter_neg() {

        Cli cli = createCli();

        String[] args = {"-d", "show", "-v"};

        try {
            cli.parse(args);
            fail("Parameter is missing. Exception expected.");
        } catch (UnrecognizedArgumentException e) {
            // din
            println(e.getMessage());
        }
    }

}
