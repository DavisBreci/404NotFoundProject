module com.urock {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive json.simple;
    requires jfugue;
    requires transitive java.desktop;
    requires junit;
    requires javafx.graphics;
    requires javafx.base;
    
    opens com.urock to javafx.fxml;
    exports com.urock;

    opens com.model to javafx.fxml;
    exports com.model;

}
