package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.CLIAbstractParser;
import de.arthurpicht.cli.common.CLIParserException;

public class OptionParser extends CLIAbstractParser {

    private Options options;
    private OptionParserResult optionParserResult;

    public OptionParser(Options options) {
        this.options = options;
        this.optionParserResult = new OptionParserResult();
    }

    public OptionParserResult getOptionParserResult() {
//        if (this.lastProcessedArgIndex < 0) throw new IllegalStateException("Not parsed yet.");
        return this.optionParserResult;
    }

//    public int getLastProcessedArgIndex() {
////        if (this.lastProcessedArgIndex < 0) throw new IllegalStateException("Not parsed yet.");
//        return this.lastProcessedArgIndex;
//    }

    @Override
    public void parse(String[] args, int beginIndex) throws CLIParserException {

        this.lastProcessedIndex = beginIndex;

        if (beginIndex >= args.length) return;

        OptionParserState optionParserState = new OptionParserStateName(options, this);
        for (int i = beginIndex; i < args.length; i++) {

            this.lastProcessedIndex = i;

            String arg = args[i];
            System.out.println("verarbeite arg:" + arg);

            optionParserState = optionParserState.process(arg);

            System.out.println("zurückgegebener ParserState: " + optionParserState.getClass().getSimpleName());

            if (optionParserState instanceof OptionParserStateFinished) {
                this.lastProcessedIndex = i - 1;
                return;
            }

        }

        if (optionParserState instanceof OptionParserStateValue) {
            OptionParserStateValue optionParserStateValue = (OptionParserStateValue) optionParserState;
            throw new ValueExpectedExcpetion(optionParserStateValue.getNameArg());
        }

    }
}
