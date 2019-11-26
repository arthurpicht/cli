package de.arthurpicht.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionsTest {

    @Test
    void add() {

        Options options = new Options()
                .add(new Option('a', "aaa", false, "aaa help"))
                .add(new Option('b', "bbb", false, "bbb help"));


    }
}