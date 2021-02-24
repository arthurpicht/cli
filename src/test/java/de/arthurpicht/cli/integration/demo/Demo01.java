package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.Cli;
import de.arthurpicht.cli.CliBuilder;
import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliResult;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.arthurpicht.cli.TestOut.printStacktrace;
import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.*;

public class Demo01 {

    private Cli createCli() {

        return new CliBuilder()
                .withGlobalOptions(new Options()
                        .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v"))
                        .add(new OptionBuilder().withLongName("vv").withDescription("very verbose").build("vv"))
                        .add(new OptionBuilder().withLongName("vvv").withDescription("very very verbose").build("vvv")))
                .withCommands(new Commands()
                        .add(new CommandSequenceBuilder()
                                .addCommand("adduser")
                                .withSpecificOptions(
                                        new Options()
                                                .add(new OptionBuilder().withLongName("user").hasArgument().withDescription("username").build("user"))
                                                .add(new OptionBuilder().withLongName("password").hasArgument().withDescription("password").build("password"))
                                ).build()
                        )
                ).build("test");
    }

    @Test
    void test() {

        Cli cli = createCli();

        @SuppressWarnings("SpellCheckingInspection")
        String[] args = {"-v", "adduser", "--user", "Joe", "--password", "supersecret"};

        try {
            CliCall cliCall = cli.parse(args);
            CliResult cliResult = cliCall.getCliResult();

            assertNotNull(cliResult.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = cliResult.getOptionParserResultGlobal();

            assertEquals(1, optionParserResultGlobal.getSize());

            assertTrue(optionParserResultGlobal.hasOption("v"));
            assertFalse(optionParserResultGlobal.hasOption("vv"));
            assertFalse(optionParserResultGlobal.hasOption("vvv"));

            List<String> commandList = cliResult.getCommandParserResult().getCommandStringList();
            assertEquals(1, commandList.size());
            assertEquals("adduser", commandList.get(0));

            OptionParserResult optionParserResultSpecific = cliResult.getOptionParserResultSpecific();
            assertEquals(2, optionParserResultSpecific.getSize());
            assertTrue(optionParserResultSpecific.hasOption("user"));
            assertEquals("Joe", optionParserResultSpecific.getValue("user"));
            assertTrue(optionParserResultSpecific.hasOption("password"));
            //noinspection SpellCheckingInspection
            assertEquals("supersecret", optionParserResultSpecific.getValue("password"));

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test2() {

        Cli cli = createCli();

        String[] args = {};

        try {
            cli.parse(args);
            fail();
        } catch (UnrecognizedArgumentException e) {
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());
            printStacktrace(e);
        }
    }
}
