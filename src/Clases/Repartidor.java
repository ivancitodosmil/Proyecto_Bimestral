package Clases;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class Repartidor extends Empleado {

    @Basic
    private int zona;

    public Repartidor() {}

    public Repartidor(int zona, String ciudad, String cedula, String apellidos, String nombres, String email) {
        super(ciudad, cedula, apellidos, nombres, email);
        this.zona = zona;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    @Override
    public String toString() {
        return "Repartidor{" + "zona=" + zona + '}';
    }
}
