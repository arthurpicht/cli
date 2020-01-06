package de.arthurpicht.cli.option;

import de.arthurpicht.cli.Options;
import de.arthurpicht.cli.common.CLIAbstractParser;
import de.arthurpicht.cli.common.CLIParserException;

public class OptionParser extends CLIAbstractParser {

    private Options options;
    private OptionParserResult optionParserResult;
    private int lastProcessedArgIndex;

    public OptionParser(Options options) {
        this.options = options;
        this.optionParserResult = new OptionParserResult();
        this.lastProcessedArgIndex = -1;
    }

    public OptionParserResult getOptionParserResult() {
        if (this.lastProcessedArgIndex < 0) throw new IllegalStateException("Not parsed yet.");
        return this.optionParserResult;
    }

    public int getLastProcessedArgIndex() {
        if (this.lastProcessedArgIndex < 0) throw new IllegalStateException("Not parsed yet.");
        return this.lastProcessedArgIndex;
    }

    @Override
    public void parse(String[] args, int beginIndex) throws CLIParserException {

        OptionParserState optionParserState = new OptionParserStateName(options, this);
        this.lastProcessedArgIndex = beginIndex;

        // for (String arg : args) {
        for (int i=0; i<args.length; i++) {
            String arg = args[i];
            System.out.println("verarbeite arg:" + arg);

            optionParserState = optionParserState.process(arg);

            System.out.println("zurückgegebener ParserState: " + optionParserState.getClass().getSimpleName());

            if (optionParserState instanceof OptionParserStateFinished) {
                this.lastProcessedArgIndex = i;
                return;
            }

            this.lastProcessedArgIndex = i;
        }

        if (optionParserState instanceof OptionParserStateValue) {
            OptionParserStateValue optionParserStateValue = (OptionParserStateValue) optionParserState;
            throw new ValueExpectedExcpetion(optionParserStateValue.getNameArg());
        }

    }
}
