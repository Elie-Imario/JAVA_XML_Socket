package com.app.uicore.projet_2.bo.repository;

import com.app.uicore.projet_2.bo.HibernateUtil;
import com.app.uicore.projet_2.bo.beans.Etudiant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EtudiantRepositoryImpl {
    // CREATE Etudiant
    public boolean create(Etudiant etudiant){
        Transaction tx = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            etudiant.setNom(etudiant.getNom());
            etudiant.setAdresse(etudiant.getAdresse());
            etudiant.setBourse(etudiant.getBourse());
            session.persist(etudiant);
            tx.commit();

            return true;
        }catch (Exception e){
            if(tx!=null){
                tx.rollback();
            }
            return false;
        }finally {
            if(session!=null){
                session.close();
            }
        }
    }

    // GET Etudiant By Id
    public Etudiant getbyId(Long id){
        Etudiant etudiant = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx =session.beginTransaction();

            etudiant = session.get(Etudiant.class, id);
            tx.commit();

        }catch (Exception e){
            e.printStackTrace();
        }

        return etudiant;
    }

    //LIST ALL ETUDIANT
    public ObservableList<Etudiant> list(){
        Session session = null;
        Query<Etudiant> query = null;
        ObservableList<Etudiant> allEtudiants = FXCollections.observableArrayList();
        Transaction tx = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx =session.beginTransaction();
            query= session.createQuery("SELECT e FROM Etudiant e Order by e.id desc", Etudiant.class);

            for(Etudiant etudiant : query.getResultList()){
                Etudiant e = new Etudiant();
                e.setId(etudiant.getId());
                e.setNom(etudiant.getNom());
                e.setAdresse(etudiant.getAdresse());
                e.setBourse(etudiant.getBourse());

                allEtudiants.add(e);
            }

        }catch (Exception e){
            e.printStackTrace();
            if(tx != null) tx.rollback();
            return null;
        }finally {
            if(session != null) session.close();
        }
        return allEtudiants;
    }

    //DELETE ETUDIANT
    public boolean delete(Etudiant etudiant){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            session.delete(etudiant);
            tx.commit();

            return true;

        }catch (Exception e){
            if(tx!=null){
                tx.rollback();
            }
            return false;
        }finally {
            if(session!=null){
                session.close();
            }
        }
    }


    // UPDATE ETUDIANT
    public boolean update(Etudiant _etudiant_to_edit, String _nom, String _adresse, int _bourse){
        Transaction tx = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            _etudiant_to_edit.setNom(_nom);
            _etudiant_to_edit.setAdresse(_adresse);
            _etudiant_to_edit.setBourse(_bourse);
            session.update(_etudiant_to_edit);
            tx.commit();

            return true;
        }catch (Exception e){
            if(tx!=null){
                tx.rollback();
            }
            return false;
        }finally {
            if(session!=null){
                session.close();
            }
        }
    }

    public List<Etudiant> convertIntoEtudiantList(List<Etudiant> etudiants, Etudiant etoConvert){
        Etudiant e = new Etudiant();
        e.setId(etoConvert.getId());
        e.setNom(etoConvert.getNom());
        e.setAdresse(etoConvert.getAdresse());
        e.setBourse(etoConvert.getBourse());

        etudiants.add(e);
        return etudiants;
    }

    private static List<Etudiant> iterateResult(List<Etudiant> _etudiants, Query<Etudiant> query) {
        for(Etudiant etudiant : query.getResultList()){
            Etudiant e = new Etudiant();
            e.setId(etudiant.getId());
            e.setNom(etudiant.getNom());
            e.setAdresse(etudiant.getAdresse());
            e.setBourse(etudiant.getBourse());

            _etudiants.add(e);
        }
        return _etudiants;
    }


    //REFRESH TABLE
    public void RefreshTable(TableView _TableListEtudiant){
        Session session = null;
        Query<Etudiant> query = null;
        Transaction tx = null;
        _TableListEtudiant.getItems().clear();
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx =session.beginTransaction();
            query= session.createQuery("SELECT e FROM Etudiant e Order by e.id desc", Etudiant.class);

            for(Etudiant etudiant : query.getResultList()){
                Etudiant e = new Etudiant();
                e.setId(etudiant.getId());
                e.setNom(etudiant.getNom());
                e.setAdresse(etudiant.getAdresse());
                e.setBourse(etudiant.getBourse());

                _TableListEtudiant.getItems().add(e);
            }

        }catch (Exception e){
            e.printStackTrace();
            if(tx != null) tx.rollback();
        }finally {
            if(session != null) session.close();
        }
    }
}
