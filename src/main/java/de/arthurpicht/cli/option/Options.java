package de.arthurpicht.cli.option;

import java.util.HashMap;
import java.util.Map;

public class Options {

    private Map<Character, Option> shortNameMap;
    private Map<String, Option> longNameMap;

    public Options() {
        this.shortNameMap = new HashMap<>();
        this.longNameMap = new HashMap<>();
    }

    public Options add(Option option) {

        if (option.hasShortName()) {
            if (!this.shortNameMap.containsKey(option.getShortName())) {
                this.shortNameMap.put(option.getShortName(), option);
            } else {
                throw new IllegalStateException("ShortName [" + option.getShortName() + "] already specified.");
            }
        }

        if (option.hasLongName()) {
            if (!this.longNameMap.containsKey(option.getLongName())) {
                this.longNameMap.put(option.getLongName(), option);
            } else {
                throw new IllegalStateException("LongName [" + option.getLongName() + "] already specified.");
            }
        }

        return this;
    }

    public boolean hasShortNameOption(Character shortName) {
        return this.shortNameMap.containsKey(shortName);
    }

    public Option getShortNameOption(Character shortName) {
        return this.shortNameMap.get(shortName);
    }

    public boolean hasLongNameOption(String longName) {
        return this.longNameMap.containsKey(longName);
    }

    public Option getLongNameOption(String longName) {
        return this.longNameMap.get(longName);
    }

    public boolean isEmpty() {
        return this.longNameMap.isEmpty() && this.shortNameMap.isEmpty();
    }
}
