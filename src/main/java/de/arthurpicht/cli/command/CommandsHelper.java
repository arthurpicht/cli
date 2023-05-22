package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.tree.Command;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.*;

public class CommandsHelper {

    public static String toFormattedList(Set<Command> commandSet) {

        Set<String> commandStringSet = new HashSet<>();
        for (Command command : commandSet) {
            commandStringSet.add(command.asString());
        }

        List<String> commandStringList = new ArrayList<>(commandStringSet);
        Collections.sort(commandStringList);

        return Strings.listing(commandStringList, " | ", "[ ", " ]");
    }

}
