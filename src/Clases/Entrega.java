package Clases;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Entrega implements Serializable {

    @Id
    private String codigo;

    @Basic
    private String observacion;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @OneToOne
    private Paquete paquete;

    public Entrega() {}

    public Entrega(String codigo, String observacion, Date fecha, Paquete paquete) {
        this.codigo = codigo;
        this.observacion = observacion;
        this.fecha = fecha;
        this.paquete = paquete;
    }

    public String getCodigo() {
        return codigo;
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

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
        return "Entrega{" + "codigo=" + codigo + ", observacion=" + observacion + ", fecha=" + fecha + ", paquete=" + paquete + '}';
    }
}
