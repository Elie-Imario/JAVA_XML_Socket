package com.app.uicore.projet_2.bo.service;

import com.app.uicore.projet_2.bo.beans.Etudiant;
import com.app.uicore.projet_2.bo.repository.EtudiantRepositoryImpl;
import com.app.core.util.StaxWritter;
import org.w3c.dom.NodeList;

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

    public void createEtudiant(Etudiant etudiant){
        etudiantRepository.create(etudiant);
    }

    public void editEtudiant(Etudiant etudiant){
        Etudiant etudiant_to_edit = etudiantRepository.getbyId(etudiant.getId());

        etudiantRepository.update(etudiant_to_edit, etudiant.getNom(), etudiant.getAdresse(), etudiant.getBourse());
    }

    public void deleteEtudiant(Long id){
        Etudiant etudianttodelete = etudiantRepository.getbyId(id);
        etudiantRepository.delete(etudianttodelete);
    }


}
