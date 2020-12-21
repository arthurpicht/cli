package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.exceptions.AmbiguousCommandException;
import de.arthurpicht.cli.command.exceptions.CommandParserException;
import de.arthurpicht.cli.command.exceptions.IllegalCommandException;
import de.arthurpicht.cli.command.exceptions.InsufficientNrOfCommandsException;
import de.arthurpicht.cli.common.ArgsHelper;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    private static final boolean OUT = true;

    @Test
    void parse1() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "C"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(3, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            if (OUT) System.out.println(commandParserException.getMessage());
            fail();
        }
    }

    @Test
    void check2() {

        Commands commands = new Commands();
        commands.add("A").add("B").addOpen();

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "C"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(3, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            if (OUT) System.out.println(commandParserException.getMessage());
            fail();
        }
    }

    @Test
    void check3() {

        Commands commands = new Commands();
        commands.add("A").add("B").addOpen().addOneOf("C1", "C2", "C3");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "something", "C2"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(4, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("something", commandStringList.get(2));
            assertEquals("C2", commandStringList.get(3));
            assertEquals(3, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            if (OUT) System.out.println(commandParserException.getMessage());
            fail();
        }
    }


    @Test
    void checkFail1() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(3, commandParser.getCommandStringList().size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            if (OUT) System.out.println(commandParserException.getMessage());
            fail();
        }
    }

    @Test
    void
    checkFail2_expect_IllegalCommandException() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "D", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            fail();
        } catch (AmbiguousCommandException| InsufficientNrOfCommandsException e) {
            fail("Wrong exception thrown: " + e.getClass().getName());
        } catch (IllegalCommandException illegalCommandException) {
            if (OUT) System.out.println(illegalCommandException.getMessage());
            assertEquals("A B D D", illegalCommandException.getArgsAsString());
            assertEquals("    ^", illegalCommandException.getArgumentPointerString());
        }
    }

    @Test
    void checkFail3_neg() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            fail();

        } catch (IllegalCommandException | AmbiguousCommandException e) {
            fail("Wrong exception: " + e.getClass().getName());

        } catch (InsufficientNrOfCommandsException insufficientNrOfCommandsException) {
            if (OUT) {
                System.out.println(insufficientNrOfCommandsException.getMessage());

                System.out.println(insufficientNrOfCommandsException.getArgsAsString());
                System.out.println(insufficientNrOfCommandsException.getArgumentPointerString());
            }

            assertEquals("A B", insufficientNrOfCommandsException.getArgsAsString());
            assertEquals("    ^", insufficientNrOfCommandsException.getArgumentPointerString());
            assertEquals(2, insufficientNrOfCommandsException.getArgumentIndex());

            if (OUT) {
                System.out.println(ArgsHelper.getArgsString(args));
                System.out.println(insufficientNrOfCommandsException.getArgumentPointerString());
            }

        }
    }

    @Test
    void checkZeroArg1_neg() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            fail();
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
        commands.add("A").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"X"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            commandParser.parse(argumentIterator);
            fail();
        } catch (AmbiguousCommandException | IllegalCommandException e) {
            fail("Wrong exception thrown: " + e.getClass().getName());
        } catch (InsufficientNrOfCommandsException e) {
            if (OUT) System.out.println(e.getMessage());
            assertEquals("Insufficient number of commands. Next command is one of: [ A ].", e.getMessage());
            assertEquals(1, e.getArgumentIndex());
            assertEquals("X", e.getArgsAsString());
            assertEquals("  ^", e.getArgumentPointerString());
        }
    }

    @Test
    void commandNotRecognized1_neg() {

        Commands commands = new Commands();
        commands.add("E").add("F");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            fail();
        } catch (AmbiguousCommandException | InsufficientNrOfCommandsException e) {
            fail("Wrong exception thrown: " + e.getClass().getName());
        } catch (IllegalCommandException e) {
            if (OUT) System.out.println(e.getMessage());
            assertEquals("Command [A] not recognized. Possible commands are: [ E ].", e.getMessage());
            assertEquals(0, e.getArgumentIndex());
            assertEquals("A B C D", e.getArgsAsString());
            assertEquals("^", e.getArgumentPointerString());
        }
    }

    @Test
    void commandNotRecognized2_neg() {

        Commands commands = new Commands();
        commands.add("E").add("F");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"E", "B", "C", "D"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            fail();
        } catch (AmbiguousCommandException | InsufficientNrOfCommandsException e) {
            fail("Wrong exception thrown: " + e.getClass().getName());
        } catch (IllegalCommandException e) {
            if (OUT) System.out.println(e.getMessage());
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
        commands.add("A").withSpecificOptions(optionsSpecific);

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(1, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals(0, argumentIterator.getIndex());

            Options optionsSpecificBack = commandParser.getSpecificOptions();
            assertFalse(optionsSpecificBack.isEmpty());
            assertTrue(optionsSpecificBack.hasOptionWithId("x"));

        } catch (CommandParserException e) {
            if (OUT) e.printStackTrace();
            fail();
        }
    }

    @Test
    void specificOptionOfSingleCommandFollowedByOption() {

        Options optionsSpecific = new Options()
                .add(new OptionBuilder().withShortName('x').withLongName("x").withDescription("this is x").build("x"));

        Commands commands = new Commands();
        commands.add("A").withSpecificOptions(optionsSpecific);

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "--test"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(1, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals(0, argumentIterator.getIndex());

            Options optionsSpecificBack = commandParser.getSpecificOptions();
            assertFalse(optionsSpecificBack.isEmpty());
            assertTrue(optionsSpecificBack.hasOptionWithId("x"));

        } catch (CommandParserException e) {
            if (OUT) e.printStackTrace();
            fail();
        }
    }


    @Test
    void specificOptionOfCommandChain() {

        Options optionsSpecific = new Options()
                .add(new OptionBuilder().withShortName('x').withLongName("x").withDescription("this is x").build("x"));

        Commands commands = new Commands();
        commands.add("A").add("B").withSpecificOptions(optionsSpecific);

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(2, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals(1, argumentIterator.getIndex());

            Options optionsSpecificBack = commandParser.getSpecificOptions();

            assertFalse(optionsSpecificBack.isEmpty());
            assertTrue(optionsSpecificBack.hasOptionWithId("x"));

        } catch (CommandParserException e) {
            if (OUT) e.printStackTrace();
            fail();
        }
    }

    @Test
    void specificOptionOfCommandChainFollowedByOption() {

        Options optionsSpecific = new Options()
                .add(new OptionBuilder().withShortName('x').withLongName("x").withDescription("this is x").build("x"));

        Commands commands = new Commands();
        commands.add("A").add("B").withSpecificOptions(optionsSpecific);

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "--test"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);

            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(2, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));

            assertEquals(1, argumentIterator.getIndex());

            Options optionsSpecificBack = commandParser.getSpecificOptions();
            assertFalse(optionsSpecificBack.isEmpty());
            assertTrue(optionsSpecificBack.hasOptionWithId("x"));

        } catch (CommandParserException e) {
            if (OUT) e.printStackTrace();
            fail();
        }
    }

    @Test
    void abbreviation1() {

        Commands commands = new Commands();
        commands.add("ABC").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "C"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(3, commandStringList.size());
            assertEquals("ABC", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            if (OUT) System.out.println(commandParserException.getMessage());
            fail();
        }
    }

    @Test
    void abbreviation2() {

        Commands commands = new Commands();
        commands.addOneOf("ABC", "X", "Y").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "C"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(3, commandStringList.size());
            assertEquals("ABC", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            if (OUT) System.out.println(commandParserException.getMessage());
            fail();
        }
    }

    @Test
    void abbreviation3() {

        Commands commands = new Commands();
        commands.add("ABC").add("B").add("C").reset().add("X").add("Z");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "C"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(3, commandStringList.size());
            assertEquals("ABC", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, argumentIterator.getIndex());

        } catch (CommandParserException commandParserException) {
            if (OUT) System.out.println(commandParserException.getMessage());
            fail();
        }
    }

    @Test
    void abbreviation_fail_() {

        Commands commands = new Commands();
        commands.add("ABC").add("B").add("C").reset().add("AX").add("Z");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "C"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            commandParser.parse(argumentIterator);
            fail();

        } catch (AmbiguousCommandException e) {

            if (OUT) {
                System.out.println(e.getMessage());
                System.out.println(e.getArgsAsString());
                System.out.println(e.getArgumentPointerString());
            }

            assertEquals("A B C", e.getArgsAsString());
            assertEquals("^", e.getArgumentPointerString());
            // TODO Check candidates

        } catch (CommandParserException commandParserException) {
            if (OUT) System.out.println(commandParserException.getMessage());
            fail();
        }
    }


}