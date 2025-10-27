module bd.edu.seu.rms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens bd.edu.seu.rms to javafx.fxml;
    exports bd.edu.seu.rms;

    opens bd.edu.seu.rms.controllers to javafx.fxml;
    exports bd.edu.seu.rms.controllers;
    exports bd.edu.seu.rms.utility;
    opens bd.edu.seu.rms.utility to javafx.fxml;
    exports bd.edu.seu.rms.models;
    opens bd.edu.seu.rms.models to javafx.fxml;
}