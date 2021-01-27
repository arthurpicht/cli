package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import org.junit.jupiter.api.Test;

import static de.arthurpicht.cli.TestOut.printStacktrace;
import static de.arthurpicht.cli.TestOut.println;
import static org.junit.jupiter.api.Assertions.fail;

public class ZeroArgs1 {

    @Test
    void zeroArgs() {

        CommandLineInterface commandLineInterface = new CommandLineInterfaceBuilder()
                .withGlobalOptions(new Options()
                        .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v")))
                .build();

        String[] args = {};

        try {
            commandLineInterface.parse(args);
        } catch (UnrecognizedArgumentException e) {
            printStacktrace(e);
            println(e.getMessage());
            println(e.getArgumentIndex());
            fail(e);
        }
    }

    // TODO reimplement with "default command"

//    @Test
//    void oneParamter() {
//        CommandLineInterface commandLineInterface = new CommandLineInterfaceBuilder()
////                .withParameters(new ParametersVar(1))
//                .build();
//
//        String[] args = {};
//
//        try {
//            ParserResult parserResult = commandLineInterface.parse(args);
//            fail();
//        } catch (UnrecognizedArgumentException e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//            System.out.println(e.getArgumentIndex());
//
//        }
//
//    }

}
