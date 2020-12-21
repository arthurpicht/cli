package de.arthurpicht.cli.common;

import de.arthurpicht.utils.core.collection.Lists;
import de.arthurpicht.utils.core.strings.Strings;

@Deprecated
public class ArgsHelper {

    public static String getArgsString(String[] args) {
        return Strings.listing(Lists.newArrayList(args), " ");
    }

}
