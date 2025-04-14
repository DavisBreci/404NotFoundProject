package com.urock;

import javafx.scene.control.TextField;

import java.io.IOException;

import com.model.MusicSystemFACADE;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import model.*;

public class LoginController {
    String username;
    String password;
    boolean teacher;
    @FXML
    private Button loginButton;
    @FXML
    private TextField password_txt;

    @FXML
    private ToggleButton teacherToggle;

    @FXML
    private TextField username_txt;

    @FXML
    void login(ActionEvent event) throws IOException {
        username = username_txt.getText();
        password = password_txt.getText();
        teacher = teacherToggle.isSelected();
        MusicSystemFACADE facade = MusicSystemFACADE.getInstance();
        if(facade.login(username, password)){
            System.out.println("le epic login complete");
            App.setRoot("UserHome");
        }
    }

}
