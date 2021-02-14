package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.CommandLineInterfaceCall;
import de.arthurpicht.cli.CommandLineInterfaceResult;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.common.CLISpecificationException;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.*;

public class ZeroArgs1 {

    @Test
    void zeroArgs() throws UnrecognizedArgumentException {

        CommandLineInterface commandLineInterface = new CommandLineInterfaceBuilder()
                .withGlobalOptions(new Options()
                        .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v")))
                .withCommands(new Commands().setDefaultCommand(new DefaultCommand(null, null)))
                .build("test");

        String[] args = {};

        CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.parse(args);
        CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

        assertTrue(commandLineInterfaceResult.getOptionParserResultGlobal().isEmpty());
        assertTrue(commandLineInterfaceResult.getCommandParserResult().getCommandStringList().isEmpty());
        assertTrue(commandLineInterfaceResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(commandLineInterfaceResult.getParameterParserResult().isEmpty());
        assertNull(commandLineInterfaceResult.getCommandParserResult().getCommandExecutor());
    }

    @Test
    void zeroArgsWithoutDefaultCommand_neg() {

        try {
            new CommandLineInterfaceBuilder()
                    .withGlobalOptions(new Options()
                            .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v")))
                    .build("test");

            fail(CLISpecificationException.class.getSimpleName() + " expected");

        } catch (CLISpecificationException e) {
            // din
        }
    }

}
