package com.urock;

import javafx.scene.control.TextField;

import java.io.IOException;

import com.model.MusicSystemFACADE;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import com.model.*;

public class LoginController {
    String username;
    String password;
    boolean teacher;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField password_pw;

    @FXML
    private Text loginStatus_text;

    @FXML
    private ToggleButton teacherToggle;

    @FXML
    private TextField username_txt;

    @FXML
    private Button sign_up;

    @FXML
    void signup(ActionEvent event) throws IOException {
        try {
            App.setRoot("SignUp");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        username = username_txt.getText();
        password = password_pw.getText();
        loginStatus_text.setVisible(false);
        MusicSystemFACADE facade = MusicSystemFACADE.getInstance();
        if(facade.login(username, password)){
            try {
                Teacher test = (Teacher)facade.getCurrentUser();
                App.setRoot("TeacherHome");
            } catch(ClassCastException e) {
                App.setRoot("UserHome");
            }
        } else {
            loginStatus_text.setFill(Color.RED);
            loginStatus_text.setStroke(Color.RED);
            loginStatus_text.setText("Invalid username or password.");
            loginStatus_text.setVisible(true);
        }
    }
}
