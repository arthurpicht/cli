package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.command.RecognizedCommand;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.Set;

/**
 * Is thrown if specified command is a valid abbreviation - but for more than one command.
 */
public class AmbiguousCommandException extends CommandParserException {

    private Set<RecognizedCommand> matchingCandidates;

    public static AmbiguousCommandException createInstance(String[] args, int argumentIndex, Set<RecognizedCommand> matchingCandidates) {
        String message = "Ambiguous command '" + args[argumentIndex] + "'. Possible candidates are: " + Strings.listing(RecognizedCommand.getCommandNames(matchingCandidates), ", ", "[", "]");
        return new AmbiguousCommandException(args, argumentIndex, message, matchingCandidates);
    }

    private AmbiguousCommandException(String[] args, int argumentIndex, String message, Set<RecognizedCommand> matchingCandidates) {
        super(args, argumentIndex, message);
        this.matchingCandidates = matchingCandidates;
    }

    public Set<RecognizedCommand> getMatchingCandidates() {
        return this.matchingCandidates;
    }

}
