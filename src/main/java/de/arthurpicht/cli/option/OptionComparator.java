package de.arthurpicht.cli.option;

import java.util.Comparator;

public class OptionComparator implements Comparator<Option> {

    @Override
    public int compare(Option o1, Option o2) {
        return String.CASE_INSENSITIVE_ORDER.compare(getCompareString(o1), getCompareString(o2));
//        return getCompareString(o1).compareTo(getCompareString(o2));
    }

    private String getCompareString(Option option) {

        StringBuilder stringBuilder = new StringBuilder();

        if (option.hasShortName()) {
            stringBuilder.append(option.getShortName());
        } else {
            stringBuilder.append(option.getLongName(), 0, 1);
        }

        if (option.hasLongName()) {
            stringBuilder.append(option.getLongName());
        }

        return stringBuilder.toString();
    }

}
