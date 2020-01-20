package de.arthurpicht.cli.parameter;

public class IllegalNumberOfParametersException extends ParameterParserException {

    private String nrParametersRequired;
    private int nrParametersFound;

    public IllegalNumberOfParametersException(String[] args, int argumentIndex, int nrParametersRequired, int nrParametersFound) {
        super(args, argumentIndex, "Illegal number of parameters. Required: " + nrParametersRequired + " Found: " + nrParametersFound);
        this.nrParametersRequired = String.valueOf(nrParametersRequired);
        this.nrParametersFound = nrParametersFound;

    }

    public IllegalNumberOfParametersException(String[] args, int argumentIndex, String nrParametersRequired, int nrParametersFound) {
        super(args, argumentIndex, "Illegal number of parameters. Required: " + nrParametersRequired + " Found: " + nrParametersFound);
    }

    public String getNrParametersRequired() {
        return nrParametersRequired;
    }

    public int getNrParametersFound() {
        return nrParametersFound;
    }
}
