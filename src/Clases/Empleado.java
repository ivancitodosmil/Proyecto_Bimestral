package Clases;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Empleado extends Persona implements Serializable {

    @Basic
    private String ciudad;

    public Empleado() {}

    public Empleado(String ciudad, String cedula, String apellidos, String nombres, String email) {
        super(cedula, apellidos, nombres, email);
        this.ciudad = ciudad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Empleado{" + "ciudad=" + ciudad + '}';
    }
}
