DROP DATABASE IF EXISTS MLR2;

CREATE DATABASE IF NOT EXISTS MLR2;
USE MLR2;
# -----------------------------------------------------------------------------
#       TABLE : ETUDIANT
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ETUDIANT
 (
   MATRICULE VARCHAR(128) NOT NULL  ,
   ETAT VARCHAR(255) NULL  ,
   TELEPHONE LONGBLOB NULL  ,
   HANDICAPE VARCHAR(32) NULL  ,
   LANGUE VARCHAR(32) NULL  ,
   PHOTO VARCHAR(32) NULL  ,
   ACTIVE_SPORTIVE VARCHAR(32) NULL  ,
   CONNAISANCE_INFORMATIQUE VARCHAR(32) NULL  ,
   ACTIVITE_ASSOCIATIVE VARCHAR(32) NULL  ,
   ACTIVITE_CULTURELLE VARCHAR(32) NULL  ,
   SITUATIONEMPLOI VARCHAR(32) NULL  
   , PRIMARY KEY (MATRICULE) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : EVALUATION
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS EVALUATION
 (
   ID_EVAL INTEGER(2) NOT NULL  ,
   DATE_EVAL DATE NULL  ,
   TYPE_EVAL VARCHAR(32) NULL  ,
   FORME_EVAL VARCHAR(32) NULL  ,
   ANONYMAT_EVAL VARCHAR(32) NULL  ,
   NOTE_EVAL VARCHAR(32) NULL  ,
   COURS VARCHAR(32) NULL  
   , PRIMARY KEY (ID_EVAL) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : ASSO_1
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ASSO_1
 (
   MATRICULE VARCHAR(128) NOT NULL  ,
   ID_EVAL INTEGER(2) NOT NULL  
   , PRIMARY KEY (MATRICULE,ID_EVAL) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE ASSO_1
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_ASSO_1_ETUDIANT
     ON ASSO_1 (MATRICULE ASC);

CREATE  INDEX I_FK_ASSO_1_EVALUATION
     ON ASSO_1 (ID_EVAL ASC);


# -----------------------------------------------------------------------------
#       CREATION DES REFERENCES DE TABLE
# -----------------------------------------------------------------------------


ALTER TABLE ASSO_1 
  ADD FOREIGN KEY FK_ASSO_1_ETUDIANT (MATRICULE)
      REFERENCES ETUDIANT (MATRICULE) ;


ALTER TABLE ASSO_1 
  ADD FOREIGN KEY FK_ASSO_1_EVALUATION (ID_EVAL)
      REFERENCES EVALUATION (ID_EVAL) ;

