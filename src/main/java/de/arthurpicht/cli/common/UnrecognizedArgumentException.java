package de.arthurpicht.cli.common;

public class UnrecognizedArgumentException extends Exception {

    private final Arguments arguments;
    private final int argumentIndex;

    public UnrecognizedArgumentException(ArgumentIterator argumentIterator, String message) {
        super(message);
        this.arguments = argumentIterator.getArguments();
        this.argumentIndex = argumentIterator.getIndex();
    }

    public UnrecognizedArgumentException(Arguments arguments, int argumentIndex, String message) {
        super(message);
        this.arguments = arguments;
        this.argumentIndex = argumentIndex;
    }

    public int getArgumentIndex() {
        return this.argumentIndex;
    }

    public String getArgsAsString() {
        return this.arguments.asString();
    }

    public String getIndexedArgument() {
        return this.arguments.get(this.argumentIndex);
    }

    public String getArgumentPointerString() {

        if (this.argumentIndex < 0) return "";

        int precursorIndex = this.argumentIndex - 1;
        int totalLengthPrecursors = 0;
        for (int i=0; i <= precursorIndex; i++) {
            totalLengthPrecursors += arguments.get(i).length();
        }

        return " ".repeat(Math.max(0, totalLengthPrecursors)) +
                " ".repeat(this.argumentIndex) +
                '^';
    }

}
