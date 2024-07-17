module com.msebastiao.sap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Si usas JDBC, por ejemplo

    opens com.msebastiao.sap to javafx.fxml;
    opens com.msebastiao.sap.controller to javafx.fxml;

    exports com.msebastiao.sap;
    exports com.msebastiao.sap.controller;
}
