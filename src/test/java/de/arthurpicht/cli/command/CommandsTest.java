package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.exceptions.CommandSpecException;
import de.arthurpicht.cli.command.tree.*;
import de.arthurpicht.cli.command.tree.generic.UnsortedMultiBiTreeNode;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.cli.parameter.ParametersOne;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommandsTest {

    @Test
    void afterInit() {
        Commands commands = new Commands();
        assertTrue(commands.isEmpty());
        assertFalse(Commands.hasDefinitions(commands));
    }

    @Test
    void firstCommand() {
        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommand("A").build());

        assertFalse(commands.isEmpty());

        CommandTree commandTree = commands.getCommandTree();
        assertFalse(commands.isEmpty());
        CommandTreeNode rootNode = commandTree.getRootNode();

        assertTrue(rootNode.hasChildWithCommandString("A"));

        CommandTreeNode nodeA = rootNode.getChildByCommand(new OneCommand("A"));
        Command command = nodeA.getCommand();

        assertTrue(command instanceof OneCommand);
        assertTrue(command.isTerminated());
    }

    @Test
    void checkInitialCommands() {
        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "C", "D").build());
        commands.add(new CommandSequenceBuilder().addCommands("X", "Y", "Z").build());

        assertFalse(commands.isEmpty());

        CommandTree commandTree = commands.getCommandTree();
        assertFalse(commandTree.isEmpty());

        CommandTreeNode rootNode = commandTree.getRootNode();
        assertEquals(2, rootNode.getNrOfChildren());

        assertTrue(rootNode.hasChildWithCommandString("A"));
        assertTrue(rootNode.hasChildWithCommandString("X"));
    }

    @Test
    void checkNodePropertyStatus() {
        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "B").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "D", "E").build());
        commands.add(new CommandSequenceBuilder().addCommands("X", "Y", "Z").build());

        assertFalse(commands.isEmpty());

        CommandTree commandTree = commands.getCommandTree();
        assertFalse(commandTree.isEmpty());

        CommandTreeNode rootNode = commands.getCommandTree().getRootNode();
        assertTrue(rootNode.hasChildWithCommandString("A"));

        CommandTreeNode nodeA = rootNode.getChildByCommand(new OneCommand("A"));
        assertTrue(nodeA.isInitial());
        assertFalse(nodeA.isRoot());
        assertFalse(nodeA.isLeaf());
        assertTrue(nodeA.isAttachedToParent());
        assertFalse(nodeA.isTerminated());
        assertFalse(nodeA.isSubsequence());

        CommandTreeNode nodeB = nodeA.getChildByCommand(new OneCommand("B"));
        assertFalse(nodeB.isInitial());
        assertFalse(nodeB.isRoot());
        assertFalse(nodeB.isLeaf());
        assertTrue(nodeB.isAttachedToParent());
        assertTrue(nodeB.isTerminated());
        assertTrue(nodeB.isSubsequence());

        CommandTreeNode nodeC = nodeB.getChildByCommand(new OneCommand("C"));
        assertFalse(nodeC.isInitial());
        assertFalse(nodeC.isRoot());
        assertTrue(nodeC.isLeaf());
        assertTrue(nodeC.isAttachedToParent());
        assertTrue(nodeC.isTerminated());
        assertFalse(nodeC.isSubsequence());

        CommandTreeNode nodeD = nodeA.getChildByCommand(new OneCommand("D"));
        assertFalse(nodeD.isInitial());
        assertFalse(nodeD.isRoot());
        assertFalse(nodeD.isLeaf());
        assertTrue(nodeD.isAttachedToParent());
        assertFalse(nodeD.isTerminated());
        assertFalse(nodeD.isSubsequence());

        CommandTreeNode nodeE = nodeD.getChildByCommand(new OneCommand("E"));
        assertFalse(nodeE.isInitial());
        assertFalse(nodeE.isRoot());
        assertTrue(nodeE.isLeaf());
        assertTrue(nodeE.isAttachedToParent());
        assertTrue(nodeE.isTerminated());
        assertFalse(nodeE.isSubsequence());

        CommandTreeNode nodeX = rootNode.getChildByCommand(new OneCommand("X"));
        assertTrue(nodeX.isInitial());
        assertFalse(nodeX.isRoot());
        assertFalse(nodeX.isLeaf());
        assertTrue(nodeX.isAttachedToParent());
        assertFalse(nodeX.isTerminated());
        assertFalse(nodeX.isSubsequence());

        CommandTreeNode nodeY = nodeX.getChildByCommand(new OneCommand("Y"));
        assertFalse(nodeY.isInitial());
        assertFalse(nodeY.isRoot());
        assertFalse(nodeY.isLeaf());
        assertTrue(nodeY.isAttachedToParent());
        assertFalse(nodeY.isTerminated());
        assertFalse(nodeY.isSubsequence());

        CommandTreeNode nodeZ = nodeY.getChildByCommand(new OneCommand("Z"));
        assertFalse(nodeZ.isInitial());
        assertFalse(nodeZ.isRoot());
        assertTrue(nodeZ.isLeaf());
        assertTrue(nodeZ.isAttachedToParent());
        assertTrue(nodeZ.isTerminated());
        assertFalse(nodeZ.isSubsequence());
    }

    @Test
    void preventAddingPredefinedCommandSequence_singleCommand_neg() {
        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A").build());
        try {
            commands.add(new CommandSequenceBuilder().addCommands("A").build());
            fail(CommandSpecException.class.getSimpleName() + " expected.");
        } catch (CommandSpecException e) {
            // din
        }
    }

    @Test
    void preventAddingPredefinedCommandSequence_multipleCommands_neg() {
        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B").build());
        try {
            commands.add(new CommandSequenceBuilder().addCommands("A", "B").build());
            fail(CommandSpecException.class.getSimpleName() + " expected.");
        } catch (CommandSpecException e) {
            // din
        }
    }

    @Test
    void preventAddingPredefinedCommandSequence_multipleCommands2_neg() {
        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "B").build());
        try {
            commands.add(new CommandSequenceBuilder().addCommands("A", "B").build());
            fail(CommandSpecException.class.getSimpleName() + " expected.");
        } catch (CommandSpecException e) {
            // din
        }
    }

    @Test
    void addSequenceWithCoherentBeginning() {
        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "D").build());

        Set<String> commandChainStrings = commands.getCommandChainStrings();
        assertEquals(2, commandChainStrings.size());
        assertTrue(commandChainStrings.contains("root A B C"));
        assertTrue(commandChainStrings.contains("root A B D"));

        Set<UnsortedMultiBiTreeNode<Command>> allNodes = commands.getCommandTree().getAllNodes();
        assertEquals(5, allNodes.size());

        Set<CommandTreeNode> terminatedNodes = commands.getCommandTree().getTerminatedNodes();
        assertEquals(2, terminatedNodes.size());
    }


    @Test
    void getAllCommandChainStringsTest() {
        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C").build());
        commands.add(new CommandSequenceBuilder().addCommands("A", "C", "D").build());
        commands.add(new CommandSequenceBuilder().addCommands("X", "Y", "Z").build());
        Set<String> commandChainStrings = commands.getCommandChainStrings();

        assertEquals(3, commandChainStrings.size());
        assertTrue(commandChainStrings.contains("root A B C"));
        assertTrue(commandChainStrings.contains("root A C D"));
        assertTrue(commandChainStrings.contains("root X Y Z"));
    }

    @Test
    void getSpecificOptionsOfSingleCommand() {
        Options optionsSpecific = new Options()
                .add(new OptionBuilder().withShortName('x').withLongName("x").withDescription("this is x").build("x"));
        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("A").withSpecificOptions(optionsSpecific).build();

        Commands commands = new Commands();
        commands.add(commandSequence);

        Command commandA = commands.getCommandTree().getRootNode().getChildByString("A").getCommand();

        assertTrue(commandA.isTerminated());

        CommandTerminator commandTerminator = commandA.getCommandTerminator();
        assertTrue(commandTerminator.hasSpecificOptions());
        assertTrue(commandTerminator.getSpecificOptions().hasOptionWithId("x"));
        assertFalse(commandTerminator.hasParameters());
    }

    @Test
    void getParametersOfSingleCommand() {
        Parameters parameters = new ParametersOne();
        CommandSequence commandSequence = new CommandSequenceBuilder().addCommands("A").withParameters(parameters).build();

        Commands commands = new Commands();
        commands.add(commandSequence);
        Command commandA = commands.getCommandTree().getRootNode().getChildByString("A").getCommand();

        assertTrue(commandA.isTerminated());
        CommandTerminator commandTerminator = commandA.getCommandTerminator();

        assertTrue(commandTerminator.hasParameters());
        assertTrue(commandTerminator.getParameters() instanceof ParametersOne);
        assertFalse(commandTerminator.hasSpecificOptions());
    }

}