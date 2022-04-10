/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.udmmicro.micro.evaluation.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ppitbull
 */
@Entity
@Table(name = "ETUDIANT")
@NamedQueries({
    @NamedQuery(name = "Etudiant.findAll", query = "SELECT e FROM Etudiant e"),
    @NamedQuery(name = "Etudiant.findByMatricule", query = "SELECT e FROM Etudiant e WHERE e.matricule = :matricule"),
    @NamedQuery(name = "Etudiant.findByEtat", query = "SELECT e FROM Etudiant e WHERE e.etat = :etat"),
    @NamedQuery(name = "Etudiant.findByHandicape", query = "SELECT e FROM Etudiant e WHERE e.handicape = :handicape"),
    @NamedQuery(name = "Etudiant.findByLangue", query = "SELECT e FROM Etudiant e WHERE e.langue = :langue"),
    @NamedQuery(name = "Etudiant.findByPhoto", query = "SELECT e FROM Etudiant e WHERE e.photo = :photo"),
    @NamedQuery(name = "Etudiant.findByActiveSportive", query = "SELECT e FROM Etudiant e WHERE e.activeSportive = :activeSportive"),
    @NamedQuery(name = "Etudiant.findByConnaisanceInformatique", query = "SELECT e FROM Etudiant e WHERE e.connaisanceInformatique = :connaisanceInformatique"),
    @NamedQuery(name = "Etudiant.findByActiviteAssociative", query = "SELECT e FROM Etudiant e WHERE e.activiteAssociative = :activiteAssociative"),
    @NamedQuery(name = "Etudiant.findByActiviteCulturelle", query = "SELECT e FROM Etudiant e WHERE e.activiteCulturelle = :activiteCulturelle"),
    @NamedQuery(name = "Etudiant.findBySituationemploi", query = "SELECT e FROM Etudiant e WHERE e.situationemploi = :situationemploi")})
public class Etudiant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MATRICULE")
    private String matricule;
    @Column(name = "ETAT")
    private String etat;
    @Lob
    @Column(name = "TELEPHONE")
    private byte[] telephone;
    @Column(name = "HANDICAPE")
    private String handicape;
    @Column(name = "LANGUE")
    private String langue;
    @Column(name = "PHOTO")
    private String photo;
    @Column(name = "ACTIVE_SPORTIVE")
    private String activeSportive;
    @Column(name = "CONNAISANCE_INFORMATIQUE")
    private String connaisanceInformatique;
    @Column(name = "ACTIVITE_ASSOCIATIVE")
    private String activiteAssociative;
    @Column(name = "ACTIVITE_CULTURELLE")
    private String activiteCulturelle;
    @Column(name = "SITUATIONEMPLOI")
    private String situationemploi;
    @JoinTable(name = "ASSO_1", joinColumns = {
        @JoinColumn(name = "MATRICULE", referencedColumnName = "MATRICULE")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_EVAL", referencedColumnName = "ID_EVAL")})
    @ManyToMany
    private Collection<Evaluation> evaluationCollection;

    public Etudiant() {
    }

    public Etudiant(String matricule) {
        this.matricule = matricule;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public byte[] getTelephone() {
        return telephone;
    }

    public void setTelephone(byte[] telephone) {
        this.telephone = telephone;
    }

    public String getHandicape() {
        return handicape;
    }

    public void setHandicape(String handicape) {
        this.handicape = handicape;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getActiveSportive() {
        return activeSportive;
    }

    public void setActiveSportive(String activeSportive) {
        this.activeSportive = activeSportive;
    }

    public String getConnaisanceInformatique() {
        return connaisanceInformatique;
    }

    public void setConnaisanceInformatique(String connaisanceInformatique) {
        this.connaisanceInformatique = connaisanceInformatique;
    }

    public String getActiviteAssociative() {
        return activiteAssociative;
    }

    public void setActiviteAssociative(String activiteAssociative) {
        this.activiteAssociative = activiteAssociative;
    }

    public String getActiviteCulturelle() {
        return activiteCulturelle;
    }

    public void setActiviteCulturelle(String activiteCulturelle) {
        this.activiteCulturelle = activiteCulturelle;
    }

    public String getSituationemploi() {
        return situationemploi;
    }

    public void setSituationemploi(String situationemploi) {
        this.situationemploi = situationemploi;
    }

    public Collection<Evaluation> getEvaluationCollection() {
        return evaluationCollection;
    }

    public void setEvaluationCollection(Collection<Evaluation> evaluationCollection) {
        this.evaluationCollection = evaluationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matricule != null ? matricule.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etudiant)) {
            return false;
        }
        Etudiant other = (Etudiant) object;
        if ((this.matricule == null && other.matricule != null) || (this.matricule != null && !this.matricule.equals(other.matricule))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.udmmicro.micro.evaluation.entities.Etudiant[ matricule=" + matricule + " ]";
    }
    
}
