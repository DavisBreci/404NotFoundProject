package com.urock;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.*;

public class UserHomeController implements Initializable{

    @FXML
    private HBox assigned_lessons;

    @FXML
    private Label assigned_lessons_label;

    @FXML
    private Button compose;

    @FXML
    private Line h_line;

    @FXML
    private Label home_label;

    @FXML
    private ImageView logo;

    @FXML
    private Button logout;

    @FXML
    private HBox playlists;

    @FXML
    private Label playlists_label;

    @FXML
    private Button search;

    @FXML
    private Line v_line;

    @FXML
    private Pane root;

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void compose(ActionEvent event) {

    }

    MusicSystemFACADE facade = MusicSystemFACADE.getInstance();
    public void initialize(URL arg0, ResourceBundle arg1) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(assigned_lessons_label);
        nodes.add(compose);
        nodes.add(assigned_lessons);
        nodes.add(search);
        nodes.add(playlists);
        nodes.add(playlists_label);
        nodes.add(home_label);
        nodes.add(logout);
//        nodes.add(logo);
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            final double SCALE = newValue.doubleValue()/600;
            h_line.layoutXProperty().set(root.getWidth()/2);
            h_line.setStartX(root.getWidth()*(-0.45));
            h_line.setEndX(root.getWidth()*0.45);

            v_line.layoutXProperty().set(root.getWidth()*0.17);
            v_line.setStartX(0);
            v_line.setEndX(0);

            logo.setX(logo.getLayoutX()*SCALE);
            logo.setScaleX(SCALE);
            
            for (int i = 0; i < nodes.size(); ++i) {
                nodes.get(i).setLayoutX(nodes.get(i).getLayoutX() * SCALE);
                System.out.println(nodes.get(i).toString() + " X coordinate: " + nodes.get(i).getLayoutX());
                nodes.get(i).setScaleX(SCALE);
            }
        });
        root.heightProperty().addListener((observable, oldvalue, newValue) -> {
            final double SCALE = newValue.doubleValue()/400;

            h_line.layoutYProperty().set(root.getHeight()*0.23);
            h_line.setStartY(0);
            h_line.setEndY(0);

            v_line.layoutYProperty().set(root.getHeight()/2);
            v_line.setStartY(root.getHeight()*(-0.44));
            v_line.setEndY(root.getHeight()*0.44);

            logo.setY(logo.getLayoutY() * SCALE);
            logo.setScaleY(SCALE);
            
            for (int i = 0; i < nodes.size(); ++i) {
                nodes.get(i).setLayoutY(nodes.get(i).getLayoutY() * SCALE);
                System.out.println(nodes.get(i).toString() + " Y coordinate: "+ nodes.get(i).getLayoutY());
                nodes.get(i).setScaleY(SCALE);
            }
        });

        try {
           initializePlaylists();
           initializeLessons();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void initializePlaylists() throws IOException {
        Button scrollingPlaylist = null;
            for(Playlist p : facade.getCurrentUser().getPlaylists()){
                scrollingPlaylist = (Button)App.loadFXML("TemplateScrollPlaylist");
                scrollingPlaylist.setText(p.getTitle());
                scrollingPlaylist.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent arg0) {
                            System.out.println("Go to " + "\"" +  p.getTitle() + "\"");
                            try{
                                PlaylistViewerController.setPlaylist(p);
                                App.setRoot("PlaylistViewer");
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                );
                playlists.getChildren().add(scrollingPlaylist);
            }
    }

    public void initializeLessons() throws IOException {
        Button scrollingPlaylist = null;
            for(Lesson l : facade.getCurrentUser().getAssignedLessons()){
                scrollingPlaylist = (Button)App.loadFXML("TemplateScrollLesson");
                scrollingPlaylist.setText(l.getTitle());
                scrollingPlaylist.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent arg0) {
                            try {
                                PlaylistViewerController.setLesson(l);
                                App.setRoot("PlaylistViewer");
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                );
                assigned_lessons.getChildren().add(scrollingPlaylist);
            }
    }
}