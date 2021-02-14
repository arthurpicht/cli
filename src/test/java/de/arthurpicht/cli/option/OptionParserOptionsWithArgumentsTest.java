package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import org.junit.jupiter.api.Test;

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
            OptionParser optionParser = new OptionParser(options);
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

        Options options = new Options()
                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true, "" , "bbb help"));

        String[] args = {"-a", "valueOfA", "-b", "valueOfB"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args);

        try {
            OptionParser optionParser = new OptionParser(options);
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
            OptionParser optionParser = new OptionParser(options);
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
            OptionParser optionParser = new OptionParser(options);
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
            OptionParser optionParser = new OptionParser(options);
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

            OptionParser optionParser = new OptionParser(options);
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
        }
    }

}