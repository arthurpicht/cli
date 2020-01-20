package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.ArgsHelper;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParameterParserExceptionTest {

    @Test
    void noArg() {

        Commands commands = new Commands();
        commands.add("A");

        ParametersOne argumentsOne = new ParametersOne();

        CommandLineInterface commandLineInterface = new CommandLineInterface(null, commands, null, argumentsOne);

        String[] args = {"A"};

        try {
            commandLineInterface.parse(args);
        } catch (UnrecognizedArgumentException e) {
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());
            e.printStackTrace();
        }
    }

}
