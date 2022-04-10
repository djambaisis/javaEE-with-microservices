DROP DATABASE IF EXISTS SessionEval;
CREATE DATABASE IF NOT EXISTS SessionEval;
USE SessionEval;
# -----------------------------------------------------------------------------
#       TABLE : SESSIONEVALUATION
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS SESSIONEVALUATION
 (
   id_session int4  NOT NULL  AUTO_INCREMENT,
 
   
   datedebut date NULL  ,
   datefin date NULL  ,
   libelle varchar(128) NULL,
   activer bool NULL  ,
   verouillage bool NULL  ,
   cloturer bool NULL  ,
   PRIMARY KEY (id_session) 
 ) 
 comment = "";


