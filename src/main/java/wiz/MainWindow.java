package wiz;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Wiz wiz;

    private final Image userImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaUser.png"))
    );
    private final Image dukeImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaDuke.png"))
    );

    @FXML
    public void initialize() {
        assert scrollPane != null : "scrollPane was not injected properly";
        assert dialogContainer != null : "dialogContainer was not injected properly";
        assert userInput != null : "userInput was not injected properly";
        assert sendButton != null : "sendButton was not injected properly";

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Wiz instance and shows the welcome message.
     *
     * @param d The Wiz instance to use.
     */
    public void setWiz(Wiz d) {
        assert d != null : "Wiz instance cannot be null";
        wiz = d;
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog("Hello! I'm Wiz, your personal task assistant.\n"
                        + "How can I help you today?", dukeImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke/Wiz's reply and then appends them
     * to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        assert wiz != null : "Wiz instance must be initialized before handling user input";
        String input = userInput.getText();
        String response = wiz.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
    }
}
