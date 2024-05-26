package Presentación;

import Logica.LPersona;
import Clases.Bodeguero;
import Clases.Repartidor;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class metodosGerente {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bimestral_1PU");
    private static EntityManager em = emf.createEntityManager();

    public static void crearRepartidor(Scanner scanner) {
        System.out.print("Ingrese la cedula del repartidor: ");
        String cedula = scanner.nextLine();
        if (!LPersona.validaCedula(cedula)) {
            System.err.println("Cedula Incorrecta: Ingrese una cedula valida");
            return;
        }

        System.out.print("Ingrese los apellidos del repartidor: ");
        String apellidos = scanner.nextLine();
        System.out.print("Ingrese los nombres del repartidor: ");
        String nombres = scanner.nextLine();
        System.out.print("Ingrese el correo del repartidor: ");
        String email = scanner.nextLine();
        if (!LPersona.validaEmail(email)) {
            System.err.println("Correo Incorrecto: Ingrese un correo valido");
            return;
        }

        System.out.print("Ingrese la ciudad del repartidor: ");
        String ciudad = scanner.nextLine();
        System.out.print("Ingrese la zona del repartidor: ");
        int zona = scanner.nextInt();
        scanner.nextLine();

        em.getTransaction().begin();
        Repartidor repartidor = new Repartidor(zona, ciudad, cedula, apellidos, nombres, email);
        em.persist(repartidor);
        em.getTransaction().commit();

        System.out.println("Repartidor Registrado");
    }

    public static void crearBodeguero(Scanner scanner) {
        System.out.print("Ingrese la cedula del bodeguero: ");
        String cedula = scanner.nextLine();
        if (!LPersona.validaCedula(cedula)) {
            System.err.println("Cedula Incorrecta: Ingrese una cedula valida");
            return;
        }

        System.out.print("Ingrese los apellidos del bodeguero: ");
        String apellidos = scanner.nextLine();
        System.out.print("Ingrese los nombres del bodeguero: ");
        String nombres = scanner.nextLine();
        System.out.print("Ingrese el correo del bodeguero: ");
        String email = scanner.nextLine();
        if (!LPersona.validaEmail(email)) {
            System.err.println("Correo Incorrecto: Ingrese un correo valido");
            return;
        }

        System.out.print("Ingrese la ciudad del bodeguero: ");
        String ciudad = scanner.nextLine();
        System.out.print("Ingrese el local del bodeguero: ");
        String local = scanner.nextLine();

        em.getTransaction().begin();
        Bodeguero bodeguero = new Bodeguero(local, ciudad, cedula, apellidos, nombres, email);
        em.persist(bodeguero);
        em.getTransaction().commit();
        System.out.println("Bodeguero Registrado");

    }
}
