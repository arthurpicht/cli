package de.arthurpicht.cli.common;

public class UnrecognizedArgumentException extends Exception {

    private final String executableName;
    private final Arguments arguments;
    private final int argumentIndex;

    public UnrecognizedArgumentException(String executableName, ArgumentIterator argumentIterator, String message) {
        super(message);
        this.executableName = executableName;
        this.arguments = argumentIterator.getArguments();
        this.argumentIndex = argumentIterator.getIndex();
    }

    public UnrecognizedArgumentException(String executableName, Arguments arguments, int argumentIndex, String message) {
        super(message);
        this.executableName = executableName;
        this.arguments = arguments;
        this.argumentIndex = argumentIndex;
    }

    public String getExecutableName() {
        return this.executableName;
    }

    public int getArgumentIndex() {
        return this.argumentIndex;
    }

    /**
     * Returns all arguments given on cli as one string.
     *
     * @return Arguments as string
     */
    public String getArgsAsString() {
        return this.arguments.asString();
    }

    /**
     * Returns the complete call string, including the executable name and
     * all arguments.
     *
     * @return call string
     */
    public String getCallString() {
        return this.executableName + " " + getArgsAsString();
    }

    public String getIndexedArgument() {
        return this.arguments.get(this.argumentIndex);
    }

    /**
     * Returns a pointer denoting the argument which triggered that exception.
     * The resulting string is relative to the string given by getArgsAsString.
     *
     * @return argument pointer string
     */
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

    public String getCallPointerString() {

        return " ".repeat(this.executableName.length())
                + " "
                + getArgumentPointerString();
    }

}
