package wiz;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class WizTest {

    @TempDir
    Path tempDir;

    @Test
    public void getResponse_todoCommand_successResponse() {
        Path testFile = tempDir.resolve("wiz_test.txt");
        Wiz wiz = new Wiz(testFile.toString());

        String response = wiz.getResponse("todo read book");
        assertTrue(response.contains("Got it. I've added this task:"));
        assertTrue(response.contains("read book"));
    }

    @Test
    public void getResponse_unknownCommand_errorResponse() {
        Path testFile = tempDir.resolve("wiz_test.txt");
        Wiz wiz = new Wiz(testFile.toString());

        String response = wiz.getResponse("invalidcommand");
        assertTrue(response.contains("Oops! I don't know what that command means."));
    }

    @Test
    public void getResponse_emptyInput_promptResponse() {
        Path testFile = tempDir.resolve("wiz_test.txt");
        Wiz wiz = new Wiz(testFile.toString());

        String response = wiz.getResponse("   ");
        assertTrue(response.contains("Please enter a valid command."));
    }
}
