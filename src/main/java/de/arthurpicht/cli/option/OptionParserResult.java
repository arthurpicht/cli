package de.arthurpicht.cli.option;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OptionParserResult {

    private Map<String, OptionParserResultBean> optionParserResultBeanMap;

    public OptionParserResult() {
        this.optionParserResultBeanMap = new HashMap<>();
    }

    public void addOption(Option option) {
        if (option.hasArgument()) throw new IllegalStateException("Value expected.");
        this.optionParserResultBeanMap.put(option.getId(), new OptionParserResultBean(option));
    }

    public void addOption(Option option, String value) {

//        System.out.println("Option added to Result: " + option.getId() + " Value: " + value);

        if (!option.hasArgument()) throw new IllegalStateException("No value expected.");
        this.optionParserResultBeanMap.put(option.getId(), new OptionParserResultBean(option, value));

//        System.out.println("Size: " + this.optionParserResultBeanMap.size());
    }

    public boolean hasOption(String id) {
        return this.optionParserResultBeanMap.containsKey(id);
    }

    public String getValue(String id) {
        OptionParserResultBean optionParserResultBean = this.optionParserResultBeanMap.get(id);
        if (optionParserResultBean == null) throw new IllegalStateException("Unchecked call of method 'getValue' for id [" + id + "].");

        if (!optionParserResultBean.getOption().hasArgument()) throw new RuntimeException("Option is specified with no value.");
        return optionParserResultBean.getValue();
    }

    public Set<String> getIdSet() {
        return this.optionParserResultBeanMap.keySet();
    }

    public int getSize() {
        return this.optionParserResultBeanMap.size();
    }

    public boolean isEmpty() {
        return this.optionParserResultBeanMap.isEmpty();
    }






}
