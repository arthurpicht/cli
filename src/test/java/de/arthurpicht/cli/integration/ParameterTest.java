package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.ParserResult;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParametersVar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParameterTest {

    @Test
    void parameterOnly() {

        CommandLineInterface commandLineInterface = new CommandLineInterfaceBuilder()
                .withGlobalOptions(new Options()
                        .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v")))
                .withParameters(new ParametersVar(0))
                .build();

        String[] args = {"myArg"};

        try {
            ParserResult parserResult = commandLineInterface.parse(args);

            assertTrue(parserResult.getOptionParserResultGlobal().isEmpty());
            assertTrue(parserResult.getCommandList().isEmpty());
            assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());

            assertEquals(1, parserResult.getParameterList().size());
            assertEquals("myArg", parserResult.getParameterList().get(0));

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getArgumentIndex());
            fail();
        }
    }

    @Test
    void oneOptionAndParameter() {

        CommandLineInterface commandLineInterface = new CommandLineInterfaceBuilder()
                .withGlobalOptions(new Options()
                        .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v")))
                .withParameters(new ParametersVar(0))
                .build();

        String[] args = {"-v", "myArg"};

        try {
            ParserResult parserResult = commandLineInterface.parse(args);

            assertEquals(1, parserResult.getOptionParserResultGlobal().getSize());
            assertTrue(parserResult.getOptionParserResultGlobal().hasOption("v"));

            assertTrue(parserResult.getCommandList().isEmpty());
            assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());

            assertEquals(1, parserResult.getParameterList().size());
            assertEquals("myArg", parserResult.getParameterList().get(0));

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getArgumentIndex());
            fail();
        }
    }

    @Test
    void oneOptionWithArgumentAndParameter() {

        CommandLineInterface commandLineInterface = new CommandLineInterfaceBuilder()
                .withGlobalOptions(new Options()
                        .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v"))
                        .add(new OptionBuilder().withShortName('o').withLongName("option").hasArgument().build("option")))
                .withParameters(new ParametersVar(0))
                .build();

        String[] args = {"-o", "myArg", "myPara"};

        try {
            ParserResult parserResult = commandLineInterface.parse(args);

            assertEquals(1, parserResult.getOptionParserResultGlobal().getSize());
            assertTrue(parserResult.getOptionParserResultGlobal().hasOption("option"));
            assertEquals("myArg", parserResult.getOptionParserResultGlobal().getValue("option"));

            assertTrue(parserResult.getCommandList().isEmpty());
            assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());

            assertEquals(1, parserResult.getParameterList().size());
            assertEquals("myPara", parserResult.getParameterList().get(0));

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getArgumentIndex());
            fail();
        }
    }


}
