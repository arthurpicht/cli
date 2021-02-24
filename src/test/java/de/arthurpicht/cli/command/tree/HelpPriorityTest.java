package de.arthurpicht.cli.command.tree;

import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HelpPriorityTest {

    @Test
    public void test() {

        Commands commands = new Commands();
        commands.add(new CommandSequenceBuilder()
                .addCommands("A", "B", "C")
                .withHelpPriority(5)
                .build());
        commands.add(new CommandSequenceBuilder()
                .addCommands("X", "Y", "Z")
                .withHelpPriority(3)
                .build());
        commands.add(new CommandSequenceBuilder()
                .addCommands("P1", "P2", "PA")
                .withHelpPriority(2)
                .build());
        commands.add(new CommandSequenceBuilder()
                .addCommands("P1", "P2", "PB")
                .withHelpPriority(2)
                .build());
        commands.add(new CommandSequenceBuilder()
                .addCommands("Q1", "Q2", "QA")
                .build());
        commands.add(new CommandSequenceBuilder()
                .addCommands("Q1", "Q2", "QB")
                .build());
        CommandTree commandTree = commands.getCommandTree();

        List<CommandTreeNode> commandTreeNodeList = commandTree.getTerminatedNodesSorted();
        StringBuilder commandsStringCombo = new StringBuilder();
        for (CommandTreeNode commandTreeNode : commandTreeNodeList) {
            commandsStringCombo.append(commandTreeNode.getCommandsString()).append("\n");
        }

        String commandsStringComboExpected =
                "P1 P2 PA\n" +
                        "P1 P2 PB\n" +
                        "X Y Z\n" +
                        "A B C\n" +
                        "Q1 Q2 QA\n" +
                        "Q1 Q2 QB\n";

        Assertions.assertEquals(commandsStringComboExpected, commandsStringCombo.toString());
    }

}
