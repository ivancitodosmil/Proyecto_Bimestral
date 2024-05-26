package Clases;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona implements Serializable {

    @Id
    private String cedula;
    @Basic
    private String apellidos;
    private String nombres;
    private String email;

    public Persona() {}

    public Persona(String cedula, String apellidos, String nombres, String email) {
        this.cedula = cedula;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.email = email;
    }

    public String getCedula() {
        return cedula;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public String getEmail() {
        return email;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Persona{" + "cedula=" + cedula + ", apellidos=" + apellidos + ", nombres=" + nombres + ", email=" + email + '}';
    }
}
