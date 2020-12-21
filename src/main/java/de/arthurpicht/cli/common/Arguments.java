package de.arthurpicht.cli.common;

import de.arthurpicht.utils.core.collection.Lists;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.Collections;
import java.util.List;

public class Arguments {

    private final List<String> argumentList;

    public Arguments(String[] args) {
        this.argumentList = Lists.newArrayList(args);
    }

    public boolean isEmpty() {
        return this.argumentList.isEmpty();
    }

    public int size() {
        return this.argumentList.size();
    }

    public String get(int index) {
        if (index < 0 || index > size() -1) throw new IllegalStateException("Index of arguments out of bounds.");
        return this.argumentList.get(index);
    }

    public String asString() {
        return Strings.listing(this.argumentList, " ");
    }

    public List<String> asList() {
        return Collections.unmodifiableList(this.argumentList);
    }

}
