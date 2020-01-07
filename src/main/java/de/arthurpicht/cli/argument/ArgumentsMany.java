package de.arthurpicht.cli.argument;

public class ArgumentsMany extends Arguments {

    private int nrOfArguments;

    public ArgumentsMany(int nrOfArguments) {
        this.nrOfArguments = nrOfArguments;
    }

    @Override
    public ArgumentParser getArgumentParser() {
        return new ArgumentParserMany(this.nrOfArguments);
    }
}
