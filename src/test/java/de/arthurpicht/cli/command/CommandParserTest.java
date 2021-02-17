package de.arthurpicht.cli.command;

import de.arthurpicht.cli.CommandLineInterfaceResult;
import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.command.exceptions.AmbiguousCommandException;
import de.arthurpicht.cli.command.exceptions.CommandParserException;
import de.arthurpicht.cli.command.exceptions.IllegalCommandException;
import de.arthurpicht.cli.command.exceptions.InsufficientNrOfCommandsException;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.arthurpicht.cli.CommandLineInterfaceResult.Status.TEST;
import static de.arthurpicht.cli.TestOut.printStacktrace;
import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void singleCommand() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommand("A").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(
                commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            CommandLineInterfaceResult result = commandLineInterfaceResultBuilder.build(TEST);
            List<String> commandStringList = result.getCommandParserResult().getCommandStringList();

            assertEquals(1, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals(0, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            println(commandParserException.getMessage());
            fail(commandParserException);
        }

    }

    @Test
    void parse1() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(
                commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            CommandLineInterfaceResult result = commandLineInterfaceResultBuilder.build(TEST);
            List<String> commandStringList = result.getCommandParserResult().getCommandStringList();

            assertEquals(3, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            println(commandParserException.getMessage());
            fail(commandParserException);
        }
    }

    @Test
    void check2() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B").addOpen().build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(
                commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            CommandLineInterfaceResult result = commandLineInterfaceResultBuilder.build(TEST);
            List<String> commandStringList = result.getCommandParserResult().getCommandStringList();

            assertEquals(3, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            println(commandParserException.getMessage());
            fail(commandParserException);
        }
    }

    @Test
    void illegalCommand_neg() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C1").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C2").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C3").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(
                commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "something", "C2"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            fail();

        } catch (CommandParserException commandParserException) {
            assertTrue(commandParserException instanceof IllegalCommandException);
            assertEquals("something", commandParserException.getIndexedArgument());
        }
    }

    @Test
    void checkFail1() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(
                commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            CommandLineInterfaceResult result = commandLineInterfaceResultBuilder.build(TEST);
            List<String> commandStringList = result.getCommandParserResult().getCommandStringList();

            assertEquals(3, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            println(commandParserException.getMessage());
            fail(commandParserException);
        }
    }

    @Test
    void illegalCommandException_neg() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "D", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            fail();
        } catch (AmbiguousCommandException| InsufficientNrOfCommandsException e) {
            fail("Wrong exception thrown: " + e.getClass().getName());
        } catch (IllegalCommandException illegalCommandException) {
            println(illegalCommandException.getMessage());
            assertEquals("A B D D", illegalCommandException.getArgsAsString());
            assertEquals("    ^", illegalCommandException.getArgumentPointerString());
        }
    }

    @Test
    void insufficientNumberOfCommands_neg() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            fail();

        } catch (IllegalCommandException | AmbiguousCommandException e) {
            fail("Wrong exception: " + e.getClass().getName());

        } catch (InsufficientNrOfCommandsException insufficientNrOfCommandsException) {
            println(insufficientNrOfCommandsException.getMessage());
            println(insufficientNrOfCommandsException.getArgsAsString());
            println(insufficientNrOfCommandsException.getArgumentPointerString());

            assertEquals("A B", insufficientNrOfCommandsException.getArgsAsString());
            assertEquals("    ^", insufficientNrOfCommandsException.getArgumentPointerString());
            assertEquals(2, insufficientNrOfCommandsException.getArgumentIndex());

            println(insufficientNrOfCommandsException.getArgsAsString());
            println(insufficientNrOfCommandsException.getArgumentPointerString());
        }
    }

    @Test
    void checkZeroArg1_neg() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            fail("Exception expected");
        } catch (AmbiguousCommandException | IllegalCommandException e) {
            fail("Wrong exception thrown: " + e.getClass().getName());
        } catch (InsufficientNrOfCommandsException insufficientNrOfCommandsException) {
            assertEquals(
                    "Insufficient number of commands. Next command is one of: [ A ].",
                    insufficientNrOfCommandsException.getMessage());
            assertEquals(0, insufficientNrOfCommandsException.getArgumentIndex());
            assertEquals("", insufficientNrOfCommandsException.getArgsAsString());
            assertEquals("^", insufficientNrOfCommandsException.getArgumentPointerString());
        }
    }

    @Test
    void checkZeroArg2_neg() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"X"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            commandParser.parse(argumentIterator);
            fail("Exception expected.");
        } catch (AmbiguousCommandException | IllegalCommandException e) {
            fail("Wrong exception thrown: " + e.getClass().getName());
        } catch (InsufficientNrOfCommandsException e) {
            println(e.getMessage());
            assertEquals("Insufficient number of commands. Next command is one of: [ A ].", e.getMessage());
            assertEquals(1, e.getArgumentIndex());
            assertEquals("X", e.getArgsAsString());
            assertEquals("  ^", e.getArgumentPointerString());
        }
    }

    @Test
    void commandNotRecognized1_neg() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("E", "F").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            fail("Exception expected.");
        } catch (AmbiguousCommandException | InsufficientNrOfCommandsException e) {
            fail("Wrong exception thrown: " + e.getClass().getName());
        } catch (IllegalCommandException e) {
            println(e.getMessage());
            assertEquals("Command [A] not recognized. Possible commands are: [ E ].", e.getMessage());
            assertEquals(0, e.getArgumentIndex());
            assertEquals("A B C D", e.getArgsAsString());
            assertEquals("^", e.getArgumentPointerString());
        }
    }

    @Test
    void commandNotRecognized2_neg() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("E", "F").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"E", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            fail("Exception expected.");
        } catch (AmbiguousCommandException | InsufficientNrOfCommandsException e) {
            fail("Wrong exception thrown: " + e.getClass().getName());
        } catch (IllegalCommandException e) {
            println(e.getMessage());
            assertEquals("Command [B] not recognized. Possible commands are: [ F ].", e.getMessage());
            assertEquals(1, e.getArgumentIndex());
            assertEquals("E B C D", e.getArgsAsString());
            assertEquals("  ^", e.getArgumentPointerString());
        }
    }

    @Test
    void specificOptionOfSingleCommand() {

        Options optionsSpecific = new Options()
                .add(new OptionBuilder().withShortName('x').withLongName("x").withDescription("this is x").build("x"));

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommand("A").withSpecificOptions(optionsSpecific).build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            CommandLineInterfaceResult cliResult = commandLineInterfaceResultBuilder.build(TEST);
            List<String> commandStringList = cliResult.getCommandParserResult().getCommandStringList();

            assertEquals(1, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals(0, argumentIterator.getIndex());

            Options optionsSpecificBack = cliResult.getCommandParserResult().getSpecificOptions();
            assertFalse(optionsSpecificBack.isEmpty());
            assertTrue(optionsSpecificBack.hasOptionWithId("x"));

        } catch (CommandParserException e) {
            printStacktrace(e);
            fail(e);
        }
    }

    @Test
    void specificOptionOfSingleCommandFollowedByOption() {

        Options optionsSpecific = new Options()
                .add(new OptionBuilder().withShortName('x').withLongName("x").withDescription("this is x").build("x"));

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommand("A").withSpecificOptions(optionsSpecific).build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "--test"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            CommandLineInterfaceResult cliResult = commandLineInterfaceResultBuilder.build(TEST);
            List<String> commandStringList = cliResult.getCommandParserResult().getCommandStringList();

            assertEquals(1, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals(0, argumentIterator.getIndex());

            Options optionsSpecificBack = cliResult.getCommandParserResult().getSpecificOptions();
            assertFalse(optionsSpecificBack.isEmpty());
            assertTrue(optionsSpecificBack.hasOptionWithId("x"));

        } catch (CommandParserException e) {
            printStacktrace(e);
            fail(e);
        }
    }

    @Test
    void specificOptionOfCommandChain() {

        Options optionsSpecific = new Options()
                .add(new OptionBuilder().withShortName('x').withLongName("x").withDescription("this is x").build("x"));

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B").withSpecificOptions(optionsSpecific).build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            CommandLineInterfaceResult cliResult = commandLineInterfaceResultBuilder.build(TEST);
            List<String> commandStringList = cliResult.getCommandParserResult().getCommandStringList();

            assertEquals(2, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals(1, argumentIterator.getIndex());

            Options optionsSpecificBack = cliResult.getCommandParserResult().getSpecificOptions();

            assertFalse(optionsSpecificBack.isEmpty());
            assertTrue(optionsSpecificBack.hasOptionWithId("x"));

        } catch (CommandParserException e) {
            printStacktrace(e);
            fail(e);
        }
    }

    @Test
    void specificOptionOfCommandChainFollowedByOption() {

        Options optionsSpecific = new Options()
                .add(new OptionBuilder().withShortName('x').withLongName("x").withDescription("this is x").build("x"));

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B").withSpecificOptions(optionsSpecific).build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "--test"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            CommandLineInterfaceResult cliResult = commandLineInterfaceResultBuilder.build(TEST);
            List<String> commandStringList = cliResult.getCommandParserResult().getCommandStringList();
            Options optionsSpecificBack = cliResult.getCommandParserResult().getSpecificOptions();

            assertEquals(2, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals(1, argumentIterator.getIndex());
            assertFalse(optionsSpecificBack.isEmpty());
            assertTrue(optionsSpecificBack.hasOptionWithId("x"));

        } catch (CommandParserException e) {
            printStacktrace(e);
            fail(e);
        }
    }

    @Test
    void abbreviation1() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("ABC", "B", "C").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            CommandLineInterfaceResult cliResult = commandLineInterfaceResultBuilder.build(TEST);
            List<String> commandStringList = cliResult.getCommandParserResult().getCommandStringList();

            assertEquals(3, commandStringList.size());
            assertEquals("ABC", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            println(commandParserException.getMessage());
            printStacktrace(commandParserException);
            fail(commandParserException);
        }
    }

    @Test
    void abbreviation2() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("ABC", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("X", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("Y", "B", "C").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            CommandLineInterfaceResult cliResult = commandLineInterfaceResultBuilder.build(TEST);
            List<String> commandStringList = cliResult.getCommandParserResult().getCommandStringList();

            assertEquals(3, commandStringList.size());
            assertEquals("ABC", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            println(commandParserException.getMessage());
            printStacktrace(commandParserException);
            fail(commandParserException);
        }
    }

    @Test
    void abbreviation3() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("ABC", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("X", "Z").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            CommandLineInterfaceResult cliResult = commandLineInterfaceResultBuilder.build(TEST);
            List<String> commandStringList = cliResult.getCommandParserResult().getCommandStringList();

            assertEquals(3, commandStringList.size());
            assertEquals("ABC", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            println(commandParserException.getMessage());
            printStacktrace(commandParserException);
            fail(commandParserException);
        }
    }

    @Test
    void abbreviation_fail_() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("ABC", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("AX", "BB", "CC").build());
        commands.add(new CommandSequenceBuilder().addCommands("X", "Z").build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "C"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            fail();

        } catch (AmbiguousCommandException e) {

            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            assertEquals("A B C", e.getArgsAsString());
            assertEquals("^", e.getArgumentPointerString());

            assertEquals(2, e.getMatchingCandidatesStrings().size());
            assertTrue(e.getMatchingCandidatesStrings().contains("ABC"));
            assertTrue(e.getMatchingCandidatesStrings().contains("AX"));

        } catch (CommandParserException commandParserException) {
            println(commandParserException.getMessage());
            fail(commandParserException);
        }
    }

    @Test
    void overlapShortSequence() throws CommandParserException {

        Commands commands = new Commands();
        Options optionsSpecificD = new Options()
                .add(new OptionBuilder().withShortName('d').withLongName("doption").hasArgument().build("d"));
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C", "D").withSpecificOptions(optionsSpecificD).build());
        Options optionsSpecificB = new Options()
                .add(new OptionBuilder().withShortName('b').withLongName("boption").hasArgument().build("d"));
        commands.add(new CommandSequenceBuilder().addCommands("A", "B").withSpecificOptions(optionsSpecificB).build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        commandParser.parse(argumentIterator);
        CommandLineInterfaceResult result = commandLineInterfaceResultBuilder.build(TEST);
        List<String> commandStringList = result.getCommandParserResult().getCommandStringList();

        assertEquals(2, commandStringList.size());
        assertEquals("A", commandStringList.get(0));
        assertEquals("B", commandStringList.get(1));
        assertEquals(1, argumentIterator.getIndex());

    }

    @Test
    void overlapShortSequenceWithSpecificOption() throws CommandParserException {

        Commands commands = new Commands();
        Options optionsSpecificD = new Options()
                .add(new OptionBuilder().withShortName('d').withLongName("doption").hasArgument().build("d"));
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C", "D").withSpecificOptions(optionsSpecificD).build());
        Options optionsSpecificB = new Options()
                .add(new OptionBuilder().withShortName('b').withLongName("boption").hasArgument().build("d"));
        commands.add(new CommandSequenceBuilder().addCommands("A", "B").withSpecificOptions(optionsSpecificB).build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "-b"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        commandParser.parse(argumentIterator);
        CommandLineInterfaceResult result = commandLineInterfaceResultBuilder.build(TEST);
        List<String> commandStringList = result.getCommandParserResult().getCommandStringList();

        assertEquals(2, commandStringList.size());
        assertEquals("A", commandStringList.get(0));
        assertEquals("B", commandStringList.get(1));
        assertEquals(1, argumentIterator.getIndex());
    }

    @Test
    void overlapShortSequenceWithDoubleDash() throws CommandParserException {

        Commands commands = new Commands();
        Options optionsSpecificD = new Options()
                .add(new OptionBuilder().withShortName('d').withLongName("doption").hasArgument().build("d"));
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C", "D").withSpecificOptions(optionsSpecificD).build());
        Options optionsSpecificB = new Options()
                .add(new OptionBuilder().withShortName('b').withLongName("boption").hasArgument().build("d"));
        commands.add(new CommandSequenceBuilder().addCommands("A", "B").withSpecificOptions(optionsSpecificB).build());

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();

        CommandParser commandParser = new CommandParser(commands.getCommandTree(),
                null,
                commandLineInterfaceResultBuilder);

        String[] args = {"A", "B", "--", "somethingElse"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        commandParser.parse(argumentIterator);
        CommandLineInterfaceResult result = commandLineInterfaceResultBuilder.build(TEST);
        List<String> commandStringList = result.getCommandParserResult().getCommandStringList();

        assertEquals(2, commandStringList.size());
        assertEquals("A", commandStringList.get(0));
        assertEquals("B", commandStringList.get(1));
        assertEquals(2, argumentIterator.getIndex());
    }

}