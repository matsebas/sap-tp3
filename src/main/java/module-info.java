module com.msebastiao.sap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.msebastiao.sap to javafx.fxml;
    opens com.msebastiao.sap.controller to javafx.fxml;
    opens com.msebastiao.sap.model to javafx.base;

    exports com.msebastiao.sap;
    exports com.msebastiao.sap.controller;
    exports com.msebastiao.sap.model;
    exports com.msebastiao.sap.dao;
}
