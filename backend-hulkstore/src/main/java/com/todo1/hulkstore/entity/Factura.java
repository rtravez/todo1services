package com.todo1.hulkstore.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * <b> Descripcion de la clase, interface o enumeracion. </b>
 * 
 * @author rene.travez
 * @version $1.0$
 */
@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

	private static final long serialVersionUID = -3445786208951360497L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "observacion")
	private String observacion;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = { "facturas", "handler", "hibernateLazyInitializer" }, allowSetters = true)
	@JoinColumn(name = "empleado_id")
	private Empleado empleado;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
	@JoinColumn(name = "factura_id")
	private List<ItemFactura> items;

	@PrePersist
	public void prePersist() {
		this.createAt = new Date();

	}

	public Factura() {
		items = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@XmlTransient
	public List<ItemFactura> getItems() {
		return items;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

	public void addItem(ItemFactura item) {
		this.items.add(item);
	}

	public Double getTotal() {
		Double total = Double.valueOf(0);
		for (int i = 0; i < items.size(); i++) {
			total += items.get(i).getImporte();
		}
		return total;
	}
}
