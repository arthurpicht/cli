package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.Parser;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

public class OptionParser extends Parser {

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
    public void parse(String[] args, int beginIndex) throws UnrecognizedArgumentException {

        this.lastProcessedIndex = beginIndex;

        if (beginIndex >= args.length) return;

        OptionParserState optionParserState = new OptionParserStateName(options, this);
        for (int i = beginIndex; i < args.length; i++) {

            this.lastProcessedIndex = i;

            String arg = args[i];
//            System.out.println("verarbeite arg:" + arg);

            optionParserState = optionParserState.process(args, i);

//            System.out.println("zurückgegebener ParserState: " + optionParserState.getClass().getSimpleName());

            if (optionParserState instanceof OptionParserStateFinished) {
                this.lastProcessedIndex = i - 1;
                return;
            }

        }

        if (optionParserState instanceof OptionParserStateValue) {
            OptionParserStateValue optionParserStateValue = (OptionParserStateValue) optionParserState;
            throw new ValueExpectedExcpetion(args, beginIndex, optionParserStateValue.getNameArg());
        }

    }
}
