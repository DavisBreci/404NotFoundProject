package com.urock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Polygon;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.*;

/**
 * Controller for the ClassViewer screen. Displays the students in a class.
 * @author Davis Breci
 */
public class ClassViewerController implements Initializable{

    @FXML
    private Label class_title;

    @FXML
    private Polygon back;

    @FXML
    private Label back_label;

    @FXML
    private Region header_box;

    @FXML
    private Pane root;

    @FXML
    private ListView<User> roster;

    /**
     * Handles the back button event. Navigates to the TeacherHome screen.
     * @param event The event triggered by clicking the back button.
     */
    @FXML
    void back(MouseEvent event) {
        try {
            App.setRoot("TeacherHome");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the class to be displayed in the ClassViewer.
     * @param classroom The class to be displayed.
     */
    static ArrayList<User> c = null;
    public static void setClass(ArrayList<User> classroom) {
        c = classroom;
    }

    /**
     * Initializes the scene. Sets the scale of the UI elements based on the window size.
     * @param arg0
     * @param arg1
     */
    public void initialize(URL arg0, ResourceBundle arg1) {
        MusicSystemFACADE facade = MusicSystemFACADE.getInstance();
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            final double SCALE = newValue.doubleValue() / (oldValue.doubleValue() != 0 ? oldValue.doubleValue() : 600);
            header_box.setLayoutX((header_box.getLayoutX() + header_box.getPrefWidth() /4)*SCALE);
            header_box.setScaleX(SCALE);

            class_title.setLayoutX((class_title.getLayoutX() + class_title.getPrefWidth() /4)*SCALE);
            class_title.setScaleX(SCALE);

            roster.setLayoutX((roster.getLayoutX() + roster.getPrefWidth() /4)*SCALE);
            roster.setScaleX(SCALE);

            back.setLayoutX(back.getLayoutX() * SCALE);
            for(int i = 0; i < back.getPoints().size(); i+=2) {
                back.getPoints().set(i, back.getPoints().get(i)*SCALE);
            }
            
            back_label.setLayoutX((back_label.getLayoutX() + back_label.getPrefWidth() /4)*SCALE);
            back_label.setScaleX(SCALE);
        });
        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            final double SCALE = newValue.doubleValue() / (oldValue.doubleValue() != 0 ? oldValue.doubleValue() : 400);
            header_box.setLayoutY((header_box.getLayoutY() + header_box.getPrefHeight() /4)*SCALE);
            header_box.setScaleY(SCALE);

            class_title.setLayoutY((class_title.getLayoutY() + class_title.getPrefHeight() /4)*SCALE);
            class_title.setScaleY(SCALE);

            roster.setLayoutY((roster.getLayoutY() + roster.getPrefHeight() /4)*SCALE);
            roster.setScaleY(SCALE);

            back.setLayoutY(back.getLayoutY() * SCALE);
            for(int i = 1; i < back.getPoints().size(); i+=2) {
                back.getPoints().set(i, back.getPoints().get(i)*SCALE);
            }
            
            back_label.setLayoutY((back_label.getLayoutY() + back_label.getPrefHeight() /4)*SCALE);
            back_label.setScaleY(SCALE);
        });
        Teacher temp = (Teacher)facade.getCurrentUser();
        int num = 1 + temp.getClasses().indexOf(c);
        class_title.setText("Class " + num);
        initializeStudents();
    }

    /**
     * Initializes the students in the class and sets them in the list.
     */
    public void initializeStudents() {
        ObservableList<User> fill = FXCollections.observableArrayList(c);
        roster.setItems(fill);
    }
}

        

