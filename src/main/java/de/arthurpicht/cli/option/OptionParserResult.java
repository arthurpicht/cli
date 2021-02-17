package de.arthurpicht.cli.option;

import de.arthurpicht.cli.common.ParserResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OptionParserResult implements ParserResult {

    private final Map<String, OptionParserResultBean> optionParserResultBeanMap;
    private boolean hasBreakingOption;

    public OptionParserResult() {
        this.optionParserResultBeanMap = new HashMap<>();
        this.hasBreakingOption = false;
    }

    public void addOption(Option option) {
        if (option.hasArgument()) throw new IllegalStateException("Value expected.");
        this.optionParserResultBeanMap.put(option.getId(), new OptionParserResultBean(option));
        if (option.isBreaking()) hasBreakingOption = true;
    }

    public void addOption(Option option, String value) {
        if (!option.hasArgument()) throw new IllegalStateException("No value expected.");
        this.optionParserResultBeanMap.put(option.getId(), new OptionParserResultBean(option, value));
        if (option.isBreaking()) hasBreakingOption = true;
    }

    public boolean hasOption(String id) {
        return this.optionParserResultBeanMap.containsKey(id);
    }

    public String getValue(String id) {
        OptionParserResultBean optionParserResultBean = this.optionParserResultBeanMap.get(id);
        if (optionParserResultBean == null)
            throw new IllegalStateException("Unchecked call of method 'getValue' for id [" + id + "].");

        if (!optionParserResultBean.getOption().hasArgument())
            throw new RuntimeException("Option is specified with no value.");

        return optionParserResultBean.getValue();
    }

    public boolean hasBreakingOption() {
        return this.hasBreakingOption;
    }

    public Option getOption(String id) {
        OptionParserResultBean optionParserResultBean = this.optionParserResultBeanMap.get(id);
        if (optionParserResultBean == null)
            throw new IllegalStateException("Unchecked call of method 'getValue' for id [" + id + "].");

        return optionParserResultBean.getOption();
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

    public boolean hasOptions() {
        return !isEmpty();
    }

}
