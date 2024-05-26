/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersistenciaControladores;

import Clases.Bodeguero;
import PersistenciaControladores.exceptions.NonexistentEntityException;
import PersistenciaControladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author USER
 */
public class BodegueroJpaController implements Serializable {

    public BodegueroJpaController() {
        emf = Persistence.createEntityManagerFactory("Bimestral_1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bodeguero bodeguero) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bodeguero);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBodeguero(bodeguero.getCedula()) != null) {
                throw new PreexistingEntityException("Bodeguero " + bodeguero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bodeguero bodeguero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            bodeguero = em.merge(bodeguero);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = bodeguero.getCedula();
                if (findBodeguero(id) == null) {
                    throw new NonexistentEntityException("The bodeguero with id " + id + " no longer exists.");
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
            Bodeguero bodeguero;
            try {
                bodeguero = em.getReference(Bodeguero.class, id);
                bodeguero.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bodeguero with id " + id + " no longer exists.", enfe);
            }
            em.remove(bodeguero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bodeguero> findBodegueroEntities() {
        return findBodegueroEntities(true, -1, -1);
    }

    public List<Bodeguero> findBodegueroEntities(int maxResults, int firstResult) {
        return findBodegueroEntities(false, maxResults, firstResult);
    }

    private List<Bodeguero> findBodegueroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bodeguero.class));
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

    public Bodeguero findBodeguero(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bodeguero.class, id);
        } finally {
            em.close();
        }
    }

    public int getBodegueroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bodeguero> rt = cq.from(Bodeguero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
