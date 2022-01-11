package com.todo1.hulkstore.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_operaciones")
public class TipoOperacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tipo_operacion_id", unique = true, nullable = false)
	private Long tipoOperacionId;

	@Column(length = 45)
	private String descripcion;

	@Column(nullable = false, length = 45)
	private String nombre;

	// bi-directional many-to-one association to KardexCabecera
	@OneToMany(mappedBy = "tipoOperacion")
	private List<KardexCabecera> kardexCabeceras;

	public TipoOperacion() {
		super();
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getTipoOperacionId() {
		return tipoOperacionId;
	}

	public void setTipoOperacionId(Long tipoOperacionId) {
		this.tipoOperacionId = tipoOperacionId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<KardexCabecera> getKardexCabeceras() {
		return this.kardexCabeceras;
	}

	public void setKardexCabeceras(List<KardexCabecera> kardexCabeceras) {
		this.kardexCabeceras = kardexCabeceras;
	}

	public KardexCabecera addKardexCabecera(KardexCabecera kardexCabecera) {
		getKardexCabeceras().add(kardexCabecera);
		kardexCabecera.setTipoOperacion(this);

		return kardexCabecera;
	}

	public KardexCabecera removeKardexCabecera(KardexCabecera kardexCabecera) {
		getKardexCabeceras().remove(kardexCabecera);
		kardexCabecera.setTipoOperacion(null);

		return kardexCabecera;
	}

}