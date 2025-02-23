package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.IOException;

public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    public DialogBox(String text, Image img, boolean isUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
        styleDialogBox(isUser);

        if (!isUser) {
            flip();
        }
    }

    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    private void styleDialogBox(boolean isUser) {
        if (isUser) {
            dialog.setBackground(new Background(new BackgroundFill(
                    Color.web("#D1E8FF"),
                    new CornerRadii(15), Insets.EMPTY)));
            this.setAlignment(Pos.TOP_RIGHT); // 用户消息靠右
        } else {
            dialog.setBackground(new Background(new BackgroundFill(
                    Color.web("#E0E0E0"),
                    new CornerRadii(15), Insets.EMPTY)));
            this.setAlignment(Pos.TOP_LEFT);
        }

        dialog.setPadding(new Insets(10));
        dialog.setMinHeight(Region.USE_PREF_SIZE);

        dialog.setMaxWidth(300);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, true);
    }

    public static DialogBox getNeoChatDialog(String text, Image img) {
        return new DialogBox(text, img, false);
    }
}
