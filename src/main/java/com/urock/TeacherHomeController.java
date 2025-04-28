package com.urock;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.*;

/**
 * Controller for the Teacher Home Screen
 * @author Davis Breci
 */
public class TeacherHomeController implements Initializable{

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
    private ImageView streak_img;

    @FXML
    private Label streak_text;

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
    private Region region;

    @FXML
    private Label classes_label;

    @FXML
    private VBox classes;

    /**
     * Method that logs the teacher out and sets the root screen to the login screen
     * @param event
     */
    @FXML
    private Label add_playlist;

    @FXML
    private Label add_lesson;

    @FXML
    void logout(ActionEvent event) {
        try {
            facade.logout();
            App.setRoot("login");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Action that sets the root screen to the Search Page
     * @param event
     */
    @FXML
    void search(ActionEvent event) {
        try {
            App.setRoot("SearchPage");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Action that sets the root screen to the Copmose Landing Page
     * @param event
     */
    @FXML
    void compose(ActionEvent event) {
        try {
            App.setRoot("ComposeLandingPage");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    MusicSystemFACADE facade = MusicSystemFACADE.getInstance();
    /**
     * Method that initializes the UI componenets and Scaling
     */
    public void initialize(URL arg0, ResourceBundle arg1) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(region);
        nodes.add(assigned_lessons_label);
        nodes.add(compose);
        nodes.add(assigned_lessons);
        nodes.add(search);
        nodes.add(playlists);
        nodes.add(playlists_label);
        nodes.add(home_label);
        nodes.add(logout);
        nodes.add(classes_label);
        nodes.add(classes);
        nodes.add(streak_img);
        nodes.add(streak_text);
        nodes.add(add_playlist);
        nodes.add(add_lesson);

        streak_text.setText(Integer.toString(facade.getCurrentUser().getStreak()));

        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            final double SCALE = newValue.doubleValue()/(oldValue.doubleValue() == 0 ? 600 : oldValue.doubleValue());
            h_line.layoutXProperty().set(root.getWidth()/2);
            h_line.setStartX(root.getWidth()*(-0.45));
            h_line.setEndX(root.getWidth()*0.45);

            v_line.layoutXProperty().set(root.getWidth()*0.17);
            v_line.setStartX(0);
            v_line.setEndX(0);

            logo.setX(logo.getLayoutX()*SCALE);
            logo.setScaleX(SCALE);
            
            home_label.setLayoutX((home_label.getLayoutX() + home_label.getPrefWidth() /4)* SCALE);
            playlists_label.setLayoutX((playlists_label.getLayoutX() + playlists_label.getPrefWidth() /4)*SCALE);
            assigned_lessons_label.setLayoutX((assigned_lessons_label.getLayoutX() + assigned_lessons_label.getPrefWidth() /4)*SCALE);
            playlists.setLayoutX((playlists.getLayoutX() + playlists.getPrefWidth() /4)*SCALE);
            assigned_lessons.setLayoutX((assigned_lessons.getLayoutX() + assigned_lessons.getPrefWidth() /4)*SCALE);
            region.setLayoutX((region.getLayoutX() + region.getPrefWidth() /4)*SCALE);
            compose.setLayoutX((compose.getLayoutX() + compose.getPrefWidth() /4)*SCALE);
            search.setLayoutX((search.getLayoutX() + search.getPrefWidth() /4)*SCALE);
            logout.setLayoutX((logout.getLayoutX() + logout.getPrefWidth() /4)*SCALE);
            classes_label.setLayoutX((classes_label.getLayoutX() + classes_label.getPrefWidth() /4)*SCALE);
            classes.setLayoutX((classes.getLayoutX() + classes.getPrefWidth() /4)*SCALE);
            streak_img.setLayoutX((streak_img.getLayoutX() + streak_img.getFitWidth() /4)* SCALE);
            streak_text.setLayoutX((streak_text.getLayoutX() + streak_text.getPrefWidth() /4)*SCALE);
            add_playlist.setLayoutX((add_playlist.getLayoutX() + add_playlist.getPrefWidth() /4)*SCALE);
            add_lesson.setLayoutX((add_lesson.getLayoutX() + add_lesson.getPrefWidth() /4)*SCALE);


            for (int i = 0; i < nodes.size(); ++i) {
                // nodes.get(i).setLayoutX(nodes.get(i).getLayoutX() * SCALE);
                nodes.get(i).setScaleX(SCALE);
            }
        });
        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            final double SCALE = newValue.doubleValue()/(oldValue.doubleValue() == 0 ? 400 : oldValue.doubleValue());

            h_line.layoutYProperty().set(root.getHeight()*0.23);
            h_line.setStartY(0);
            h_line.setEndY(0);

            v_line.layoutYProperty().set(root.getHeight()/2);
            v_line.setStartY(root.getHeight()*(-0.44));
            v_line.setEndY(root.getHeight()*0.44);

            logo.setY(logo.getLayoutY() * SCALE);
            logo.setScaleY(SCALE);
            
            home_label.setLayoutY((home_label.getLayoutY() + home_label.getPrefHeight() / 2)* SCALE);
            playlists_label.setLayoutY((playlists_label.getLayoutY() + playlists_label.getPrefHeight() /4)*SCALE);
            assigned_lessons_label.setLayoutY((assigned_lessons_label.getLayoutY() + assigned_lessons_label.getPrefHeight() /4)*SCALE);
            playlists.setLayoutY((playlists.getLayoutY() + playlists.getPrefHeight() /4)*SCALE);
            assigned_lessons.setLayoutY((assigned_lessons.getLayoutY() + assigned_lessons.getPrefHeight() /4)*SCALE);
            region.setLayoutY((region.getLayoutY() + region.getPrefHeight() /4)*SCALE);
            compose.setLayoutY((compose.getLayoutY() + compose.getPrefHeight() /4)*SCALE);
            search.setLayoutY((search.getLayoutY() + search.getPrefHeight() /4)*SCALE);
            logout.setLayoutY((logout.getLayoutY() + logout.getPrefHeight() /4)*SCALE);
            classes_label.setLayoutY((classes_label.getLayoutY() + classes_label.getPrefHeight() /4)*SCALE);
            classes.setLayoutY((classes.getLayoutY() + classes.getPrefHeight() /4)*SCALE);
            streak_img.setLayoutY((streak_img.getLayoutY() + streak_img.getFitHeight() /4)*SCALE);
            streak_text.setLayoutY((streak_text.getLayoutY() + streak_text.getPrefHeight() /4)*SCALE);
            add_playlist.setLayoutY((add_playlist.getLayoutY() + add_playlist.getPrefHeight() /4)*SCALE);
            add_lesson.setLayoutY((add_lesson.getLayoutY() + add_lesson.getPrefHeight() /4)*SCALE);

            for (int i = 0; i < nodes.size(); ++i) {
                // nodes.get(i).setLayoutY(nodes.get(i).getLayoutY()* SCALE);
                nodes.get(i).setScaleY(SCALE);
            }
        });

        try {
           initializePlaylists();
           initializeLessons();
           initializeClasses();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Method that initializes the playlists and displays them on the screen
     * On select, sets root screen to Playlist Viewer Screen
     * @throws IOException
     */
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
    /**
     * Method that initializes the lessons and displays them on the screen
     * On select, sets root screen to Playlist Viewer Screen
     * @throws IOException
     */
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
        /**
     * Method that initializes the classes and displays them on the screen
     * On select, sets root screen to Class Viewer Screen
     * @throws IOException
     */

    public void initializeClasses() {
        Button scrollingPlaylist = null;
        final ArrayList<ArrayList<User>> classrooms = ((Teacher)facade.getCurrentUser()).getClasses();
        for(ArrayList<User> c : classrooms){
            try {
                scrollingPlaylist = (Button)App.loadFXML("TemplateScrollClasses");
            } catch(Exception e) {
                e.printStackTrace();
            }
            // int m = i + 1;
            scrollingPlaylist.setText("Class "+(classrooms.indexOf(c)+1));
            scrollingPlaylist.setOnAction(
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent arg0) {
                        try{
                            ClassViewerController.setClass(classrooms.get(classrooms.indexOf(c)));
                            App.setRoot("ClassViewer");
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            );
            classes.getChildren().add(scrollingPlaylist);
        }
    }

     @FXML
    void onAdd(MouseEvent event) throws IOException{
        App.setRoot("LessonCreator");
    }   
}