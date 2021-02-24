package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.parameter.ParametersMin;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemoCommandExecutor {

    private static class AddExecutor implements CommandExecutor {

        private boolean isExecuted = false;

        @Override
        public void execute(CliCall cliCall) {
            TestOut.println("Adding the following items:");
            for (String item : cliCall.getParameterParserResult().getParameterList()) {
                TestOut.println(item);
            }
            isExecuted = true;
        }

        public boolean isExecuted() {
            return this.isExecuted;
        }
    }

    private static class DeleteExecutor implements CommandExecutor {

        private boolean isExecuted = false;

        @Override
        public void execute(CliCall cliCall) {
            TestOut.println("Deleting the following items:");
            for (String item : cliCall.getParameterParserResult().getParameterList()) {
                TestOut.println(item);
            }
            isExecuted = true;
        }

        public boolean isExecuted() {
            return this.isExecuted;
        }
    }

    private Cli createCli() {
        Commands commands = new Commands();
        commands.add(
                new CommandSequenceBuilder()
                        .addCommands("add")
                        .withParameters(new ParametersMin(1))
                        .withCommandExecutor(new AddExecutor())
                        .build()
        );
        commands.add(
                new CommandSequenceBuilder()
                        .addCommands("delete")
                        .withParameters(new ParametersMin(1))
                        .withCommandExecutor((cliCall) -> {
                            TestOut.println("Deleting the following items:");
                            for (String item : cliCall.getParameterParserResult().getParameterList()) {
                                TestOut.println(item);
                            }
                        })
                        .build()
        );

        return new CliBuilder().withCommands(commands).build("test");
    }

    @Test
    public void execute() {

        Cli cli = createCli();

        String[] args = {"add", "item1", "item2"};

        try {
            CliCall cliCall = cli.execute(args);
            CliResult cliResult = cliCall.getCliResult();

            assertNotNull(cliResult.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = cliResult.getOptionParserResultGlobal();

            assertEquals(0, optionParserResultGlobal.getSize());

            List<String> commandList = cliResult.getCommandParserResult().getCommandStringList();
            assertEquals(1, commandList.size());
            assertEquals("add", commandList.get(0));

            OptionParserResult optionParserResultSpecific = cliResult.getOptionParserResultSpecific();
            assertEquals(0, optionParserResultSpecific.getSize());

            CommandExecutor commandExecutor = cliResult.getCommandParserResult().getCommandExecutor();
            assertTrue(commandExecutor instanceof AddExecutor);
            assertTrue(((AddExecutor) commandExecutor).isExecuted);

        } catch (UnrecognizedArgumentException | CommandExecutorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseAndExecuteInSequence() {

        Cli cli = createCli();

        String[] args = {"add", "item1", "item2"};

        CliCall cliCall = null;

        try {
            cliCall = cli.parse(args);
            CliResult cliResult = cliCall.getCliResult();

            assertNotNull(cliResult.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = cliResult.getOptionParserResultGlobal();

            assertEquals(0, optionParserResultGlobal.getSize());

            List<String> commandList = cliResult.getCommandParserResult().getCommandStringList();
            assertEquals(1, commandList.size());
            assertEquals("add", commandList.get(0));

            OptionParserResult optionParserResultSpecific = cliResult.getOptionParserResultSpecific();
            assertEquals(0, optionParserResultSpecific.getSize());

            CommandExecutor commandExecutor = cliResult.getCommandParserResult().getCommandExecutor();
            assertTrue(commandExecutor instanceof AddExecutor);
            assertFalse(((AddExecutor) commandExecutor).isExecuted);

        } catch (UnrecognizedArgumentException e) {
            fail(e);
        }

        assertNotNull(cliCall);

        try {
            CommandExecutor commandExecutor = cliCall.getCliResult().getCommandParserResult().getCommandExecutor();

            cli.execute(cliCall);
            assertTrue(((AddExecutor) commandExecutor).isExecuted);

        } catch (CommandExecutorException e) {
            fail(e);
        }
    }

}
