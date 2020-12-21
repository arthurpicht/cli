package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.command.RecognizedCommand;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.Set;

/**
 * Is thrown if specified command is a valid abbreviation - but for more than one command.
 */
public class AmbiguousCommandException extends CommandParserException {

    private Set<RecognizedCommand> matchingCandidates;

    public static AmbiguousCommandException createInstance(ArgumentIterator argumentIterator, Set<RecognizedCommand> matchingCandidates) {
        String message = "Ambiguous command '" + argumentIterator.getCurrent() + "'. Possible candidates are: " + Strings.listing(RecognizedCommand.getCommandNames(matchingCandidates), ", ", "[", "]");
        return new AmbiguousCommandException(argumentIterator, message, matchingCandidates);
    }

    private AmbiguousCommandException(ArgumentIterator argumentIterator, String message, Set<RecognizedCommand> matchingCandidates) {
        super(argumentIterator, message);
        this.matchingCandidates = matchingCandidates;
    }

    public Set<RecognizedCommand> getMatchingCandidates() {
        return this.matchingCandidates;
    }

}
