package com.todo1.hulkstore.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "kardex_detalles")
public class KardexDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "kardex_detalle_id", unique = true, nullable = false)
	private Long kardexDetalleId;

	@Column(nullable = false)
	private Long cantidad;

	// bi-directional many-to-one association to KardexCabecera
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kardex_cabecera_id", nullable = false)
	private KardexCabecera kardexCabecera;

	// bi-directional many-to-one association to Producto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id", nullable = false)
	private Producto producto;

	public KardexDetalle() {
		super();
	}

	public Long getKardexDetalleId() {
		return kardexDetalleId;
	}

	public void setKardexDetalleId(Long kardexDetalleId) {
		this.kardexDetalleId = kardexDetalleId;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public KardexCabecera getKardexCabecera() {
		return this.kardexCabecera;
	}

	public void setKardexCabecera(KardexCabecera kardexCabecera) {
		this.kardexCabecera = kardexCabecera;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}