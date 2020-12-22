package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgumentIterator;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.printStacktrace;
import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class OptionParserOptionsWithArgumentsExceptionTest {

    private Options getOptions() {
        return new Options()
                .add(new OptionBuilder().withShortName('a').withLongName("aaa").hasArgument().build("idA"))
                .add(new OptionBuilder().withShortName('b').withLongName("bbb").hasArgument().build("idB"));
    }

    @Test
    void malformedOptionExceptionShortNameNotSpecified_neg() {

        // see second element
        String[] args = {"command1", "-", "valueOfA", "-b", "valueOfB", "somethingElse"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            fail("Exception expected.");

        } catch (MalformedOptionException e) {
            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            assertEquals(1, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            println(e.getMessage());
            fail(e);
        }
    }

    @Test
    void malformedOptionExceptionShortNameTooLong_neg() {

        // see second element
        String[] args = {"command1", "-aaa", "valueOfA", "-b", "valueOfB", "somethingElse"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            fail("Exception expected.");

        } catch (MalformedOptionException e) {
            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            assertEquals(1, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail("Wrong exception.");
        }
    }

    @Test
    void malformedOptionExceptionLongNameNotSpecified_neg() {

        // see second element
        String[] args = {"command1", "--", "valueOfA", "-b", "valueOfB", "somethingElse"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            fail("Exception expected.");

        } catch (MalformedOptionException e) {
            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            assertEquals(1, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            println(e.getMessage());
            fail("Wrong exception.");
        }
    }

    @Test
    void unspecifiedOptionExceptionLongName_neg() {

        // see second element
        String[] args = {"command1", "--xxx", "valueOfA", "-b", "valueOfB", "somethingElse"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            fail("Exception expected.");

        } catch (UnspecifiedOptionException e) {
            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            assertEquals(1, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail("Wrong exception.");
        }
    }

    @Test
    void unspecifiedOptionExceptionShortName_neg() {

        // see second element
        String[] args = {"command1", "-x", "valueOfA", "-b", "valueOfB", "somethingElse"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            fail("Exception exception.");

        } catch (UnspecifiedOptionException e) {
            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            assertEquals(1, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            println(e.getMessage());
            fail("Wrong exception");
        }
    }

    @Test
    void valueExpectedExceptionShortName_neg() {

        // see second element
        String[] args = {"command1", "-a", "-b", "valueOfB", "somethingElse"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            fail("Exception expected.");

        } catch (ValueExpectedException e) {
            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            assertEquals(2, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            println(e.getMessage());
            fail("Wrong exception.");
        }
    }

    @Test
    void valueExpectedExceptionLongName_neg() {

        // see third element: no value for --aaa
        String[] args = {"command1", "--aaa", "-b", "valueOfB", "somethingElse"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            fail("Exception expected.");

        } catch (ValueExpectedException e) {
            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            printStacktrace(e);

            assertEquals(2, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void valueExpectedExceptionLongNameEnd() {

        // see second element
        String[] args = {"command1", "-b", "valueOfB", "--aaa"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            fail("Exception expected.");

        } catch (ValueExpectedException e) {
            printStacktrace(e);
            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            assertEquals(4, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            printStacktrace(e);
            fail("Wrong exception thrown: " + e.getClass().getSimpleName());
        }
    }

    @Test
    void valueExpectedExceptionShortNameEnd() {

        // see second element
        String[] args = {"command1", "-b", "valueOfB", "-a"};
        ArgumentIterator argumentIterator = new ArgumentIterator(args, 0);

        try {
            OptionParser optionParser = new OptionParser(getOptions());
            optionParser.parse(argumentIterator);
            fail("Exception expected.");

        } catch (ValueExpectedException e) {
            printStacktrace(e);
            println(e.getMessage());
            println(e.getArgsAsString());
            println(e.getArgumentPointerString());

            assertEquals(4, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            printStacktrace(e);
            fail("Wrong exception: " + e.getClass().getSimpleName());
        }
    }

}
