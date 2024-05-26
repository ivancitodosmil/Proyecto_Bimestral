/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersistenciaControladores;

import Clases.Repartidor;
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
public class RepartidorJpaController implements Serializable {

    public RepartidorJpaController() {
        emf = Persistence.createEntityManagerFactory("Bimestral_1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Repartidor repartidor) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(repartidor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRepartidor(repartidor.getCedula()) != null) {
                throw new PreexistingEntityException("Repartidor " + repartidor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Repartidor repartidor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            repartidor = em.merge(repartidor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = repartidor.getCedula();
                if (findRepartidor(id) == null) {
                    throw new NonexistentEntityException("The repartidor with id " + id + " no longer exists.");
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
            Repartidor repartidor;
            try {
                repartidor = em.getReference(Repartidor.class, id);
                repartidor.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The repartidor with id " + id + " no longer exists.", enfe);
            }
            em.remove(repartidor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Repartidor> findRepartidorEntities() {
        return findRepartidorEntities(true, -1, -1);
    }

    public List<Repartidor> findRepartidorEntities(int maxResults, int firstResult) {
        return findRepartidorEntities(false, maxResults, firstResult);
    }

    private List<Repartidor> findRepartidorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Repartidor.class));
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

    public Repartidor findRepartidor(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Repartidor.class, id);
        } finally {
            em.close();
        }
    }

    public int getRepartidorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Repartidor> rt = cq.from(Repartidor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
