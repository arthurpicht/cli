package de.arthurpicht.cli.option;

import de.arthurpicht.cli.CommandLineInterfaceResultBuilder;
import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.ParsingBrokenEvent;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.option.OptionParser.Target.GLOBAL;
import static org.junit.jupiter.api.Assertions.*;

class OptionParserOptionsWithArgumentsTest {

    @Test
    void empty() {

        Options options = new Options()
                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true, "", "bbb help"));

        String[] args = {};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
            OptionParser optionParser = new OptionParser(GLOBAL, options, commandLineInterfaceResultBuilder);
            optionParser.parse(argumentIterator);
            OptionParserResult optionParserResult = optionParser.getParserResult();

            assertEquals(0, optionParserResult.getSize());
            assertEquals(-1, argumentIterator.getIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        } catch (ParsingBrokenEvent parsingBrokenEvent) {
            fail(parsingBrokenEvent);
        }
    }

    @Test
    void optionValues1() {

        Options options = new Options()
                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true, "" , "bbb help"));

        String[] args = {"-a", "valueOfA", "-b", "valueOfB"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
            OptionParser optionParser = new OptionParser(GLOBAL, options, commandLineInterfaceResultBuilder);
            optionParser.parse(argumentIterator);
            OptionParserResult optionParserResult = optionParser.getParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertEquals("valueOfA", optionParserResult.getValue("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(3, argumentIterator.getIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        } catch (ParsingBrokenEvent parsingBrokenEvent) {
            fail(parsingBrokenEvent);
        }
    }

    @Test
    void optionValues2() {

        Options options = new Options()
                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true, "", "bbb help"));

        String[] args = {"-b", "valueOfB", "-a", "valueOfA"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
            OptionParser optionParser = new OptionParser(GLOBAL, options, commandLineInterfaceResultBuilder);
            optionParser.parse(argumentIterator);
            OptionParserResult optionParserResult = optionParser.getParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertEquals("valueOfA", optionParserResult.getValue("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(3, argumentIterator.getIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        } catch (ParsingBrokenEvent parsingBrokenEvent) {
            fail(parsingBrokenEvent);
        }
    }

    @Test
    void optionValuesLong() {

        Options options = new Options()
                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true, "", "bbb help"));

        String[] args = {"-b", "valueOfB", "--aaa", "valueOfA"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
            OptionParser optionParser = new OptionParser(GLOBAL, options, commandLineInterfaceResultBuilder);
            optionParser.parse(argumentIterator);
            OptionParserResult optionParserResult = optionParser.getParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertEquals("valueOfA", optionParserResult.getValue("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(3, argumentIterator.getIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        } catch (ParsingBrokenEvent parsingBrokenEvent) {
            fail(parsingBrokenEvent);
        }
    }

    @Test
    void optionValuesFinished() {

        Options options = new Options()
                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true, "", "bbb help"));

        String[] args = {"-a", "valueOfA", "-b", "valueOfB", "command"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
            OptionParser optionParser = new OptionParser(GLOBAL, options, commandLineInterfaceResultBuilder);
            optionParser.parse(argumentIterator);
            OptionParserResult optionParserResult = optionParser.getParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertEquals("valueOfA", optionParserResult.getValue("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(3, argumentIterator.getIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        } catch (ParsingBrokenEvent parsingBrokenEvent) {
            fail(parsingBrokenEvent);
        }
    }

    @Test
    void optionBeginFinished() {

        Options options = new Options()
                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true, "", "bbb help"));

        String[] args = {"command1", "-a", "valueOfA", "-b", "valueOfB", "command2"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            CommandLineInterfaceResultBuilder commandLineInterfaceResultBuilder = new CommandLineInterfaceResultBuilder();
            OptionParser optionParser = new OptionParser(GLOBAL, options, commandLineInterfaceResultBuilder);
            optionParser.parse(argumentIterator);
            OptionParserResult optionParserResult = optionParser.getParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertEquals("valueOfA", optionParserResult.getValue("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(4, argumentIterator.getIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        } catch (ParsingBrokenEvent parsingBrokenEvent) {
            fail(parsingBrokenEvent);
        }
    }

}