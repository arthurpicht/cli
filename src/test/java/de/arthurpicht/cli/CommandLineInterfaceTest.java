package de.arthurpicht.cli;

class CommandLineInterfaceTest {

    // TODO for all test cases: default command with parametersVar min 0

//    @Test
//    void optionsGlobalArgs() {
//
//        Options optionsGlobal = new Options()
//                .add(new Option("idA", 'a', "aaa", true, "", "aaa help"))
//                .add(new Option("idB", 'b', "bbb", true,"" , "bbb help"));
//
//        CommandLineInterface commandLineInterface = new CommandLineInterface(optionsGlobal, null);
//
//        String[] args = {"-a", "valueOfA", "-b", "valueOfB", "arg1"};
//
//        try {
//            ParserResult parserResult = commandLineInterface.parse(args);
//
//            assertNotNull(parserResult.getOptionParserResultGlobal());
//            OptionParserResult optionParserResultGlobal = parserResult.getOptionParserResultGlobal();
//
//            assertEquals(2, optionParserResultGlobal.getSize());
//
//            assertTrue(optionParserResultGlobal.hasOption("idA"));
//            assertEquals("valueOfA", optionParserResultGlobal.getValue("idA"));
//            assertTrue(optionParserResultGlobal.hasOption("idB"));
//            assertEquals("valueOfB", optionParserResultGlobal.getValue("idB"));
//
//            List<String> argumentList = parserResult.getParameterList();
//            assertEquals(1, argumentList.size());
//            assertEquals("arg1", argumentList.get(0));
//
//        } catch (UnrecognizedArgumentException e) {
//            e.printStackTrace();
//            fail();
//        }
//    }

//    @Test
//    void optionsGlobalArgs2() {
//
//        Options optionsGlobal = new Options()
//                .add(new Option("idA", 'a', "aaa", true,"" , "aaa help"))
//                .add(new Option("idB", 'b', "bbb", true,"" , "bbb help"));
//
//        CommandLineInterface commandLineInterface = new CommandLineInterface(optionsGlobal, null);
//
//        String[] args = {"arg1"};
//
//        try {
//            ParserResult parserResult = commandLineInterface.parse(args);
//
//            assertNotNull(parserResult.getOptionParserResultGlobal());
//            OptionParserResult optionParserResultGlobal = parserResult.getOptionParserResultGlobal();
//
//            assertEquals(0, optionParserResultGlobal.getSize());
//
//            List<String> argumentList = parserResult.getParameterList();
//            assertEquals(1, argumentList.size());
//            assertEquals("arg1", argumentList.get(0));
//
//        } catch (UnrecognizedArgumentException e) {
//            e.printStackTrace();
//            fail();
//        }
//
//    }

//    @Test
//    void optionsGlobalCommandsArgs() {
//
//        Options optionsGlobal = new Options()
//                .add(new Option("idA", 'a', "aaa", true,"" , "aaa help"))
//                .add(new Option("idB", 'b', "bbb", true,"" , "bbb help"));
//
//        Commands commands = new Commands();
//        commands.add("commandA").add("commandB").withParameters(new ParametersVar(0));
//
//        CommandLineInterface commandLineInterface = new CommandLineInterface(optionsGlobal, commands);
//
//        String[] args = {"-a", "valueOfA", "-b", "valueOfB", "commandA", "commandB", "arg1"};
//
//        try {
//            ParserResult parserResult = commandLineInterface.parse(args);
//
//            assertNotNull(parserResult.getOptionParserResultGlobal());
//            OptionParserResult optionParserResultGlobal = parserResult.getOptionParserResultGlobal();
//
//            assertEquals(2, optionParserResultGlobal.getSize());
//
//            assertTrue(optionParserResultGlobal.hasOption("idA"));
//            assertEquals("valueOfA", optionParserResultGlobal.getValue("idA"));
//            assertTrue(optionParserResultGlobal.hasOption("idB"));
//            assertEquals("valueOfB", optionParserResultGlobal.getValue("idB"));
//
//            List<String> commandList = parserResult.getCommandList();
//            assertEquals(2, commandList.size(), "number of commands");
//            assertEquals("commandA", commandList.get(0));
//            assertEquals("commandB", commandList.get(1));
//
//            List<String> argumentList = parserResult.getParameterList();
//            assertEquals(1, argumentList.size());
//            assertEquals("arg1", argumentList.get(0));
//
//        } catch (UnrecognizedArgumentException e) {
//            e.printStackTrace();
//            fail();
//        }
//    }

//    @Test
//    void full() {
//
//        Options optionsGlobal = new Options()
//                .add(new Option("idA", 'a', "aaa", true,"" , "aaa help"))
//                .add(new Option("idB", 'b', "bbb", true,"" , "bbb help"));
//
//        Commands commands = new Commands();
//        commands.add("commandA").withParameters(new ParametersVar(0))
//                .add("commandB").withSpecificOptions(
//                    new Options()
//                            .add(new Option("idC", 'c', "ccc", true,"" , "ccc help"))
//                            .add(new Option("idD", 'd', "ddd", true,"" , "ddd help"))
//                ).withParameters(new ParametersVar(0));
//
//        CommandLineInterface commandLineInterface = new CommandLineInterface(optionsGlobal, commands);
//
//        String[] args = {"-a", "valueOfA", "-b", "valueOfB", "commandA", "commandB", "--ccc", "valueOfC", "arg1"};
//
//        try {
//            ParserResult parserResult = commandLineInterface.parse(args);
//
//            // global options
//
//            assertNotNull(parserResult.getOptionParserResultGlobal());
//            OptionParserResult optionParserResultGlobal = parserResult.getOptionParserResultGlobal();
//
//            assertEquals(2, optionParserResultGlobal.getSize());
//
//            assertTrue(optionParserResultGlobal.hasOption("idA"));
//            assertEquals("valueOfA", optionParserResultGlobal.getValue("idA"));
//            assertTrue(optionParserResultGlobal.hasOption("idB"));
//            assertEquals("valueOfB", optionParserResultGlobal.getValue("idB"));
//
//            // commands
//
//            List<String> commandList = parserResult.getCommandList();
//            assertEquals(2, commandList.size(), "number of commands");
//            assertEquals("commandA", commandList.get(0));
//            assertEquals("commandB", commandList.get(1));
//
//            // specific options
//
//            assertNotNull(parserResult.getOptionParserResultSpecific());
//            OptionParserResult optionParserResultSpecific = parserResult.getOptionParserResultSpecific();
//
//            assertEquals(1, optionParserResultSpecific.getSize());
//
//            assertTrue(optionParserResultSpecific.hasOption("idC"));
//            assertEquals("valueOfC", optionParserResultSpecific.getValue("idC"));
//
//            // arguments
//
//            List<String> argumentList = parserResult.getParameterList();
//            assertEquals(1, argumentList.size());
//            assertEquals("arg1", argumentList.get(0));
//
//        } catch (UnrecognizedArgumentException e) {
//            e.printStackTrace();
//            fail();
//        }
//    }

}