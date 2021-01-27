package de.arthurpicht.cli.command;

import de.arthurpicht.cli.command.tree.Command;
import de.arthurpicht.utils.core.collection.Sets;

import java.util.HashSet;
import java.util.Set;

public class CommandMatcher {

    private final Set<Command> commandCandidates;
    private final String arg;
    private final boolean allowAbbreviation;

    private RecognizedCommand matchingCommand;
    private Set<RecognizedCommand> matchingCandidates;

    public CommandMatcher(Set<Command> commandCandidates, String arg, boolean allowAbbreviation) {
        this.commandCandidates = commandCandidates;
        this.arg = arg;
        this.allowAbbreviation = allowAbbreviation;

        this.matchingCommand = null;
        this.matchingCandidates = new HashSet<>();

        this.findMatchingCommand();

    }

    private void findMatchingCommand() {

        for (Command command : this.commandCandidates) {

            if (command.matches(this.arg)) {
                this.matchingCommand = new RecognizedCommand(command, this.arg, this.arg);
                return;
            }

            if (this.allowAbbreviation) {
                Set<String> matchingCandidatesNameSet = command.getMatchingCandidates(this.arg);
                for (String matchingCandidateName : matchingCandidatesNameSet) {
                    RecognizedCommand recognizedCommand = new RecognizedCommand(command, matchingCandidateName, this.arg);
                    this.matchingCandidates.add(recognizedCommand);
                }
            }
        }

        if (this.allowAbbreviation && this.matchingCandidates.size() == 1) {
            this.matchingCommand = Sets.getSomeElement(this.matchingCandidates);
            this.matchingCandidates = new HashSet<>();
        }

    }

    public boolean hasMatchingCommand() {
        return this.matchingCommand != null;
    }

    public RecognizedCommand getMatchingCommand() {
        return this.matchingCommand;
    }

    public boolean hasCandidates() {
        return (!this.matchingCandidates.isEmpty());
    }

    public Set<RecognizedCommand> getMatchingCandidates() {
        return this.matchingCandidates;
    }

}
