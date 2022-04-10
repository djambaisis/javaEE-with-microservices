package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.SessionEvaluation;

public class Database {
	private static List<SessionEvaluation> sessions = new ArrayList<>();

    public static List<SessionEvaluation> getSessionEvaluations() {
        return sessions;
    }

    public static SessionEvaluation getSessionEvaluation(int customerId) {
        for (SessionEvaluation session : sessions) {
            if (session.getId()==customerId)
                return session;
        }

        return null;
    }
    
    
    public   void   addSessionEvaluation(SessionEvaluation session) {
    	EntityManager em = emFactory.createEntityManager();
    	em.getTransaction().begin();
        em.persist(session);
        em.getTransaction().commit();
    	
    }
        
    
    public List<SessionEvaluation> getSessionEvaluation() {
    	
		// TODO Auto-generated method stub
		 EntityManager em = emFactory.createEntityManager();
	        Query query = em.createQuery("SELECT a FROM SessionEvaluation a");
	        List<SessionEvaluation> result = new ArrayList<>();
	        query.getResultList().forEach((Object o)-> result.add((SessionEvaluation)o));
	        return result;
	}
	
    
    
    
private EntityManagerFactory emFactory;
	
public Database() {
		emFactory = Persistence.createEntityManagerFactory("EvaluationService");
		
	}
	public EntityManager getEntityManager() {
		return emFactory.createEntityManager();
	}
	public void close() {
        emFactory.close();
	}
	
	
	
    
    
    
    

   
}
