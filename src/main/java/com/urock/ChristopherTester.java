package com.urock;

import java.io.IOException;

import com.model.DataLoader;
import com.model.Instrument;
import com.model.MusicSystemFACADE;
import com.model.Score;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChristopherTester extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        MusicSystemFACADE.getInstance().login("dbreci", "404NF!");
        ScoreEditorController.loadScore(Score.midiToScore(DataLoader.loadSequence("Teen_Town.mid"), 0, Instrument.FRETLESS_BASS));
        Parent newRoot = loadFXML("ScoreEditor");
        // ScoreEditorController.loadScore(null);
        scene = new Scene(newRoot, 1280, 720);
        stage.setScene(scene);  
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    

    public static void main(String[] args) {
        launch();
    }

}