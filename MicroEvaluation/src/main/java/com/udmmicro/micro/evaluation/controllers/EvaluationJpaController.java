/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.udmmicro.micro.evaluation.controllers;

import com.udmmicro.micro.evaluation.controllers.exceptions.NonexistentEntityException;
import com.udmmicro.micro.evaluation.controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.udmmicro.micro.evaluation.entities.Etudiant;
import com.udmmicro.micro.evaluation.entities.Evaluation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ppitbull
 */
public class EvaluationJpaController extends AbstractJpaController implements Serializable {

    public EvaluationJpaController() {
        super();
    }
    
    public EvaluationJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evaluation evaluation) throws PreexistingEntityException, Exception {
        if (evaluation.getEtudiantCollection() == null) {
            evaluation.setEtudiantCollection(new ArrayList<Etudiant>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Etudiant> attachedEtudiantCollection = new ArrayList<Etudiant>();
            for (Etudiant etudiantCollectionEtudiantToAttach : evaluation.getEtudiantCollection()) {
                etudiantCollectionEtudiantToAttach = em.getReference(etudiantCollectionEtudiantToAttach.getClass(), etudiantCollectionEtudiantToAttach.getMatricule());
                attachedEtudiantCollection.add(etudiantCollectionEtudiantToAttach);
            }
            evaluation.setEtudiantCollection(attachedEtudiantCollection);
            em.persist(evaluation);
            for (Etudiant etudiantCollectionEtudiant : evaluation.getEtudiantCollection()) {
                etudiantCollectionEtudiant.getEvaluationCollection().add(evaluation);
                etudiantCollectionEtudiant = em.merge(etudiantCollectionEtudiant);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEvaluation(evaluation.getIdEval()) != null) {
                throw new PreexistingEntityException("Evaluation " + evaluation + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Evaluation evaluation) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evaluation persistentEvaluation = em.find(Evaluation.class, evaluation.getIdEval());
            Collection<Etudiant> etudiantCollectionOld = persistentEvaluation.getEtudiantCollection();
            Collection<Etudiant> etudiantCollectionNew = evaluation.getEtudiantCollection();
            Collection<Etudiant> attachedEtudiantCollectionNew = new ArrayList<Etudiant>();
            for (Etudiant etudiantCollectionNewEtudiantToAttach : etudiantCollectionNew) {
                etudiantCollectionNewEtudiantToAttach = em.getReference(etudiantCollectionNewEtudiantToAttach.getClass(), etudiantCollectionNewEtudiantToAttach.getMatricule());
                attachedEtudiantCollectionNew.add(etudiantCollectionNewEtudiantToAttach);
            }
            etudiantCollectionNew = attachedEtudiantCollectionNew;
            evaluation.setEtudiantCollection(etudiantCollectionNew);
            evaluation = em.merge(evaluation);
            for (Etudiant etudiantCollectionOldEtudiant : etudiantCollectionOld) {
                if (!etudiantCollectionNew.contains(etudiantCollectionOldEtudiant)) {
                    etudiantCollectionOldEtudiant.getEvaluationCollection().remove(evaluation);
                    etudiantCollectionOldEtudiant = em.merge(etudiantCollectionOldEtudiant);
                }
            }
            for (Etudiant etudiantCollectionNewEtudiant : etudiantCollectionNew) {
                if (!etudiantCollectionOld.contains(etudiantCollectionNewEtudiant)) {
                    etudiantCollectionNewEtudiant.getEvaluationCollection().add(evaluation);
                    etudiantCollectionNewEtudiant = em.merge(etudiantCollectionNewEtudiant);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evaluation.getIdEval();
                if (findEvaluation(id) == null) {
                    throw new NonexistentEntityException("The evaluation with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evaluation evaluation;
            try {
                evaluation = em.getReference(Evaluation.class, id);
                evaluation.getIdEval();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evaluation with id " + id + " no longer exists.", enfe);
            }
            Collection<Etudiant> etudiantCollection = evaluation.getEtudiantCollection();
            for (Etudiant etudiantCollectionEtudiant : etudiantCollection) {
                etudiantCollectionEtudiant.getEvaluationCollection().remove(evaluation);
                etudiantCollectionEtudiant = em.merge(etudiantCollectionEtudiant);
            }
            em.remove(evaluation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Evaluation> findEvaluationEntities() {
        return findEvaluationEntities(true, -1, -1);
    }

    public List<Evaluation> findEvaluationEntities(int maxResults, int firstResult) {
        return findEvaluationEntities(false, maxResults, firstResult);
    }

    private List<Evaluation> findEvaluationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evaluation.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return new ArrayList();
        } finally {
            em.close();
        }
    }

    public Evaluation findEvaluation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evaluation.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvaluationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evaluation> rt = cq.from(Evaluation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
