package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionParserOptionsMixedOptionsTest {

    private Options getOptions() {
        return new Options()
                .add(new Option("idA", 'a', "aaa", false, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true, "", "bbb help"));
    }

    @Test
    void empty() {

        String[] args = {};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            OptionParserResult optionParserResult = optionParser.getParserResult();

            assertEquals(0, optionParserResult.getSize());
            assertEquals(-1, argumentIterator.getIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    void optionValues1() {

        String[] args = {"-a", "-b", "valueOfB"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            OptionParserResult optionParserResult = optionParser.getParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(2, argumentIterator.getIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    void optionValues2() {

        String[] args = {"-b", "valueOfB", "-a"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            OptionParserResult optionParserResult = optionParser.getParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(2, argumentIterator.getIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    void optionValuesLong() {

        String[] args = {"-b", "valueOfB", "--aaa"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            OptionParserResult optionParserResult = optionParser.getParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(2, argumentIterator.getIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    void optionValuesFinished() {

        String[] args = {"-a", "-b", "valueOfB", "command"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            OptionParserResult optionParserResult = optionParser.getParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(2, argumentIterator.getIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @Test
    void optionBeginFinished() {

        String[] args = {"command1", "-a", "-b", "valueOfB", "command2"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {

            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            OptionParserResult optionParserResult = optionParser.getParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(3, argumentIterator.getIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail(e);
        }
    }

}