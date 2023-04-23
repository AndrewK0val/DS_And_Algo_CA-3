module com.example.sem2ca1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
//    requires javafx.swing;


    opens com.example.sem2ca1 to javafx.fxml;
    exports com.example.sem2ca1;
}