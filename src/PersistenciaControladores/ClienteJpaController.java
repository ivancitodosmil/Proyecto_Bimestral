/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PersistenciaControladores;

import Clases.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Clases.Direccion;
import java.util.ArrayList;
import java.util.List;
import Clases.Paquete;
import PersistenciaControladores.exceptions.NonexistentEntityException;
import PersistenciaControladores.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author USER
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController() {
        emf = Persistence.createEntityManagerFactory("Bimestral_1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws PreexistingEntityException, Exception {
        if (cliente.getDirecciones() == null) {
            cliente.setDirecciones(new ArrayList<Direccion>());
        }
        if (cliente.getPaquetes() == null) {
            cliente.setPaquetes(new ArrayList<Paquete>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Direccion> attachedDirecciones = new ArrayList<Direccion>();
            for (Direccion direccionesDireccionToAttach : cliente.getDirecciones()) {
                direccionesDireccionToAttach = em.getReference(direccionesDireccionToAttach.getClass(), direccionesDireccionToAttach.getCodigo());
                attachedDirecciones.add(direccionesDireccionToAttach);
            }
            cliente.setDirecciones(attachedDirecciones);
            List<Paquete> attachedPaquetes = new ArrayList<Paquete>();
            for (Paquete paquetesPaqueteToAttach : cliente.getPaquetes()) {
                paquetesPaqueteToAttach = em.getReference(paquetesPaqueteToAttach.getClass(), paquetesPaqueteToAttach.getIdpaq());
                attachedPaquetes.add(paquetesPaqueteToAttach);
            }
            cliente.setPaquetes(attachedPaquetes);
            em.persist(cliente);
            for (Direccion direccionesDireccion : cliente.getDirecciones()) {
                Cliente oldClienteOfDireccionesDireccion = direccionesDireccion.getCliente();
                direccionesDireccion.setCliente(cliente);
                direccionesDireccion = em.merge(direccionesDireccion);
                if (oldClienteOfDireccionesDireccion != null) {
                    oldClienteOfDireccionesDireccion.getDirecciones().remove(direccionesDireccion);
                    oldClienteOfDireccionesDireccion = em.merge(oldClienteOfDireccionesDireccion);
                }
            }
            for (Paquete paquetesPaquete : cliente.getPaquetes()) {
                Cliente oldClienteOfPaquetesPaquete = paquetesPaquete.getCliente();
                paquetesPaquete.setCliente(cliente);
                paquetesPaquete = em.merge(paquetesPaquete);
                if (oldClienteOfPaquetesPaquete != null) {
                    oldClienteOfPaquetesPaquete.getPaquetes().remove(paquetesPaquete);
                    oldClienteOfPaquetesPaquete = em.merge(oldClienteOfPaquetesPaquete);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getCedula()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getCedula());
            List<Direccion> direccionesOld = persistentCliente.getDirecciones();
            List<Direccion> direccionesNew = cliente.getDirecciones();
            List<Paquete> paquetesOld = persistentCliente.getPaquetes();
            List<Paquete> paquetesNew = cliente.getPaquetes();
            List<Direccion> attachedDireccionesNew = new ArrayList<Direccion>();
            for (Direccion direccionesNewDireccionToAttach : direccionesNew) {
                direccionesNewDireccionToAttach = em.getReference(direccionesNewDireccionToAttach.getClass(), direccionesNewDireccionToAttach.getCodigo());
                attachedDireccionesNew.add(direccionesNewDireccionToAttach);
            }
            direccionesNew = attachedDireccionesNew;
            cliente.setDirecciones(direccionesNew);
            List<Paquete> attachedPaquetesNew = new ArrayList<Paquete>();
            for (Paquete paquetesNewPaqueteToAttach : paquetesNew) {
                paquetesNewPaqueteToAttach = em.getReference(paquetesNewPaqueteToAttach.getClass(), paquetesNewPaqueteToAttach.getIdpaq());
                attachedPaquetesNew.add(paquetesNewPaqueteToAttach);
            }
            paquetesNew = attachedPaquetesNew;
            cliente.setPaquetes(paquetesNew);
            cliente = em.merge(cliente);
            for (Direccion direccionesOldDireccion : direccionesOld) {
                if (!direccionesNew.contains(direccionesOldDireccion)) {
                    direccionesOldDireccion.setCliente(null);
                    direccionesOldDireccion = em.merge(direccionesOldDireccion);
                }
            }
            for (Direccion direccionesNewDireccion : direccionesNew) {
                if (!direccionesOld.contains(direccionesNewDireccion)) {
                    Cliente oldClienteOfDireccionesNewDireccion = direccionesNewDireccion.getCliente();
                    direccionesNewDireccion.setCliente(cliente);
                    direccionesNewDireccion = em.merge(direccionesNewDireccion);
                    if (oldClienteOfDireccionesNewDireccion != null && !oldClienteOfDireccionesNewDireccion.equals(cliente)) {
                        oldClienteOfDireccionesNewDireccion.getDirecciones().remove(direccionesNewDireccion);
                        oldClienteOfDireccionesNewDireccion = em.merge(oldClienteOfDireccionesNewDireccion);
                    }
                }
            }
            for (Paquete paquetesOldPaquete : paquetesOld) {
                if (!paquetesNew.contains(paquetesOldPaquete)) {
                    paquetesOldPaquete.setCliente(null);
                    paquetesOldPaquete = em.merge(paquetesOldPaquete);
                }
            }
            for (Paquete paquetesNewPaquete : paquetesNew) {
                if (!paquetesOld.contains(paquetesNewPaquete)) {
                    Cliente oldClienteOfPaquetesNewPaquete = paquetesNewPaquete.getCliente();
                    paquetesNewPaquete.setCliente(cliente);
                    paquetesNewPaquete = em.merge(paquetesNewPaquete);
                    if (oldClienteOfPaquetesNewPaquete != null && !oldClienteOfPaquetesNewPaquete.equals(cliente)) {
                        oldClienteOfPaquetesNewPaquete.getPaquetes().remove(paquetesNewPaquete);
                        oldClienteOfPaquetesNewPaquete = em.merge(oldClienteOfPaquetesNewPaquete);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cliente.getCedula();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<Direccion> direcciones = cliente.getDirecciones();
            for (Direccion direccionesDireccion : direcciones) {
                direccionesDireccion.setCliente(null);
                direccionesDireccion = em.merge(direccionesDireccion);
            }
            List<Paquete> paquetes = cliente.getPaquetes();
            for (Paquete paquetesPaquete : paquetes) {
                paquetesPaquete.setCliente(null);
                paquetesPaquete = em.merge(paquetesPaquete);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
