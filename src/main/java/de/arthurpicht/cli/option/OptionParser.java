package de.arthurpicht.cli.option;

import de.arthurpicht.cli.Options;

public class OptionParser {

//    private Set<OptionParserResultBean> optionParserResultBeanSet;
    private OptionParserResult optionParserResult;

    public OptionParser(Options options, String[] args) throws OptionParserException {

//        this.optionParserResultBeanSet = new HashSet<>();
        this.optionParserResult = new OptionParserResult();

        OptionParserState optionParserState = new OptionParserStateName(options, this);

        for (String arg : args) {
            System.out.println("verarbeite arg:" + arg);

            optionParserState = optionParserState.process(arg);

            System.out.println("zurückgegebener ParserState: " + optionParserState.getClass().getSimpleName());

        }

        if (optionParserState instanceof OptionParserStateValue) {
            OptionParserStateValue optionParserStateValue = (OptionParserStateValue) optionParserState;
            throw new ValueExpectedExcpetion(optionParserStateValue.getNameArg());
        }

    }

    public OptionParserResult getOptionParserResult() {
        return this.optionParserResult;
    }
//    public void addOptionParserResult(OptionParserResultBean optionParserResultBean) {
//        this.optionParserResult.add(optionParserResultBean);
//    }
//
//    public Set<OptionParserResultBean> getOptionParserResultBeanSet() {
//        return this.optionParserResultBeanSet;
//    }

}
