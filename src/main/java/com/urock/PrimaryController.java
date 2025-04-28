package com.urock;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 * 
 * @author
 */
public class PrimaryController {

    /**
     * Switches to the secondary screen.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
