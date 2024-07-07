module proFit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    requires java.mail;
    requires java.prefs;
    requires itextpdf;
    requires activation;
    requires com.gluonhq.maps;
    requires jfxtras.agenda;
    requires org.apache.poi.poi;
    requires org.apache.pdfbox;
    requires org.json;
    requires com.google.zxing;
    requires org.controlsfx.controls;


    opens entites to javafx.fxml;
    opens controllers to javafx.fxml;

    exports entites;
    exports controllers;





}
