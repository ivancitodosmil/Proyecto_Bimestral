package Clases;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Cliente extends Persona implements Serializable {

    @Basic
    private String celular;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Direccion> direcciones;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Paquete> paquetes;

    public Cliente() {}

    public Cliente(String celular, String cedula, String apellidos, String nombres, String email) {
        super(cedula, apellidos, nombres, email);
        this.celular = celular;
    }
   
    public Cliente(String celular, List<Direccion> direcciones, List<Paquete> paquetes, String cedula, String apellidos, String nombres, String email) {
        super(cedula, apellidos, nombres, email);
        this.celular = celular;
        this.direcciones = direcciones;
        this.paquetes = paquetes;
    }
    
    public String getCelular() {
        return celular;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public List<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public void setPaquetes(List<Paquete> paquetes) {
        this.paquetes = paquetes;
    }

    @Override
    public String toString() {
        return "Cliente{" + "celular=" + celular + ", direcciones=" + direcciones + ", paquetes=" + paquetes + '}';
    }

}
