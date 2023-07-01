package com.app.uicore.projet_2.bo.service;

import com.app.uicore.projet_2.bo.beans.Etudiant;
import com.app.uicore.projet_2.bo.repository.EtudiantRepositoryImpl;
import com.app.uicore.projet_2.bo.util.StaxWritter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.w3c.dom.NodeList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class EtudiantService {
    private final EtudiantRepositoryImpl etudiantRepository;

    public EtudiantService() {
        this.etudiantRepository = new EtudiantRepositoryImpl();
    }

    public void saveEtudiantData_toXML(StaxWritter staxWritter, Long id, String nom, String adresse, int bourse, String _requestType){
        try {
            staxWritter.saveData(id, nom, adresse, bourse, _requestType);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveEtudiantData_toCurrentXML(StaxWritter staxWritter,Long Id, String nom, String adresse, int bourse, String _requestType){
        try {
            staxWritter.saveDataToCurrentFIle(Id, nom, adresse, bourse, _requestType);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public NodeList getNodeListEtudiants(StaxWritter staxWritter){
        NodeList nodeListetudiants = null;
        try {
            nodeListetudiants = staxWritter.getNodeList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return nodeListetudiants;
    }

    public boolean createEtudiant(Etudiant etudiant){
        return etudiantRepository.create(etudiant);
    }

    public boolean editEtudiant(Etudiant etudiant){
        Etudiant etudiant_to_edit = etudiantRepository.getbyId(etudiant.getId());

        return etudiantRepository.update(etudiant_to_edit, etudiant.getNom(), etudiant.getAdresse(), etudiant.getBourse());
    }

    public boolean deleteEtudiant(Long id){
        Etudiant etudianttodelete = etudiantRepository.getbyId(id);
        return etudiantRepository.delete(etudianttodelete);
    }

    public void fillTableEtudiant(TableView _tableEtudiant, TableColumn _idEtudiant, TableColumn _nomEtudiant, TableColumn _adresseEtudiant, TableColumn _bourseEtudiant){
        ObservableList<Etudiant> etudiants = etudiantRepository.list();
        _idEtudiant.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Etudiant, Integer>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Etudiant, Integer> param) {
                Etudiant etudiant = param.getValue();
                Long Id = etudiant.getId();
                String cellData = "ET-"+Id;
                return new SimpleStringProperty(cellData);
            }
        });
        _nomEtudiant.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("nom"));

        _adresseEtudiant.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("adresse"));
        _bourseEtudiant.setCellValueFactory(new PropertyValueFactory<Etudiant, Integer>("bourse"));

        if(etudiants.isEmpty()){
            _tableEtudiant.getItems().clear();
            _tableEtudiant.setPlaceholder(new Label("Aucune donnée à afficher"));
            _tableEtudiant.setPrefHeight(100);
        }
        else {
            _tableEtudiant.setPrefHeight(361);
            _tableEtudiant.setItems(etudiants);
        }

    }

    public void RefreshTableEtudiant(TableView tableListEtudiant){
        etudiantRepository.RefreshTable(tableListEtudiant);
    }

    public List<Etudiant> getListEtudiant(){
        List<Etudiant> etudiants;
        etudiants = etudiantRepository.list();

        return etudiants;
    }

}
