package wiz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    public void getCommandWord_commandWithArguments_extractedCorrectly() {
        assertEquals("todo", Parser.getCommandWord("todo read book"));
    }

    @Test
    public void getCommandWord_commandOnly_extractedCorrectly() {
        assertEquals("list", Parser.getCommandWord("list"));
    }

    @Test
    public void getCommandWord_emptyInput_emptyStringReturned() {
        assertEquals("", Parser.getCommandWord(""));
    }

    @Test
    public void getArguments_commandWithArguments_extractedCorrectly() {
        assertEquals("read book", Parser.getArguments("todo read book"));
    }

    @Test
    public void getArguments_commandOnly_emptyStringReturned() {
        assertEquals("", Parser.getArguments("list"));
    }
}
