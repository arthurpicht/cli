package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.InfoDefaultCommand;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.HelpOption;
import de.arthurpicht.cli.option.ManOption;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InfoDefaultCommandTest {

    @Test
    public void full() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        Options globalOptions = new Options()
                .add(new HelpOption())
                .add(new ManOption());

        Commands commands = new Commands()
                .add(new CommandSequenceBuilder().addCommands("A", "B").withSpecificOptions(new Options().add(new HelpOption())).build())
                .setDefaultCommand(new InfoDefaultCommand());

        CliDescription cliDescription = new CliDescriptionBuilder()
                .withDescription("a dummy description\nwith a second line")
                .build("myExec");

        Cli cli = new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .withOut(out)
                .build(cliDescription);

        String[] args = {};
        cli.execute(args);

        String output = outBAOS.toString();
        TestOut.println(output);

        assertEquals("myExec - a dummy description\n" +
                "Call \"myExec --help\" or \"myExec --man\" for more info.\n", output);
    }

    @Test
    public void helpManNoDescription() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        Options globalOptions = new Options()
                .add(new HelpOption())
                .add(new ManOption());

        Commands commands = new Commands()
                .add(new CommandSequenceBuilder().addCommands("A", "B").withSpecificOptions(new Options().add(new HelpOption())).build())
                .setDefaultCommand(new InfoDefaultCommand());

        Cli cli = new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .withOut(out)
                .build("myExec");

        String[] args = {};
        cli.execute(args);

        String output = outBAOS.toString();
        TestOut.println(output);

        assertEquals("myExec. Call \"myExec --help\" or \"myExec --man\" for more info.\n", output);
    }

    @Test
    public void helpNoDescription() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        Options globalOptions = new Options()
                .add(new HelpOption());

        Commands commands = new Commands()
                .add(new CommandSequenceBuilder().addCommands("A", "B").withSpecificOptions(new Options().add(new HelpOption())).build())
                .setDefaultCommand(new InfoDefaultCommand());

        Cli cli = new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .withOut(out)
                .build("myExec");

        String[] args = {};
        cli.execute(args);

        String output = outBAOS.toString();
        TestOut.println(output);

        assertEquals("myExec. Call \"myExec --help\" for more info.\n", output);
    }

    @Test
    public void manNoDescription() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        Options globalOptions = new Options()
                .add(new ManOption());

        Commands commands = new Commands()
                .add(new CommandSequenceBuilder().addCommands("A", "B").withSpecificOptions(new Options().add(new HelpOption())).build())
                .setDefaultCommand(new InfoDefaultCommand());

        Cli cli = new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .withOut(out)
                .build("myExec");

        String[] args = {};
        cli.execute(args);

        String output = outBAOS.toString();
        TestOut.println(output);

        assertEquals("myExec. Call \"myExec --man\" for more info.\n", output);
    }

}
