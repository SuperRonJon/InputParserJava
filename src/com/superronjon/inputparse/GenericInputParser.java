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

	public GenericInputParser() {
		tokens = new ArrayList<>();
		expectedOptions = new ArrayList<>();
		dangling = new ArrayList<>();
	}

	public void parseInput(String[] args) {
		tokens.addAll(Arrays.asList(args));

		Map<Character, Integer> indices = new HashMap<>();
		for(int i = 0; i < tokens.size(); i++) {
			String token = tokens.get(i);
			if(token.startsWith("-")) {
				int plusIndex = 1;
				String options = token.substring(1);
				for(int j = 0; j < options.length(); j++) {
					Option currentOption = getOptionWithFlag(options.charAt(j));
					if(currentOption != null) {
						if(currentOption.getTakesArgument()) {
							indices.put(currentOption.getFlag(), i + plusIndex);
							plusIndex++;
						}
						else {
							currentOption.setValue();
						}
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

	public String getOptionValue(char flag) {
		for (Option option : expectedOptions) {
			if(option.getFlag() == flag) {
				return option.getValue();
			}
		}
		return null;
	}

	public List<String> getUnflaggedArguments() {
		return dangling;
	}

	public String getUnflaggedArgument(int i) {
		return dangling.get(i);
	}

	private Option getOptionWithFlag(char flag) {
		for (Option option : expectedOptions) {
			if(option.getFlag() == flag) {
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
