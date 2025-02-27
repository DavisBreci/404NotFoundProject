module com.urock {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires jfugue;
    

    opens com.urock to javafx.fxml;
    exports com.urock;

    opens com.model to javafx.fxml;
    exports com.model;

}
