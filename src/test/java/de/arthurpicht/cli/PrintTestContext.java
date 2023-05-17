package de.arthurpicht.cli;

import de.arthurpicht.console.Console;
import de.arthurpicht.console.config.ConsoleConfiguration;
import de.arthurpicht.console.config.ConsoleConfigurationBuilder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PrintTestContext {

    private final ByteArrayOutputStream byteArrayOutputStream;
    private final PrintStream printStream;
    private final ConsoleConfiguration consoleConfiguration;

    public PrintTestContext() {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.printStream = new PrintStream(this.byteArrayOutputStream);
        this.consoleConfiguration = new ConsoleConfigurationBuilder()
                .withStandardOut(this.printStream)
                .withSuppressedColors()
                .build();
    }

    public PrintTestContext(boolean suppressColors) {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.printStream = new PrintStream(this.byteArrayOutputStream);

        ConsoleConfigurationBuilder consoleConfigurationBuilder = new ConsoleConfigurationBuilder()
                .withStandardOut(this.printStream);
        if (suppressColors)
            consoleConfigurationBuilder.withSuppressedColors();
        this.consoleConfiguration = consoleConfigurationBuilder.build();
    }

    public void configureConsole() {
        Console.init(this.consoleConfiguration);
    }

    public ConsoleConfiguration getConsoleConfiguration() {
        return this.consoleConfiguration;
    }

    public String getOutput() {
        return this.byteArrayOutputStream.toString();
    }

}
