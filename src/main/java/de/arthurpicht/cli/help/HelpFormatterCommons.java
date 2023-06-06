package de.arthurpicht.cli.help;

import de.arthurpicht.cli.CliDefinition;
import de.arthurpicht.cli.CliDescription;
import de.arthurpicht.utils.core.strings.Strings;

public class HelpFormatterCommons {

    public static final String INDENT = "  ";
    public static final int COL_WIDTH = 30;

    public static String getHeaderString(CliDefinition cliDefinition) {

        String executableName = cliDefinition.getCliDescription().getExecutableName();
        CliDescription cliDescription = cliDefinition.getCliDescription();

        String header = executableName;
        if (cliDescription.hasVersionText()) {
            header += " " + cliDescription.getVersionText();
        }

        return header;
    }

    public static String indentString(String string) {
        return INDENT + string.replace("\n", "\n" + INDENT);
    }

    public static String formatStringsToCols(String col1, String col2) {

        StringBuilder stringBuilder = new StringBuilder();

        if (Strings.isSpecified(col1)) {
            stringBuilder.append(col1);
        }
        if (Strings.isSpecified(col2)) {
            Strings.fillUpRight(stringBuilder, ' ', COL_WIDTH);
            stringBuilder.append(col2);
        }

        return stringBuilder.toString();
    }

    public static String getFullVersionText(CliDefinition cliDefinition) {
        CliDescription cliDescription
                = cliDefinition.getCliDescription();

        String fullVersionText = "";

        if (cliDescription.hasVersionText()) {
            fullVersionText += cliDescription.getVersionText();
        }

        if (cliDescription.hasVersionTextSupplement()) {
            fullVersionText += "\n" + cliDescription.getVersionTextSupplement();
        }

        return fullVersionText;
    }

}
