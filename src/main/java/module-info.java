module com.example.trung {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.trung to javafx.fxml;
    exports com.example.trung;
}