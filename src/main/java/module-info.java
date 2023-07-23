module com.example.individualhealthassessment_program {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.xml.bind;
    requires jakarta.xml.bind; // Use javax.xml.bind; for Java versions before 9

    opens com.example.individualhealthassessment_program to javafx.fxml, jakarta.xml.bind;
    exports com.example.individualhealthassessment_program;
}
