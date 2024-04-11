module com.example.miniproject_hotel_management_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;
    requires kotlin.stdlib;

    opens com.example.miniproject_hotel_management_system to javafx.fxml;
    exports com.example.miniproject_hotel_management_system;
}