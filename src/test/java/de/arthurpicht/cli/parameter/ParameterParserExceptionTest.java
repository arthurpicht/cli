package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.printStacktrace;
import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParameterParserExceptionTest {

    @Test
    void noArg() {

        Commands commands = new Commands();
        commands.add("A");

        CommandLineInterface commandLineInterface = new CommandLineInterface(null, commands);

        String[] args = {"A"};

        try {
            commandLineInterface.parse(args);
        } catch (UnrecognizedArgumentException e) {
            println("ArgumentIndex: " + e.getArgumentIndex());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());
            printStacktrace(e);
            fail(e);
        }
    }

}
