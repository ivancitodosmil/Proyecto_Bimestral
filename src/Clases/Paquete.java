package Clases;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Paquete implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idpaq;

    @Basic
    private String codigo;
    private String descripcion;
    private int peso;
    private int alto;

    @OneToMany(mappedBy = "paquete", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Estado> estados;

    @OneToOne(mappedBy = "paquete", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Entrega entrega;

    @ManyToOne
    private Cliente cliente;

    public Paquete() {}

    public Paquete(int idpaq, String codigo, String descripcion, int peso, int alto, Cliente cliente) {
        this.idpaq = idpaq;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.peso = peso;
        this.alto = alto;
        this.cliente = cliente;
    }

    public Paquete(int idpaq, String codigo, String descripcion, int peso, int alto, List<Estado> estados, Entrega entrega, Cliente cliente) {
        this.idpaq = idpaq;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.peso = peso;
        this.alto = alto;
        this.estados = estados;
        this.entrega = entrega;
        this.cliente = cliente;
    }

    public int getIdpaq() {
        return idpaq;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPeso() {
        return peso;
    }

    public int getAlto() {
        return alto;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setIdpaq(int idpaq) {
        this.idpaq = idpaq;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Paquete{" + "idpaq=" + idpaq + ", codigo=" + codigo + ", descripcion=" + descripcion + ", peso=" + peso + ", alto=" + alto + ", estados=" + estados + ", entrega=" + entrega + ", cliente=" + cliente + '}';
    }

}
