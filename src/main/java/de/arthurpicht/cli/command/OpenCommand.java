package de.arthurpicht.cli.command;

import java.util.HashSet;
import java.util.Set;

/**
 * Für die Definition eines OpenCommand gilt die Einschränkung, dass dies die einzige Command-Definition
 * auf der betreffenden Stufe sein darf.
 *
 */
public class OpenCommand extends Command {


    public OpenCommand(Command previousCommand) {
        super(previousCommand);
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public Set<String> getCommandStrings() {
        return new HashSet<>();
    }

    @Override
    public boolean matches(String commandString) {
        return true;
    }

    @Override
    public String toString() {
        return "[ * ]";
    }



}
