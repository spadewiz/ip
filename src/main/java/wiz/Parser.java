package wiz;

public class Parser {

    public static String getCommandWord(String input) {
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

    public static String getArguments(String input) {
        String trimmed = input.trim();

        int firstSpace = trimmed.indexOf(" ");

        if (firstSpace == -1) {
            return "";
        }

        return trimmed.substring(firstSpace + 1);
    }
}