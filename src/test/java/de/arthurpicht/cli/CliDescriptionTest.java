package de.arthurpicht.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CliDescriptionTest {

    @Test
    public void getDescriptionFirstLine_null_neg() {
        CliDescription cliDescription = new CliDescription("test", null, null, null);
        try {
            cliDescription.getDescriptionFirstLine();
            fail(IllegalStateException.class.getSimpleName() + " expected.");
        } catch (Exception e) {
            // din
        }
    }

    @Test
    public void getDescriptionFirstLine_empty_new() {
        CliDescription cliDescription = new CliDescription("test", "", "version", "date");
        try {
            cliDescription.getDescriptionFirstLine();
            fail(IllegalStateException.class.getSimpleName() + " expected.");
        } catch (Exception e) {
            // din
        }
    }

    @Test
    public void getDescriptionFirstLine_justOneLine() {
        CliDescription cliDescription = new CliDescription("test", "description", "version", "date");
        assertEquals("description", cliDescription.getDescriptionFirstLine());
    }

    @Test
    public void getDescriptionFirstLine_twoLines() {
        CliDescription cliDescription = new CliDescription("test", "description\nsecondLine", "version", "date");
        assertEquals("description", cliDescription.getDescriptionFirstLine());
    }

}