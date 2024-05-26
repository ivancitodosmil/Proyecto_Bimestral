package Clases;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Estado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tipo;

    @Basic
    private String estado;
    private String observacion;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne
    private Paquete paquete;

    public Estado() {}

    public Estado(int tipo, String estado, String observacion, Date fecha, Paquete paquete) {
        this.tipo = tipo;
        this.estado = estado;
        this.observacion = observacion;
        this.fecha = fecha;
        this.paquete = paquete;
    }

    public int getTipo() {
        return tipo;
    }

    public String getEstado() {
        return estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    @Override
    public String toString() {
        return "Estado{" + "tipo=" + tipo + ", estado=" + estado + ", observacion=" + observacion + ", fecha=" + fecha + ", paquete=" + paquete + '}';
    }

}
