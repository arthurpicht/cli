package de.arthurpicht.cli.common;

public class UnrecognizedArgumentException extends Exception {

    private String[] args;
    private int argumentIndex = -1;

    public UnrecognizedArgumentException() {
    }

    public UnrecognizedArgumentException(String message) {
        super(message);
    }

    public UnrecognizedArgumentException(String[] args, int argumentIndex, String message) {
        super(message);
        this.args = args;
        this.argumentIndex = argumentIndex;
    }

    public int getArgumentIndex() {
        return this.argumentIndex;
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

//        if (this.argumentIndex > 0) {
//            stringBuilder.append(' ');
//        }

        stringBuilder.append('^');

        return stringBuilder.toString();
    }

//    public UnrecognizedCLArgumentException(String message, Throwable cause) {
//        super(message, cause);
//    }
//
//    public UnrecognizedCLArgumentException(Throwable cause) {
//        super(cause);
//    }
//
//    public UnrecognizedCLArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//        super(message, cause, enableSuppression, writableStackTrace);
//    }
}
