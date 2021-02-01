package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.ParserResult;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandOverlap {

    private CommandLineInterface getCommandLineInterface() {

        Options globalOptions = new Options()
                .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v"))
                .add(new OptionBuilder().withShortName('o').withLongName("option").hasArgument().build("option"));

        Commands commands = new Commands();

        Options optionsSpecificD = new Options()
                .add(new OptionBuilder().withShortName('d').withLongName("doption").hasArgument().build("d"));
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C", "D").withSpecificOptions(optionsSpecificD).build());

        Options optionsSpecificB = new Options()
                .add(new OptionBuilder().withShortName('b').withLongName("boption").hasArgument().build("d"));
        commands.add(new CommandSequenceBuilder().addCommands("A", "B").withSpecificOptions(optionsSpecificB).build());

        return new CommandLineInterfaceBuilder().withGlobalOptions(globalOptions).withCommands(commands).build();
    }

//    @Test
//    public void longSequence() throws UnrecognizedArgumentException {
//
//        CommandLineInterface commandLineInterface = getCommandLineInterface();
//        String[] args = {"A", "B", "C", "D"};
//        ParserResult parserResult = commandLineInterface.parse(args);
//
//        assertTrue(parserResult.getOptionParserResultGlobal().isEmpty());
//        assertEquals(Arrays.asList(args), parserResult.getCommandList());
//        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());
//        assertTrue(parserResult.getParameterList().isEmpty());
//    }

//    @Test
//    public void shortSequence() throws UnrecognizedArgumentException {
//
//        CommandLineInterface commandLineInterface = getCommandLineInterface();
//        String[] args = {"A", "B"};
//        ParserResult parserResult = commandLineInterface.parse(args);
//
//        assertTrue(parserResult.getOptionParserResultGlobal().isEmpty());
//        assertEquals(Arrays.asList(args), parserResult.getCommandList());
//        assertTrue(parserResult.getOptionParserResultSpecific().isEmpty());
//        assertTrue(parserResult.getParameterList().isEmpty());
//    }


}
