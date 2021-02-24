package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.Cli;
import de.arthurpicht.cli.CliBuilder;
import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CliResult;
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

        Cli cli = new CliBuilder()
                .withGlobalOptions(new Options()
                        .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v")))
                .withCommands(new Commands().setDefaultCommand(new DefaultCommand(null, null, null)))
                .build("test");

        String[] args = {};

        CliCall cliCall = cli.parse(args);
        CliResult cliResult = cliCall.getCliResult();

        assertTrue(cliResult.getOptionParserResultGlobal().isEmpty());
        assertTrue(cliResult.getCommandParserResult().getCommandStringList().isEmpty());
        assertTrue(cliResult.getOptionParserResultSpecific().isEmpty());
        assertTrue(cliResult.getParameterParserResult().isEmpty());
        assertNull(cliResult.getCommandParserResult().getCommandExecutor());
    }

    @Test
    void zeroArgsWithoutDefaultCommand_neg() {

        try {
            new CliBuilder()
                    .withGlobalOptions(new Options()
                            .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v")))
                    .build("test");

            fail(CLISpecificationException.class.getSimpleName() + " expected");

        } catch (CLISpecificationException e) {
            // din
        }
    }

}
