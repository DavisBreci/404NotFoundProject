package com.urock;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class SearchResultCell extends ListCell{
    @FXML
    private Label primary;

    @FXML
    private Label secondary;

    @FXML
    private Label tertiary;
    
    public void setLabels(String primary, String secondary, String tertiary){
        if(primary == null || secondary == null || tertiary == null) return;
        this.primary.setText(primary);
        this.secondary.setText(secondary);
        this.tertiary.setText(tertiary);
    }


}