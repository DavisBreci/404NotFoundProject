package com.urock;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import com.model.MusicSystemFACADE;

/**
 * Urock App main class.
 * @author
 */
public class App extends Application {

    private static Scene scene;

    /**
     * Sets up the initial scene and stage for the application.
     * @param stage The stage for the app where scenes are loaded.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Ensures a logout when the application is closed.
     */
    @Override
    public void stop(){
        MusicSystemFACADE facade = MusicSystemFACADE.getInstance();
        facade.logout();
    }

    /**
     * Sets the root of the scene to a new FXML file.
     * @param fxml The name of the FXML file to load.
     * @throws IOException If the FXML file cannot be loaded.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads an FXML file and returns the root node.
     * @param fxml The name of the FXML file to load.
     * @return The root node of the loaded FXML file.
     * @throws IOException If the FXML file cannot be loaded.
     */
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}