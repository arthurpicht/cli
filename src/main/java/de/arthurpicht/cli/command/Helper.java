package de.arthurpicht.cli.command;

import java.util.HashSet;
import java.util.Set;

public class Helper {

    @Deprecated
    public static Set<String> intersection(Set<String> set1, Set<String> set2) {
        Set<String> intersection = new HashSet<>();
        for (String string : set1) {
            if (set2.contains(string)) {
                intersection.add(string);
            }
        }
        return intersection;

    }
}
