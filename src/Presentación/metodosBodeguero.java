package Presentación;

import Logica.LPersona;
import Clases.Cliente;
import Clases.Direccion;
import Clases.Estado;
import Clases.Paquete;
import Logica.LDireccion;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class metodosBodeguero {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bimestral_1PU");
    private static EntityManager em = emf.createEntityManager();

    public static void registrarNuevoCliente(Scanner scanner) {
        System.out.print("Ingrese la cedula del cliente: ");
        String cedula = scanner.nextLine();
        if (!LPersona.validaCedula(cedula)) {
            System.err.println("Cedula Incorrecta: Ingrese una cedula valida");
            return;
        }
        System.out.print("Ingrese los apellidos del cliente: ");
        String apellidos = scanner.nextLine();
        System.out.print("Ingrese los nombres del cliente: ");
        String nombres = scanner.nextLine();
        System.out.print("Ingrese el correo del cliente: ");
        String email = scanner.nextLine();
        if (!LPersona.validaEmail(email)) {
            System.err.println("Correo Incorrecto: Ingrese un correo valido");
            return;
        }
        System.out.print("Ingrese el numero de celular del cliente: ");
        String celular = scanner.nextLine();

        em.getTransaction().begin();
        Cliente cliente = new Cliente();
        cliente.setCedula(cedula);
        cliente.setApellidos(apellidos);
        cliente.setNombres(nombres);
        cliente.setEmail(email);
        cliente.setCelular(celular);
        em.persist(cliente);
        em.getTransaction().commit();

        System.out.println("Cliente Registrado");
    }

    public static void registrarDireccionesCliente(Scanner scanner) {
        System.out.print("Ingrese la cedula del cliente: ");
        String cedula = scanner.nextLine();

        em.getTransaction().begin();
        Cliente cliente = em.find(Cliente.class, cedula);
        em.getTransaction().commit();

        if (cliente != null) {
            boolean agregar = true;
            while (agregar) {
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

                Direccion direccion = new Direccion();
                direccion.setCodigo(codigo);
                direccion.setCalle1(calle1);
                direccion.setCalle2(calle2);
                direccion.setReferencia(referencia);
                direccion.setActual(false);
                direccion.setCliente(cliente);

                em.getTransaction().begin();
                em.persist(direccion);
                em.getTransaction().commit();

                System.out.print("¿Desea agregar otra direccion? (si/no): ");
                String response = scanner.nextLine();
                if (!response.equalsIgnoreCase("si")) {
                    agregar = false;
                    direccion.setActual(true);
                }
            }
            System.out.println("Direcciones registradas");
        } else {
            System.out.println("No Existe el Cliente");
        }
    }

    public static void registrarNuevoPaquete(Scanner scanner) {
        System.out.print("Ingrese el codigo del paquete: ");
        String codigo = scanner.nextLine();
        System.out.print("Ingrese la descripcion del paquete: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese el peso del paquete: ");
        int peso = scanner.nextInt();
        System.out.print("Ingrese el alto del paquete: ");
        int alto = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese la cedula del cliente: ");
        String cedula = scanner.nextLine();
        Cliente cliente = em.find(Cliente.class, cedula);

        em.getTransaction().begin();
        Paquete paquete = new Paquete();
        paquete.setCodigo(codigo);
        paquete.setDescripcion(descripcion);
        paquete.setPeso(peso);
        paquete.setAlto(alto);
        paquete.setCliente(cliente);
        em.persist(paquete);

        Estado estado = new Estado();
        estado.setTipo(1);
        estado.setEstado("Creado");
        estado.setFecha(new java.util.Date());
        estado.setObservacion("Paquete Creado");
        estado.setPaquete(paquete);

        em.persist(estado);
        paquete.getEstados().add(estado);
        em.merge(paquete);
        em.getTransaction().commit();

        System.out.println("Paquete Registrado");
    }

    public static void despacharPaquete(Scanner scanner) {
        System.out.print("Ingrese el ID del paquete: ");
        int idPaquete = scanner.nextInt();
        scanner.nextLine();
        Paquete paquete = em.find(Paquete.class, idPaquete);

        if (paquete != null) {
            em.getTransaction().begin();
            Estado estado = new Estado();
            estado.setTipo(2);
            estado.setEstado("Despachado");
            estado.setFecha(new java.util.Date());
            estado.setObservacion("El paquete ha sido Despachado");
            estado.setPaquete(paquete);
            paquete.getEstados().add(estado);
            em.merge(paquete);
            em.getTransaction().commit();
            System.out.println("Paquete Despachado");
        } else {
            System.out.println("Paquete no encontrado");
        }
    }
}
