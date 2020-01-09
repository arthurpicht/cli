package de.arthurpicht.cli.option;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionsHelpStringTest {

    @Test
    void helpString() {

        Options options = new Options()
                .add(new Option("A", 'A', "almost-all", false, "", "do not list implied . and .."))
                .add(new Option("a", 'a', "all", false, "", "do not hide entries starting with ."))
                .add(new Option("B", 'B', "ignore-backups", false, "", "do not hide entries starting with ."))
                .add(new Option("b", 'b', "escape", false, "", "print octal escapes for nongraphic characters"))
                .add(new Option("bs", null, "block-size", true, "SIZE", "use SIZE-byte blocks"))
                .add(new Option("c", 'c', null, false, "", "with -lt: sort by, and show ctime"))
                .add(new Option("C", 'C', null, false, "", "list entries by columns"));

        String helpString = options.getHelpString();

        System.out.println(helpString);




    }

}