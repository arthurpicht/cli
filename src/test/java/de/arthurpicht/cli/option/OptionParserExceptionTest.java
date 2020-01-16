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

}
