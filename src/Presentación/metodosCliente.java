package Presentación;

import Logica.LDireccion;
import Clases.Cliente;
import Clases.Direccion;
import Clases.Estado;
import Clases.Paquete;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class metodosCliente {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bimestral_1PU");
    private static EntityManager em = emf.createEntityManager();

    public static void revisarEstadosPaquetes(Scanner scanner) {
        System.out.print("Ingrese su cedula: ");
        String cedula = scanner.nextLine();

        Cliente cliente = em.find(Cliente.class, cedula);

        if (cliente != null) {
            List<Paquete> paquetes = null;
            try {
                em.getTransaction().begin();

                paquetes = em.createQuery(
                        "SELECT p FROM Paquete p WHERE p.cliente.cedula = :cedula", Paquete.class)
                        .setParameter("cedula", cedula)
                        .getResultList();

                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.err.println("Error al revisar los estados de los paquetes: " + e.getMessage());
                return;
            }

            if (paquetes.isEmpty()) {
                System.out.println("El cliente No Tiene Paquetes");
            } else {
                for (Paquete paquete : paquetes) {
                    System.out.println("Paquete ID: " + paquete.getIdpaq());
                    for (Estado estado : paquete.getEstados()) {
                        System.out.println("Estado: " + estado.getEstado() + ", Fecha: " + estado.getFecha());
                    }
                }
            }
        } else {
            System.out.println("Cliente No Registrado");
        }
    }

    public static void agregarDireccionYActualizarEstado(Scanner scanner) {
        System.out.print("Ingrese la cedula del cliente: ");
        String cedula = scanner.nextLine();

        Cliente cliente = em.find(Cliente.class, cedula);

        if (cliente != null) {
            System.out.println("1. Agregar nueva direccion");
            System.out.println("2. Cambiar estado de alguna direccion");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el codigo de la direccion: ");
                    String codigo = scanner.nextLine();
                    System.out.print("Ingrese calle 1: ");
                    String calle1 = scanner.nextLine();
                    System.out.print("Ingrese calle 2: ");
                    String calle2 = scanner.nextLine();
                    if (LDireccion.callesDiferentes(calle1, calle2)) {
                        System.err.println("Calles Iguales. Deben ser diferentes");
                        return;
                    }
                    System.out.print("Ingrese referencia: ");
                    String referencia = scanner.nextLine();

                    em.getTransaction().begin();

                    Direccion nuevaDireccion = new Direccion();
                    nuevaDireccion.setCodigo(codigo);
                    nuevaDireccion.setCalle1(calle1);
                    nuevaDireccion.setCalle2(calle2);
                    nuevaDireccion.setReferencia(referencia);
                    nuevaDireccion.setActual(false);
                    nuevaDireccion.setCliente(cliente);

                    em.persist(nuevaDireccion);
                    em.getTransaction().commit();
                    System.out.println("Direccion Agregada");
                    break;

                case 2:
                    System.out.print("Ingrese el codigo de la direccion a actualizar: ");
                    String codigoActualizar = scanner.nextLine();

                    boolean direccionEncontrada = false;

                    em.getTransaction().begin();

                    List<Direccion> direcciones = em.createQuery(
                            "SELECT d FROM Direccion d WHERE d.cliente.cedula = :cedula", Direccion.class)
                            .setParameter("cedula", cedula)
                            .getResultList();

                    for (Direccion direccion : direcciones) {
                        if (direccion.getCodigo().equals(codigoActualizar)) {
                            direccion.setActual(true);
                            em.merge(direccion);
                            direccionEncontrada = true;
                            System.out.println("Direccion Actualizada");
                        } else if (direccion.isActual()) {
                            direccion.setActual(false);
                            em.merge(direccion);
                        }
                    }

                    if (!direccionEncontrada) {
                        System.out.println("Direccion No Encontrada");
                    }

                    em.getTransaction().commit();
                    break;

                default:
                    System.out.println("Eliga una opcion valida");
                    break;
            }

        }
    }
}
