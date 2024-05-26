package Presentación;

import Clases.Entrega;
import Clases.Estado;
import Clases.Paquete;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class metodosRepartidor {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bimestral_1PU");
    private static EntityManager em = emf.createEntityManager();

    public static void registrarEntregaPaquete(Scanner scanner) {
        System.out.print("Ingrese el id del paquete: ");
        int idPaquete = scanner.nextInt();
        scanner.nextLine();

        Paquete paquete = em.find(Paquete.class, idPaquete);

        if (paquete != null) {
            System.out.println("Paquete encontrado");
            
            System.out.print("Ingrese codigo de entrega: ");
            String codigo = scanner.nextLine();
            System.out.print("Ingrese observacion: ");
            String observacion = scanner.nextLine();
      
            em.getTransaction().begin();
            Entrega entrega = new Entrega();
            entrega.setCodigo(codigo);
            entrega.setFecha(new java.util.Date());
            entrega.setObservacion(observacion);
            entrega.setPaquete(paquete);
            em.persist(entrega);

            Estado estado = new Estado();
            estado.setTipo(3);
            estado.setEstado("Entregado");
            estado.setFecha(new java.util.Date());
            estado.setObservacion("Entregado con exito");
            estado.setPaquete(paquete);

            em.persist(estado);
            paquete.getEstados().add(estado);

            em.merge(paquete);

            em.getTransaction().commit();
            System.out.println("Entrega Registrada");
        } else {
            System.out.println("Paquete no encontrado");
        }
    }
}
