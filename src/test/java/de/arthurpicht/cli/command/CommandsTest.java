package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.exceptions.CommandSpecException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommandsTest {

    @Test
    void getRootCommands() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");

        Set<Command> rootCommandSet = commands.getRootCommands();
        assertEquals(1, rootCommandSet.size());

        Command command = rootCommandSet.iterator().next();
        assertTrue(command instanceof OneCommand);

        Set<String> commandStringSet = command.getCommandStrings();
        assertEquals(1, commandStringSet.size());

        String commandString = commandStringSet.iterator().next();
        assertEquals("A", commandString);

    }

    @Test
    @DisplayName("Commands2")
    void getRootCommands2() {

        Commands commands = new Commands();
        commands.add("A").add("B").add("C");
        commands.root().add("D").add("E").add("F");

        Set<Command> rootCommandSet = commands.getRootCommands();
        assertEquals(2, rootCommandSet.size());

        Set<Command> rootCommandSetExp = new HashSet<>();
        rootCommandSetExp.add(new OneCommand(null, "A"));
        rootCommandSetExp.add(new OneCommand(null, "D"));

//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("[ ");
//        boolean first = true;
//        for (String commandString : this.commandSet) {
//            if (!first) stringBuilder.append(" | ");
//            stringBuilder.append(commandString);
//            first = false;
//        }
//        stringBuilder.append(" ]");
//        return stringBuilder.toString();

        for (Command command : rootCommandSet) {
            assertTrue(command instanceof OneCommand);
//            assertEquals("A", command.getCommands().get(0));
        }

        assertEquals(rootCommandSetExp, rootCommandSet);

    }

    @Test
    void getSpecificOptionsOfSingleCommand() {

        Options optionsSpecific = new Options()
                .add(new OptionBuilder().withShortName('x').withLongName("x").withDescription("this is x").build("x"));

        Commands commands = new Commands();
        commands.add("A").withSpecificOptions(optionsSpecific);

        Set<Command> rootCommandSet = commands.getRootCommands();
        assertEquals(1, rootCommandSet.size());

        Command command = Sets.getSomeElement(rootCommandSet);
        assertNotNull(command);
        assertEquals("[ A ]", command.toString());

        assertTrue(command.hasSpecificOptions());
        assertTrue(command.getSpecificOptions().hasOptionWithId("x"));
    }

    @Test
    void preventMultiCommand() {

        Commands commandsRoot = new Commands();
        Commands commandsA = commandsRoot.add("A");

//        commandsA.showStatus();

        try {
            Commands commandsA2 = commandsRoot.add("A");
//            commandsA2.showStatus();

            fail();
        } catch (CommandSpecException e) {
            // intended
            System.out.println(e.getMessage());
        }
    }

    @Test
    void preventMultiCommandOpen() {

        Commands commandsRoot = new Commands();
        Commands commandsA = commandsRoot.add("A");

        commandsA.addOpen();

        try {
            commandsA.addOpen();

            fail();
        } catch (CommandSpecException e) {
            // intended
            System.out.println(e.getMessage());
        }
    }


}