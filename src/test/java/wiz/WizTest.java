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

    @Test
    public void getResponse_clientCommand_addsClientSuccessfully() {
        Path testFile = tempDir.resolve("wiz_test.txt");
        Wiz wiz = new Wiz(testFile.toString());

        String addResponse = wiz.getResponse("client Alice Tan /phone 91234567 /email alice@example.com /note VIP");
        assertTrue(addResponse.contains("Got it. I've added this client:"));
        assertTrue(addResponse.contains("Alice Tan"));

        String listResponse = wiz.getResponse("clients");
        assertTrue(listResponse.contains("1.[C] Alice Tan (Phone: 91234567, Email: alice@example.com, Note: VIP)"));

        String findResponse = wiz.getResponse("findclient Alice");
        assertTrue(findResponse.contains("1.[C] Alice Tan"));

        String deleteResponse = wiz.getResponse("deleteclient 1");
        assertTrue(deleteResponse.contains("Noted. I've removed this client:"));

        String emptyListResponse = wiz.getResponse("clients");
        assertTrue(emptyListResponse.equals("Here are the clients in your list:"));
    }
}
