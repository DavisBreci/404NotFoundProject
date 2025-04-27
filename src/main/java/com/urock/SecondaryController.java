package com.urock;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 * Controller for secondary Screen
 */
public class SecondaryController {

    /** 
     * Method to switch the root screen from secondary to primary
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}