package de.arthurpicht.cli.print;

import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.cli.parameter.ParametersMin;
import de.arthurpicht.cli.parameter.ParametersN;
import de.arthurpicht.cli.parameter.ParametersOne;
import de.arthurpicht.console.message.Message;

public class ParametersMessage {

    public static Message asMessage(Parameters parameters) {
        if (parameters instanceof ParametersOne) {
            return ParametersOneMessage.asMessage((ParametersOne) parameters);
        } else if (parameters instanceof ParametersMin) {
            return ParametersMinMessage.asMessage((ParametersMin) parameters);
        } else if (parameters instanceof ParametersN) {
            return ParametersNMessage.asMessage((ParametersN) parameters);
        } else {
            throw new IllegalArgumentException("Unrecognized type of argument [parameters].");
        }
    }

}
