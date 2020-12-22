package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OptionParserBasicTest {

    @Test
    public void noOptionsCommandParameter() {

        Options options = new Options();

        String[] args = {"command", "parameter"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        OptionParser optionParser = new OptionParser(options);
        try {
            optionParser.parse(argumentIterator);
        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        }

        assertEquals(0, argumentIterator.getIndex());

        OptionParserResult optionParserResult = optionParser.getOptionParserResult();
        assertTrue(optionParserResult.isEmpty());
    }

    @Test
    public void noOptionsCommand() {

        Options options = new Options();

        String[] args = {"command"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        OptionParser optionParser = new OptionParser(options);
        try {
            optionParser.parse(argumentIterator);
        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        }

        assertEquals(0, argumentIterator.getIndex());

        OptionParserResult optionParserResult = optionParser.getOptionParserResult();
        assertTrue(optionParserResult.isEmpty());
    }

    @Test
    public void booleanOptionSpecifiedNoOptionsCommandParameter() {

        Options options = new Options().add(new OptionBuilder().withShortName('d').withLongName("debug").withDescription("debug").build("DEBUG"));

        String[] args = {"command", "parameter"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        OptionParser optionParser = new OptionParser(options);
        try {
            optionParser.parse(argumentIterator);
        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        }

        assertEquals(0, argumentIterator.getIndex());

        OptionParserResult optionParserResult = optionParser.getOptionParserResult();
        assertTrue(optionParserResult.isEmpty());
    }

    @Test
    public void booleanOptionSpecifiedNoOptionsCommand() {

        Options options = new Options().add(new OptionBuilder().withShortName('d').withLongName("debug").withDescription("debug").build("DEBUG"));

        String[] args = {"command"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        OptionParser optionParser = new OptionParser(options);
        try {
            optionParser.parse(argumentIterator);
        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        }

        assertEquals(0, argumentIterator.getIndex());

        OptionParserResult optionParserResult = optionParser.getOptionParserResult();
        assertTrue(optionParserResult.isEmpty());
    }

    @Test
    public void valueOptionSpecifiedNoOptionsCommandParameter() {

        Options options = new Options().add(new OptionBuilder().withShortName('l').withLongName("logLevel").withDescription("log level").hasArgument().build("LOG_LEVEL"));

        String[] args = {"command", "parameter"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        OptionParser optionParser = new OptionParser(options);
        try {
            optionParser.parse(argumentIterator);
        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        }

        assertEquals(0, argumentIterator.getIndex());

        OptionParserResult optionParserResult = optionParser.getOptionParserResult();
        assertTrue(optionParserResult.isEmpty());
    }

    @Test
    public void valueOptionSpecifiedNoOptionsCommand() {

        Options options = new Options().add(new OptionBuilder().withShortName('l').withLongName("logLevel").withDescription("log level").hasArgument().build("LOG_LEVEL"));

        String[] args = {"command"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        OptionParser optionParser = new OptionParser(options);
        try {
            optionParser.parse(argumentIterator);
        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        }

        assertEquals(0, argumentIterator.getIndex());

        OptionParserResult optionParserResult = optionParser.getOptionParserResult();
        assertTrue(optionParserResult.isEmpty());
    }

    @Test
    public void optionBasicNoOptionsCommand() {

        Options options = new Options().add(new OptionBuilder().withShortName('l').withLongName("logLevel").withDescription("log level").hasArgument().build("LOG_LEVEL"));

        String[] args = {"command"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        OptionParser optionParser = new OptionParser(options);
        try {
            optionParser.parse(argumentIterator);
        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        }
    }

}
