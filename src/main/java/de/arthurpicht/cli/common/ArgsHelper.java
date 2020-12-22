package de.arthurpicht.cli.common;

import de.arthurpicht.utils.core.collection.Lists;
import de.arthurpicht.utils.core.strings.Strings;

@Deprecated
public class ArgsHelper {

    /**
     * Moved to {@link UnrecognizedArgumentException}
     * @param args
     * @return
     */
    public static String getArgsString(String[] args) {
        return Strings.listing(Lists.newArrayList(args), " ");
    }

}
