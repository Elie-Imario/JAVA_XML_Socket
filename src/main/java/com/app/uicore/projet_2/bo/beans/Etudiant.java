package com.app.uicore.projet_2.bo.beans;

import javax.persistence.*;

@Entity
@Table(name = "Etudiant")
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_etudiant_key_generator")
    @TableGenerator(name = "id_etudiant_key_generator",
            table = "pk_etudiant",
            pkColumnName = "name",
            valueColumnName = "value",
            allocationSize = 1)
    @Column(name = "id_edutiant")
    private Long id;

    private String nom;
    private String adresse;
    private int bourse;

    public Etudiant() {}

    public Etudiant(String nom, String adresse, int bourse) {
        this.nom = nom;
        this.adresse = adresse;
        this.bourse = bourse;
    }

    public Etudiant(Long id, String nom, String adresse, int bourse) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.bourse = bourse;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getBourse() {
        return bourse;
    }

    public void setBourse(int bourse) {
        this.bourse = bourse;
    }
}
