package com.urock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.*;
/**
 * Controller for the Sign Up screen 
 * @author
 */
public class SignUpController implements Initializable{

    @FXML
    private Polygon back;

    @FXML
    private Label back_label;

    @FXML
    private TextField email;

    @FXML
    private TextField first;

    @FXML
    private TextField last;

    @FXML
    private ImageView logo;

    @FXML
    private TextField pass;

    @FXML
    private Pane root;

    @FXML
    private Button signup;

    @FXML
    private ToggleButton teacher;

    @FXML
    private TextField user;

    /**
     * Method that switches the root screen to the login screen, going back
     * @param event
     */
    @FXML
    void back(MouseEvent event) {
        try {
            App.setRoot("login");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that creates an instance of the facade and then uses the input values to create a new user
     * Also checks whether the person signing up is a teacher or user
     * @param event 
     */
    @FXML
    void signup(ActionEvent event) {
        MusicSystemFACADE facade = MusicSystemFACADE.getInstance();
        String f = first.getText();
        String l = last.getText();
        String e = email.getText();
        String u = user.getText();
        String p = pass.getText();
        boolean t = teacher.isSelected();
        if(facade.signUp(t,f,l,e,u,p)) {
            try {
                Teacher test = (Teacher)facade.getCurrentUser();
                try {
                    App.setRoot("TeacherHome");
                } catch(Exception exception){
                    exception.printStackTrace();
                }
            } catch(ClassCastException cce) {
                try {
                    App.setRoot("UserHome");
                } catch(Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    /**
     * Method that initializes the UI components and Scaling
     * @param arg0
     * @param arg1
     */
    public void initialize(URL arg0, ResourceBundle arg1) {
        ArrayList<TextField> fields = new ArrayList<TextField>();
        fields.add(first);
        fields.add(last);
        fields.add(email);
        fields.add(user);
        fields.add(pass);

        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            final double SCALE = newValue.doubleValue() / (oldValue.doubleValue() != 0 ? oldValue.doubleValue() : 600);
            for (TextField tf : fields) {
                tf.setLayoutX((tf.getLayoutX() + tf.getPrefWidth() /4)*SCALE);
                tf.setScaleX(SCALE);
            }
            back.setLayoutX(back.getLayoutX() * SCALE);
            for(int i = 0; i < back.getPoints().size(); i+=2) {
                back.getPoints().set(i, back.getPoints().get(i)*SCALE);
            }
            
            back_label.setLayoutX((back_label.getLayoutX() + back_label.getPrefWidth() /4)*SCALE);
            back_label.setScaleX(SCALE);
            
            logo.setLayoutX((logo.getLayoutX() + logo.getFitWidth() /4)*SCALE);
            logo.setScaleX(SCALE);

            teacher.setLayoutX((teacher.getLayoutX() + 93.05078125 /4)*SCALE);
            teacher.setScaleX(SCALE);

            signup.setLayoutX((signup.getLayoutX() + signup.getPrefWidth() /4)*SCALE);
            signup.setScaleX(SCALE);
        });
        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            final double SCALE = newValue.doubleValue() / (oldValue.doubleValue() != 0 ? oldValue.doubleValue() : 400);
            for (TextField tf : fields) {
                tf.setLayoutY((tf.getLayoutY() + tf.getPrefHeight() /4)*SCALE);
                tf.setScaleY(SCALE);
            }
            back.setLayoutY(back.getLayoutY() * SCALE);
            for(int i = 1; i < back.getPoints().size(); i+=2) {
                back.getPoints().set(i, back.getPoints().get(i)*SCALE);
            }
            
            back_label.setLayoutY((back_label.getLayoutY() + back_label.getPrefHeight() /4)*SCALE);
            back_label.setScaleY(SCALE);
            
            logo.setLayoutY((logo.getLayoutY() + logo.getFitHeight() /4)*SCALE);
            logo.setScaleY(SCALE);

            teacher.setLayoutY((teacher.getLayoutY() + 25 /4)*SCALE);
            teacher.setScaleY(SCALE);

            signup.setLayoutY((signup.getLayoutY() + signup.getPrefHeight() /4)*SCALE);
            signup.setScaleY(SCALE);
        });
        
    }
}
