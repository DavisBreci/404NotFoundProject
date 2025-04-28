package com.urock;

import java.net.URL;
import java.util.ResourceBundle;

import com.model.NoteValue;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author 
 */
public class QuaverListViewCell extends ListCell<NoteValue> implements Initializable{
    static final double STROKE_WIDTH = 4;
    static final double ARC = 30;
    private NoteValue noteValue;
    @FXML
    private Rectangle bgPanel;

    @FXML
    private ImageView quaverImage;
    @FXML
    private StackPane quaverFrame;

    /**
     * 
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        quaverImage.fitWidthProperty().bind(bgPanel.widthProperty().multiply(.8));
        quaverImage.fitHeightProperty().bind(bgPanel.heightProperty().multiply(.8));
        quaverImage.setEffect(new ColorAdjust(0,0,1,0));
        bgPanel.setFill(Color.web("333333"));
        bgPanel.setStyle("-fx-stroke:#1F1F1F;");
        bgPanel.setArcHeight(ARC);
        bgPanel.setArcWidth(ARC);
        bgPanel.setStrokeWidth(STROKE_WIDTH);
    }

    /**
     * 
     * @param width
     * @param height
     */
    public void setDimensions(double width, double height){
        quaverFrame.setPrefWidth(height);
        quaverFrame.setPrefHeight(height);
        bgPanel.setWidth(width);
        bgPanel.setHeight(height);
    }

    /**
     * 
     * @param noteValue
     */
    public void setNoteValue(NoteValue noteValue){
        if(noteValue != null){
            this.noteValue = noteValue;
            quaverImage.setImage(new Image(App.class.getResourceAsStream("Images/Quavers/" + noteValue + ".png")));
        }
    }
    
}
