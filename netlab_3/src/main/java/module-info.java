module com.company {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires java.net.http;
    requires org.apache.commons.lang3;
    requires java.desktop;


    opens com.company.app to org.apache.commons.lang3, com.fasterxml.jackson.databind;
    exports com.company.app;
    opens com.company.controller to javafx.fxml;
    exports com.company.controller;
    opens com.company.view to javafx.fxml;
    exports com.company.view;
}