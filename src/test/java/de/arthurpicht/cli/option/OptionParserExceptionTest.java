package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ArgsHelper;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OptionParserExceptionTest {

    @Test
    void malformedOptionExceptionShortNameNotSpecified() {

        Options options = new Options()
                .add(new OptionBuilder().withShortName('a').withLongName("aaa").hasArgument().build("idA"))
                .add(new OptionBuilder().withShortName('b').withLongName("bbb").hasArgument().build("idB"));

        // see second element
        String[] args = {"command1", "-", "valueOfA", "-b", "valueOfB", "somethingElse"};

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 1);
            fail();

        } catch (MalformedOptionException e) {
            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

            assertEquals(1, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void malformedOptionExceptionShortNameTooLong() {

        Options options = new Options()
                .add(new OptionBuilder().withShortName('a').withLongName("aaa").hasArgument().build("idA"))
                .add(new OptionBuilder().withShortName('b').withLongName("bbb").hasArgument().build("idB"));

        // see second element
        String[] args = {"command1", "-aaa", "valueOfA", "-b", "valueOfB", "somethingElse"};

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 1);
            fail();

        } catch (MalformedOptionException e) {
            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

            assertEquals(1, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void malformedOptionExceptionLongNameNotSpecified() {

        Options options = new Options()
                .add(new OptionBuilder().withShortName('a').withLongName("aaa").hasArgument().build("idA"))
                .add(new OptionBuilder().withShortName('b').withLongName("bbb").hasArgument().build("idB"));

        // see second element
        String[] args = {"command1", "--", "valueOfA", "-b", "valueOfB", "somethingElse"};

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 1);
            fail();

        } catch (MalformedOptionException e) {
            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

            assertEquals(1, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void unspecifiedOptionExceptionLongName() {

        Options options = new Options()
                .add(new OptionBuilder().withShortName('a').withLongName("aaa").hasArgument().build("idA"))
                .add(new OptionBuilder().withShortName('b').withLongName("bbb").hasArgument().build("idB"));

        // see second element
        String[] args = {"command1", "--xxx", "valueOfA", "-b", "valueOfB", "somethingElse"};

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 1);
            fail();

        } catch (UnspecifiedOptionException e) {
            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

            assertEquals(1, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void unspecifiedOptionExceptionShortName() {

        Options options = new Options()
                .add(new OptionBuilder().withShortName('a').withLongName("aaa").hasArgument().build("idA"))
                .add(new OptionBuilder().withShortName('b').withLongName("bbb").hasArgument().build("idB"));

        // see second element
        String[] args = {"command1", "-x", "valueOfA", "-b", "valueOfB", "somethingElse"};

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 1);
            fail();

        } catch (UnspecifiedOptionException e) {
            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

            assertEquals(1, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void valueExpectedExceptionShortName() {

        Options options = new Options()
                .add(new OptionBuilder().withShortName('a').withLongName("aaa").hasArgument().build("idA"))
                .add(new OptionBuilder().withShortName('b').withLongName("bbb").hasArgument().build("idB"));

        // see second element
        String[] args = {"command1", "-a", "-b", "valueOfB", "somethingElse"};

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 1);
            fail();

        } catch (ValueExpectedExcpetion e) {
            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

            assertEquals(2, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void valueExpectedExceptionLongName() {

        Options options = new Options()
                .add(new OptionBuilder().withShortName('a').withLongName("aaa").hasArgument().build("idA"))
                .add(new OptionBuilder().withShortName('b').withLongName("bbb").hasArgument().build("idB"));

        // see third element: no value for --aaa
        String[] args = {"command1", "--aaa", "-b", "valueOfB", "somethingElse"};

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 1);
            fail();

        } catch (ValueExpectedExcpetion e) {
            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

            assertEquals(2, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void valueExpectedExceptionLongNameEnd() {

        Options options = new Options()
                .add(new OptionBuilder().withShortName('a').withLongName("aaa").hasArgument().build("idA"))
                .add(new OptionBuilder().withShortName('b').withLongName("bbb").hasArgument().build("idB"));

        // see second element
        String[] args = {"command1", "-b", "valueOfB", "--aaa"};

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 1);
            fail();

        } catch (ValueExpectedExcpetion e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

            assertEquals(4, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void valueExpectedExceptionShortNameEnd() {

        Options options = new Options()
                .add(new OptionBuilder().withShortName('a').withLongName("aaa").hasArgument().build("idA"))
                .add(new OptionBuilder().withShortName('b').withLongName("bbb").hasArgument().build("idB"));

        // see second element
        String[] args = {"command1", "-b", "valueOfB", "-a"};

        try {
            OptionParser optionParser = new OptionParser(options);
            optionParser.parse(args, 1);
            fail();

        } catch (ValueExpectedExcpetion e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(ArgsHelper.getArgsString(args));
            System.out.println(e.getArgumentPointerString());

            assertEquals(4, e.getArgumentIndex());

        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            fail();
        }
    }


}
