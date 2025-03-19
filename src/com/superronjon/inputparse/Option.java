package com.superronjon.inputparse;

public class Option {
	private final char flag;
	private final boolean takesArgument;
	private boolean wasGiven;
	private String value;

	public Option(char flag, boolean takesArgument, String defaultValue) {
		this.takesArgument = takesArgument;
		this.flag = flag;
		this.value = defaultValue;
		this.wasGiven = false;
	}

	public Option(char flag) {
		this.takesArgument = false;
		this.flag = flag;
		this.value = "False";
	}

	public void setValue(String val) {
		this.value = val;
		this.wasGiven = true;
	}

	public void setValue() {
		this.value = "True";
		this.wasGiven = true;
	}

	public char getFlag() { return this.flag; }
	public boolean getTakesArgument() { return this.takesArgument; }
	public String getValue() { return this.value; }
	public boolean getWasGiven() { return this.wasGiven; }
}
