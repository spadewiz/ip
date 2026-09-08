package wiz;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Custom JavaFX control representing a dialog box consisting of an ImageView and a text label.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        assert text != null : "Dialog text cannot be null";
        assert img != null : "Dialog image cannot be null";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert dialog != null : "Dialog label was not injected properly";
        assert displayPicture != null : "displayPicture was not injected properly";

        dialog.setText(text);
        displayPicture.setImage(img);

        // Clip the avatar to a circle
        Circle clip = new Circle(25, 25, 25);
        displayPicture.setClip(clip);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Gets a dialog box for the user.
     *
     * @param text The text content.
     * @param img The user avatar image.
     * @return A DialogBox configured for the user.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        assert text != null : "User dialog text cannot be null";
        assert img != null : "User image cannot be null";
        var db = new DialogBox(text, img);
        db.getStyleClass().add("user-dialog");
        return db;
    }

    /**
     * Gets a dialog box for Duke/Wiz.
     *
     * @param text The text content.
     * @param img The Duke/Wiz avatar image.
     * @return A DialogBox configured for Duke/Wiz, flipped.
     */
    public static DialogBox getDukeDialog(String text, Image img) {
        assert text != null : "Duke dialog text cannot be null";
        assert img != null : "Duke image cannot be null";
        var db = new DialogBox(text, img);
        db.flip();
        db.getStyleClass().add("wiz-dialog");
        return db;
    }
}
