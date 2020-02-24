package de.arthurpicht.cli.command;

import de.arthurpicht.cli.common.ArgsHelper;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void parse1() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "C"};

        try {
            commandParser.parse(args, 0);
            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(3, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, commandParser.getLastProcessedIndex());

        } catch (CommandSyntaxException commandSyntaxException) {
            System.out.println(commandSyntaxException.getMessage());
            fail();
        }
    }

    @Test
    void check2() {

        Commands commands = new Commands();
        commands.add("A").add("B").addOpen();

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "C"};

        try {
            commandParser.parse(args, 0);
            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(3, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, commandParser.getLastProcessedIndex());

        } catch (CommandSyntaxException commandSyntaxException) {
            System.out.println(commandSyntaxException.getMessage());
            fail();
        }
    }

    @Test
    void check3() {

        Commands commands = new Commands();
        commands.add("A").add("B").addOpen().addOneOf("C1", "C2", "C3");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "something", "C2"};

        try {
            commandParser.parse(args, 0);
            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(4, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("something", commandStringList.get(2));
            assertEquals("C2", commandStringList.get(3));
            assertEquals(3, commandParser.getLastProcessedIndex());

        } catch (CommandSyntaxException commandSyntaxException) {
            System.out.println(commandSyntaxException.getMessage());
            fail();
        }
    }


    @Test
    void checkFail1() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "C", "D"};

        try {
            commandParser.parse(args, 0);
            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(3, commandParser.getCommandStringList().size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals("C", commandStringList.get(2));
            assertEquals(2, commandParser.getLastProcessedIndex());

        } catch (CommandSyntaxException commandSyntaxException) {
            fail();
        }

    }

    @Test
    void
    checkFail2() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "D", "D"};

        try {
            commandParser.parse(args, 0);
            fail();
        } catch (CommandSyntaxException commandSyntaxException) {
            System.out.println(commandSyntaxException.getMessage());
            assertEquals("A B D D", commandSyntaxException.getArgsAsString());
            assertEquals("    ^", commandSyntaxException.getArgumentPointerString());

        }
    }

    @Test
    void checkFail3() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B"};

        try {
            commandParser.parse(args, 0);
            fail();
        } catch (CommandSyntaxException commandSyntaxException) {
            System.out.println(commandSyntaxException.getMessage());

            System.out.println(commandSyntaxException.getArgsAsString());
            System.out.println(commandSyntaxException.getArgumentPointerString());

            assertEquals("A B", commandSyntaxException.getArgsAsString());
            assertEquals("    ^", commandSyntaxException.getArgumentPointerString());
            assertEquals(2, commandSyntaxException.getArgumentIndex());


            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(commandSyntaxException.getArgumentPointerString());

        }
    }

    @Test
    void
    checkZeroArg1() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {};

        try {
            commandParser.parse(args, 0);
            fail();
        } catch (CommandSyntaxException commandSyntaxException) {
            System.out.println(commandSyntaxException.getMessage());
            assertEquals(0, commandSyntaxException.getArgumentIndex());
            assertEquals("", commandSyntaxException.getArgsAsString());
            assertEquals("^", commandSyntaxException.getArgumentPointerString());
        }
    }

    @Test
    void
    checkZeroArg2() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"X"};

        try {
            commandParser.parse(args, 1);
            fail();
        } catch (CommandSyntaxException commandSyntaxException) {
            System.out.println(commandSyntaxException.getMessage());
            assertEquals(1, commandSyntaxException.getArgumentIndex());
            assertEquals("X", commandSyntaxException.getArgsAsString());
            assertEquals("  ^", commandSyntaxException.getArgumentPointerString());
        }
    }

    @Test
    void
    checkNoFound() {

        Commands commands = new Commands();
        commands.add("E").add("F");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "C", "D"};

        try {
            commandParser.parse(args, 1);
            fail();
        } catch (CommandSyntaxException commandSyntaxException) {
            System.out.println(commandSyntaxException.getMessage());
            assertEquals(1, commandSyntaxException.getArgumentIndex());
            assertEquals("A B C D", commandSyntaxException.getArgsAsString());
            assertEquals("  ^", commandSyntaxException.getArgumentPointerString());

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

        try {
            commandParser.parse(args, 0);

            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(1, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals(0, commandParser.getLastProcessedIndex());

            Options optionsSpecificBack = commandParser.getSpecificOptions();
            assertFalse(optionsSpecificBack.isEmpty());
            assertTrue(optionsSpecificBack.hasOptionWithId("x"));

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
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

        try {
            commandParser.parse(args, 0);
            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(1, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals(0, commandParser.getLastProcessedIndex());

            Options optionsSpecificBack = commandParser.getSpecificOptions();
            assertFalse(optionsSpecificBack.isEmpty());
            assertTrue(optionsSpecificBack.hasOptionWithId("x"));

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
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

        try {
            commandParser.parse(args, 0);

            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(2, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));
            assertEquals(1, commandParser.getLastProcessedIndex());

            Options optionsSpecificBack = commandParser.getSpecificOptions();

            assertFalse(optionsSpecificBack.isEmpty());
            assertTrue(optionsSpecificBack.hasOptionWithId("x"));

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
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

        try {
            commandParser.parse(args, 0);

            List<String> commandStringList = commandParser.getCommandStringList();

            assertEquals(2, commandStringList.size());
            assertEquals("A", commandStringList.get(0));
            assertEquals("B", commandStringList.get(1));

            assertEquals(1, commandParser.getLastProcessedIndex());

            Options optionsSpecificBack = commandParser.getSpecificOptions();
            assertFalse(optionsSpecificBack.isEmpty());
            assertTrue(optionsSpecificBack.hasOptionWithId("x"));

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            fail();
        }
    }

}