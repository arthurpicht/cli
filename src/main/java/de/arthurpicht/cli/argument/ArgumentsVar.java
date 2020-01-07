package de.arthurpicht.cli.argument;

public class ArgumentsVar extends Arguments {

    private int minimalNrOfArguments;

    public ArgumentsVar(int minimalNrOfArguments) {
        this.minimalNrOfArguments = minimalNrOfArguments;
    }

    @Override
    public ArgumentParser getArgumentParser() {
        return new ArgumentParserVar(this.minimalNrOfArguments);
    }
}
