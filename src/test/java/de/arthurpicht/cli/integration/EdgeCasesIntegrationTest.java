package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.exceptions.IllegalCommandException;
import de.arthurpicht.cli.command.tree.CommandTree;
import de.arthurpicht.cli.command.tree.CommandTreeNode;
import de.arthurpicht.cli.command.tree.OneCommand;
import de.arthurpicht.cli.command.tree.RootTreeNode;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static de.arthurpicht.cli.TestOut.printStacktrace;
import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.*;

public class EdgeCasesIntegrationTest {

    @Test
    void noArg() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommand("A").build());

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

    @Test
    void headingDoubleDash_NoGlobalOptions() throws UnrecognizedArgumentException {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommand("A").build());

        CommandLineInterface commandLineInterface = new CommandLineInterface(null, commands);

        String[] args = {"--", "A"};

        try {
            commandLineInterface.parse(args);
            fail(IllegalCommandException.class.getSimpleName() + " expected.");
        } catch (IllegalCommandException e) {
            assertEquals("Double dash \"--\" not allowed here. Possible commands are: [ A ].", e.getMessage());
        }
    }

}
