package de.arthurpicht.cli.common;

public class UnrecognizedArgumentException extends Exception {

    private String[] args;
    private int argumentIndex = -1;

    public UnrecognizedArgumentException(String[] args, int argumentIndex, String message) {
        super(message);
        this.args = args;
        this.argumentIndex = argumentIndex;
    }

    public int getArgumentIndex() {
        return this.argumentIndex;
    }

    public String getArgsAsString() {
        return ArgsHelper.getArgsString(this.args);
    }

    public String getArgumentPointerString() {

        if (this.argumentIndex < 0) return "";

        int precursorIndex = this.argumentIndex - 1;
        int totalLengthPrecursors = 0;
        for (int i=0; i <= precursorIndex; i++) {
            totalLengthPrecursors += args[i].length();
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i < totalLengthPrecursors; i++) {
            stringBuilder.append(' ');
        }

        for (int i=1; i <= this.argumentIndex; i++) {
            stringBuilder.append(' ');
        }

        stringBuilder.append('^');

        return stringBuilder.toString();
    }

}
