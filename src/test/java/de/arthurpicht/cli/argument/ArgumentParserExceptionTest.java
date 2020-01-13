package de.arthurpicht.cli.argument;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.CLIArgsHelper;
import de.arthurpicht.cli.common.UnrecognizedCLArgumentException;
import org.junit.jupiter.api.Test;

public class ArgumentParserExceptionTest {

    @Test
    void noArg() {

        Commands commands = new Commands();
        commands.add("A");

        ArgumentsOne argumentsOne = new ArgumentsOne();

        CommandLineInterface commandLineInterface = new CommandLineInterface(null, commands, null, argumentsOne);

        String[] args = {"A"};

        try {
            commandLineInterface.parse(args);
        } catch (UnrecognizedCLArgumentException e) {
            System.out.println(CLIArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());
            e.printStackTrace();
        }


    }
}
