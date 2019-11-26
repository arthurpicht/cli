package de.arthurpicht.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Options {

    private Map<Character, Option> shortNameMap;
    private Map<String, Option> longNameMap;

//    private List<Option> optionList;

    public Options() {

        this.shortNameMap = new HashMap<>();
        this.longNameMap = new HashMap<>();

//        this.optionList = new ArrayList<>();
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

//    public List<Option> getOptions() {
//        return this.optionList;
//    }

}
