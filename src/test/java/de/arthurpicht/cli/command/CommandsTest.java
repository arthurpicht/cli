package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.exceptions.CommandSpecException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.cli.parameter.ParametersOne;
import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.*;

class CommandsTest {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void afterInit() {
        Commands commands = new Commands();

        assertTrue(commands.isEmpty());
        assertFalse(commands.hasCurrentCommands());

        try {
            commands.getCurrentCommands();
            fail(CommandSpecException.class.getSimpleName() + " expected");
        } catch (CommandSpecException e) {
            // din
        }

        try {
            commands.getCurrentCommand();
            fail(CommandSpecException.class.getSimpleName() + " expected");
        } catch (CommandSpecException e) {
            // din
        }

    }

    @Test
    void firstCommand() {

        Commands commands = new Commands();
        Commands newStateOfCommands = commands.add("test");

        assertFalse(commands.isEmpty());
        assertFalse(commands.hasCurrentCommands());

        assertFalse(newStateOfCommands.isEmpty());
        assertTrue(newStateOfCommands.hasCurrentCommands());

        Set<Command> currentCommands = newStateOfCommands.getCurrentCommands();
        assertEquals(1, currentCommands.size());

        Command currentCommand = Sets.getSomeElement(currentCommands);
        assertEquals("test", currentCommand.asString());

        currentCommand = newStateOfCommands.getCurrentCommand();
        assertEquals("test", currentCommand.asString());
    }

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
        commands.add("D").add("E").add("F");

        Set<Command> rootCommandSet = commands.getRootCommands();
        assertEquals(2, rootCommandSet.size());

        Set<Command> rootCommandSetExp = new HashSet<>();
        rootCommandSetExp.add(new OneCommand(null, "A"));
        rootCommandSetExp.add(new OneCommand(null, "D"));

        for (Command command : rootCommandSet) {
            assertTrue(command instanceof OneCommand);
        }

        assertEquals(rootCommandSetExp, rootCommandSet);
    }

    @Test
    void CommandTreeAfterOneOf() {

        Commands commands = new Commands().add("A");
        assertEquals(1, commands.getCurrentCommands().size());

        commands = commands.addOneOf("C", "D");
        assertEquals(2, commands.getCurrentCommands().size());

        commands = commands.addOneOf("X", "Y");
        assertEquals(4, commands.getCurrentCommands().size());

        commands = commands.add("Z");
        assertEquals(4, commands.getCurrentCommands().size());

        Set<String> commandChains = CommandsHelper.getAllCommandChains(commands);

        for (String commandChain : commandChains) {
            println(commandChain);
        }

        Set<String> commandChainsExp = Sets.newHashSet(
                "A C X Z",
                "A C Y Z",
                "A D X Z",
                "A D Y Z"
        );

        assertEquals(commandChainsExp, commandChains);
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
        assertEquals("A", command.toString());

        assertTrue(command.hasSpecificOptions());
        assertTrue(command.getSpecificOptions().hasOptionWithId("x"));
        assertFalse(command.hasParameters());
    }

    @Test
    void getParametersOfSingleCommand() {

        Parameters parameters = new ParametersOne();

        Commands commands = new Commands();
        commands.add("A").withParameters(parameters);

        Set<Command> rootCommandSet = commands.getRootCommands();
        assertEquals(1, rootCommandSet.size());

        Command command = Sets.getSomeElement(rootCommandSet);
        assertNotNull(command);
        assertEquals("A", command.toString());

        assertTrue(command.hasParameters());
        assertTrue(command.getParameters() instanceof ParametersOne);
        assertFalse(command.hasSpecificOptions());
    }

    @Test
    void preventCommandOverlap() {

        Commands commandsRoot = new Commands();
        commandsRoot.add("A");

        try {
            commandsRoot.add("A");
            fail();
        } catch (CommandSpecException e) {
            // expected
            println(e.getMessage());
        }
    }

    @Test
    void preventRepeatedOpenCommand() {

        Commands commandsRoot = new Commands();
        Commands commandsA = commandsRoot.add("A");

        commandsA.addOpen();

        try {
            commandsA.addOpen();
            fail();
        } catch (CommandSpecException e) {
            // expected
            println(e.getMessage());
        }
    }

}