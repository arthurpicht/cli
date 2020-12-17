package de.arthurpicht.cli.integration;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.ParserResult;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.parameter.ParametersVar;
import org.junit.jupiter.api.Test;

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
            ParserResult parserResult = commandLineInterface.parse(args);
        } catch (UnrecognizedArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getArgumentIndex());
            fail();
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
