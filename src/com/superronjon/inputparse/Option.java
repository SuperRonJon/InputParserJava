package com.superronjon.inputparse;

public class Option {
	private final char flag;
	private final boolean takesArgument;
	private boolean wasGiven;
	private String value;
	private final String name;
	private final String description;

	public Option(char flag, String name, boolean takesArgument, String defaultValue) {
		this.takesArgument = takesArgument;
		this.flag = flag;
		this.value = defaultValue;
		this.wasGiven = false;
		this.name = name;
		this.description = "";
	}

	public Option(char flag, String name, boolean takesArgument, String defaultValue, String description) {
		this.takesArgument = takesArgument;
		this.flag = flag;
		this.value = defaultValue;
		this.wasGiven = false;
		this.name = name;
		this.description = description;
	}

	public Option(char flag, String name) {
		this.takesArgument = false;
		this.flag = flag;
		this.value = "False";
		this.name = name;
		this.description = "";
		this.wasGiven = false;
	}

	public Option(char flag, String name, String description) {
		this.takesArgument = false;
		this.flag = flag;
		this.value = "False";
		this.name = name;
		this.description = description;
		this.wasGiven = false;
	}

	public void setValue(String val) {
		this.value = val;
		this.wasGiven = true;
	}

	public void setValue() {
		this.setValue(true);
	}

	public void setValue(boolean val) {
		if(val) {
			this.value = "True";
		}
		else {
			this.value = "False";
		}
		this.wasGiven = true;
	}

	public char getFlag() { return this.flag; }
	public String getName() { return this.name; }
	public boolean getTakesArgument() { return this.takesArgument; }
	public String getValue() { return this.value; }
	public boolean getWasGiven() { return this.wasGiven; }
	public String getDescription() { return this.description; }
}
