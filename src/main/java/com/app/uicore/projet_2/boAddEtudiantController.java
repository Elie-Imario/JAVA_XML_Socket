package com.app.uicore.projet_2;

import com.app.uicore.projet_2.bo.beans.Etudiant;
import com.app.uicore.projet_2.bo.service.EtudiantService;
import com.app.uicore.projet_2.bo.util.StaxWritter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class boAddEtudiantController implements Initializable {
    @FXML
    private Button btnConfirm, btnCancel;
    @FXML
    private TextField nomEtudiant, adresseEtudiant, bourseEtudiant;
    @FXML
    private VBox mainWindow;

    private StaxWritter staxWritter;
    private File file;
    private EtudiantService etudiantService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnCancel.setOnMouseClicked((ActionEvent)->{
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        });

        btnConfirm.setOnMouseClicked((ActionEvent)->{
            try {
                initAddEtudiant(nomEtudiant.getText(), adresseEtudiant.getText(), Integer.valueOf(bourseEtudiant.getText()));
                Stage stage = (Stage) btnConfirm.getScene().getWindow();
                stage.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        });
    }
    public void initAddEtudiant(String _nom, String _adresse, int _bourse){
        if(!file.exists()){
            String file_path = System.getProperty("user.home") + "\\Downloads\\exportedFile\\etudiant.xml";
            staxWritter.setFile(file_path);
            etudiantService.saveEtudiantData_toXML(staxWritter, null, _nom, _adresse, _bourse, "AJOUT");
        }
        else{
            etudiantService.saveEtudiantData_toCurrentXML(staxWritter, null, _nom, _adresse, _bourse, "AJOUT");
        }
    }

    public void setMainWindow(VBox vBox){
        this.mainWindow = vBox;
    }
    public void setStaxWritter(StaxWritter staxWritter) {
        this.staxWritter = staxWritter;
    }
    public void setFile(File file) {
        this.file = file;
    }
    public void setEtudiantService(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }
}
