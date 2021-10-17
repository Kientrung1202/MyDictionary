module com.example.trung {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
//    requires org.json;
    requires java.net.http;
    requires freetts;
//    requires com.google.api.services.sourcerepo;

    opens com.example.trung to javafx.fxml;
    exports com.example.trung;
}