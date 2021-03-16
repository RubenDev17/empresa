package com.cev.empresa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Pintor.
 */
@Entity
@Table(name = "pintor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "pintor")
public class Pintor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 0, max = 20)
    @Column(name = "nombre", length = 20)
    private String nombre;

    @Size(min = 0, max = 20)
    @Column(name = "apellidos", length = 20)
    private String apellidos;

    @Column(name = "sueldo")
    private Integer sueldo;

    @Column(name = "experiencia")
    private Integer experiencia;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Pintor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public Pintor apellidos(String apellidos) {
        this.apellidos = apellidos;
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getSueldo() {
        return sueldo;
    }

    public Pintor sueldo(Integer sueldo) {
        this.sueldo = sueldo;
        return this;
    }

    public void setSueldo(Integer sueldo) {
        this.sueldo = sueldo;
    }

    public Integer getExperiencia() {
        return experiencia;
    }

    public Pintor experiencia(Integer experiencia) {
        this.experiencia = experiencia;
        return this;
    }

    public void setExperiencia(Integer experiencia) {
        this.experiencia = experiencia;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pintor)) {
            return false;
        }
        return id != null && id.equals(((Pintor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pintor{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", sueldo=" + getSueldo() +
            ", experiencia=" + getExperiencia() +
            "}";
    }
}
