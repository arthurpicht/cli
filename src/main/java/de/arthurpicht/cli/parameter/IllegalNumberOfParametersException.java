package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Arguments;

public class IllegalNumberOfParametersException extends ParameterParserException {

    private String nrParametersRequired;
    private int nrParametersFound;

//    public IllegalNumberOfParametersException(ArgumentIterator argumentIterator, int nrParametersRequired, int nrParametersFound) {
//        super(argumentIterator, "Illegal number of parameters. Required: " + nrParametersRequired + " Found: " + nrParametersFound);
//        this.nrParametersRequired = String.valueOf(nrParametersRequired);
//        this.nrParametersFound = nrParametersFound;
//    }


    public IllegalNumberOfParametersException(Arguments arguments, int argumentIndex, int nrParametersRequired, int nrParametersFound) {
        super(arguments, argumentIndex, "Illegal number of parameters. Required: " + nrParametersRequired + " Found: " + nrParametersFound);
        this.nrParametersRequired = String.valueOf(nrParametersRequired);
        this.nrParametersFound = nrParametersFound;

    }

    public IllegalNumberOfParametersException(Arguments arguments, int argumentIndex, String nrParametersRequired, int nrParametersFound) {
        super(arguments, argumentIndex, "Illegal number of parameters. Required: " + nrParametersRequired + " Found: " + nrParametersFound);
    }

    public String getNrParametersRequired() {
        return nrParametersRequired;
    }

    public int getNrParametersFound() {
        return nrParametersFound;
    }
}
