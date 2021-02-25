package de.arthurpicht.cli.option;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Parser;
import de.arthurpicht.cli.common.ParsingBrokenEvent;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

import static de.arthurpicht.cli.CliResult.Status.BROKEN;

public class OptionParser extends Parser {

    public enum Target {GLOBAL, SPECIFIC}

    private final Target target;
    private final Options options;
    private final OptionParserResult optionParserResult;
    private final String executableName;

    public OptionParser(Target target, Options options, CliResultBuilder cliResultBuilder, String executableName) {
        super(cliResultBuilder);
        this.target = target;
        this.options = options;
        this.optionParserResult = new OptionParserResult();
        this.executableName = executableName;
    }

    OptionParserResult getParserResult() {
        return this.optionParserResult;
    }

    @Override
    public void parse(ArgumentIterator argumentIterator) throws UnrecognizedArgumentException, ParsingBrokenEvent {

        parseInner(argumentIterator);

        if (this.target == Target.GLOBAL) {
            cliResultBuilder.withOptionParserResultGlobal(this.optionParserResult);
        } else {
            cliResultBuilder.withOptionParserResultSpecific(this.optionParserResult);
        }

        if (this.optionParserResult.hasBreakingOption())
            throw new ParsingBrokenEvent(cliResultBuilder.build(BROKEN));
    }

    public void parseInner(ArgumentIterator argumentIterator) throws UnrecognizedArgumentException {

        if (!argumentIterator.hasNext()) return;

        OptionParserState optionParserState = new OptionParserStateName(options, this, this.executableName);
        while (argumentIterator.hasNext()) {

            argumentIterator.getNext();
            optionParserState = optionParserState.process(argumentIterator);

            if (optionParserState instanceof OptionParserStateFinished) {
                stepBack(argumentIterator);
                return;
            }
        }

        if (optionParserState instanceof OptionParserStateValue) {
            throw new ValueExpectedException(this.executableName, argumentIterator);
        }
    }

    private void stepBack(ArgumentIterator argumentIterator) {
        if (argumentIterator.hasPrevious()) {
            argumentIterator.getPrevious();
        } else {
            argumentIterator.reset();
        }
    }

}
