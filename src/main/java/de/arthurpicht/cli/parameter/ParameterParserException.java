package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.Arguments;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class ParameterParserException extends UnrecognizedArgumentException {

    public ParameterParserException(String executableName, Arguments arguments, int argumentIndex, String message) {
        super(executableName, arguments, argumentIndex, message);
    }

}
