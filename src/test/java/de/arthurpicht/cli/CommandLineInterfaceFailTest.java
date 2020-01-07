package de.arthurpicht.cli;

import de.arthurpicht.cli.argument.ArgumentParser;
import de.arthurpicht.cli.argument.ArgumentParserOne;
import de.arthurpicht.cli.argument.Arguments;
import de.arthurpicht.cli.argument.ArgumentsOne;
import de.arthurpicht.cli.common.CLIParserException;
import de.arthurpicht.cli.option.Option;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineInterfaceFailTest {

    @Test
    void notFinished() {

        Options optionsGlobal = new Options()
                .add(new Option("idA", 'a', "aaa", true, "aaa help"))
                .add(new Option("idB", 'b', "bbb", true, "bbb help"));

        Arguments arguments = new ArgumentsOne();

        CommandLineInterface commandLineInterface = new CommandLineInterface(optionsGlobal, null, null, arguments);

        String[] args = {"-a", "valueOfA", "-b", "valueOfB", "arg1", "arg2"};

        try {
            ParserResult parserResult = commandLineInterface.parse(args);
            fail();

        } catch (CLIParserException e) {
            System.out.println("Expected exception: " + e.getMessage());
        }
    }


}