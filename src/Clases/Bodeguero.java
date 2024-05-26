package Clases;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class Bodeguero extends Empleado {

    @Basic
    private String local;

    public Bodeguero() {}

    public Bodeguero(String local, String ciudad, String cedula, String apellidos, String nombres, String email) {
        super(ciudad, cedula, apellidos, nombres, email);
        this.local = local;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "Bodeguero{" + "local=" + local + '}';
    }
}
