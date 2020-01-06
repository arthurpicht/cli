package de.arthurpicht.cli.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void getCommandChainString() {

        Commands commands = new Commands();
        Command command = commands.add("A").add("B").add("C").getCurrentCommand();

        String commandChainString = command.getCommandChainString();
        assertEquals("[ A ] [ B ] [ C ]", commandChainString);

    }

    @Test
    void getCommandChainString2() {

        Commands commands = new Commands();
        Command command = commands.add("A").add("B").addOpen().getCurrentCommand();

        String commandChainString = command.getCommandChainString();
        assertEquals("[ A ] [ B ] [ * ]", commandChainString);

    }


}