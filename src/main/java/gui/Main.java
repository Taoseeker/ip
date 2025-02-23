package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import neochat.NeoChat;

import java.io.IOException;

public class Main extends Application {
    private NeoChat neochat = new NeoChat();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("NeoChat");
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setNeoChat(neochat);  // inject the NeoChat instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}