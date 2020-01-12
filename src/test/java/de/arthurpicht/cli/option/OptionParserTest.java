package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.UnrecognizedCLArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionParserTest {

//    @Test
//    void empty() {
//
//        Options options = new Options()
//                .add(new Option("idA", 'a', "aaa", true, "aaa help"))
//                .add(new Option("idB", 'b', "bbb", true, "bbb help"));
//
//        String[] args = {};
//
//        try {
//            OptionParser optionParser = new OptionParser(options);
//            optionParser.parse(args, 0);
//            OptionParserResult optionParserResult = optionParser.getOptionParserResult();
//
//            assertEquals(0, optionParserResult.getSize());
//            assertEquals(0, optionParser.getLastProcessedArgIndex());
//
//        } catch (CLIParserException e) {
//            e.printStackTrace();
//            fail(e);
//        }
//    }

    @Test
    void optionValues1() {

        Options options = new Options()
                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true, "" , "bbb help"));

        String[] args = {"-a", "valueOfA", "-b", "valueOfB"};

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 0);
            OptionParserResult optionParserResult = optionParser.getOptionParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertEquals("valueOfA", optionParserResult.getValue("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(3, optionParser.getLastProcessedIndex());

        } catch (UnrecognizedCLArgumentException e) {
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

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 0);
            OptionParserResult optionParserResult = optionParser.getOptionParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertEquals("valueOfA", optionParserResult.getValue("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(3, optionParser.getLastProcessedIndex());

        } catch (UnrecognizedCLArgumentException e) {
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

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 0);
            OptionParserResult optionParserResult = optionParser.getOptionParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertEquals("valueOfA", optionParserResult.getValue("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(3, optionParser.getLastProcessedIndex());

        } catch (UnrecognizedCLArgumentException e) {
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

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 0);
            OptionParserResult optionParserResult = optionParser.getOptionParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertEquals("valueOfA", optionParserResult.getValue("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(3, optionParser.getLastProcessedIndex());

        } catch (UnrecognizedCLArgumentException e) {
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

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 1);
            OptionParserResult optionParserResult = optionParser.getOptionParserResult();

            assertEquals(2, optionParserResult.getSize());

            assertTrue(optionParserResult.hasOption("idA"));
            assertEquals("valueOfA", optionParserResult.getValue("idA"));
            assertTrue(optionParserResult.hasOption("idB"));
            assertEquals("valueOfB", optionParserResult.getValue("idB"));

            assertEquals(4, optionParser.getLastProcessedIndex());

        } catch (UnrecognizedCLArgumentException e) {
            e.printStackTrace();
            fail(e);
        }
    }


}