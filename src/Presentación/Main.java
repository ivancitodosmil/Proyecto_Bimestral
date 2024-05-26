package Presentación;

import Clases.Bodeguero;
import Clases.Cliente;
import Clases.Repartidor;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *   González Castro Iván Patricio
 *   Guamán González Ana Cristina
 *   Steven Isaac Neira Granda
 *   Solórzano Orellana Jhandry Josué
 */
public class Main {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bimestral_1PU");
    private static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rol;
        do {
            System.out.println("Seleccione su rol:");
            System.out.println("1. Gerente");
            System.out.println("2. Bodeguero");
            System.out.println("3. Repartidor");
            System.out.println("4. Cliente");
            System.out.println("0. Salir");
            System.out.print("ELIGA UNA OPCION: ");
            rol = scanner.nextInt();
            scanner.nextLine();

            switch (rol) {
                case 1:
                    menuGerente(scanner);
                    break;
                case 2:
                    menuBodeguero(scanner);
                    break;
                case 3:
                    menuRepartidor(scanner);
                    break;
                case 4:
                    menuCliente(scanner);
                    break;
                case 0:
                    System.out.println("Programa Finalizado");
                    break;
                default:
                    System.out.println("Eliga una opcion valida");
            }
        } while (rol != 0);
        scanner.close();
    }

    private static void menuGerente(Scanner scanner) {
        System.out.print("Ingrese la clave para acceder al menu de Gerente: ");
        String clave = scanner.nextLine();

        if (!"Gerente2024".equals(clave)) {
            System.out.println("Acceso Denegado. Clave Incorrecta");
            return;
        }

        int opcion;
        do {
            System.out.println("Menu Gerente:");
            System.out.println("1. Crear Repartidor");
            System.out.println("2. Crear Bodeguero");
            System.out.println("0. Volver");
            System.out.print("ELIGA UNA OPCION: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    metodosGerente.crearRepartidor(scanner);
                    break;
                case 2:
                    metodosGerente.crearBodeguero(scanner);
                    break;
                case 0:
                    System.out.println("Regresando al Menu Principal");
                    break;
                default:
                    System.out.println("Eliga una opcion valida");
            }
        } while (opcion != 0);
    }

    private static void menuBodeguero(Scanner scanner) {
        System.out.print("Ingrese su cedula para verificar si es Bodeguero: ");
        String cedulaBodeguero = scanner.nextLine();

        Bodeguero bodeguero = em.find(Bodeguero.class, cedulaBodeguero);

        if (bodeguero == null) {
            System.out.println("No se encuentra registrado como Bodeguero");
            return;
        }

        int opcion;
        do {
            System.out.println("Menu Bodeguero:");
            System.out.println("1. Registrar nuevo cliente");
            System.out.println("2. Registrar Direcciones Para Los Clientes");
            System.out.println("3. Registrar nuevo paquete");
            System.out.println("4. Despachar paquete");
            System.out.println("0. Volver");
            System.out.print("ELIGA UNA OPCION: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    metodosBodeguero.registrarNuevoCliente(scanner);
                    break;
                case 2:
                    metodosBodeguero.registrarDireccionesCliente(scanner);
                    break;
                case 3:
                    metodosBodeguero.registrarNuevoPaquete(scanner);
                    break;
                case 4:
                    metodosBodeguero.despacharPaquete(scanner);
                    break;
                case 0:
                    System.out.println("Regresando al Menu Principal");
                    break;
                default:
                    System.out.println("Eliga una opcion valida");
            }
        } while (opcion != 0);
    }

    private static void menuRepartidor(Scanner scanner) {
        System.out.print("Ingrese su cedula para verificar si es Repartidor ");
        String cedulaRepartidor = scanner.nextLine();

        Repartidor repartidor = em.find(Repartidor.class, cedulaRepartidor);

        if (repartidor == null) {
            System.out.println("No se encuentra registrado como Repartidor");
            return;
        }

        int opcion;
        do {
            System.out.println("Menu Repartidor:");
            System.out.println("1. Registrar entrega de paquete");
            System.out.println("0. Volver");
            System.out.print("ELIGA UNA OPCION: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    metodosRepartidor.registrarEntregaPaquete(scanner);
                    break;
                case 0:
                    System.out.println("Regresando al Menu Principal");
                    break;
                default:
                    System.out.println("Eliga una opcion valida");
            }
        } while (opcion != 0);
    }

    private static void menuCliente(Scanner scanner) {
        System.out.print("Ingrese su cedula para verificar si es cliente: ");
        String cedulaCliente = scanner.nextLine();

        Cliente cliente = em.find(Cliente.class, cedulaCliente);

        if (cliente == null) {
            System.out.println("No se encuentra registrado como Cliente");
            return;
        }

        int opcion;
        do {
            System.out.println("Menu Cliente:");
            System.out.println("1. Revisar estados de sus paquetes");
            System.out.println("2. Agregar nueva direccion y cambiar el estado actual de las que ya tenga");
            System.out.println("0. Volver");
            System.out.print("ELIGA UNA OPCION: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    metodosCliente.revisarEstadosPaquetes(scanner);
                    break;
                case 2:
                    metodosCliente.agregarDireccionYActualizarEstado(scanner);
                    break;
                case 0:
                    System.out.println("Regresando al Menu Principal");
                    break;
                default:
                    System.out.println("Eliga una opcion valida");
            }
        } while (opcion != 0);
    }
}
