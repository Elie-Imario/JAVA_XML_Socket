module com.app.uicore.projet_2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.xml;
    requires java.sql;
    requires org.hibernate.commons.annotations;


    exports com.app.uicore.projet_2;
    exports com.app.uicore.projet_2.bo;
    exports com.app.uicore.projet_2.bo.beans;
    opens com.app.uicore.projet_2 to javafx.fxml;
    opens com.app.uicore.projet_2.bo.beans;
    opens com.app.uicore.projet_2.bo to javafx.fxml;
}