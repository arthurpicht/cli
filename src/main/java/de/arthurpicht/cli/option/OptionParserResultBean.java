package de.arthurpicht.cli.option;

import java.util.Objects;

public class OptionParserResultBean {

    private Option option;
    private String value;

    public OptionParserResultBean(Option option) {
        this.option = option;
        this.value = null;
    }

    public OptionParserResultBean(Option option, String value) {

        this.option = option;
        this.value = value;
    }

    public String getOptionId() {
        return this.option.getId();
    }

    public String getValue() {
        return this.value;
    }

    public Option getOption() {
        return this.option;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionParserResultBean that = (OptionParserResultBean) o;
        return option.equals(that.option) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(option);
    }
}
