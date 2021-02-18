package de.arthurpicht.cli.option;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.Parser;
import de.arthurpicht.cli.common.ParsingBrokenEvent;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;

import static de.arthurpicht.cli.CommandLineInterfaceResult.Status.BROKEN;

public class OptionParser extends Parser {

    public enum Target {GLOBAL, SPECIFIC}

    private final Target target;
    private final Options options;
    private final OptionParserResult optionParserResult;

    public OptionParser(Target target, Options options, CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder) {
        super(commandLineInterfaceResultBuilder);
        this.target = target;
        this.options = options;
        this.optionParserResult = new OptionParserResult();
    }

    OptionParserResult getParserResult() {
        return this.optionParserResult;
    }

    @Override
    public void parse(ArgumentIterator argumentIterator) throws UnrecognizedArgumentException, ParsingBrokenEvent {

        parseInner(argumentIterator);

        if (this.target == Target.GLOBAL) {
            commandLineInterfaceResultBuilder.withOptionParserResultGlobal(this.optionParserResult);
        } else {
            commandLineInterfaceResultBuilder.withOptionParserResultSpecific(this.optionParserResult);
        }

        if (this.optionParserResult.hasBreakingOption())
            throw new ParsingBrokenEvent(commandLineInterfaceResultBuilder.build(BROKEN));
    }

    public void parseInner(ArgumentIterator argumentIterator) throws UnrecognizedArgumentException {

        if (!argumentIterator.hasNext()) return;

        OptionParserState optionParserState = new OptionParserStateName(options, this);
        while (argumentIterator.hasNext()) {

            argumentIterator.getNext();
            optionParserState = optionParserState.process(argumentIterator);

            if (optionParserState instanceof OptionParserStateFinished) {
                stepBack(argumentIterator);
                return;
            }
        }

        if (optionParserState instanceof OptionParserStateValue) {
            throw new ValueExpectedException(argumentIterator);
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
