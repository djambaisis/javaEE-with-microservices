/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.udmmicro.micro.evaluation.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ppitbull
 */
@Entity
@Table(name = "EVALUATION")
@NamedQueries({
    @NamedQuery(name = "Evaluation.findAll", query = "SELECT e FROM Evaluation e"),
    @NamedQuery(name = "Evaluation.findByIdEval", query = "SELECT e FROM Evaluation e WHERE e.idEval = :idEval"),
    @NamedQuery(name = "Evaluation.findByDateEval", query = "SELECT e FROM Evaluation e WHERE e.dateEval = :dateEval"),
    @NamedQuery(name = "Evaluation.findByTypeEval", query = "SELECT e FROM Evaluation e WHERE e.typeEval = :typeEval"),
    @NamedQuery(name = "Evaluation.findByFormeEval", query = "SELECT e FROM Evaluation e WHERE e.formeEval = :formeEval"),
    @NamedQuery(name = "Evaluation.findByAnonymatEval", query = "SELECT e FROM Evaluation e WHERE e.anonymatEval = :anonymatEval"),
    @NamedQuery(name = "Evaluation.findByNoteEval", query = "SELECT e FROM Evaluation e WHERE e.noteEval = :noteEval"),
    @NamedQuery(name = "Evaluation.findByCours", query = "SELECT e FROM Evaluation e WHERE e.cours = :cours")})
public class Evaluation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_EVAL")
    private Integer idEval;
    @Column(name = "DATE_EVAL")
    @Temporal(TemporalType.DATE)
    private Date dateEval;
    @Column(name = "TYPE_EVAL")
    private String typeEval;
    @Column(name = "FORME_EVAL")
    private String formeEval;
    @Column(name = "ANONYMAT_EVAL")
    private String anonymatEval;
    @Column(name = "NOTE_EVAL")
    private String noteEval;
    @Column(name = "COURS")
    private String cours;
    @ManyToMany(mappedBy = "evaluationCollection")
    private Collection<Etudiant> etudiantCollection;

    public Evaluation() {
    }

    public Evaluation(Integer idEval) {
        this.idEval = idEval;
    }

    public Integer getIdEval() {
        return idEval;
    }

    public void setIdEval(Integer idEval) {
        this.idEval = idEval;
    }

    public Date getDateEval() {
        return dateEval;
    }

    public void setDateEval(Date dateEval) {
        this.dateEval = dateEval;
    }

    public String getTypeEval() {
        return typeEval;
    }

    public void setTypeEval(String typeEval) {
        this.typeEval = typeEval;
    }

    public String getFormeEval() {
        return formeEval;
    }

    public void setFormeEval(String formeEval) {
        this.formeEval = formeEval;
    }

    public String getAnonymatEval() {
        return anonymatEval;
    }

    public void setAnonymatEval(String anonymatEval) {
        this.anonymatEval = anonymatEval;
    }

    public String getNoteEval() {
        return noteEval;
    }

    public void setNoteEval(String noteEval) {
        this.noteEval = noteEval;
    }

    public String getCours() {
        return cours;
    }

    public void setCours(String cours) {
        this.cours = cours;
    }

    public Collection<Etudiant> getEtudiantCollection() {
        return etudiantCollection;
    }

    public void setEtudiantCollection(Collection<Etudiant> etudiantCollection) {
        this.etudiantCollection = etudiantCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEval != null ? idEval.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evaluation)) {
            return false;
        }
        Evaluation other = (Evaluation) object;
        if ((this.idEval == null && other.idEval != null) || (this.idEval != null && !this.idEval.equals(other.idEval))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.udmmicro.micro.evaluation.entities.Evaluation[ idEval=" + idEval + " ]";
    }
    
}
