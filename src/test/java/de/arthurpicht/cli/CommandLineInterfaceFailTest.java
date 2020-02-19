package de.arthurpicht.cli;

import de.arthurpicht.cli.parameter.Parameters;
import de.arthurpicht.cli.parameter.ParametersOne;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.Option;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class CommandLineInterfaceFailTest {

    @Test
    void notFinished() {

        Options optionsGlobal = new Options()
                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
                .add(new Option("idB", 'b', "bbb", true, "", "bbb help"));

        Parameters parameters = new ParametersOne();

        CommandLineInterface commandLineInterface = new CommandLineInterface(optionsGlobal, null, parameters);

        String[] args = {"-a", "valueOfA", "-b", "valueOfB", "arg1", "arg2"};

        try {
            ParserResult parserResult = commandLineInterface.parse(args);
            fail();

        } catch (UnrecognizedArgumentException e) {
            System.out.println("Expected exception: " + e.getMessage());
        }
    }


}