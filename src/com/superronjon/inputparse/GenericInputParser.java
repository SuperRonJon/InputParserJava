package com.superronjon.inputparse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericInputParser
{
    private final List<String> tokens;
    private final List<Option> expectedOptions;
    private final List<String> dangling;

    private final String programTitle;
    private final String usageString;

    public GenericInputParser() {
        tokens = new ArrayList<>();
        expectedOptions = new ArrayList<>();
        dangling = new ArrayList<>();

        programTitle = "";
        usageString = "";
    }

    public GenericInputParser(String name) {
        tokens = new ArrayList<>();
        expectedOptions = new ArrayList<>();
        dangling = new ArrayList<>();

        programTitle = name;
        usageString = "";
    }

    public GenericInputParser(String name, String usage) {
        tokens = new ArrayList<>();
        expectedOptions = new ArrayList<>();
        dangling = new ArrayList<>();

        programTitle = name;
        usageString = usage;
    }

    public void parseInput(String[] args) throws UnrecognizedOptionException
    {
        tokens.addAll(Arrays.asList(args));

        Map<Character, Integer> indices = new HashMap<>();
        for(int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if(token.startsWith("--")) {
                String commandName = token.substring(2);
                Option currentOption = getOptionWithName(commandName);
                if(currentOption != null) {
                    if(currentOption.getTakesArgument()) {
                        indices.put(currentOption.getFlag(), Integer.valueOf(i + 1));
                    }
                    else {
                        currentOption.setValue();
                    }
                }
                else {
                    throw new UnrecognizedOptionException("Error: Command \"" + commandName + "\" not recognized...");
                }
            }
            else if(token.startsWith("-")) {
                int plusIndex = 1;
                String options = token.substring(1);
                for(int j = 0; j < options.length(); j++) {
                    Option currentOption = getOptionWithFlag(options.charAt(j));
                    if(currentOption != null) {
                        if(currentOption.getTakesArgument()) {
                            indices.put(currentOption.getFlag(), Integer.valueOf(i + plusIndex));
                            plusIndex++;
                        }
                        else {
                            currentOption.setValue();
                        }
                    }
                    else {
                        throw new UnrecognizedOptionException("Error: Option \"" + options.charAt(j) + "\" not recognized...");
                    }
                }
            }
            else {
                Character flag = getFlagAtIndex(indices, i);
                if(flag != null) {
                    Option currentOption = getOptionWithFlag(flag);
                    if(currentOption != null) {
                        currentOption.setValue(token);
                    }
                }
                else {
                    dangling.add(token);
                }
            }
        }
    }

    public void addOption(Option option) {
        this.expectedOptions.add(option);
    }

    public void addOption(char flag, String name) {
        this.expectedOptions.add(new Option(flag, name));
    }

    public void addOption(char flag, String name, String description) {
        this.expectedOptions.add(new Option(flag, name, description));
    }

    public void addOption(char flag, String name, boolean takesArgument, String defaultValue) {
        this.expectedOptions.add(new Option(flag, name, takesArgument, defaultValue));
    }

    public void addOption(char flag, String name, boolean takesArgument, String defaultValue, String description) {
        this.expectedOptions.add(new Option(flag, name, takesArgument, defaultValue, description));
    }

    public String getOptionValue(char flag) {
        Option foundOption = getOptionWithFlag(flag);
        if(foundOption != null) {
            return foundOption.getValue();
        }
        return null;
    }

    public String getOptionValue(String name) {
        Option foundOption = getOptionWithName(name);
        if (foundOption != null) {
            return foundOption.getValue();
        }
        return null;
    }

    public List<String> getUnflaggedArguments() {
        return dangling;
    }

    public String getUnflaggedArgument(int i) {
        if(0 <= i && i < dangling.size()) {
            return dangling.get(i);
        }
        return null;
    }

    public void printHelp() {
        boolean printed = false;
        if(!this.programTitle.isEmpty()) {
            System.out.printf("%s\n\n", this.programTitle);
            printed = true;
        }
        if(!this.usageString.isEmpty()) {
            System.out.printf("Usage:\n       %s\n", this.usageString);
        }
        else if(printed) {
            System.out.println();
        }
        System.out.println("Options:");
        int longestOption = getLongestOptionStringLength(expectedOptions) + 3;
        for (Option option : expectedOptions) {
            String optionHelp = option.getOptionFlagString(option);
            System.out.printf("  %-" + longestOption + "s %s\n", optionHelp, option.getDescription());
        }
    }

    private int getLongestOptionStringLength(List<Option> expectedOptions) {
        int longest = 0;
        for(Option opt : expectedOptions) {
            String helpString = opt.getOptionFlagString(opt);
            if(helpString.length() > longest) longest = helpString.length();
        }
        return longest;
    }

    private Option getOptionWithFlag(char flag) {
        for (Option option : expectedOptions) {
            if(option.getFlag() == flag) {
                return option;
            }
        }
        return null;
    }

    private Option getOptionWithName(String name) {
        for (Option option : expectedOptions) {
            if(option.getName().equals(name)) {
                return option;
            }
        }
        return null;
    }

    private Character getFlagAtIndex(Map<Character, Integer> saved, int i) {
        for(Map.Entry<Character, Integer> entry : saved.entrySet()) {
            if(entry.getValue() == i) {
                return entry.getKey();
            }
        }
        return null;
    }
}
