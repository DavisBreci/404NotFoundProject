package com.urock;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

/**
 * Controller for Search Result Cell
 */
public class SearchResultCell extends ListCell{
    @FXML
    private Label primary;

    @FXML
    private Label secondary;

    @FXML
    private Label tertiary;
    
    /**
     * Setter for Labels, checks if any values are null and if they are, set those values according to input 
     * @param primary
     * @param secondary
     * @param tertiary
     */
    public void setLabels(String primary, String secondary, String tertiary){
        if(primary == null || secondary == null || tertiary == null) return;
        this.primary.setText(primary);
        this.secondary.setText(secondary);
        this.tertiary.setText(tertiary);
    }


}