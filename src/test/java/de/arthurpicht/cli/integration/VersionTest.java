package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.DefaultCommand;
import de.arthurpicht.cli.command.DefaultCommandBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.option.VersionOption;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VersionTest {

    private Cli createCli(PrintStream out, CliDescription cliDescription) {

        Options globalOptions = new Options()
                .add(new VersionOption());

        DefaultCommand dummyDefaultCommand = new DefaultCommandBuilder().build();
        Commands commands = new Commands().setDefaultCommand(dummyDefaultCommand);

        return new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .withOut(out)
                .build(cliDescription);
    }

    @Test
    public void versionByVersionTag() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        CliDescription cliDescription
                = new CliDescriptionBuilder()
                .withVersionByTag("v1.0.0")
                .build("test");

        Cli cli = createCli(out, cliDescription);
        String[] args = {"-v"};
        cli.execute(args);

        String output = outBAOS.toString();
        TestOut.println(output);

        String expectedOutput = "version v1.0.0\n";

        assertEquals(expectedOutput, output);
    }

    @Test
    public void versionByVersionAndDateTag() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        CliDescription cliDescription
                = new CliDescriptionBuilder()
                .withVersionByTag("v1.0.0", "18.02.2021")
                .build("test");

        Cli cli = createCli(out, cliDescription);
        String[] args = {"-v"};
        cli.execute(args);

        String output = outBAOS.toString();
        TestOut.println(output);

        String expectedOutput = "version v1.0.0 from 18.02.2021\n";

        assertEquals(expectedOutput, output);
    }

    @Test
    public void versionByVersionAndDateTag_versionLongName() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        CliDescription cliDescription
                = new CliDescriptionBuilder()
                .withVersionByTag("v1.0.0", "18.02.2021")
                .build("test");

        Cli cli = createCli(out, cliDescription);
        String[] args = {"--version"};
        cli.execute(args);

        String output = outBAOS.toString();
        TestOut.println(output);

        String expectedOutput = "version v1.0.0 from 18.02.2021\n";

        assertEquals(expectedOutput, output);
    }

    @Test
    public void versionByTagsAndSupplement() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        CliDescription cliDescription
                = new CliDescriptionBuilder()
                .withVersionByTag("v1.0.0", "18.02.2021", "This is a supplementary text.")
                .build("test");

        Cli cli = createCli(out, cliDescription);
        String[] args = {"-v"};
        cli.execute(args);

        String output = outBAOS.toString();
        TestOut.println(output);

        String expectedOutput = "version v1.0.0 from 18.02.2021" +
                "\nThis is a supplementary text.\n";

        assertEquals(expectedOutput, output);
    }

    @Test
    public void versionByCustomVersionText() throws CommandExecutorException, UnrecognizedArgumentException {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        CliDescription cliDescription
                = new CliDescriptionBuilder()
                .withVersionText("This is a custom version text" +
                        "\nextending more" +
                        "\nthan one line.")
                .build("test");

        Cli cli = createCli(out, cliDescription);
        String[] args = {"-v"};
        cli.execute(args);

        String output = outBAOS.toString();
        TestOut.println(output);

        String expectedOutput = "This is a custom version text" +
                "\nextending more" +
                "\nthan one line.\n";

        assertEquals(expectedOutput, output);
    }

}
