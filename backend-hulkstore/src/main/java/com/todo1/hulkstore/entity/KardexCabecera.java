package com.todo1.hulkstore.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "kardex_cabeceras")
public class KardexCabecera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "kardex_cabecera_id", unique = true, nullable = false)
	private Long kardexCabeceraId;

	@Column(length = 45)
	private String descripcion;

	@Column(nullable = false, length = 45)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	// bi-directional many-to-one association to TipoOperacion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_operacion_id", nullable = false)
	private TipoOperacion tipoOperacion;

	// bi-directional many-to-one association to KardexDetalle
	@OneToMany(mappedBy = "kardexCabecera")
	private List<KardexDetalle> kardexDetalles;

	public KardexCabecera() {
		super();
	}

	public Long getKardexCabeceraId() {
		return kardexCabeceraId;
	}

	public void setKardexCabeceraId(Long kardexCabeceraId) {
		this.kardexCabeceraId = kardexCabeceraId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public List<KardexDetalle> getKardexDetalles() {
		return this.kardexDetalles;
	}

	public void setKardexDetalles(List<KardexDetalle> kardexDetalles) {
		this.kardexDetalles = kardexDetalles;
	}

	public KardexDetalle addKardexDetalle(KardexDetalle kardexDetalle) {
		getKardexDetalles().add(kardexDetalle);
		kardexDetalle.setKardexCabecera(this);

		return kardexDetalle;
	}

	public KardexDetalle removeKardexDetalle(KardexDetalle kardexDetalle) {
		getKardexDetalles().remove(kardexDetalle);
		kardexDetalle.setKardexCabecera(null);

		return kardexDetalle;
	}

}