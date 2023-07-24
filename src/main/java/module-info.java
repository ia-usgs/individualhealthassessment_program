module com.example.individualhealthassessment_program {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.xml.bind;
    requires jakarta.xml.bind;

    opens com.example.individualhealthassessment_program to javafx.fxml, jakarta.xml.bind;
    exports com.example.individualhealthassessment_program;
}
