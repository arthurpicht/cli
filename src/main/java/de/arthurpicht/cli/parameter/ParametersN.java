package de.arthurpicht.cli.parameter;

import de.arthurpicht.cli.CliResultBuilder;
import de.arthurpicht.cli.help.HelpFormatterCommons;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParametersN extends Parameters {

    private final List<Parameter> parameterList;

    public ParametersN(int nrOfParameters) {
        if (nrOfParameters < 1)
            throw new IllegalArgumentException("Number of arguments out of range: " + nrOfParameters + ". Value >= 1 is expected.");

        this.parameterList = init(nrOfParameters, Parameter.DEFAULT_NAME);
    }

    public ParametersN(int nrOfParameters, String genericName) {
        if (nrOfParameters < 1)
            throw new IllegalArgumentException("Number of arguments out of range: " + nrOfParameters + ". Value >= 1 is expected.");
        if (Strings.isUnspecified(genericName))
            throw new IllegalArgumentException("Generic name is unspecified.");

        this.parameterList = init(nrOfParameters, genericName);
    }

    public ParametersN(List<Parameter> parameterList) {
        if (parameterList == null || parameterList.isEmpty())
            throw new IllegalArgumentException("Number of parameters in list must be > 0");

        this.parameterList = Collections.unmodifiableList(parameterList);
    }

    private List<Parameter> init(int nrOfParameters, String genericName) {
        List<Parameter> parameterList = new ArrayList<>();
        for (int i = 1; i <= nrOfParameters; i++) {
            String name = genericName + "-" + i;
            String description = "";
            Parameter parameter = new Parameter(name, description);
            parameterList.add(parameter);
        }
        return Collections.unmodifiableList(parameterList);
    }

    @Override
    public ParameterParser getParameterParser(CliResultBuilder cliResultBuilder, String executableName) {
        return new ParameterParserN(getNrOfParameters(), cliResultBuilder, executableName);
    }

    @Override
    public String getHelpUsageSubString() {
        List<String> nameList = new ArrayList<>();
        for (Parameter parameter : this.parameterList) {
            nameList.add(parameter.getUsageString());
        }
        return Strings.listing(nameList, " ");
    }

    @Override
    public String getHelpString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Parameter parameter : this.parameterList) {
            if (stringBuilder.length() != 0) stringBuilder.append("\n");
            String helpString = HelpFormatterCommons.formatStringsToCols(
                    parameter.getUsageString(),
                    parameter.getDescription()
            );
            stringBuilder.append(helpString);
        }
        return stringBuilder.toString();
    }

    public int getNrOfParameters() {
        return this.parameterList.size();
    }
}
