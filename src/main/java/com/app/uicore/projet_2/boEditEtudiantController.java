package com.app.uicore.projet_2;

import com.app.uicore.projet_2.bo.beans.Etudiant;
import com.app.uicore.projet_2.bo.service.EtudiantService;
import com.app.uicore.projet_2.bo.util.StaxWritter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ResourceBundle;

public class boEditEtudiantController implements Initializable {
    @FXML private Label TitleBoEditEtudiant;
    @FXML private TextField _currentNumEtudiant, _nomEtudiant, _adresseEtudiant, _bourseEtudiant;

    @FXML
    private Button btnConfirmEdit, btnCancel;
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
        btnConfirmEdit.setOnMouseClicked((ActionEvent)->{
            try {
                initEditEtudiant(Long.valueOf(_currentNumEtudiant.getText()),_nomEtudiant.getText(), _adresseEtudiant.getText(), Integer.valueOf(_bourseEtudiant.getText()));
                Stage stage = (Stage) btnConfirmEdit.getScene().getWindow();
                stage.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        });
    }
    public void setDetailToUpdate(Etudiant _selectedEtudiantToUpdate){
        TitleBoEditEtudiant.setText("MODIFICATION DE L'ETUDIANT N°"+ _selectedEtudiantToUpdate.getId());
        _nomEtudiant.setText(_selectedEtudiantToUpdate.getNom());
        _adresseEtudiant.setText(_selectedEtudiantToUpdate.getAdresse());
        _bourseEtudiant.setText(Integer.valueOf(_selectedEtudiantToUpdate.getBourse()).toString());
        _currentNumEtudiant.setText(_selectedEtudiantToUpdate.getId().toString());
    }
    public void initEditEtudiant(Long _id, String _nom, String _adresse, int _bourse){
        if(!file.exists()){
            String file_path = System.getProperty("user.home") + "\\Downloads\\exportedFile\\etudiant.xml";
            staxWritter.setFile(file_path);
            etudiantService.saveEtudiantData_toXML(staxWritter, _id, _nom, _adresse, _bourse, "EDIT");
        }
        else{
            etudiantService.saveEtudiantData_toCurrentXML(staxWritter, _id, _nom, _adresse, _bourse, "EDIT");
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
