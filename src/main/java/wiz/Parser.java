package wiz;

/**
 * Parses user input strings into command words and argument strings.
 */
public class Parser {

    /**
     * Extracts the command word from raw user input.
     *
     * @param input The raw input string from the user.
     * @return The command word, or empty string if input is blank.
     */
    public static String getCommandWord(String input) {
        assert input != null : "Input string cannot be null";
        String trimmed = input.trim();

        if (trimmed.isEmpty()) {
            return "";
        }

        int firstSpace = trimmed.indexOf(" ");

        if (firstSpace == -1) {
            return trimmed;
        }

        return trimmed.substring(0, firstSpace);
    }

    /**
     * Extracts the arguments portion from raw user input.
     *
     * @param input The raw input string from the user.
     * @return The argument string, or empty string if no arguments are provided.
     */
    public static String getArguments(String input) {
        assert input != null : "Input string cannot be null";
        String trimmed = input.trim();

        int firstSpace = trimmed.indexOf(" ");

        if (firstSpace == -1) {
            return "";
        }

        return trimmed.substring(firstSpace + 1);
    }
}