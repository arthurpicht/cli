package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.exceptions.IllegalCommandException;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.printStacktrace;
import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class EdgeCasesIntegrationTest {

    @Test
    void noArg() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommand("A").build());

        CommandLineInterfaceBuilder commandLineInterfaceBuilder = new CommandLineInterfaceBuilder()
                .withCommands(commands);

        CommandLineInterface commandLineInterface = commandLineInterfaceBuilder.build("test");

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

    @Test
    void headingDoubleDash_NoGlobalOptions() throws UnrecognizedArgumentException {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommand("A").build());

        CommandLineInterfaceBuilder commandLineInterfaceBuilder = new CommandLineInterfaceBuilder()
                .withCommands(commands);

        CommandLineInterface commandLineInterface = commandLineInterfaceBuilder.build("test");

        String[] args = {"--", "A"};

        try {
            commandLineInterface.parse(args);
            fail(IllegalCommandException.class.getSimpleName() + " expected.");
        } catch (IllegalCommandException e) {
            assertEquals("Double dash \"--\" not allowed here. Possible commands are: [ A ].", e.getMessage());
        }
    }

    @Test
    void trailingDoubleDash_NoGlobalOptions() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommand("A").build());

        CommandLineInterfaceBuilder commandLineInterfaceBuilder = new CommandLineInterfaceBuilder()
                .withCommands(commands);

        CommandLineInterface commandLineInterface = commandLineInterfaceBuilder.build("test");

        String[] args = {"A", "--"};

        try {
            commandLineInterface.parse(args);
            fail(UnrecognizedArgumentException.class.getSimpleName() + " expected.");
        } catch (UnrecognizedArgumentException e) {
            assertEquals("Unrecognized argument: --", e.getMessage());
        }
    }


}
