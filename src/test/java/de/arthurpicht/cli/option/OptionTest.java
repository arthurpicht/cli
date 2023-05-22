package de.arthurpicht.cli.option;

import de.arthurpicht.cli.PrintTestContext;
import de.arthurpicht.cli.print.OptionMessage;
import de.arthurpicht.console.config.ConsoleConfiguration;
import de.arthurpicht.console.message.Message;
import de.arthurpicht.console.processor.StringComposer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionTest {

    @Test
    public void helpMessageSimple() {
        Option option = new Option("A", 'A', "almost-all", false, "", "do not list implied . and ..");
        Message message = OptionMessage.asMessage(option);

        PrintTestContext printTestContext = new PrintTestContext();
        ConsoleConfiguration consoleConfiguration = printTestContext.getConsoleConfiguration();
        StringComposer stringComposer = new StringComposer(consoleConfiguration);
        String string = stringComposer.compose(message, StringComposer.Target.CONSOLE);

        assertEquals("  -A, --almost-all              do not list implied . and ..", string);
    }

    @Test
    public void helpMessageShortFormOnly() {
        Option option = new Option("A", 'A', null, false, "", "do not list implied . and ..");
        Message message = OptionMessage.asMessage(option);

        PrintTestContext printTestContext = new PrintTestContext();
        ConsoleConfiguration consoleConfiguration = printTestContext.getConsoleConfiguration();
        StringComposer stringComposer = new StringComposer(consoleConfiguration);
        String string = stringComposer.compose(message, StringComposer.Target.CONSOLE);

        assertEquals("  -A                            do not list implied . and ..", string);
    }

    @Test
    public void helpMessageShortFormOnlyWithArgument() {
        Option option = new Option("A", 'A', null, true, "DUMMY", "do not list implied . and ..");
        Message message = OptionMessage.asMessage(option);

        PrintTestContext printTestContext = new PrintTestContext();
        ConsoleConfiguration consoleConfiguration = printTestContext.getConsoleConfiguration();
        StringComposer stringComposer = new StringComposer(consoleConfiguration);
        String string = stringComposer.compose(message, StringComposer.Target.CONSOLE);

        assertEquals("  -A <DUMMY>                    do not list implied . and ..", string);
    }

    @Test
    public void helpMessageLongFormOnlyWithOutArgument() {
        Option option = new Option("bs", null, "block-size", false, null, "use SIZE-byte blocks");
        Message message = OptionMessage.asMessage(option);

        PrintTestContext printTestContext = new PrintTestContext();
        ConsoleConfiguration consoleConfiguration = printTestContext.getConsoleConfiguration();
        StringComposer stringComposer = new StringComposer(consoleConfiguration);
        String string = stringComposer.compose(message, StringComposer.Target.CONSOLE);

        assertEquals("      --block-size              use SIZE-byte blocks", string);
    }

    @Test
    public void helpMessageLongFormOnlyWithArgument() {
        Option option = new Option("bs", null, "block-size", true, "SIZE", "use SIZE-byte blocks");
        Message message = OptionMessage.asMessage(option);

        PrintTestContext printTestContext = new PrintTestContext();
        ConsoleConfiguration consoleConfiguration = printTestContext.getConsoleConfiguration();
        StringComposer stringComposer = new StringComposer(consoleConfiguration);
        String string = stringComposer.compose(message, StringComposer.Target.CONSOLE);

        assertEquals("      --block-size <SIZE>       use SIZE-byte blocks", string);
    }

    @Test
    public void helpMessageOverflowingLongName() {
        Option option = new Option("A", 'A', "almost-all-with-overflowing-column-width", false, "", "do not list implied . and ..");
        Message message = OptionMessage.asMessage(option);

        PrintTestContext printTestContext = new PrintTestContext();
        ConsoleConfiguration consoleConfiguration = printTestContext.getConsoleConfiguration();
        StringComposer stringComposer = new StringComposer(consoleConfiguration);
        String string = stringComposer.compose(message, StringComposer.Target.CONSOLE);

        assertEquals("  -A, --almost-all-with-overflowing-column-width do not list implied . and ..", string);
    }

}