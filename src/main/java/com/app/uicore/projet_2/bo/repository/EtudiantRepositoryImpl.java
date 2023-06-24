package com.app.uicore.projet_2.bo.repository;

import com.app.uicore.projet_2.bo.HibernateUtil;
import com.app.uicore.projet_2.bo.beans.Etudiant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class EtudiantRepositoryImpl {
    // CREATE Etudiant
    public void create(Etudiant etudiant){
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

            System.out.println("Enregistrement ajouté!");
        }catch (Exception e){
            if(tx!=null){
                tx.rollback();
            }
            System.out.println(e);
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
    public List<Etudiant> list(){
        Session session = null;
        Query<Etudiant> query = null;
        List<Etudiant> allEtudiants = new ArrayList<>();
        Transaction tx = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx =session.beginTransaction();
            query= session.createQuery("SELECT e FROM Etudiant e", Etudiant.class);
            allEtudiants = EtudiantRepositoryImpl.iterateResult(allEtudiants, query);
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
    public void delete(Etudiant etudiant){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            session.delete(etudiant);
            tx.commit();

            System.out.println("Enregistrement supprimé!");

        }catch (Exception e){
            if(tx!=null){
                tx.rollback();
            }
        }finally {
            if(session!=null){
                session.close();
            }
        }
    }


    // UPDATE ETUDIANT
    public void update(Etudiant _etudiant_to_edit, String _nom, String _adresse, int _bourse){
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

            System.out.println("Enregistrement modifié!");
        }catch (Exception e){
            if(tx!=null){
                tx.rollback();
            }
            System.out.println(e);
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
}
