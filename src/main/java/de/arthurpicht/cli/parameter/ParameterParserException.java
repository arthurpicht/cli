package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.Arguments;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class ParameterParserException extends UnrecognizedArgumentException {

    public ParameterParserException(Arguments arguments, int argumentIndex, String message) {
        super(arguments, argumentIndex, message);
    }

}
