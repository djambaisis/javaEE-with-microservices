/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.udmmicro.micro.evaluation.controllers;

import com.udmmicro.micro.evaluation.controllers.exceptions.NonexistentEntityException;
import com.udmmicro.micro.evaluation.controllers.exceptions.PreexistingEntityException;
import com.udmmicro.micro.evaluation.entities.Etudiant;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.udmmicro.micro.evaluation.entities.Evaluation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author isis
 */
public class EtudiantJpaController extends AbstractJpaController implements Serializable  {

    public EtudiantJpaController() {
        super();
    }
    
    public EtudiantJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Etudiant etudiant) throws PreexistingEntityException, Exception {
        if (etudiant.getEvaluationCollection() == null) {
            etudiant.setEvaluationCollection(new ArrayList<Evaluation>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Evaluation> attachedEvaluationCollection = new ArrayList<Evaluation>();
            for (Evaluation evaluationCollectionEvaluationToAttach : etudiant.getEvaluationCollection()) {
                evaluationCollectionEvaluationToAttach = em.getReference(evaluationCollectionEvaluationToAttach.getClass(), evaluationCollectionEvaluationToAttach.getIdEval());
                attachedEvaluationCollection.add(evaluationCollectionEvaluationToAttach);
            }
            etudiant.setEvaluationCollection(attachedEvaluationCollection);
            em.persist(etudiant);
            for (Evaluation evaluationCollectionEvaluation : etudiant.getEvaluationCollection()) {
                evaluationCollectionEvaluation.getEtudiantCollection().add(etudiant);
                evaluationCollectionEvaluation = em.merge(evaluationCollectionEvaluation);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEtudiant(etudiant.getMatricule()) != null) {
                throw new PreexistingEntityException("Etudiant " + etudiant + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Etudiant etudiant) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Etudiant persistentEtudiant = em.find(Etudiant.class, etudiant.getMatricule());
            Collection<Evaluation> evaluationCollectionOld = persistentEtudiant.getEvaluationCollection();
            Collection<Evaluation> evaluationCollectionNew = etudiant.getEvaluationCollection();
            Collection<Evaluation> attachedEvaluationCollectionNew = new ArrayList<Evaluation>();
            for (Evaluation evaluationCollectionNewEvaluationToAttach : evaluationCollectionNew) {
                evaluationCollectionNewEvaluationToAttach = em.getReference(evaluationCollectionNewEvaluationToAttach.getClass(), evaluationCollectionNewEvaluationToAttach.getIdEval());
                attachedEvaluationCollectionNew.add(evaluationCollectionNewEvaluationToAttach);
            }
            evaluationCollectionNew = attachedEvaluationCollectionNew;
            etudiant.setEvaluationCollection(evaluationCollectionNew);
            etudiant = em.merge(etudiant);
            for (Evaluation evaluationCollectionOldEvaluation : evaluationCollectionOld) {
                if (!evaluationCollectionNew.contains(evaluationCollectionOldEvaluation)) {
                    evaluationCollectionOldEvaluation.getEtudiantCollection().remove(etudiant);
                    evaluationCollectionOldEvaluation = em.merge(evaluationCollectionOldEvaluation);
                }
            }
            for (Evaluation evaluationCollectionNewEvaluation : evaluationCollectionNew) {
                if (!evaluationCollectionOld.contains(evaluationCollectionNewEvaluation)) {
                    evaluationCollectionNewEvaluation.getEtudiantCollection().add(etudiant);
                    evaluationCollectionNewEvaluation = em.merge(evaluationCollectionNewEvaluation);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = etudiant.getMatricule();
                if (findEtudiant(id) == null) {
                    throw new NonexistentEntityException("The etudiant with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Etudiant etudiant;
            try {
                etudiant = em.getReference(Etudiant.class, id);
                etudiant.getMatricule();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The etudiant with id " + id + " no longer exists.", enfe);
            }
            Collection<Evaluation> evaluationCollection = etudiant.getEvaluationCollection();
            for (Evaluation evaluationCollectionEvaluation : evaluationCollection) {
                evaluationCollectionEvaluation.getEtudiantCollection().remove(etudiant);
                evaluationCollectionEvaluation = em.merge(evaluationCollectionEvaluation);
            }
            em.remove(etudiant);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Etudiant> findEtudiantEntities() {
        return findEtudiantEntities(true, -1, -1);
    }

    public List<Etudiant> findEtudiantEntities(int maxResults, int firstResult) {
        return findEtudiantEntities(false, maxResults, firstResult);
    }

    private List<Etudiant> findEtudiantEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Etudiant.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Etudiant findEtudiant(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Etudiant.class, id);
        } finally {
            em.close();
        }
    }

    public int getEtudiantCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Etudiant> rt = cq.from(Etudiant.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
