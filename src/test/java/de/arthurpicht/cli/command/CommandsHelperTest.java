package de.arthurpicht.cli.command;

import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommandsHelperTest {

    @Test
    void getLeaves() {

        Commands commands = new Commands();

        Command leafC = commands.add("A").add("B").add("C").getCurrentCommand();
        Command leafF = commands.root().add("D").add("E").add("F").getCurrentCommand();

        Set<Command> rootCommands = commands.getRootCommands();
        Set<Command> leaveCommands = CommandsHelper.getLeaves(rootCommands);

        Set<Command> leaveCommandSetExp = new HashSet<>();
        leaveCommandSetExp.add(leafC);
        leaveCommandSetExp.add(leafF);

        assertEquals(leaveCommandSetExp, leaveCommands);

    }


    @Test
    void getAllCommandChains() {

        Commands commands = new Commands();

        Commands commandsB = commands.add("A").add("B");
        commandsB.add("C1");
        commandsB.add("C2");
        commands.root().add("D").add("E").add("F").addOneOf("G1", "G2", "G3").addOpen();

        Set<String> commandChains = CommandsHelper.getAllCommandChains(commands);

        Set<String> commandChainsExp = Sets.newHashSet(
                "[ D ] [ E ] [ F ] [ G1 | G2 | G3 ] [ * ]",
                "[ A ] [ B ] [ C1 ]",
                "[ A ] [ B ] [ C2 ]"
        );

        assertEquals(commandChainsExp, commandChains);

//        for (String commandChain : commandChains) {
//            System.out.println(commandChain);
//        }

    }
}