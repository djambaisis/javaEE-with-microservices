package model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "SESSIONEVALUATION")
public class SessionEvaluation {
	private int id;
	private Date  datedebut;
	private Date datefin;
	private String libelle;
	private boolean activer;
	private boolean verouillage;
	private boolean cloturer;
		
	
	@Id
	@Column(name = "id_session")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDatedebut() {
		return datedebut;
	}
	public void setDatedebut(Date datedebut) {
		this.datedebut = datedebut;
	}
	public Date getDatefin() {
		return datefin;
	}
	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public boolean isActiver() {
		return activer;
	}
	public void setActiver(boolean activer) {
		this.activer = activer;
	}
	public boolean isVerouillage() {
		return verouillage;
	}
	public void setVerouillage(boolean verouillage) {
		this.verouillage = verouillage;
	}
	public boolean isCloturer() {
		return cloturer;
	}
	public void setCloturer(boolean cloturer) {
		this.cloturer = cloturer;
	}
	public List<SessionEvaluation> SessionEvaluation() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		return "SessionEvaluation [id=" + id + ", datedebut=" + datedebut + ", datefin=" + datefin + ", libelle="
				+ libelle + ", activer=" + activer + ", verouillage=" + verouillage + ", cloturer=" + cloturer + "]";
	}
	
	//private int idsemestre;
	

	//@ManyToOne
	//@JoinColumn(name="ID_SEMESTRE")
	//@Column(name = "ID_SESSION")
	//@GeneratedValue(strategy = GenerationType.AUTO)	
	//public int getIdsemestre() {
	//	return idsemestre;
	//}
	//public void setIdsemestre(int idsemestre) {
		//this.idsemestre = idsemestre;
	//}
	
	
	
}
