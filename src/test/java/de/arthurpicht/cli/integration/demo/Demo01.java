package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceResult;
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

    private CommandLineInterface getCommandLineInterface() {

        return new CommandLineInterfaceBuilder()
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

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"-v", "adduser", "--user", "Joe", "--password", "supersecret"};

        try {
            CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
            CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

            assertNotNull(commandLineInterfaceResult.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = commandLineInterfaceResult.getOptionParserResultGlobal();

            assertEquals(1, optionParserResultGlobal.getSize());

            assertTrue(optionParserResultGlobal.hasOption("v"));
            assertFalse(optionParserResultGlobal.hasOption("vv"));
            assertFalse(optionParserResultGlobal.hasOption("vvv"));

            List<String> commandList = commandLineInterfaceResult.getCommandParserResult().getCommandStringList();
            assertEquals(1, commandList.size());
            assertEquals("adduser", commandList.get(0));

            OptionParserResult optionParserResultSpecific = commandLineInterfaceResult.getOptionParserResultSpecific();
            assertEquals(2, optionParserResultSpecific.getSize());
            assertTrue(optionParserResultSpecific.hasOption("user"));
            assertEquals("Joe", optionParserResultSpecific.getValue("user"));
            assertTrue(optionParserResultSpecific.hasOption("password"));
            assertEquals("supersecret", optionParserResultSpecific.getValue("password"));

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test2() {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {};

        try {
            commandLineInterface.parse(args);
            fail();
        } catch (UnrecognizedArgumentException e) {
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());
            printStacktrace(e);
        }
    }
}
