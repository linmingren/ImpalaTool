package me.linmingren.impalatool;

public class ConsolePrinter {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static void printError(String string) {
		if (System.getProperty("disableColor") != null) {
			System.out.println(string);
		} else {
			System.out.println(ANSI_RED + string + ANSI_RESET);
		}
	}
	
	public static void printInfo(String string) {
		if (System.getProperty("disableColor") != null) {
			System.out.println(string);
		} else {
			System.out.println(ANSI_GREEN + string + ANSI_RESET);
		}
	}
	
	
	
	public static void println(String string) {
		System.out.println(string);
	}
}
