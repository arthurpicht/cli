package de.arthurpicht.cli.command.integration;

import de.arthurpicht.cli.CommandLineInterface;
import de.arthurpicht.cli.CommandLineInterfaceBuilder;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;

public class CommandsTest {

    private CommandLineInterface getCommandLineInterface() {

        Options globalOptions = new Options()
                .add(new OptionBuilder().withShortName('v').withLongName("v").withDescription("verbose").build("v"))
                .add(new OptionBuilder().withShortName('o').withLongName("option").hasArgument().build("option"));

        Commands commands = new Commands();

        Options optionsSpecificD = new Options()
                .add(new OptionBuilder().withShortName('d').withLongName("doption").hasArgument().build("d"));
        commands.add(new CommandSequenceBuilder().addCommands("A", "B", "C", "D").withSpecificOptions(optionsSpecificD).build());

        Options optionsSpecificB = new Options()
                .add(new OptionBuilder().withShortName('b').withLongName("boption").hasArgument().build("d"));
        commands.add(new CommandSequenceBuilder().addCommands("A", "B").withSpecificOptions(optionsSpecificB).build());

        return new CommandLineInterfaceBuilder().withGlobalOptions(globalOptions).withCommands(commands).build();
    }


}
