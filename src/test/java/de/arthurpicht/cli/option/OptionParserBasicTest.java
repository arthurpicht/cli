package de.arthurpicht.cli.option;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.ParsingBrokenEvent;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.option.OptionParser.Target.GLOBAL;
import static org.junit.jupiter.api.Assertions.*;

public class OptionParserBasicTest {

    @Test
    public void noOptionsCommandParameter() {

        Options options = new Options();

        String[] args = {"command", "parameter"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        OptionParserResult optionParserResult = execute(argumentIterator, options);

        assertEquals(0, argumentIterator.getIndex());
        assertTrue(optionParserResult.isEmpty());
    }

    @Test
    public void noOptionsCommand() {

        Options options = new Options();

        String[] args = {"command"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        OptionParserResult optionParserResult = execute(argumentIterator, options);

        assertEquals(0, argumentIterator.getIndex());
        assertTrue(optionParserResult.isEmpty());
    }

    @Test
    public void booleanOptionSpecifiedNoOptionsCommandParameter() {

        Options options = new Options().add(new OptionBuilder().withShortName('d').withLongName("debug").withDescription("debug").build("DEBUG"));

        String[] args = {"command", "parameter"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        OptionParserResult optionParserResult = execute(argumentIterator, options);

        assertEquals(0, argumentIterator.getIndex());
        assertTrue(optionParserResult.isEmpty());
    }

    @Test
    public void booleanOptionSpecifiedNoOptionsCommand() {

        Options options = new Options().add(new OptionBuilder().withShortName('d').withLongName("debug").withDescription("debug").build("DEBUG"));

        String[] args = {"command"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        OptionParserResult optionParserResult = execute(argumentIterator, options);

        assertEquals(0, argumentIterator.getIndex());
        assertTrue(optionParserResult.isEmpty());
    }

    @Test
    public void valueOptionSpecifiedNoOptionsCommandParameter() {

        Options options = new Options().add(new OptionBuilder().withShortName('l').withLongName("logLevel").withDescription("log level").hasArgument().build("LOG_LEVEL"));

        String[] args = {"command", "parameter"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        OptionParserResult optionParserResult = execute(argumentIterator, options);

        assertEquals(0, argumentIterator.getIndex());
        assertTrue(optionParserResult.isEmpty());
    }

    @Test
    public void valueOptionSpecifiedNoOptionsCommand() {

        Options options = new Options().add(new OptionBuilder().withShortName('l').withLongName("logLevel").withDescription("log level").hasArgument().build("LOG_LEVEL"));

        String[] args = {"command"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        OptionParserResult optionParserResult = execute(argumentIterator, options);

        assertEquals(0, argumentIterator.getIndex());
        assertTrue(optionParserResult.isEmpty());
    }

    @Test
    public void optionBasicNoOptionsCommand() {

        Options options = new Options().add(new OptionBuilder().withShortName('l').withLongName("logLevel").withDescription("log level").hasArgument().build("LOG_LEVEL"));

        String[] args = {"command"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        execute(argumentIterator, options);
    }

    private OptionParserResult execute(ArgumentIterator argumentIterator, Options options) {

        CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
        OptionParser optionParser = new OptionParser(GLOBAL, options, commandLineInterfaceResultBuilder);

        try {
            optionParser.parse(argumentIterator);
        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        } catch (ParsingBrokenEvent parsingBrokenEvent) {
            fail(parsingBrokenEvent);
        }

        return optionParser.getParserResult();
    }

}
