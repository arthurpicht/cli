package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.command.RecognizedCommand;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.Set;

/**
 * Is thrown if specified command is a valid abbreviation - but for more than one command.
 */
public class AmbiguousCommandException extends CommandParserException {

    private final Set<String> matchingCandidatesStrings;

    public static AmbiguousCommandException createInstance(String executableName, ArgumentIterator argumentIterator, Set<RecognizedCommand> matchingCandidates) {
        String message = "Ambiguous command '" + argumentIterator.getCurrent() + "'. Possible candidates are: " + Strings.listing(RecognizedCommand.getCommandNames(matchingCandidates), ", ", "[", "]");
        return new AmbiguousCommandException(executableName, argumentIterator, message, matchingCandidates);
    }

    private AmbiguousCommandException(String executableName, ArgumentIterator argumentIterator, String message, Set<RecognizedCommand> matchingCandidates) {
        super(executableName, argumentIterator, message);
        this.matchingCandidatesStrings = RecognizedCommand.getCommandNames(matchingCandidates);
    }

    public Set<String> getMatchingCandidatesStrings() {
        return matchingCandidatesStrings;
    }
}
