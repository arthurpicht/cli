package de.arthurpicht.cli.command;

import org.junit.jupiter.api.Test;

import java.util.List;

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

        } catch (CommandSyntaxError commandSyntaxError) {
            System.out.println(commandSyntaxError.getMessage());
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
        } catch (CommandSyntaxError commandSyntaxError) {
            System.out.println(commandSyntaxError.getMessage());
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
        } catch (CommandSyntaxError commandSyntaxError) {
            System.out.println(commandSyntaxError.getMessage());
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
            fail();
        } catch (CommandSyntaxError commandSyntaxError) {
            System.out.println(commandSyntaxError.getMessage());

        }

    }

    @Test
    void checkFail2() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        CommandParser commandParser = new CommandParser(commands);

        String[] args = {"A", "B", "D", "D"};

        try {
            commandParser.parse(args, 0);
            fail();
        } catch (CommandSyntaxError commandSyntaxError) {
            System.out.println(commandSyntaxError.getMessage());

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
        } catch (CommandSyntaxError commandSyntaxError) {
            System.out.println(commandSyntaxError.getMessage());

        }
    }


}