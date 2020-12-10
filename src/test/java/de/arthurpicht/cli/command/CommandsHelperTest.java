package de.arthurpicht.cli.command;

import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommandsHelperTest {

    private final boolean OUT = false;

    @Test
    void getLeaves() {

        Commands commands = new Commands();

        Set<Command> leafC = commands.add("A").add("B").add("C").getCurrentCommands();
        Set<Command> leafF = commands.root().add("D").add("E").add("F").getCurrentCommands();

        Set<Command> rootCommands = commands.getRootCommands();
        Set<Command> leaveCommands = CommandsHelper.getLeaves(rootCommands);

        Set<Command> leaveCommandSetExp = new HashSet<>();
        leaveCommandSetExp.addAll(leafC);
        leaveCommandSetExp.addAll(leafF);

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

        if (OUT) {
            for (String commandChain : commandChains) {
                System.out.println(commandChain);
            }
        }

        Set<String> commandChainsExp = Sets.newHashSet(
                "D E F G1 *",
                "D E F G2 *",
                "D E F G3 *",
                "A B C1",
                "A B C2"
        );

        assertEquals(commandChainsExp, commandChains);


    }

    @Test
    void toFormattedList() {

        Commands commands = new Commands()
                .add("A")
                .root().add("B")
                .root().addOneOf("X1", "X2", "X3")
                .root();

        String commandsFormatted = CommandsHelper.toFormattedList(commands.getRootCommands());

        // TODO Das könnte auch schief gehen, da keine zugesicherte Reihenfolge auf Sets:
//        assertEquals("[ A | B | X1 | X2 | X3 ]", commandsFormatted);
//        System.out.println(commandsFormatted);

    }
}