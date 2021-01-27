package de.arthurpicht.cli.integration.demo;

import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.ParserResult;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.cli.parameter.ParametersVar;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemoCommandExecutor {

    private static class AddExecutor implements CommandExecutor {
        @Override
        public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) {
            System.out.println("Adding the following items:");
            for (String item : parameterList) {
                System.out.println(item);
            }
        }
    }

    private static class DeleteExecutor implements CommandExecutor {
        @Override
        public void execute(OptionParserResult optionParserResultGlobal, List<String> commandList, OptionParserResult optionParserResultSpecific, List<String> parameterList) {
            System.out.println("Deleting the following items:");
            for (String item : parameterList) {
                System.out.println(item);
            }
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
                        .withCommandExecutor((optionParserResultGlobal, commandList, optionParserResultSpecific, parameterList) -> {
                            System.out.println("Deleting the following items:");
                            for (String item : parameterList) {
                                System.out.println(item);
                            }
                        })
                        .build()
        );

        return new CommandLineInterfaceBuilder().withCommands(commands).build();
    }

    @Test
    public void test() {

        CommandLineInterface commandLineInterface = getCommandLineInterface();

        String[] args = {"add", "item1", "item2"};

        try {
            ParserResult parserResult = commandLineInterface.execute(args);

            assertNotNull(parserResult.getOptionParserResultGlobal());
            OptionParserResult optionParserResultGlobal = parserResult.getOptionParserResultGlobal();

            assertEquals(0, optionParserResultGlobal.getSize());

            List<String> commandList = parserResult.getCommandList();
            assertEquals(1, commandList.size());
            assertEquals("add", commandList.get(0));

            OptionParserResult optionParserResultSpecific = parserResult.getOptionParserResultSpecific();
            assertEquals(0, optionParserResultSpecific.getSize());

            CommandExecutor commandExecutor = parserResult.getCommandExecutor();
            assertTrue(commandExecutor instanceof AddExecutor);

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
        }


    }
}
