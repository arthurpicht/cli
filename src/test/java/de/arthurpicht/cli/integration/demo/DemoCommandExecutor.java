package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.parameter.ParameterParserResult;
import de.arthurpicht.cli.parameter.ParametersVar;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemoCommandExecutor {

    private static class AddExecutor implements CommandExecutor {

        private boolean isExecuted = false;

        @Override
        public void execute(CommandLineInterfaceCall commandLineInterfaceCall) {
            System.out.println("Adding the following items:");
            for (String item : commandLineInterfaceCall.getParameterParserResult().getParameterList()) {
                System.out.println(item);
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
        public void execute(CommandLineInterfaceCall commandLineInterfaceCall) {
            System.out.println("Deleting the following items:");
            for (String item : commandLineInterfaceCall.getParameterParserResult().getParameterList()) {
                System.out.println(item);
            }
            isExecuted = true;
        }

        public boolean isExecuted() {
            return this.isExecuted;
        }
    }

    private CommandLineInterface getCommandLineInterface() {
        Commands commands = new Commands();
        commands.add(
                new CommandSequenceBuilder()
                        .addCommands("add")
                        .withParameters(new ParametersVar(1))
                        .withCommandExecutor(new AddExecutor())
                        .build()
        );
        commands.add(
                new CommandSequenceBuilder()
                        .addCommands("delete")
                        .withParameters(new ParametersVar(1))
                        .withCommandExecutor((commandLineInterfaceCall) -> {
                            System.out.println("Deleting the following items:");
                            for (String item : commandLineInterfaceCall.getParameterParserResult().getParameterList()) {
                                System.out.println(item);
                            }
                        })
                        .build()
        );

        return new CommandLineInterfaceBuilder().withCommands(commands).build("test");
    }

    @Test
    public void execute() {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"add", "item1", "item2"};

        try {
            CommandLineInterfaceCall commandLineInterfaceCall = commandLineInterface.execute(args);
            CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

            assertNotNull(commandLineInterfaceResult.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = commandLineInterfaceResult.getOptionParserResultGlobal();

            assertEquals(0, optionParserResultGlobal.getSize());

            List<String> commandList = commandLineInterfaceResult.getCommandParserResult().getCommandStringList();
            assertEquals(1, commandList.size());
            assertEquals("add", commandList.get(0));

            OptionParserResult optionParserResultSpecific = commandLineInterfaceResult.getOptionParserResultSpecific();
            assertEquals(0, optionParserResultSpecific.getSize());

            CommandExecutor commandExecutor = commandLineInterfaceResult.getCommandParserResult().getCommandExecutor();
            assertTrue(commandExecutor instanceof AddExecutor);
            assertTrue(((AddExecutor) commandExecutor).isExecuted);

        } catch (UnrecognizedArgumentException | CommandExecutorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseAndExecuteInSequence() {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"add", "item1", "item2"};

        CommandLineInterfaceCall commandLineInterfaceCall = null;

        try {
            commandLineInterfaceCall = commandLineInterface.parse(args);
            CommandLineInterfaceResult commandLineInterfaceResult = commandLineInterfaceCall.getCommandLineInterfaceResult();

            assertNotNull(commandLineInterfaceResult.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = commandLineInterfaceResult.getOptionParserResultGlobal();

            assertEquals(0, optionParserResultGlobal.getSize());

            List<String> commandList = commandLineInterfaceResult.getCommandParserResult().getCommandStringList();
            assertEquals(1, commandList.size());
            assertEquals("add", commandList.get(0));

            OptionParserResult optionParserResultSpecific = commandLineInterfaceResult.getOptionParserResultSpecific();
            assertEquals(0, optionParserResultSpecific.getSize());

            CommandExecutor commandExecutor = commandLineInterfaceResult.getCommandParserResult().getCommandExecutor();
            assertTrue(commandExecutor instanceof AddExecutor);
            assertFalse(((AddExecutor) commandExecutor).isExecuted);

        } catch (UnrecognizedArgumentException e) {
            fail(e);
        }

        assertNotNull(commandLineInterfaceCall);

        try {
            CommandExecutor commandExecutor = commandLineInterfaceCall.getCommandLineInterfaceResult().getCommandParserResult().getCommandExecutor();

            commandLineInterface.execute(commandLineInterfaceCall);
            assertTrue(((AddExecutor) commandExecutor).isExecuted);

        } catch (CommandExecutorException e) {
            fail(e);
        }
    }

}
