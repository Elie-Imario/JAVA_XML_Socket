module com.app.uicore.projet_2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.xml;

    opens com.app.uicore.projet_2 to javafx.fxml;
    exports com.app.uicore.projet_2;
    exports com.app.uicore.projet_2.bo;
    exports com.app.uicore.projet_2.boController;
    opens com.app.uicore.projet_2.bo to javafx.fxml;
}