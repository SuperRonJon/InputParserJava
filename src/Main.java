import com.superronjon.inputparse.GenericInputParser;
import com.superronjon.inputparse.UnrecognizedOptionException;

public class Main
{
	public static void  main(String[] args) {
		GenericInputParser parser = new GenericInputParser("ascii-generator", "ascii-generator [OPTIONS] image.jpg");
		parser.addOption('i', "invert", "Invert colors so brightest pixels use densest characters");
		parser.addOption('b', "borders", "Print board with borders");
		parser.addOption('f', "toFile", "Output to file");
		parser.addOption('s', "scaling", true, "1.0", "Used to scale height and width evenly with single value");
		parser.addOption('w', "width", true, "-1.0", "Used to scale width separately from height");
		parser.addOption('h', "height", true, "-1.0", "Used to scale height separately from width");
		parser.addOption('H', "help", "Show help menu");

		try{
			parser.parseInput(args);
		}
		catch (UnrecognizedOptionException e)
		{
			System.out.println(e.getMessage());
			return;
		}

		if(Boolean.parseBoolean(parser.getOptionValue("help"))) {
			parser.printHelp();
			return;
		}

//		printBool(parser.getOptionValue("invert").equals("True"));
//		printBool(parser.getOptionValue("borders").equals("False"));
//		printBool(parser.getOptionValue("toFile").equals("False"));
//		printBool(parser.getOptionValue("scaling").equals("1.0"));
//		printBool(parser.getOptionValue("width").equals("0.3"));
//		printBool(parser.getOptionValue("height").equals("0.25"));
//		printBool(parser.getUnflaggedArgument(0).equals("test"));
//		printBool(parser.getUnflaggedArgument(1).equals("input"));

		printBool(parser.getOptionValue("invert").equals("True"));
		printBool(parser.getOptionValue("scaling").equals("0.1"));
		printBool(parser.getUnflaggedArgument(0).equals("test"));
		printBool(parser.getUnflaggedArgument(1).equals("input"));

		printBool(parser.getOptionValue("width").equals("0.2"));
		printBool(parser.getOptionValue("height").equals("0.3"));
	}

	private static void printBool(boolean bool) {
		if(bool) {
			System.out.println("Pass...");
		}
		else {
			System.out.println("FAIL!");
		}
	}

}
