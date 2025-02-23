package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import neochat.NeoChat;


/**
 * Controller for the main GUI.
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

    private NeoChat neoChat;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/rikki.jpg"));
    private Image neoChatImage = new Image(this.getClass().getResourceAsStream("/images/anon.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getNeoChatDialog("Hello! Welcome to NeoChat", neoChatImage));

        dialogContainer.setStyle("-fx-background-color: linear-gradient(to bottom, #E3F2FD, #BBDEFB);");
    }

    /** Injects the neochat instance */
    public void setNeoChat(NeoChat n) {
        this.neoChat = n;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing neochat's reply
     * and then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.equals("bye")) {
            this.neoChat.save();
            Platform.exit();
            return;
        }
        String response = neoChat.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getNeoChatDialog(response, neoChatImage)
        );
        userInput.clear();
    }
}
