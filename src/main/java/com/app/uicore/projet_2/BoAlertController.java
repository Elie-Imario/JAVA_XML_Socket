package com.app.uicore.projet_2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class BoAlertController implements Initializable {

    @FXML protected Label alertMsg;
    @FXML protected ImageView icoAlert;
    @FXML protected Button alertBtn;
    @FXML protected VBox BoAlert;


    protected int type;
    protected Stage stage;

    protected
    String alertSuccessStyle = """
             -fx-background-color: #12C06A""";
    String alertErrorStyle = """
             -fx-background-color: #fff""";

    String alertWarningStyle = """
             -fx-background-color: #fff""";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alertBtn.setOnMouseClicked((ActionEvent)->{
            Stage stage = (Stage) alertBtn.getScene().getWindow();
            stage.close();
        });
    }


    public void setStage(Stage stage){
        this.stage = stage;
    }


    public void SuccessAlert(String alertmsg, String icoalerturl){
        BoAlert.setStyle(alertSuccessStyle);
        alertMsg.setText(alertmsg);
        alertMsg.setStyle("-fx-text-fill:WHITE;"+
                "-fx-font-family: \"sans-serif\";");
        Image image = new Image(this.getClass().getResourceAsStream(icoalerturl));
        icoAlert.setImage(image);
    }

    public void WarningAlert(String alertmsg, String icoalerturl){
        BoAlert.setStyle(alertWarningStyle);
        alertMsg.setText(alertmsg);
        alertMsg.setStyle("-fx-text-fill:BLACK;"+
                        "-fx-font-family: \"sans-serif\";");
        Image image = new Image(this.getClass().getResourceAsStream(icoalerturl));
        icoAlert.setImage(image);
    }

    public void ErrorAlert(String alertmsg, String icoalerturl){
        BoAlert.setStyle(alertErrorStyle);
        alertMsg.setText(alertmsg);
        Image image = new Image(this.getClass().getResourceAsStream(icoalerturl));
        alertMsg.setStyle("-fx-text-fill:RED;"+
                        "-fx-font-family: \"sans-serif\";");
        icoAlert.setImage(image);
    }
}
