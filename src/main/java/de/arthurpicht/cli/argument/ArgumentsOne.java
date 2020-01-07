package de.arthurpicht.cli.argument;

public class ArgumentsOne extends Arguments {

    public ArgumentsOne() {
        super();
    }

    @Override
    public ArgumentParser getArgumentParser() {
        return new ArgumentParserOne();
    }
}
