package de.arthurpicht.cli.command.tree;

import java.util.HashSet;
import java.util.Set;

/**
 * Für die Definition eines OpenCommand gilt die Einschränkung, dass dies die einzige Command-Definition
 * auf der betreffenden Stufe sein darf.
 *
 */
public class OpenCommand extends Command {

    public OpenCommand() {
        super();
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public String getCommandString() {
        return null;
    }

    @Override
    public boolean matches(String commandString) {
        return true;
    }

    @Override
    public Set<String> getMatchingCandidates(String commandString) {
        return new HashSet<>(Set.of(commandString));
    }

    @Override
    public String asString() {
        return "*";
    }

    @Override
    public String toString() {
        return "*";
    }

}
