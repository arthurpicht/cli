package de.arthurpicht.cli.command;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommandsHelperTest {

    @Test
    void getLeaves() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");
        commands.root().add("D").add("E").add("F");

        Set<Command> rootCommands = commands.getRootCommands();
        Set<Command> leaveCommands = CommandsHelper.getLeaves(rootCommands);

        Set<Command> leaveCommandSetExp = new HashSet<>();
        leaveCommandSetExp.add(new OneCommand(null, "C"));
        leaveCommandSetExp.add(new OneCommand(null, "F"));

        assertEquals(leaveCommandSetExp, leaveCommands);

    }


}