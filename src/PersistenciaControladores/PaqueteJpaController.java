/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersistenciaControladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Clases.Entrega;
import Clases.Cliente;
import Clases.Estado;
import Clases.Paquete;
import PersistenciaControladores.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author USER
 */
public class PaqueteJpaController implements Serializable {

    public PaqueteJpaController() {
    emf = Persistence.createEntityManagerFactory("Bimestral_1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paquete paquete) {
        if (paquete.getEstados() == null) {
            paquete.setEstados(new ArrayList<Estado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entrega entrega = paquete.getEntrega();
            if (entrega != null) {
                entrega = em.getReference(entrega.getClass(), entrega.getCodigo());
                paquete.setEntrega(entrega);
            }
            Cliente cliente = paquete.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getCedula());
                paquete.setCliente(cliente);
            }
            List<Estado> attachedEstados = new ArrayList<Estado>();
            for (Estado estadosEstadoToAttach : paquete.getEstados()) {
                estadosEstadoToAttach = em.getReference(estadosEstadoToAttach.getClass(), estadosEstadoToAttach.getTipo());
                attachedEstados.add(estadosEstadoToAttach);
            }
            paquete.setEstados(attachedEstados);
            em.persist(paquete);
            if (entrega != null) {
                Paquete oldPaqueteOfEntrega = entrega.getPaquete();
                if (oldPaqueteOfEntrega != null) {
                    oldPaqueteOfEntrega.setEntrega(null);
                    oldPaqueteOfEntrega = em.merge(oldPaqueteOfEntrega);
                }
                entrega.setPaquete(paquete);
                entrega = em.merge(entrega);
            }
            if (cliente != null) {
                cliente.getPaquetes().add(paquete);
                cliente = em.merge(cliente);
            }
            for (Estado estadosEstado : paquete.getEstados()) {
                Paquete oldPaqueteOfEstadosEstado = estadosEstado.getPaquete();
                estadosEstado.setPaquete(paquete);
                estadosEstado = em.merge(estadosEstado);
                if (oldPaqueteOfEstadosEstado != null) {
                    oldPaqueteOfEstadosEstado.getEstados().remove(estadosEstado);
                    oldPaqueteOfEstadosEstado = em.merge(oldPaqueteOfEstadosEstado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paquete paquete) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paquete persistentPaquete = em.find(Paquete.class, paquete.getIdpaq());
            Entrega entregaOld = persistentPaquete.getEntrega();
            Entrega entregaNew = paquete.getEntrega();
            Cliente clienteOld = persistentPaquete.getCliente();
            Cliente clienteNew = paquete.getCliente();
            List<Estado> estadosOld = persistentPaquete.getEstados();
            List<Estado> estadosNew = paquete.getEstados();
            if (entregaNew != null) {
                entregaNew = em.getReference(entregaNew.getClass(), entregaNew.getCodigo());
                paquete.setEntrega(entregaNew);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getCedula());
                paquete.setCliente(clienteNew);
            }
            List<Estado> attachedEstadosNew = new ArrayList<Estado>();
            for (Estado estadosNewEstadoToAttach : estadosNew) {
                estadosNewEstadoToAttach = em.getReference(estadosNewEstadoToAttach.getClass(), estadosNewEstadoToAttach.getTipo());
                attachedEstadosNew.add(estadosNewEstadoToAttach);
            }
            estadosNew = attachedEstadosNew;
            paquete.setEstados(estadosNew);
            paquete = em.merge(paquete);
            if (entregaOld != null && !entregaOld.equals(entregaNew)) {
                entregaOld.setPaquete(null);
                entregaOld = em.merge(entregaOld);
            }
            if (entregaNew != null && !entregaNew.equals(entregaOld)) {
                Paquete oldPaqueteOfEntrega = entregaNew.getPaquete();
                if (oldPaqueteOfEntrega != null) {
                    oldPaqueteOfEntrega.setEntrega(null);
                    oldPaqueteOfEntrega = em.merge(oldPaqueteOfEntrega);
                }
                entregaNew.setPaquete(paquete);
                entregaNew = em.merge(entregaNew);
            }
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getPaquetes().remove(paquete);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getPaquetes().add(paquete);
                clienteNew = em.merge(clienteNew);
            }
            for (Estado estadosOldEstado : estadosOld) {
                if (!estadosNew.contains(estadosOldEstado)) {
                    estadosOldEstado.setPaquete(null);
                    estadosOldEstado = em.merge(estadosOldEstado);
                }
            }
            for (Estado estadosNewEstado : estadosNew) {
                if (!estadosOld.contains(estadosNewEstado)) {
                    Paquete oldPaqueteOfEstadosNewEstado = estadosNewEstado.getPaquete();
                    estadosNewEstado.setPaquete(paquete);
                    estadosNewEstado = em.merge(estadosNewEstado);
                    if (oldPaqueteOfEstadosNewEstado != null && !oldPaqueteOfEstadosNewEstado.equals(paquete)) {
                        oldPaqueteOfEstadosNewEstado.getEstados().remove(estadosNewEstado);
                        oldPaqueteOfEstadosNewEstado = em.merge(oldPaqueteOfEstadosNewEstado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = paquete.getIdpaq();
                if (findPaquete(id) == null) {
                    throw new NonexistentEntityException("The paquete with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paquete paquete;
            try {
                paquete = em.getReference(Paquete.class, id);
                paquete.getIdpaq();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paquete with id " + id + " no longer exists.", enfe);
            }
            Entrega entrega = paquete.getEntrega();
            if (entrega != null) {
                entrega.setPaquete(null);
                entrega = em.merge(entrega);
            }
            Cliente cliente = paquete.getCliente();
            if (cliente != null) {
                cliente.getPaquetes().remove(paquete);
                cliente = em.merge(cliente);
            }
            List<Estado> estados = paquete.getEstados();
            for (Estado estadosEstado : estados) {
                estadosEstado.setPaquete(null);
                estadosEstado = em.merge(estadosEstado);
            }
            em.remove(paquete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paquete> findPaqueteEntities() {
        return findPaqueteEntities(true, -1, -1);
    }

    public List<Paquete> findPaqueteEntities(int maxResults, int firstResult) {
        return findPaqueteEntities(false, maxResults, firstResult);
    }

    private List<Paquete> findPaqueteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paquete.class));
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

    public Paquete findPaquete(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paquete.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaqueteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paquete> rt = cq.from(Paquete.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
