package de.arthurpicht.cli;

import de.arthurpicht.cli.option.Option;
import org.junit.jupiter.api.Test;

class OptionsTest {

    @Test
    void add() {

        Options options = new Options()
                .add(new Option("idA", 'a', "aaa", false, "aaa help"))
                .add(new Option("idB", 'b', "bbb", false, "bbb help"));


    }
}