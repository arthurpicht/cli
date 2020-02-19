package de.arthurpicht.cli.integration;


import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.ParserResult;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class integTest1 {

    @Test
    void test() {

        CommandLineInterface commandLineInterface = new CommandLineInterfaceBuilder()
                .withGlobalOptions(new Options()
                        .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v"))
                        .add(new OptionBuilder().withLongName("vv").withDescription("very verbose").build("vv"))
                        .add(new OptionBuilder().withLongName("vvv").withDescription("very very verbose").build("vvv")))
                .withCommands(new Commands().add("adduser")
                        .withSpecificOptions(new Options()
                                .add(new OptionBuilder().withLongName("user").hasArgument().withDescription("username").build("user"))
                                .add(new OptionBuilder().withLongName("password").hasArgument().withDescription("password").build("password"))))
                .build();

        String[] args = {"-v", "adduser", "--user", "Joe", "--password", "supersecret"};

        try {
            ParserResult parserResult = commandLineInterface.parse(args);

            assertNotNull(parserResult.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = parserResult.getOptionParserResultGlobal();

            assertEquals(1, optionParserResultGlobal.getSize());

            assertTrue(optionParserResultGlobal.hasOption("v"));
            assertFalse(optionParserResultGlobal.hasOption("vv"));
            assertFalse(optionParserResultGlobal.hasOption("vvv"));

            List<String> commandList = parserResult.getCommandList();
            assertEquals(1, commandList.size());
            assertEquals("adduser", commandList.get(0));

            OptionParserResult optionParserResultSpecific = parserResult.getOptionParserResultSpecific();
            assertEquals(2, optionParserResultSpecific.getSize());
            assertTrue(optionParserResultSpecific.hasOption("user"));
            assertEquals("Joe", optionParserResultSpecific.getValue("user"));
            assertTrue(optionParserResultSpecific.hasOption("password"));
            assertEquals("supersecret", optionParserResultSpecific.getValue("password"));

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
        }


    }
}
