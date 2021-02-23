package de.arthurpicht.cli.option;

import java.util.*;

public class Options {

    private final Map<Character, Option> shortNameMap;
    private final Map<String, Option> longNameMap;
    private final Set<String> idSet;

    public Options() {
        this.shortNameMap = new HashMap<>();
        this.longNameMap = new HashMap<>();
        this.idSet = new HashSet<>();
    }

    public Options add(Option option) {

        if (this.idSet.contains(option.getId())) {
            throw new IllegalArgumentException("Option with id [" + option.getId() + "] already added.");
        }
        this.idSet.add(option.getId());

        if (option.hasShortName()) {
            if (!this.shortNameMap.containsKey(option.getShortName())) {
                this.shortNameMap.put(option.getShortName(), option);
            } else {
                throw new IllegalArgumentException("ShortName [" + option.getShortName() + "] already specified.");
            }
        }

        if (option.hasLongName()) {
            if (!this.longNameMap.containsKey(option.getLongName())) {
                this.longNameMap.put(option.getLongName(), option);
            } else {
                throw new IllegalArgumentException("LongName [" + option.getLongName() + "] already specified.");
            }
        }

        return this;
    }

    public  boolean hasOptionWithId(String id) {
        return this.idSet.contains(id);
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

    public Set<String> getIds() {
        return this.idSet;
    }

    private Set<Option> getAllOptions() {

        Set<Option> optionSet = new HashSet<>();

        optionSet.addAll(this.shortNameMap.values());
        optionSet.addAll(this.longNameMap.values());

        return optionSet;
    }

    public String getHelpString() {

        List<Option> orderedOptionList = new ArrayList<>(this.getAllOptions());
        orderedOptionList.sort(new OptionComparator());

        StringBuilder stringBuilder = new StringBuilder();

        boolean first = true;
        for (Option option : orderedOptionList) {

            if (first) {
                first = false;
            } else {
                stringBuilder.append("\n");
            }

            stringBuilder.append(option.getHelpString());

        }

        return stringBuilder.toString();
    }

    public static boolean hasDefinitions(Options options) {
        return (options != null && options.idSet.size() != 0);
    }

}