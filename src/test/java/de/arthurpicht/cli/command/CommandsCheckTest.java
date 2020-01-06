package de.arthurpicht.cli.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class CommandsCheckTest {

    @Test
    void check1() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        String[] args = {"A", "B", "C"};

        try {
            commands.check(args);
        } catch (CommandSyntaxError commandSyntaxError) {
            System.out.println(commandSyntaxError.getMessage());
            fail();
        }
    }

    @Test
    void check2() {

        Commands commands = new Commands();
        commands.add("A").add("B").addOpen();

        String[] args = {"A", "B", "C"};

        try {
            commands.check(args);
        } catch (CommandSyntaxError commandSyntaxError) {
            System.out.println(commandSyntaxError.getMessage());
            fail();
        }
    }

    @Test
    void check3() {

        Commands commands = new Commands();
        commands.add("A").add("B").addOpen().addOneOf("C1", "C2", "C3");

        String[] args = {"A", "B", "something", "C2"};

        try {
            commands.check(args);
        } catch (CommandSyntaxError commandSyntaxError) {
            System.out.println(commandSyntaxError.getMessage());
            fail();
        }
    }


    @Test
    void checkFail1() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        String[] args = {"A", "B", "C", "D"};

        try {
            commands.check(args);
            fail();
        } catch (CommandSyntaxError commandSyntaxError) {
            System.out.println(commandSyntaxError.getMessage());

        }

    }

    @Test
    void checkFail2() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        String[] args = {"A", "B", "D", "D"};

        try {
            commands.check(args);
            fail();
        } catch (CommandSyntaxError commandSyntaxError) {
            System.out.println(commandSyntaxError.getMessage());

        }
    }

    @Test
    void checkFail3() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        String[] args = {"A", "B"};

        try {
            commands.check(args);
            fail();
        } catch (CommandSyntaxError commandSyntaxError) {
            System.out.println(commandSyntaxError.getMessage());

        }
    }


}
