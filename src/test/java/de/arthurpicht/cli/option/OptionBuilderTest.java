package de.arthurpicht.cli.option;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionBuilderTest {

    @Test
    void build() {

        Option option = new OptionBuilder().withShortName('s').build("id");


    }
}